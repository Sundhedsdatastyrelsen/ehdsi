<?xml version="1.0" ?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3">

    <xsl:variable name="healthMaintenanceCarePlanSectionCode"
                  select="'18776-5'"/>

    <!-- health maintenance care plan -->
    <xsl:template name="healthMaintenanceCarePlan" match="/">
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$healthMaintenanceCarePlanSectionCode]]"/>
    </xsl:template>

    <xsl:template match = "/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$healthMaintenanceCarePlanSectionCode]]">
        <div class="wrap-collapsible">
            <input id="collapsible-health-maintenance-care-plan-section" class="toggle" type="checkbox" checked="true" />
            <label for="collapsible-health-maintenance-care-plan-section" class="lbl-toggle-title">
                <!-- Section title -->
                <xsl:call-template name="show-eHDSISection">
                    <xsl:with-param name="code" select="$healthMaintenanceCarePlanSectionCode"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-title">
                <div class="content-inner-title">
                    <div class="wrap-collapsible">
                        <input id="collapsible-health-maintenance-care-plan-original" class="toggle" type="checkbox"/>
                        <label for="collapsible-health-maintenance-care-plan-original" class="lbl-toggle">
                            <xsl:value-of select="$originalNarrativeTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <xsl:apply-templates select="n1:text"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>
</xsl:stylesheet>
