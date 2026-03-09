package dk.sundhedsdatastyrelsen.ncpeh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record SemanticConfig(@Value("${app.semantics.atc-code-system-version}") String atcCodeSystemVersion) {}
