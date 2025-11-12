package dk.sundhedsdatastyrelsen.ncpeh.authentication;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import com.github.benmanes.caffeine.cache.Ticker;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.BootstrapTokenParams;
import dk.sundhedsdatastyrelsen.ncpeh.authentication.bootstraptoken.OpenNcpAssertions;
import org.w3c.dom.Node;

import java.time.Clock;
import java.time.Duration;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthenticationServiceCached implements AuthenticationService {
    // We want to reuse tokens as long as we can, so we don't pull new tokens all the time, but still remove them N
    // seconds before they run out, so we don't risk requests that take a long time not working.
    private static final int SECONDS_BUFFER = 30;

    private final Cache<IdwsCacheKey, EuropeanHcpIdwsToken> idwsCache;
    private final Cache<NspDgwsIdentity, DgwsAssertion> dgwsCache;
    private final AuthenticationService service;
    private final String issuer;

    public AuthenticationServiceCached(
        AuthenticationService service,
        String issuer,
        Clock clock,
        Ticker ticker
    ) {
        this.service = service;
        this.issuer = issuer;
        idwsCache = Caffeine.newBuilder()
            .ticker(ticker)
            .expireAfter(Expiry.writing((k, v) -> Duration.between(
                clock.instant(),
                ((EuropeanHcpIdwsToken) v).expires().minusSeconds(SECONDS_BUFFER))))
            .maximumSize(10000)
            .build();
        dgwsCache = Caffeine.newBuilder()
            .ticker(ticker)
            .expireAfter(Expiry.writing((k, v) -> Duration.between(
                clock.instant(),
                ((DgwsAssertion) v).expiresAt().minusSeconds(SECONDS_BUFFER))))
            .maximumSize(10000)
            .build();
    }

    public AuthenticationServiceCached(AuthenticationService service, String issuer) {
        this(service, issuer, Clock.systemUTC(), Ticker.systemTicker());
    }

    @Override
    public EuropeanHcpIdwsToken xcaSoapHeaderToIdwsToken(
        String soapHeader,
        String audience
    ) throws AuthenticationException {
        // Can't use simple get because of checked exception and lambda.
        var cacheKey = IdwsCacheKey.fromSoapHeader(soapHeader, audience, issuer);
        var cached = idwsCache.getIfPresent(cacheKey);
        if (cached == null) {
            cached = service.xcaSoapHeaderToIdwsToken(soapHeader, audience);
            idwsCache.put(cacheKey, cached);
        }
        return cached;
    }

    @Override
    public DgwsAssertion nspDgwsIdentityToAssertion(NspDgwsIdentity identity) throws AuthenticationException {
        // Can't use simple get because of checked exception and lambda.
        var cached = dgwsCache.getIfPresent(identity);
        if (cached == null) {
            cached = service.nspDgwsIdentityToAssertion(identity);
            dgwsCache.put(identity, cached);
        }
        return cached;
    }

    private record IdwsCacheKey(
        String audience,
        String nameIdFormat,
        String nameId,
        Set<CacheAttribute> attributes
    ) {
        static IdwsCacheKey fromSoapHeader(
            String soapHeader,
            String audience,
            String issuer
        ) throws AuthenticationException {
            var ncpAssertions = OpenNcpAssertions.fromSoapHeader(soapHeader);
            var bstParams = BootstrapTokenParams.fromOpenNcpAssertions(ncpAssertions, audience, issuer);
            return new IdwsCacheKey(
                audience,
                bstParams.nameIdFormat(),
                bstParams.nameId(),
                bstParams.attributes().stream().map(CacheAttribute::new).collect(Collectors.toSet()));
        }
    }

    record CacheAttribute(BootstrapTokenParams.SamlAttribute att) {
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            var that = (CacheAttribute) o;
            return switch (att) {
                case BootstrapTokenParams.SamlAttribute.New n1 ->
                    that.att instanceof BootstrapTokenParams.SamlAttribute.New n2
                        && Objects.equals(n1.name(), n2.name()) && n1.values().equals(n2.values());
                case BootstrapTokenParams.SamlAttribute.Raw r1 ->
                    that.att instanceof BootstrapTokenParams.SamlAttribute.Raw r2
                        && normalized(r1.node()).isEqualNode(normalized(r2.node()));
            };
        }

        @Override
        public int hashCode() {
            return switch (att) {
                case BootstrapTokenParams.SamlAttribute.New n -> Objects.hash(n.name(), n.values());
                // w3c.dom.Node doesn't provide a function to calculate a hash that lives up to the equals of isEqualNode,
                // and it's a big task to write that ourselves, so we opt to just return the same number.
                // Otherwise, we break the contract that equal objects must have the same hash.
                case BootstrapTokenParams.SamlAttribute.Raw ignored -> 1;
            };
        }

        static Node normalized(Node n) {
            var clone = n.cloneNode(true);
            clone.normalize();
            return clone;
        }
    }
}
