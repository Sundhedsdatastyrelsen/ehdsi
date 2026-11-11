<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" version="2.0">

    <xsl:variable name="surgicalProceduresSectionCode"
                  select="'47519-4'"/>

    <!--social histories -->
    <xsl:template name="surgicalProcedures" match="/">
        <xsl:choose>
            <xsl:when test="not(/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$surgicalProceduresSectionCode]])">
                <span class="sectionTitle">
                    <!-- The Procedures section is missing ! -->
                    <xsl:call-template name="show-eHDSIDisplayLabel">
                        <xsl:with-param name="code" select="'76'"/>
                    </xsl:call-template>
                </span>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$surgicalProceduresSectionCode]]"/>
            </xsl:otherwise>
        </xsl:choose>
        <br/>
        <br/>
    </xsl:template>

    <xsl:template match = "/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$surgicalProceduresSectionCode]]">
        <xsl:variable name="surgicalProcedure"
                      select="n1:entry/n1:procedure"/>
        <xsl:variable name="surgicalProcedureCode"
                      select="$surgicalProcedure/n1:code"/>
        <div class="wrap-collapsible">
            <input id="collapsible-surgical-procedures-section-original" class="toggle" type="checkbox" checked="true" />
            <label for="collapsible-surgical-procedures-section-original" class="lbl-toggle-title">
                <!-- Section title -->
                <xsl:call-template name="show-eHDSISection">
                    <xsl:with-param name="code" select="$surgicalProceduresSectionCode"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-title">
                <div class="content-inner-title">
                    <div class="wrap-collapsible">
                        <input id="collapsible-surgical-procedures-original" class="toggle" type="checkbox"/>
                        <label for="collapsible-surgical-procedures-original" class="lbl-toggle">
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
                        <input id="collapsible-surgical-procedures-translated" class="toggle" type="checkbox" checked="true"/>
                        <label for="collapsible-surgical-procedures-translated" class="lbl-toggle">
                            <xsl:value-of select="$translatedCodedTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <xsl:choose>
                                    <xsl:when test="not($surgicalProcedure/@nullFlavor)">
                                        <table class="translation_table">
                                            <tbody>
                                                <xsl:if test="not ($surgicalProcedureCode/@code='no-known-procedures' or $surgicalProcedureCode/@code='no-procedure-info')">
                                                    <tr>
                                                        <th>
                                                            <!--  Procedure Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'62'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                        <th>
                                                            <!--  Body site Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'154'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                        <th>
                                                            <!--  Procedure Date Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'63'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                    </tr>
                                                </xsl:if>
                                                <xsl:apply-templates select="n1:entry/n1:procedure" mode="surgicalprocedures"/>
                                            </tbody>
                                        </table>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <br/>
                                        <xsl:call-template name="show-eHDSINullFlavor">
                                            <xsl:with-param name="code" select="$surgicalProcedure/@nullFlavor"/>
                                        </xsl:call-template>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="n1:entry/n1:procedure" mode="surgicalprocedures">
        <xsl:choose>
            <xsl:when test="not(./@nullFlavor)">
                <xsl:choose>
                    <xsl:when test="(n1:code/@code='no-known-procedures' or n1:code/@code='no-procedure-info')">
                        <tr>
                            <td colspan="2">
                                <xsl:call-template name="show-eHDSIAbsentOrUnknownProcedure">
                                    <xsl:with-param name="node" select="n1:code"/>
                                </xsl:call-template>
                            </td>
                        </tr>
                    </xsl:when>
                    <xsl:otherwise>
                        <tr>
                            <td>
                                <!--  Procedure -->
                                <xsl:call-template name="show-eHDSIProcedure">
                                    <xsl:with-param name="node" select="n1:code"/>
                                </xsl:call-template>
                            </td>
                            <td>
                                <!-- Body site -->
                                <xsl:apply-templates select="n1:targetSiteCode" mode="targetsitecode"/>
                            </td>
                            <td>
                                <!--  Procedure Date -->
                                <xsl:call-template name="show-IVL_TS">
                                    <xsl:with-param name="node" select="n1:effectiveTime"/>
                                </xsl:call-template>
                            </td>
                        </tr>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <tr>
                    <td colspan="3">
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="./@nullFlavor"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="n1:targetSiteCode" mode="targetsitecode">
        <xsl:call-template name="show-eHDSIBodySite">
            <xsl:with-param name="node" select="."/>
        </xsl:call-template>
    </xsl:template>
</xsl:stylesheet>
