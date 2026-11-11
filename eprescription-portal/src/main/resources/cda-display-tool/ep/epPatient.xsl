<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                version="2.0">

    <xsl:template match="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole">
        <div class="wrap-collapsible" >
            <input id="collapsible-patient-header" class="toggle" type="checkbox" checked="true"/>
            <label for="collapsible-patient-header" class="lbl-toggle-main">
                <!-- Patient-->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'51'"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-main">
                <div class="content-inner-main">
                    <table class="header_table">
                        <tbody>
                            <tr>
                                <th>
                                    <!--  Prefix : -->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'55'"/>
                                    </xsl:call-template>
                                </th>
                                <th>
                                    <!--  Family Name : -->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'30'"/>
                                    </xsl:call-template>
                                </th>
                                <th>
                                    <!--  Given Name:-->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'34'"/>
                                    </xsl:call-template>
                                </th>
                                <th>
                                    <!--  Date of Birth: -->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'19'"/>
                                    </xsl:call-template>
                                </th>
                                <th>
                                    <!--  Gender: -->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'33'"/>
                                    </xsl:call-template>
                                </th>
                                <th>
                                    <!--  Regional/National Health ID: -->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'66'"/>
                                    </xsl:call-template>
                                </th>
                                <th>
                                    <!--  National Insurance number: -->
                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                        <xsl:with-param name="code" select="'41'"/>
                                    </xsl:call-template>
                                </th>
                            </tr>
                            <tr>
                                <td>
                                    <!--  Prefix : -->
                                    <xsl:apply-templates select="n1:patient/n1:name/n1:prefix"/>
                                </td>
                                <td>
                                    <!--  Family : -->
                                    <xsl:apply-templates select="n1:patient/n1:name/n1:family"/>
                                </td>
                                <td>
                                    <!--  Given : -->
                                    <xsl:apply-templates select="n1:patient/n1:name/n1:given"/>
                                </td>
                                <td>
                                    <!--  Date of Birth: -->
                                    <xsl:apply-templates select="n1:patient/n1:birthTime"/>
                                </td>
                                <td>
                                    <!--  Gender: -->
                                    <xsl:apply-templates select="n1:patient/n1:administrativeGenderCode"/>
                                </td>
                                <td>
                                    <!--  Regional/National Health ID: -->
                                    <xsl:call-template name="show-patient-id">
                                        <xsl:with-param name="id" select="n1:id[1]"/>
                                    </xsl:call-template>
                                </td>
                                <td>
                                    <!--  National Insurance number: -->
                                    <xsl:call-template name="show-patient-id">
                                        <xsl:with-param name="id" select="n1:id[2]"/>
                                    </xsl:call-template>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <br/>
            <br/>
        </div>
    </xsl:template>

    <xsl:template match="n1:patient/n1:name/n1:prefix">
        <xsl:value-of select="."/>&#160;
    </xsl:template>

    <xsl:template match="n1:patient/n1:name/n1:given">
        <xsl:value-of select="."/>&#160;
    </xsl:template>

    <xsl:template match="n1:patient/n1:name/n1:family">
        <xsl:value-of select="."/>&#160;
    </xsl:template>

    <xsl:template match="n1:patient/n1:birthTime">
        <xsl:call-template name="show-TS">
            <xsl:with-param name="node" select="."/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="n1:patient/n1:administrativeGenderCode">
        <xsl:call-template name="show-eHDSIAdministrativeGender">
            <xsl:with-param name="node" select="."/>
        </xsl:call-template>
    </xsl:template>
</xsl:stylesheet>
