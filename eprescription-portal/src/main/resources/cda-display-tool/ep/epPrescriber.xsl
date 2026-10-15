<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                version="2.0">

    <xsl:template match="/n1:ClinicalDocument/n1:author">
        <div class="wrap-collapsible">
            <input id="collapsible-prescriber-header" class="toggle" type="checkbox"/>
            <label for="collapsible-prescriber-header" class="lbl-toggle-main">
                <!-- Prescriber -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'56'"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-main">
                <div class="content-inner-main">
                    <table class="header_table">
                        <xsl:attribute name="id">
                            <xsl:text>prescriberTable</xsl:text>
                        </xsl:attribute>
                        <tr>
                            <th>
                                <!--  Prescriber Header -->
                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                    <xsl:with-param name="code" select="'56'"/>
                                </xsl:call-template>
                            </th>
                            <th>
                                <!--  Profession Header -->
                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                    <xsl:with-param name="code" select="'64'"/>
                                </xsl:call-template>
                            </th>
                            <th>
                                <!--  Specialty Header -->
                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                    <xsl:with-param name="code" select="'69'"/>
                                </xsl:call-template>
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <!--  Prescriber -->
                                <xsl:apply-templates select="n1:assignedAuthor/n1:assignedPerson/n1:name"/>
                            </td>
                            <td>
                                <!--  Profession -->
                                <xsl:call-template name="show-eHDSIHealthcareProfessionalRole">
                                    <xsl:with-param name="node"
                                                    select="n1:functionCode"/>
                                </xsl:call-template>
                            </td>
                            <td>
                                <!--  Specialty -->
                                <xsl:value-of select="n1:assignedAuthor/n1:code/@displayName"/>
                            </td>
                        </tr>
                        <tr>
                            <div class="extended_header_block">
                                <!--  Contact Information -->
                                <xsl:call-template name="show-contactInformation">
                                    <xsl:with-param name="contactInfoRoot" select="n1:assignedAuthor"/>
                                </xsl:call-template>
                                <!--  Represented Organization -->
                                <xsl:apply-templates select="n1:assignedAuthor/n1:representedOrganization"/>
                            </div>
                        </tr>
                    </table>
                </div>
            </div>
            <br/>
            <br/>
        </div>
    </xsl:template>

    <xsl:template match="n1:assignedAuthor/n1:assignedPerson/n1:name">
        <!--  Prefix : -->
        <xsl:apply-templates select="n1:prefix"/>
        <!--  Family : -->
        <xsl:apply-templates select="n1:given"/>
        <!--  Given : -->
        <xsl:apply-templates select="n1:family"/>
    </xsl:template>

    <xsl:template match="n1:prefix">
        <xsl:value-of select="."/>&#160;
    </xsl:template>

    <xsl:template match="n1:given">
        <xsl:value-of select="."/>&#160;
    </xsl:template>

    <xsl:template match="n1:family">
        <xsl:value-of select="."/>&#160;
    </xsl:template>

    <xsl:template match="n1:assignedAuthor/n1:representedOrganization">
        <tr>
            <td>
                <table class="contact_information_table">
                    <colgroup>
                        <col span="1" style="width: 30%;"/>
                        <col span="1" style="width: 70%;"/>
                    </colgroup>
                    <tr>
                        <th colspan="2">
                            <!-- Represented organization -->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'127'"/>
                            </xsl:call-template>
                        </th>
                    </tr>
                    <tr/>
                    <tr>
                        <th>
                            <!-- Organisation Name Header -->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'47'"/>
                            </xsl:call-template>
                        </th>
                        <td>
                            <!-- Organisation Name -->
                            <xsl:choose>
                                <xsl:when test="n1:name/@nullFlavor">
                                    <xsl:call-template name="show-eHDSINullFlavor">
                                        <xsl:with-param name="code" select="n1:name/@nullFlavor"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="n1:name"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <!--  Organisation Identifier Header -->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'46'"/>
                            </xsl:call-template>
                        </th>
                        <td>
                            <!--  Organisation Identifier -->
                            <xsl:apply-templates select="n1:id"/>
                        </td>
                    </tr>
                    <xsl:call-template name="show-contactInfo">
                        <xsl:with-param name="contact" select="."/>
                    </xsl:call-template>
                </table>
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="n1:id">
        <xsl:choose>
            <xsl:when test="@nullFlavor">
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="@nullFlavor"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="@extension"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
