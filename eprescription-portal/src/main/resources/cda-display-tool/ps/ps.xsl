<?xml version="1.0"  ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" version="2.0">
    <xsl:import href="psHeader.xsl"/>
    <xsl:import href="psExtendedHeader.xsl"/>
    <xsl:import href="psAllergiesAndOtherAdverseReactions.xsl"/>
    <xsl:import href="psCodedResults.xsl"/>
    <xsl:import href="psActiveProblems.xsl"/>
    <xsl:import href="psMedicationSummary.xsl"/>
    <xsl:import href="psMedicalDevices.xsl"/>
    <xsl:import href="psListOfSurgeries.xsl"/>
    <xsl:import href="psHistoryOfPastIllnesses.xsl"/>
    <xsl:import href="psImmunizations.xsl"/>
    <xsl:import href="psHealthMaintenanceCarePlan.xsl"/>
    <xsl:import href="psFunctionalStatus.xsl"/>
    <xsl:import href="psSocialHistory.xsl"/>
    <xsl:import href="psPregnancyHistory.xsl"/>
    <xsl:import href="psVitalSigns.xsl"/>
    <xsl:import href="psAdvanceDirectives.xsl"/>
    <xsl:import href="psOtherSection.xsl"/>

    <xsl:variable name="originalNarrativeTableTitle">
        <!-- Original narrative -->
        <xsl:call-template name="show-eHDSIDisplayLabel">
            <xsl:with-param name="code" select="'108'"/>
        </xsl:call-template>
    </xsl:variable>
    <xsl:variable name="translatedCodedTableTitle">
        <!-- Translated coded -->
        <xsl:call-template name="show-eHDSIDisplayLabel">
            <xsl:with-param name="code" select="'109'"/>
        </xsl:call-template>
    </xsl:variable>

    <xsl:template match="n1:text">
        <div class="narrative_wrapper">
            <xsl:apply-templates select="*"/>
        </div>
    </xsl:template>

    <!-- xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/ -->
    <xsl:template name="psCda">
        <!-- Main -->
        <!-- produce browser rendered, human readable clinical document	-->
        <!-- START display top portion of clinical document -->
        <!--- PATIENT INFORMATION -->
        <div class="patient">
            <div class="wrap-collapsible">
                <input id="collapsible-patient-header" class="toggle" type="checkbox" checked="true"/>
                <label for="collapsible-patient-header" class="lbl-toggle-main">
                    <!-- Patient-->
                    <xsl:call-template name="show-eHDSIDisplayLabel">
                        <xsl:with-param name="code" select="'51'"/>
                    </xsl:call-template>
                </label>
                <div class="collapsible-content-main">
                    <div class="content-inner-main">
                        <div>
                            <xsl:apply-templates select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole" mode="PS"/>
                            <br/>
                            <div class="wrap-collapsible">
                                <input id="collapsible-extended-header" class="toggle" type="checkbox"/>
                                <label for="collapsible-extended-header" class="lbl-toggle">
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'68'"/>
                                    </xsl:call-template>
                                </label>
                                <div class="collapsible-content">
                                    <div class="content-inner">
                                        <div id="extendedCdaHeader">
                                            <xsl:call-template name="extendedCdaHeader"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br/>
        <div class="content">
            <!--- BASIC HEADER INFORMATION -->
            <xsl:call-template name="basicCdaHeader"/>
            <br/>
            <div class="wrap-collapsible">
                <input id="collapsible-clinical-sections" class="toggle" type="checkbox" checked="true"/>
                <label for="collapsible-clinical-sections" class="lbl-toggle-main">
                    <!-- Clinical sections -->
                    <xsl:call-template name="show-eHDSIDisplayLabel">
                        <xsl:with-param name="code" select="'110'"/>
                    </xsl:call-template>
                </label>
                <div class="collapsible-content-main">
                    <div class="content-inner-main">
                        <!-- eHDSI Allergies and Other Adverse Reactions -->
                        <xsl:call-template name="allergiesAndIntolerances"/>
                        <!-- eHDSI Coded Results -->
                        <xsl:call-template name="codedResults"/>
                        <!-- eHDSI Active Problems -->
                        <xsl:call-template name="activeProblems"/>
                        <!-- eHDSI Medication Summary -->
                        <xsl:call-template name="medicationSummary"/>
                        <!-- eHDSI Medical Devices -->
                        <xsl:call-template name="medicalDevices"/>
                        <!-- eHDSI List of Surgeries -->
                        <xsl:call-template name="surgicalProcedures"/>
                        <!-- eHDSI History of Past Illnesses -->
                        <xsl:call-template name="historyOfPastIllnesses"/>
                        <!-- eHDSI Immunizations -->
                        <xsl:call-template name="immunizations"/>
                        <!-- eHDSI Health Maintenance Care Plan -->
                        <xsl:call-template name="healthMaintenanceCarePlan"/>
                        <!-- eHDSI Functional Status -->
                        <xsl:call-template name="functionalStatus"/>
                        <!-- eHDSI Social History -->
                        <xsl:call-template name="socialHistory"/>
                        <!-- eHDSI Pregnancy History -->
                        <xsl:call-template name="pregnancyHistory"/>
                        <!-- eHDSI Vital Signs -->
                        <xsl:call-template name="vitalSigns"/>
                        <!-- eHDSI Vital Signs -->
                        <xsl:call-template name="advanceDirectives"/>
                        <!-- All other sections -->
                        <xsl:call-template name="otherSections"/>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>
    <!-- generate table of contents -->
</xsl:stylesheet>
