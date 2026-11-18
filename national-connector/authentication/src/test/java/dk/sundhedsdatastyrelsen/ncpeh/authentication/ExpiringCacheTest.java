package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import com.github.benmanes.caffeine.cache.Ticker;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class ExpiringCacheTest {
    final ClockStub clock = new ClockStub(Instant.parse("2022-06-11T11:12:13Z"));
    final TickerStub ticker = new TickerStub();
    final ExpiringCache<String, Instant> cache = new ExpiringCache<>(clock, ticker, Function.identity());

    int loads = 0;

    /// Loads a value that expires 120 seconds from now.
    final Supplier<Instant> loader = () -> {
        loads++;
        return clock.instant().plusSeconds(120);
    };

    @Test
    void reusesFreshValue() {
        var value = cache.get("key", loader);
        assertThat(cache.get("key", loader), is(sameInstance(value)));
        assertThat(loads, is(1));
    }

    @Test
    void loadsPerKey() {
        cache.get("key", loader);
        cache.get("another key", loader);
        assertThat(loads, is(2));
    }

    @Test
    void evicts30SecsBeforeOnTicker() {
        var value = cache.get("key", loader);
        ticker.advance(89);
        assertThat(cache.get("key", loader), is(sameInstance(value)));
        ticker.advance(1);
        assertThat(cache.get("key", loader), is(not(sameInstance(value))));
        assertThat(loads, is(2));
    }

    @Test
    void evicts30SecsBeforeOnWallClockWhenTickerStandsStill() {
        // Simulates host suspend: the wall clock advances, but System.nanoTime doesn't.
        var value = cache.get("key", loader);
        clock.advance(89);
        assertThat(cache.get("key", loader), is(sameInstance(value)));
        clock.advance(1);
        assertThat(cache.get("key", loader), is(not(sameInstance(value))));
        assertThat(loads, is(2));
    }

    static class TickerStub implements Ticker {
        long value;

        @Override
        public long read() {
            // nanoseconds
            return value;
        }

        public void advance(long seconds) {
            this.value += (seconds * 1000000000);
        }
    }

    static class ClockStub extends Clock {
        Instant instant;

        ClockStub(Instant instant) {
            this.instant = instant;
        }

        void advance(long seconds) {
            instant = instant.plusSeconds(seconds);
        }

        @Override
        public Instant instant() {
            return instant;
        }

        @Override
        public ZoneId getZone() {
            return ZoneOffset.UTC;
        }

        @Override
        public Clock withZone(ZoneId zone) {
            throw new UnsupportedOperationException();
        }
    }
}
