<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                version="2.0">

    <xsl:output method="html" indent="yes" version="4.01" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
                doctype-public="-//W3C//DTD HTML 4.01//EN"/>

    <!-- Global variables -->
    <xsl:param name="userLang" select="'en-GB'"/>
    <xsl:param name="epsosLangDir" select="'../EpsosRepository'"/>
    <xsl:param name="normalizedEpsosLangDir" select="translate($epsosLangDir, '\', '/')"/>
    <xsl:param name="defaultUserLang" select="'en-GB'"/>

    <!-- eHDSIAbsentOrUnknownAllergy -->
    <xsl:template name="show-eHDSIAbsentOrUnknownAllergy">
        <xsl:param name="node"/>
        <div class="tooltip-right">
            <xsl:choose>
                <xsl:when test="($node/@code='no-allergy-info')">
                    <!-- There is no information available regarding the subject's allergy conditions -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'133'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
                <xsl:when test="($node/@code='no-known-allergies')">
                    <!-- The subject has no known allergy conditions -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'134'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
                <xsl:when test="($node/@code='no-known-medication-allergies')">
                    <!-- The subject has no known medication allergy conditions -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'135'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
                <xsl:when test="($node/@code='no-known-environmental-allergies')">
                    <!-- The subject has no known environmental allergy conditions -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'136'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
                <xsl:when test="($node/@code='no-known-food-allergies')">
                    <!-- The subject has no known food allergy conditions -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'137'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
            </xsl:choose>
            <xsl:call-template name="show-codedElement">
                <xsl:with-param name="node" select="$node"/>
                <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.47.xml'"/>
            </xsl:call-template>
        </div>
    </xsl:template>

    <!-- eHDSIAbsentOrUnknownDevice -->
    <xsl:template name="show-eHDSIAbsentOrUnknownDevice">
        <xsl:param name="node"/>
        <div class="tooltip-right">
            <xsl:choose>
                <xsl:when test="($node/@code='no-device-info')">
                    <!-- There is no information available regarding implanted or external devices for the subject -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'138'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
                <xsl:when test="($node/@code='no-known-devices')">
                    <!-- There are no devices known to be implanted in or used by the subject that have to be reported in this record. This can mean either that there are none known, or that those known are not relevant for the purpose of this record -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'139'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
            </xsl:choose>
            <xsl:call-template name="show-codedElement">
                <xsl:with-param name="node" select="$node"/>
                <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.48.xml'"/>
            </xsl:call-template>
        </div>
    </xsl:template>

    <!-- eHDSIAbsentOrUnknownMedication -->
    <xsl:template name="show-eHDSIAbsentOrUnknownMedication">
        <xsl:param name="node"/>
        <div class="tooltip-right">
            <xsl:choose>
                <xsl:when test="($node/@code='no-medication-info')">
                    <!-- There is no information available about the subject's medication use or administration -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'140'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
                <xsl:when test="($node/@code='no-known-medications')">
                    <!-- There are no medications for the subject that have to be reported in this record. This can mean either that there are none known, or that those known are not relevant for the purpose of this record -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'141'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
            </xsl:choose>
            <xsl:call-template name="show-codedElement">
                <xsl:with-param name="node" select="$node"/>
                <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.49.xml'"/>
            </xsl:call-template>
        </div>
    </xsl:template>

    <!-- eHDSIAbsentOrUnknownProblem -->
    <xsl:template name="show-eHDSIAbsentOrUnknownProblem">
        <xsl:param name="node"/>
        <div class="tooltip-right">
            <xsl:choose>
                <xsl:when test="($node/@code='no-problem-info')">
                    <!-- There is no information available about the subject's current health problems or disability -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'142'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
                <xsl:when test="($node/@code='no-known-problems')">
                    <!-- The subject is not known to have any health problems or disabilities that have to be reported in this record. This can mean either that there are none known, or that those known are not relevant for the purpose of this record -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'143'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
            </xsl:choose>
            <xsl:call-template name="show-codedElement">
                <xsl:with-param name="node" select="$node"/>
                <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.50.xml'"/>
            </xsl:call-template>
        </div>
    </xsl:template>

    <!-- eHDSIAbsentOrUnknownProcedure -->
    <xsl:template name="show-eHDSIAbsentOrUnknownProcedure">
        <xsl:param name="node"/>
        <div class="tooltip-right">
            <xsl:choose>
                <xsl:when test="($node/@code='no-procedure-info')">
                    <!-- There is no information available about the subject's past history of procedures -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'144'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
                <xsl:when test="($node/@code='no-known-procedures')">
                    <!-- The subject has no history of procedures that have to be reported in this record. This can mean either that there are none known, or that those known are not relevant for the purpose of this record -->
                    <span class="tooltiptext">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'145'"/>
                        </xsl:call-template>
                    </span>
                </xsl:when>
            </xsl:choose>
            <xsl:call-template name="show-codedElement">
                <xsl:with-param name="node" select="$node"/>
                <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.51.xml'"/>
            </xsl:call-template>
        </div>
    </xsl:template>

    <!-- eHDSIActiveIngredient -->
    <xsl:template name="show-eHDSIActiveIngredient">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.24.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIAdministrativeGender -->
    <xsl:template name="show-eHDSIAdministrativeGender">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.34.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIAdverseEventType -->
    <xsl:template name="show-eHDSIAdverseEventType">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.18.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIAllergenNoDrug -->
    <xsl:template name="show-eHDSIAllergenNoDrug">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.19.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIAllergyStatus -->
    <xsl:template name="show-eHDSIAllergyStatus">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.59.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIBloodGroup -->
    <xsl:template name="show-eHDSIBloodGroup">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.20.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIBloodPressure -->
    <xsl:template name="show-eHDSIBloodPressure">
        <xsl:param name="code"/>
        <xsl:call-template name="show-code-value">
            <xsl:with-param name="code" select="$code"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.21.xml'"/>
            <xsl:with-param name="codeSystem" select="'2.16.840.1.113883.6.1'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIBodySite -->
    <xsl:template name="show-eHDSIBodySite">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.65.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSICertainty -->
    <xsl:template name="show-eHDSICertainty">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.58.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSICodeProb -->
    <xsl:template name="show-eHDSICodeProb">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.23.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSICountry -->
    <xsl:template name="show-eHDSICountry">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.4.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSICriticality -->
    <xsl:template name="show-eHDSICriticality">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.57.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSICurrentPregnancyStatus -->
    <xsl:template name="show-eHDSICurrentPregnancyStatus">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.60.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIDisplayLabel -->
    <xsl:template name="show-eHDSIDisplayLabel">
        <xsl:param name="code"/>
        <xsl:variable name="dirFile"
                      select="concat($normalizedEpsosLangDir,'/1.3.6.1.4.1.12559.11.10.1.3.1.42.46.xml')"/>
        <xsl:variable name="foundKey"
                      select="document($dirFile)/ValueSet/concept[@code=$code]"/>
        <xsl:variable name="foundKeyLang"
                      select="$foundKey/designation[@lang=$userLang]"/>
        <xsl:variable name="defFoundKeyLang"
                      select="$foundKey/designation[@lang=$defaultUserLang]"/>
        <xsl:choose>
            <xsl:when test="not ($foundKey)">
                <xsl:value-of select="$code"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="not ($foundKeyLang)">
                        <xsl:value-of select="$defFoundKeyLang"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$foundKeyLang"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- eHDSIDocumentCode -->
    <xsl:template name="show-eHDSIDocumentCode">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.32.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIDoseForm -->
    <xsl:template name="show-eHDSIDoseForm">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.2.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIHealthcareProfessionalRole -->
    <xsl:template name="show-eHDSIHealthcareProfessionalRole">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.1.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIIllnessesandDisorder -->
    <xsl:template name="show-eHDSIIllnessandDisorder">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.5.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSILabCode -->
    <xsl:template name="show-eHDSILabCode">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.70.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSILabSpecimenType -->
    <xsl:template name="show-eHDSILabSpecimenType">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.67.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSILanguage -->
    <xsl:template name="show-eHDSILanguage">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.6.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIMedicalDevice -->
    <xsl:template name="show-eHDSIMedicalDevice">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.8.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIMicroorganism -->
    <xsl:template name="show-eHDSIMicroorganism">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.78.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIMorphologicAbnormality -->
    <xsl:template name="show-eHDSIMorphologicAbnormality">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.69.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSINullFlavor -->
    <xsl:template name="show-eHDSINullFlavor">
        <xsl:param name="code"/>
        <div class="tooltip-right">
            <i class="fas fa-exclamation-circle" style="color:#085a9f" aria-hidden="true"/>
            <span class="tooltiptext">
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'146'"/>
                </xsl:call-template>
            </span>
        </div>
        <xsl:text> </xsl:text>
        <xsl:call-template name="show-code-value">
            <xsl:with-param name="code" select="$code"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.37.xml'"/>
            <xsl:with-param name="codeSystem" select="'2.16.840.1.113883.5.1008'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIObservationCategory -->
    <xsl:template name="show-eHDSIObservationCategory">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.71.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIObservationInterpretation -->
    <xsl:template name="show-eHDSIObservationInterpretation">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.73.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIOutcomeOfPregnancy -->
    <xsl:template name="show-eHDSIOutcomeOfPregnancy">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.62.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIPackage -->
    <xsl:template name="show-eHDSIPackage">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.3.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIPersonalRelationship -->
    <xsl:template name="show-eHDSIPersonalRelationship">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.38.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIPregnancyInformation -->
    <xsl:template name="show-eHDSIPregnancyInformation">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.9.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="show-eHDSIPresenceAbsence">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.77.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIProcedure -->
    <xsl:template name="show-eHDSIProcedure">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.10.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIRareDiseases -->
    <xsl:template name="show-eHDSIRareDiseases">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.63.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIReactionAllergy -->
    <xsl:template name="show-eHDSIReactionAllergy">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.11.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIReferenceRangeAppliesTo -->
    <xsl:template name="show-eHDSIReferenceRangeAppliesTo">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.75.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIReferenceRangeMeaning -->
    <xsl:template name="show-eHDSIReferenceRangeMeaning">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.74.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIResolutionOutcome -->
    <xsl:template name="show-eHDSIResolutionOutcome">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.30.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIRoleClass -->
    <xsl:template name="show-eHDSIRoleClass">
        <xsl:param name="code"/>
        <xsl:call-template name="show-code-value">
            <xsl:with-param name="code" select="$code"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.39.xml'"/>
            <xsl:with-param name="codeSystem" select="'2.16.840.1.113883.5.110'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIRouteOfAdministration -->
    <xsl:template name="show-eHDSIRouteOfAdministration">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.12.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSISection -->
    <xsl:template name="show-eHDSISection">
        <xsl:param name="code"/>
        <xsl:call-template name="show-code-value">
            <xsl:with-param name="code" select="$code"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.26.xml'"/>
            <xsl:with-param name="codeSystem" select="'2.16.840.1.113883.6.1'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSISeverity -->
    <xsl:template name="show-eHDSISeverity">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.13.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSISpecimenCollectionMethod -->
    <xsl:template name="show-eHDSISpecimenCollectionMethod">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.94.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSISocialHistory -->
    <xsl:template name="show-eHDSISocialHistory">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.14.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIStatusCode -->
    <xsl:template name="show-eHDSIStatusCode">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.15.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSISubstance -->
    <xsl:template name="show-eHDSISubstance">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.61.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSISubstitutionCode -->
    <xsl:template name="show-eHDSISubstitutionCode">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.7.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSITelecomAddress -->
    <xsl:template name="show-eHDSITelecomAddress">
        <xsl:param name="code"/>
        <xsl:call-template name="show-code-value">
            <xsl:with-param name="code" select="$code"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.40.xml'"/>
            <xsl:with-param name="codeSystem" select="'2.16.840.1.113883.5.1119'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSITimingEvent -->
    <xsl:template name="show-eHDSITimingEvent">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.41.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIUnit -->
    <xsl:template name="show-eHDSIUnit">
        <xsl:param name="code"/>
        <xsl:call-template name="show-code-value">
            <xsl:with-param name="code" select="$code"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.16.xml'"/>
            <xsl:with-param name="codeSystem" select="'2.16.840.1.113883.6.8'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- eHDSIVaccine -->
    <xsl:template name="show-eHDSIVaccine">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'1.3.6.1.4.1.12559.11.10.1.3.1.42.28.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <!-- v3.ConfidentialityClassification -->
    <xsl:template name="show-v3ConfidentialityClassification">
        <xsl:param name="node"/>
        <xsl:call-template name="show-codedElement">
            <xsl:with-param name="node" select="$node"/>
            <xsl:with-param name="xmlFile" select="'2.16.840.1.113883.1.11.10228.xml'"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="show-codedElement">
        <xsl:param name="node"/>
        <xsl:param name="xmlFile"/>

        <xsl:choose>
            <xsl:when test="not($node/@nullFlavor)">
                <xsl:choose>
                    <xsl:when test="$node/@code">
                        <xsl:call-template name="show-code-value">
                            <xsl:with-param name="code" select="$node/@code"/>
                            <xsl:with-param name="xmlFile" select="$xmlFile"/>
                            <xsl:with-param name="codeSystem" select="$node/@codeSystem"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <!-- narrative -->
                        <xsl:if test="$node/n1:originalText/n1:reference/@value">
                            <xsl:call-template name="show-uncodedElement">
                                <xsl:with-param name="code"
                                                select="$node/n1:originalText/n1:reference/@value"/>
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="handle-nullFlavor">
                    <xsl:with-param name="node" select="$node"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="handle-nullFlavor">
        <xsl:param name="node"/>
        <xsl:choose>
            <xsl:when test="$node/@nullFlavor='OTH'">
                <div class="tooltip-right">
                    <i class="fas fa-exclamation-circle" style="color:#085a9f" aria-hidden="true"/>
                    <span class="tooltiptext">
                        <!-- Unmapped concept. Nationally used concept for which no translations are available and no safe mappings are possible. -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'153'"/>
                        </xsl:call-template>
                    </span>
                </div>
                <xsl:text> </xsl:text>
                <i><xsl:value-of select="$node/n1:translation/@displayName"/></i>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="$node/@nullFlavor"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- display translated code value -->
    <xsl:template name="show-code-value" match="/n1:ValueSet/n1:concept">
        <xsl:param name="code"/>
        <xsl:param name="xmlFile"/>
        <xsl:param name="codeSystem"/>
        <xsl:variable name="dirFile"
                      select="concat($normalizedEpsosLangDir,'/', $xmlFile)"/>
        <xsl:variable name="foundKey"
                      select="document($dirFile)/ValueSet/concept[@code=$code and @codeSystem=$codeSystem]"/>
        <xsl:variable name="foundKeyLang" select="$foundKey/designation[@lang=$userLang]"/>
        <xsl:variable name="defFoundKeyLang" select="$foundKey/designation[@lang=$defaultUserLang]"/>
        <xsl:choose>
            <xsl:when test="not ($foundKeyLang)">
                <xsl:value-of select="$defFoundKeyLang"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$foundKeyLang"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
