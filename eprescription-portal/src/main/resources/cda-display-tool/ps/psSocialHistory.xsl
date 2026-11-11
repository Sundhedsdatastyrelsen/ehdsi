<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3">

    <xsl:variable name="socialHistorySectionCode"
                  select="'29762-2'"/>

    <!-- eHDSI Social History -->
    <xsl:template name="socialHistory" match="/">
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$socialHistorySectionCode]]"/>
    </xsl:template>

    <xsl:template match = "/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$socialHistorySectionCode]]">
        <div class="wrap-collapsible">
            <input id="collapsible-social-history-section-original" class="toggle" type="checkbox" checked="true" />
            <label for="collapsible-social-history-section-original" class="lbl-toggle-title">
                <!-- Section title -->
                <xsl:call-template name="show-eHDSISection">
                    <xsl:with-param name="code" select="$socialHistorySectionCode"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-title">
                <div class="content-inner-title">
                    <div class="wrap-collapsible">
                        <input id="collapsible-social-history-original" class="toggle" type="checkbox"/>
                        <label for="collapsible-social-history-original" class="lbl-toggle">
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
                        <input id="collapsible-social-history-translated" class="toggle" type="checkbox" checked="true"/>
                        <label for="collapsible-social-history-translated" class="lbl-toggle">
                            <xsl:value-of select="$translatedCodedTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <table class="translation_table">
                                    <tbody>
                                        <tr>
                                            <th>
                                                <!-- Observation Type -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'44'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!-- Duration  -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'155'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!--  Observation Value -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'84'"/>
                                                </xsl:call-template>
                                            </th>
                                        </tr>
                                        <xsl:apply-templates select="n1:entry/n1:observation" mode="socialhistory"/>
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

    <xsl:template match="n1:entry/n1:observation" mode="socialhistory">
        <tr>
            <td>
                <!-- Observation Type -->
                <xsl:apply-templates select="n1:code"/>
            </td>
            <td>
                <!-- Duration -->
                <xsl:apply-templates select="n1:effectiveTime"/>
            </td>
            <td>
                <!-- Observation Value -->
                <xsl:apply-templates select="n1:value" mode="socialhistoryobservationvalue"/>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="n1:code">
        <xsl:call-template name="show-eHDSISocialHistory">
            <xsl:with-param name="node" select="."/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="n1:value" mode="socialhistoryobservationvalue">
        <xsl:call-template name="show-PQ">
            <xsl:with-param name="node" select="."/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="n1:effectiveTime">
        <xsl:call-template name="show-IVL_TS">
            <xsl:with-param name="node" select="."/>
        </xsl:call-template>
    </xsl:template>
</xsl:stylesheet>