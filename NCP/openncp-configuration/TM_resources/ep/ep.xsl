<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:n1="urn:hl7-org:v3"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:epsos="urn:epsos-org:ep:medication"
                xmlns:foo="newValue"
                version="1.0">

    <xsl:import href="../common/cdaCommon.xsl"/>
    <xsl:import href="epHeader.xsl"/>
    <xsl:import href="epPrescriptionItem.xsl"/>
    <xsl:import href="epPrescriberDetails.xsl"/>

    <xsl:output method="html" indent="yes" version="4.01" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
                doctype-public="-//W3C//DTD HTML 4.01//EN"/>

    <xsl:variable
            name="givenName"
            select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:name/n1:given"/>

    <xsl:variable
            name="familyName"
            select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:name/n1:family"/>

    <xsl:variable
            name="patientPrefix"
            select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:name/n1:prefix"/>

    <xsl:variable
            name="primaryPatientID"
            select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:id[1]/@extension"/>

    <xsl:variable
            name="secondaryPatientID"
            select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:id[2]/@extension"/>

    <xsl:variable
            name="dateOfDescription"
            select="/n1:ClinicalDocument/n1:author/n1:time/@value"/>

    <xsl:variable
            name="prescriptionID"
            select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.2.1']/n1:id/@extension"/>


    <xsl:variable
            name="prescriptionItemID"
            select="//n1:entry/n1:substanceAdministration[n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.2']]/n1:id/@extension"/>

    <xsl:variable
            name="prescriberPrefix"
            select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:prefix"/>


    <xsl:variable
            name="prescriberFamilyName"
            select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:family"/>

    <xsl:variable
            name="prescriberGivenName"
            select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:given"/>

    <xsl:variable
            name="birthDate"
            select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:birthTime"/>

    <xsl:variable
            name="entryNode"
            select="//n1:entry/n1:substanceAdministration[n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.2']]"/>


    <xsl:template name="show-package">
        <xsl:param name="medPackage"/>
        <xsl:choose>
            <xsl:when test="($medPackage/@nullFlavor)">
                <xsl:call-template name="show-noneFlavor">
                    <xsl:with-param name="data" select="($medPackage)/@nullFlavor"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="($medPackage)/@value"/>
                <xsl:choose>
                    <xsl:when test="($medPackage/@unit)='1'">
                        <xsl:call-template name="show-displayLabels">
                            <xsl:with-param name="data" select="'77'"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text> [</xsl:text>
                        <xsl:value-of select="($medPackage)/@unit"/>
                        <xsl:text>]</xsl:text>
                    </xsl:otherwise>
                </xsl:choose>

            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>


    <xsl:template name="prescriberName">
        <xsl:choose>
            <xsl:when test="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:prefix">
                <xsl:value-of
                        select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:prefix"/>
                &#160;
            </xsl:when>
        </xsl:choose>
        <xsl:choose>
            <xsl:when test="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:given">
                <xsl:value-of
                        select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:given"/>
                &#160;
            </xsl:when>
        </xsl:choose>
        <xsl:choose>
            <xsl:when test="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:family">
                <xsl:value-of
                        select="/n1:ClinicalDocument/n1:author/n1:assignedAuthor/n1:assignedPerson/n1:name/n1:family"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>


    <xsl:template name="show-text">
        <xsl:param name="txt"/>
        <xsl:choose>
            <xsl:when test="($txt/n1:reference)">
                <xsl:variable name="val" select="substring-after($txt/n1:reference/@value,'#')"/>
                <xsl:value-of
                        select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:text[@ID=$val]"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$txt"/>
            </xsl:otherwise>
        </xsl:choose>

    </xsl:template>


    <xsl:template name="show-formCode">
        <xsl:param name="parameter"/>
        <xsl:choose>
            <xsl:when
                    test="$parameter/n1:originalText/n1:reference/@value">
                <xsl:call-template name="show-uncodedElement">
                    <xsl:with-param name="data" select="$parameter/n1:originalText/n1:reference/@value"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>

                <xsl:choose>
                    <xsl:when test="not($parameter/@nullFlavor)">
                        <xsl:value-of select="$parameter/@displayName"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="show-noneFlavor">
                            <xsl:with-param name="data" select="$parameter/@nullFlavor"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>


    <xsl:template name="epCda">

        <xsl:call-template name="firstRow"/>
    </xsl:template>


    <xsl:template name="firstRow">
        <table>
            <tr>
                <td>
                    <xsl:call-template name="prefixCell"/>
                </td>
            </tr>
        </table>
    </xsl:template>


    <xsl:template name="prefixCell">
        <xsl:call-template name="epHeader"/>

        <table style="width : 100%">
            <tr>
                <td width="35%">
                    <xsl:call-template name="show-displayLabels">
                        <xsl:with-param name="data" select="'56'"/>
                    </xsl:call-template>
                    <xsl:call-template name="prescriberName"/>

                </td>
                <td>
                    <xsl:call-template name="show-displayLabels">
                        <xsl:with-param name="data" select="'64' "/>
                    </xsl:call-template>
                    <xsl:call-template name="show-formCode">
                        <xsl:with-param name="parameter" select="/n1:ClinicalDocument/n1:author/n1:functionCode"/>
                    </xsl:call-template>
                </td>
                <td/>
            </tr>
        </table>


        <table style="width : 100%" cols="3">
            <tr>
                <td width="35%">
                    <xsl:call-template name="show-displayLabels">
                        <xsl:with-param name="data" select="'58'"/>
                    </xsl:call-template>
                    <xsl:value-of select="$prescriptionID"/>
                </td>
                <td>
                    <span class="td_label">
                        <xsl:call-template name="show-displayLabels">
                            <xsl:with-param name="data" select="'20'"/>
                        </xsl:call-template>
                    </span>
                    <xsl:call-template name="show-time">
                        <xsl:with-param name="datetime" select="/n1:ClinicalDocument/n1:author/n1:time"/>
                    </xsl:call-template>
                </td>
            </tr>
        </table>

        <xsl:call-template name="epPrescriberDetails"/>
        <xsl:call-template name="epPrescriptionItem"/>

    </xsl:template>


</xsl:stylesheet>
