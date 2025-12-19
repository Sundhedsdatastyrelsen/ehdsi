package dk.sundhedsdatastyrelsen.ncpeh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record PatientSummaryConfig(
    @Value("${app.patient-summary.enabled:true}") boolean enabled
) {
}
