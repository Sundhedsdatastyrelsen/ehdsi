<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                xmlns:pharm="urn:hl7-org:pharm"
                version="2.0">

    <xsl:import href="epPatient.xsl"/>
    <xsl:import href="epPrescriber.xsl"/>
    <xsl:import href="epPrescriptionItem.xsl"/>

    <xsl:output method="html" indent="yes" version="4.01" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
                doctype-public="-//W3C//DTD HTML 4.01//EN"/>

    <xsl:variable name="dateOfDescription"
                  select="/n1:ClinicalDocument/n1:author/n1:time/@value"/>

    <xsl:variable name="entryNode"
                  select="//n1:entry/n1:substanceAdministration[n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.2']]"/>

    <xsl:variable name="activeIngredient"
                  select="//n1:entry/n1:substanceAdministration/n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:ingredient[@classCode='ACTI']"/>

    <xsl:variable name="strength"
                  select="$activeIngredient/pharm:quantity"/>


    <xsl:template name="show-fpn">
        <xsl:param name="value"/>
        <xsl:value-of select="translate($value, '.', ',')"/>
    </xsl:template>

    <xsl:template name="show-package">
        <xsl:param name="medPackage"/>
        <xsl:param name="showValue"/>
        <xsl:choose>
            <xsl:when test="($medPackage/@nullFlavor)">
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="($medPackage)/@nullFlavor"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="$showValue">
                        <xsl:value-of select="translate(($medPackage)/@value, '.', ',')"/>
                    </xsl:when>
                </xsl:choose>
                <xsl:text> </xsl:text>
                <xsl:choose>
                    <xsl:when test="($medPackage/@unit)='1'">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'77'"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="not($medPackage/@unit)">
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'77'"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="supportUCUMAnnotations">
                            <xsl:with-param name="value" select="$medPackage/@unit"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="show-text">
        <xsl:param name="txt"/>
        <xsl:choose>
            <xsl:when test="$txt/n1:reference/@value">
                <xsl:variable name="val" select="substring-after($txt/n1:reference/@value,'#')"/>
                <xsl:value-of
                        select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:text//*[@ID=$val]"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$txt"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="show-manufacturedMaterialStrength">
        <xsl:param name="parameter"/>
        <xsl:choose>
            <xsl:when test="not($parameter/@nullFlavor)">
                <xsl:value-of select="$parameter"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="$parameter/@nullFlavor"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="show-formCode">
        <xsl:param name="parameter"/>
        <xsl:choose>
            <xsl:when
                    test="$parameter/n1:originalText/n1:reference/@value">
                <xsl:call-template name="show-uncodedElement">
                    <xsl:with-param name="code" select="$parameter/n1:originalText/n1:reference/@value"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="not($parameter/@nullFlavor)">
                        <xsl:value-of select="$parameter/@displayName"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="$parameter/@nullFlavor"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="epCda">
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole"/>
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:author"/>
        <xsl:call-template name="epInformation"/>
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.2.1']/n1:entry"/>
    </xsl:template>

    <xsl:template name="epInformation">
        <table class="header_table">
            <tbody>
                <tr class="td_creation_date">
                    <th>
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'58'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:value-of select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.2.1']/n1:id/@extension"/>
                    </td>
                    <th>
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'20'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="/n1:ClinicalDocument/n1:author/n1:time"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </tbody>
        </table>
        <br/>
    </xsl:template>
</xsl:stylesheet>