<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                version="1.0">

    <xsl:output method="html" indent="yes" version="4.01" encoding="ISO-8859-1"
                doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>

    <xsl:template name="epHeader">
        <xsl:for-each select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole">
            <table class="header_table">
                <tbody>
                    <tr>
                        <th>
                            <!--  Prefix : -->
                            <xsl:call-template name="show-displayLabels">
                                <xsl:with-param name="data" select="'55'"/>
                            </xsl:call-template>
                        </th>
                        <th>
                            <!--  Family Name : -->
                            <xsl:call-template name="show-displayLabels">
                                <xsl:with-param name="data" select="'30'"/>
                            </xsl:call-template>
                        </th>
                        <th>
                            <!--  Given Name:-->
                            <xsl:call-template name="show-displayLabels">
                                <xsl:with-param name="data" select="'34'"/>
                            </xsl:call-template>
                        </th>
                        <th>
                            <!--  Date of Birth: -->
                            <xsl:call-template name="show-displayLabels">
                                <xsl:with-param name="data" select="'19'"/>
                            </xsl:call-template>
                        </th>
                        <th>
                            <!--  Gender: -->
                            <xsl:call-template name="show-displayLabels">
                                <xsl:with-param name="data" select="'33'"/>
                            </xsl:call-template>
                        </th>
                        <th>
                            <!--   Regional/National Health ID: -->
                            <xsl:call-template name="show-displayLabels">
                                <xsl:with-param name="data" select="'66'"/>
                            </xsl:call-template>
                        </th>
                        <th>
                            <!--  National Insurance number: -->
                            <xsl:call-template name="show-displayLabels">
                                <xsl:with-param name="data" select="'41'"/>
                            </xsl:call-template>
                        </th>
                    </tr>
                    <tr>
                        <td>
                            <xsl:value-of select="n1:patient/n1:name/n1:prefix"/>
                        </td>
                        <td>
                            <xsl:value-of select="n1:patient/n1:name/n1:family"/>
                        </td>
                        <td>
                            <xsl:value-of select="n1:patient/n1:name/n1:given"/>
                        </td>
                        <td>
                            <xsl:call-template name="show-time">
                                <xsl:with-param name="datetime" select="n1:patient/n1:birthTime"/>
                            </xsl:call-template>
                        </td>
                        <td>
                            <xsl:for-each select="n1:patient/n1:administrativeGenderCode">
                                <xsl:call-template name="show-gender"/>
                            </xsl:for-each>
                        </td>
                        <td>
                            <xsl:value-of select="n1:id[1]/@extension"/>
                        </td>
                        <td>
                            <xsl:value-of select="n1:id[2]/@extension"/>
                        </td>
                    </tr>
                </tbody>
            </table>
        </xsl:for-each>
    </xsl:template>
</xsl:stylesheet>