<?xml version="1.0" encoding="UTF-8"?>
<!--
    The portal's additions to the CDA display tool. This is the stylesheet CdaDisplayTransformer runs: it imports the
    upstream cda.xsl, which stays as it is upstream, and overrides templates in it (see README.md).
-->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                version="2.0">

    <xsl:import href="cda.xsl"/>

    <!--
        Upstream's epCda only shows the coded content, while the Patient Summary shows each section's narrative in an
        "Original narrative" panel. This adds such a panel for the ePrescription, collapsed, below the coded content.

        A template that overrides a named template can't call it, so the first four lines are a copy of upstream's
        epCda (ep/ep.xsl). CdaDisplayTransformerTest checks that they still match.
    -->
    <xsl:template name="epCda">
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole"/>
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:author"/>
        <xsl:call-template name="epInformation"/>
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.2.1']/n1:entry"/>
        <!-- End of the copy of upstream's epCda -->

        <xsl:variable name="sections" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:text]"/>
        <xsl:if test="$sections">
            <br/>
            <!-- The same markup as the Patient and Prescriber panels -->
            <div class="wrap-collapsible">
                <input id="collapsible-original-narrative" class="toggle" type="checkbox"/>
                <label for="collapsible-original-narrative" class="lbl-toggle-main">
                    <xsl:value-of select="$originalNarrativeTableTitle"/>
                </label>
                <div class="collapsible-content-main">
                    <div class="content-inner-main">
                        <!-- The n1:text template from ps/ps.xsl, which renders the narrative block -->
                        <xsl:apply-templates select="$sections/n1:text"/>
                    </div>
                </div>
            </div>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>
