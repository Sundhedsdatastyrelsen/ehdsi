<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3">

    <xsl:variable name="pregnancyHistorySectionCode"
                  select="'10162-6'"/>

    <!-- pregnancy history -->
    <xsl:template name="pregnancyHistory" match="/">
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$pregnancyHistorySectionCode]]"/>
    </xsl:template>

    <xsl:template match = "/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$pregnancyHistorySectionCode]]">
        <div class="wrap-collapsible">
            <input id="collapsible-pregnancy-history-section-original" class="toggle" type="checkbox" checked="true" />
            <label for="collapsible-pregnancy-history-section-original" class="lbl-toggle-title">
                <!-- Section title -->
                <xsl:call-template name="show-eHDSISection">
                    <xsl:with-param name="code" select="$pregnancyHistorySectionCode"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-title">
                <div class="content-inner-title">
                    <div class="wrap-collapsible">
                        <input id="collapsible-pregnancy-history-original" class="toggle" type="checkbox"/>
                        <label for="collapsible-pregnancy-history-original" class="lbl-toggle">
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
                        <input id="collapsible-pregnancy-history-translated" class="toggle" type="checkbox" checked="true"/>
                        <label for="collapsible-pregnancy-history-translated" class="lbl-toggle">
                            <xsl:value-of select="$translatedCodedTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <table class="translation_table">
                                    <tbody>
                                        <xsl:choose>
                                            <xsl:when test="not(n1:entry/n1:observation/n1:code[@code='82810-3']/../@nullFlavor)">
                                                <tr>
                                                    <th>
                                                        <!-- Current Pregnancy Status Header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'174'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Observation Date Header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'175'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Status Header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'176'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Pregnancy observation code -->
                                                        <xsl:call-template name="show-eHDSIPregnancyInformation">
                                                            <xsl:with-param name="node" select="n1:entry/n1:observation/n1:code[@code='82810-3']/../n1:entryRelationship[@typeCode='COMP']/n1:observation/n1:code"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <xsl:apply-templates select="n1:entry/n1:observation/n1:code[@code='82810-3']" mode="currentPregnancies"/>
                                                </tr>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <tr>
                                                    <td>
                                                        <xsl:call-template name="show-eHDSINullFlavor">
                                                            <xsl:with-param name="code" select="n1:entry/n1:observation/n1:code[@code='82810-3']/../@nullFlavor"/>
                                                        </xsl:call-template>
                                                    </td>
                                                </tr>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </tbody>
                                </table>
                                <table class="translation_table">
                                    <tbody>
                                        <tr>
                                            <th>
                                                <!-- History of Previous Pregnancies Header -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'177'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!-- Outcome Header -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'178'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!-- Number of Children Header -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'179'"/>
                                                </xsl:call-template>
                                            </th>
                                        </tr>
                                        <xsl:apply-templates select="n1:entry/n1:observation/n1:code[@code!='93857-1'][@code!='82810-3']" mode="outcomeobservations"/>
                                    </tbody>
                                </table>
                                <table class="translation_table">
                                    <tbody>
                                        <xsl:choose>
                                            <xsl:when test="not(n1:entry/n1:observation/n1:code[@code='93857-1']/../@nullFlavor)">
                                                <tbody>
                                                    <tr>
                                                        <th>
                                                            <!-- Outcome Dates Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'180'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                    </tr>
                                                    <xsl:apply-templates select="n1:entry/n1:observation/n1:code[@code='93857-1']" mode="outcomedates"/>
                                                </tbody>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <tr>
                                                    <td>
                                                        <xsl:call-template name="show-eHDSINullFlavor">
                                                            <xsl:with-param name="code" select="n1:entry/n1:observation/n1:code[@code='93857-1']/../@nullFlavor"/>
                                                        </xsl:call-template>
                                                    </td>
                                                </tr>
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
        <br />
        <br />
    </xsl:template>

    <xsl:template match="n1:entry/n1:observation/n1:code[@code='82810-3']" mode="currentPregnancies">
        <tr>
            <td/>
            <td>
                <!-- Observation Date -->
                <xsl:call-template name="show-IVL_TS">
                    <xsl:with-param name="node" select="../n1:effectiveTime"/>
                </xsl:call-template>
            </td>
            <td>
                <!-- Status -->
                <xsl:choose>
                    <xsl:when test="not(../n1:value/@nullFlavor)">
                        <xsl:call-template name="show-eHDSICurrentPregnancyStatus">
                            <xsl:with-param name="node" select="../n1:value"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="../n1:value/@nullFlavor"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <!-- Delivery date estimated -->
                <xsl:call-template name="show-TS">
                    <xsl:with-param name="node" select="../n1:entryRelationship[@typeCode='COMP']/n1:observation/n1:value"/>
                </xsl:call-template>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="n1:entry/n1:observation/n1:code[@code!='93857-1'][@code!='82810-3']" mode="outcomeobservations">
        <tr>
            <td/>
            <td>
                <!-- Outcome -->
                <xsl:call-template name="show-eHDSIOutcomeOfPregnancy">
                    <xsl:with-param name="node" select="../n1:code"/>
                </xsl:call-template>
            </td>
            <td>
                <!-- Number of children -->
                <xsl:choose>
                    <xsl:when test="not(../n1:value/@nullFlavor)">
                        <xsl:value-of select="../n1:value/@value"/>
                    </xsl:when>
                <xsl:otherwise>
                    <xsl:call-template name="show-eHDSINullFlavor">
                        <xsl:with-param name="code" select="../n1:value/@nullFlavor"/>
                    </xsl:call-template>
                </xsl:otherwise>
                </xsl:choose>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="n1:entry/n1:observation/n1:code[@code='93857-1']" mode="outcomedates">
        <tr>
            <td>
                <!-- Outcome date -->
                <xsl:call-template name="show-TS">
                    <xsl:with-param name="node" select="../n1:value"/>
                </xsl:call-template>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>