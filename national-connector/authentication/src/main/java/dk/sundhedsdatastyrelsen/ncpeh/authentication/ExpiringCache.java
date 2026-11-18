package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.Ticker;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Function;
import java.util.function.Supplier;

/// A cache of values with expiration times.
///
/// Caffeine suspends when the system sleeps, so entries can outlive their
/// expiry. Values are therefore also checked against the wall clock on every get, so they never outlive either.
/// See https://github.com/ben-manes/caffeine/issues/2032
///
/// If we only checked against the wall clock, a user changing the clock or clock changes could end up causing cached
/// expired tokens, so we check against both.
///
/// In a live system, we should never need this, as it doesn't suspend, but it doesn't hurt.
class ExpiringCache<K, V> {
    // We want to reuse tokens as long as we can, so we don't pull new tokens all the time, but still remove them N
    // seconds before they run out, so we don't risk requests that take a long time not working.
    private static final int SECONDS_BUFFER = 30;

    private final Cache<K, V> cache;
    private final Function<V, Instant> getExpirationTime;
    private final Clock clock;

    ExpiringCache(Clock clock, Ticker ticker, Function<V, Instant> getExpirationTime) {
        this.clock = clock;
        this.getExpirationTime = getExpirationTime;
        this.cache = Caffeine.newBuilder()
            .ticker(ticker)
            .expireAfter(Expiry.<K, V>writing((k, v) -> Duration.between(clock.instant(), bufferedExpirationTime(v))))
            .maximumSize(10000)
            .build();
    }

    V get(K key, Supplier<V> loader) {
        var value = cache.getIfPresent(key);
        if (value != null && isValidAgainstWallClock(value)) {
            return value;
        }
        // Check again under the key's lock, as another thread may have loaded a fresh value meanwhile.
        // We can't use `.asMap().compute()` as the base case, as that locks the key and caffeine treats it as a write
        // even when nothing was updated.
        return cache.asMap().compute(key, (k, v) -> v != null && isValidAgainstWallClock(v) ? v : loader.get());
    }

    private boolean isValidAgainstWallClock(V value) {
        return clock.instant().isBefore(bufferedExpirationTime(value));
    }

    private Instant bufferedExpirationTime(V value) {
        return getExpirationTime.apply(value).minusSeconds(SECONDS_BUFFER);
    }
}
