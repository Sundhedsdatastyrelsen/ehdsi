<?xml version="1.0"  ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" version="2.0">

    <xsl:variable name="vitalSignsSectionCode"
                  select="'8716-3'"/>

    <!-- vital signs -->
    <xsl:template name="vitalSigns" match="/">
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$vitalSignsSectionCode]]"/>
    </xsl:template>

    <xsl:template match = "/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$vitalSignsSectionCode]]">
        <xsl:variable
                name="systolicObservation"
                select="n1:entry/n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8480-6']/.."/>
        <xsl:variable
                name="diastolicObservation"
                select="n1:entry/n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8462-4']/.."/>
        <xsl:variable
                name="systolicDate"
                select="$systolicObservation/n1:effectiveTime/@value"/>
        <xsl:variable
                name="diastolicDate"
                select="$diastolicObservation/n1:effectiveTime/@value"/>
        <xsl:variable
                name="physicalFindingsOrganizerDate"
                select="n1:entry/n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:effectiveTime"/>
        <div class="wrap-collapsible">
            <input id="collapsible-vital-signs-section-original" class="toggle" type="checkbox" checked="true" />
            <label for="collapsible-vital-signs-section-original" class="lbl-toggle-title">
                <!-- Section title -->
                <xsl:call-template name="show-eHDSISection">
                    <xsl:with-param name="code" select="$vitalSignsSectionCode"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-title">
                <div class="content-inner-title">
                    <xsl:choose>
                        <xsl:when test="(not($physicalFindingsOrganizerDate/@value = $systolicDate) or not($physicalFindingsOrganizerDate/@value = $diastolicDate))">
                            <table>
                                <tr>
                                    <td style="background-color:#ffffcc">
                                        <i class="fas fa-exclamation-triangle" style="color:#085a9f" aria-hidden="true"/>
                                        <!-- The date of the organizer should match the date of the observations -->
                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                            <xsl:with-param name="code" select="'151'"/>
                                        </xsl:call-template>
                                        <br/>
                                    </td>
                                </tr>
                            </table>
                        </xsl:when>
                    </xsl:choose>
                    <div class="wrap-collapsible">
                        <input id="collapsible-vital-signs-original" class="toggle" type="checkbox"/>
                        <label for="collapsible-vital-signs-original" class="lbl-toggle">
                            <xsl:value-of select="$originalNarrativeTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <xsl:apply-templates select="n1:text"/>
                            </div>
                        </div>
                    </div>
                    <br/>
                    <div class="wrap-collapsible">
                        <input id="collapsible-vital-signs-translated" class="toggle" type="checkbox" checked="true"/>
                        <label for="collapsible-vital-signs-translated" class="lbl-toggle">
                            <xsl:value-of select="$translatedCodedTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <table class="translation_table">
                                    <tbody>
                                        <tr>
                                            <th>
                                                <!-- Date -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'17'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!-- Systolic blood pressure -->
                                                <xsl:call-template name="show-eHDSIBloodPressure">
                                                    <xsl:with-param name="code" select="'8480-6'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!-- Diastolic blood pressure -->
                                                <xsl:call-template name="show-eHDSIBloodPressure">
                                                    <xsl:with-param name="code" select="'8462-4'"/>
                                                </xsl:call-template>
                                            </th>
                                        </tr>
                                        <xsl:apply-templates select="n1:entry/n1:organizer"/>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br />
        <br />
    </xsl:template>

    <xsl:template match="n1:entry/n1:organizer">
        <xsl:variable
                name="systolicValue"
                select="n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8480-6']/../n1:value"/>
        <xsl:variable
                name="diastolicValue"
                select="n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8462-4']/../n1:value"/>
        <xsl:choose>
            <xsl:when test="not(n1:observation/@nullFlavor)">
                <tr>
                    <td>
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="n1:effectiveTime"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <xsl:call-template name="show-PQ">
                            <xsl:with-param name="node" select="$systolicValue"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <xsl:call-template name="show-PQ">
                            <xsl:with-param name="node" select="$diastolicValue"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </xsl:when>
            <xsl:otherwise>
                <tr>
                    <td colspan="3">
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="n1:observation/@nullFlavor"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>