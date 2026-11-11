<?xml version="1.0"  ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" version="2.0">

    <xsl:variable name="medicalDevicesSectionCode"
                  select="'46264-8'"/>

    <!-- medical devices -->
    <xsl:template name="medicalDevices" match="/">
        <xsl:choose>
            <xsl:when test="not(/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$medicalDevicesSectionCode]])">
                <span class="sectionTitle">
                    <!-- The Active Problem section is missing ! -->
                    <xsl:call-template name="show-eHDSIDisplayLabel">
                        <xsl:with-param name="code" select="'74'"/>
                    </xsl:call-template>
                </span>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$medicalDevicesSectionCode]]"/>
            </xsl:otherwise>
        </xsl:choose>
        <br/>
        <br/>
    </xsl:template>

    <xsl:template match = "/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$medicalDevicesSectionCode]]">
        <xsl:variable name="medDevAct" select="n1:entry/n1:supply"/>
        <xsl:variable name="playingDeviceCode" select="$medDevAct/n1:participant/n1:participantRole/n1:playingDevice/n1:code"/>
        <div class="wrap-collapsible">
            <input id="collapsible-medical-devices-section-original" class="toggle" type="checkbox" checked="true" />
            <label for="collapsible-medical-devices-section-original" class="lbl-toggle-title">
                <!-- Section title -->
                <xsl:call-template name="show-eHDSISection">
                    <xsl:with-param name="code" select="$medicalDevicesSectionCode"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-title">
                <div class="content-inner-title">
                    <div class="wrap-collapsible">
                        <input id="collapsible-medical-devices-original" class="toggle" type="checkbox"/>
                        <label for="collapsible-medical-devices-original" class="lbl-toggle">
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
                        <input id="collapsible-medical-devices-translated" class="toggle" type="checkbox" checked="true"/>
                        <label for="collapsible-medical-devices-translated" class="lbl-toggle">
                            <xsl:value-of select="$translatedCodedTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <table class="translation_table">
                                    <tbody>
                                        <xsl:choose>
                                            <xsl:when test="($playingDeviceCode/@code='no-known-devices' or $playingDeviceCode/@code='no-device-info')">
                                                <tr>
                                                    <td colspan="2">
                                                        <xsl:call-template name="show-eHDSIAbsentOrUnknownDevice">
                                                            <xsl:with-param name="node" select="$playingDeviceCode"/>
                                                        </xsl:call-template>
                                                    </td>
                                                </tr>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <tr>
                                                    <th>
                                                        <!-- Device Implant -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'21'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Device ID -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'203'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Implant Date -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'36'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                </tr>
                                                <xsl:apply-templates select="n1:entry/n1:supply"/>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="n1:entry/n1:supply">
        <xsl:variable name="medDeviceImplant"
                      select="n1:participant[@typeCode='DEV']/n1:participantRole/n1:playingDevice/n1:code"/>
        <xsl:variable name="medDeviceId"
                      select="n1:participant[@typeCode='DEV']/n1:participantRole/n1:id"/>
        <xsl:variable name="medDeviceImplantDate"
                      select="n1:effectiveTime"/>
        <xsl:choose>
            <xsl:when test="not(n1:supply/@nullFlavor)">

                <tr>
                    <td>
                        <!-- Device Implant -->
                        <xsl:call-template name="show-eHDSIMedicalDevice">
                            <xsl:with-param name="node" select="$medDeviceImplant"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <!-- Device ID -->
                        <xsl:apply-templates select="n1:participant[@typeCode='DEV']/n1:participantRole/n1:id"/>
                    </td>
                    <td>
                        <!-- Implant Date -->
                        <xsl:call-template name="show-IVL_TS">
                            <xsl:with-param name="node" select="$medDeviceImplantDate"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </xsl:when>
            <xsl:otherwise>
                <tr>
                    <td colspan="2">
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="n1:supply/@nullFlavor"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="n1:participant[@typeCode='DEV']/n1:participantRole/n1:id">
        <xsl:call-template name="show-id">
            <xsl:with-param name="id" select="."/>
        </xsl:call-template>
        &#160;
    </xsl:template>

</xsl:stylesheet>
