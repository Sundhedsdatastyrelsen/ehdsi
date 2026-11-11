<?xml version="1.0"  ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" version="2.0">
    <xsl:output method="html" indent="yes" version="4.01" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
                doctype-public="-//W3C//DTD HTML 4.01//EN"/>

    <xsl:template name="basicCdaHeader">
        <xsl:variable name="creationDate"
                      select="/n1:ClinicalDocument/n1:effectiveTime"/>
        <xsl:variable name="lastUpdate"
                      select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:effectiveTime/n1:high"/>
        <xsl:variable name="documentLanguageCode" select="/n1:ClinicalDocument/n1:languageCode"/>
        <table class="header_table">
            <tbody>
                <tr class="td_creation_date">
                    <th>
                        <!-- Creation Date of the Document -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'131'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="$creationDate"/>
                        </xsl:call-template>
                    </td>
                    <th>
                        <!-- Last Update of the Information-->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'132'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="$lastUpdate"/>
                        </xsl:call-template>
                    </td>
                    <th>
                        <!-- Original Document Language -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'117'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:call-template name="show-eHDSILanguage">
                            <xsl:with-param name="node" select="$documentLanguageCode"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </tbody>
        </table>
    </xsl:template>

    <xsl:template match="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole" mode="PS">
        <table class="header_table">
            <tbody>
                <tr>
                    <table class="header_table">
                        <tbody>
                            <tr>
                                <th colspan="2">
                                    <!-- Prefix-->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'55'"/>
                                    </xsl:call-template>
                                </th>
                                <th colspan="2">
                                    <!-- Family Name-->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'30'"/>
                                    </xsl:call-template>
                                </th>
                                <th>
                                    <!-- Given Name-->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'34'"/>
                                    </xsl:call-template>
                                </th>
                            </tr>
                            <tr>
                                <td colspan="2"><xsl:apply-templates select="n1:patient/n1:name/n1:prefix"/></td>
                                <td colspan="2"><xsl:apply-templates select="n1:patient/n1:name/n1:family"/></td>
                                <td><xsl:apply-templates select="n1:patient/n1:name/n1:given"/></td>
                            </tr>
                            <tr>
                                <th style="width:140px;">
                                    <!-- Primary Patient Identifier -->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'125'"/>
                                    </xsl:call-template>
                                </th>
                                <td>
                                    <xsl:call-template name="show-patient-id">
                                        <xsl:with-param name="id" select="n1:id[1]"/>
                                    </xsl:call-template>
                                </td>
                                <xsl:if test="n1:id[2]">
                                    <th style="width:140px;">
                                        <!-- Secondary Patient Identifier -->
                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                            <xsl:with-param name="code" select="'126'"/>
                                        </xsl:call-template>
                                    </th>
                                    <td>
                                        <xsl:call-template name="show-patient-id">
                                            <xsl:with-param name="id" select="n1:id[2]"/>
                                        </xsl:call-template>
                                    </td>
                                </xsl:if>
                            </tr>
                            <tr>
                                <th style="width:140px;">
                                    <!-- Gender-->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'33'"/>
                                    </xsl:call-template>
                                </th>
                                <td>
                                    <xsl:call-template name="show-eHDSIAdministrativeGender">
                                        <xsl:with-param name="node" select="n1:patient/n1:administrativeGenderCode"/>
                                    </xsl:call-template>
                                </td>
                                <th style="width:140px;">
                                    <!-- Date Of Birth-->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'19'"/>
                                    </xsl:call-template>
                                </th>
                                <td>
                                    <xsl:call-template name="show-TS">
                                        <xsl:with-param name="node" select="n1:patient/n1:birthTime"/>
                                    </xsl:call-template>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </tr>
            </tbody>
        </table>
    </xsl:template>

    <xsl:template match="n1:patient/n1:name/n1:prefix">
        <xsl:value-of select="."/>&#160;
    </xsl:template>

    <xsl:template match="n1:patient/n1:name/n1:family">
        <xsl:value-of select="."/>&#160;
    </xsl:template>

    <xsl:template match="n1:patient/n1:name/n1:given">
        <xsl:value-of select="."/>&#160;
    </xsl:template>
</xsl:stylesheet>