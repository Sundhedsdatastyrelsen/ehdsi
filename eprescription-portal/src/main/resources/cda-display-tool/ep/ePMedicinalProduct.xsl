<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                xmlns:pharm="urn:hl7-org:pharm"
                version="2.0">

    <xsl:template name="medicinalProductBlock">
        <xsl:variable name="manufacturedMaterialStrength"
                      select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:desc"/>
        <fieldset style="min-height:100px;">
            <legend>
                <!-- Medicinal Product -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'128'"/>
                </xsl:call-template>
            </legend>
            <table class="ep_table">
                <tr>
                    <th>
                        <!-- Medicinal Product Identifier Header -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'184'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:value-of select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:code/@code"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <!-- Pharmaceutical Product Identifier Header -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'186'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:value-of select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:asSpecializedKind/pharm:generalizedMaterialKind/pharm:code[@codeSystem!='2.16.840.1.113883.6.73']/@code"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <!-- Active Ingredient -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'1'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:call-template name="show-active-ingredients">
                            <xsl:with-param name="manufacturedMaterial" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial"/>
                        </xsl:call-template>
                    </td>
                </tr>
                <tr>
                    <th>
                        <!-- Brand Name -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'9'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:value-of select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:name"/>
                    </td>
                </tr>
                <xsl:if test="n1:consumable/n1:manufacturedProduct/n1:manufacturerOrganization">
                    <tr>
                        <th>
                            <!-- Marketing Authorization Holder -->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'124'"/>
                            </xsl:call-template>
                        </th>
                        <td>
                            <xsl:value-of select="n1:consumable/n1:manufacturedProduct/n1:manufacturerOrganization/n1:name"/>
                        </td>
                    </tr>
                </xsl:if>
                <xsl:if test="$manufacturedMaterialStrength">
                    <tr>
                        <th>
                            <!-- Strength -->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'70'"/>
                            </xsl:call-template>
                        </th>
                        <td>
                            <xsl:call-template name="show-manufacturedMaterialStrength">
                                <xsl:with-param name="parameter"
                                                select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:desc"/>
                            </xsl:call-template>
                        </td>
                    </tr>
                </xsl:if>
                <tr>
                    <th>
                        <!--  Dose Form -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'25'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <xsl:call-template name="show-formCode">
                            <xsl:with-param name="parameter"
                                            select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:formCode"/>
                        </xsl:call-template>
                    </td>
                </tr>
                <tr>
                    <th>
                        <!-- Route of Administration Header -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'67'"/>
                        </xsl:call-template>
                    </th>
                    <td>
                        <!-- Route of Administration -->
                        <xsl:call-template name="show-eHDSIRouteOfAdministration">
                            <xsl:with-param name="node" select="n1:routeCode"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </table>
        </fieldset>
    </xsl:template>

    <xsl:template name="show-active-ingredients">
        <xsl:param name="manufacturedMaterial" />
        <table class="ingredients_table" width="100%">
            <xsl:choose>
                <xsl:when test="$manufacturedMaterial/pharm:part">
                    <colgroup>
                        <col span="1" style="width: 15%;"/>
                        <col span="1" style="width: 15%;"/>
                        <col span="1" style="width: 10%;"/>
                        <col span="1" style="width: 25%;"/>
                        <col span="1" style="width: 35%;"/>
                    </colgroup>
                </xsl:when>
                <xsl:otherwise>
                    <colgroup>
                        <col span="1" style="width: 15%;"/>
                        <col span="1" style="width: 10%;"/>
                        <col span="1" style="width: 40%;"/>
                        <col span="1" style="width: 35%;"/>
                    </colgroup>
                </xsl:otherwise>
            </xsl:choose>

            <tbody>
                <tr>
                    <xsl:if test="$manufacturedMaterial/pharm:part">
                        <th/>
                    </xsl:if>
                    <th>
                        <!-- Code System -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'112'"/>
                        </xsl:call-template>
                    </th>
                    <th>
                        <!-- Code -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'113'"/>
                        </xsl:call-template>
                    </th>
                    <th>
                        <!-- Name -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'114'"/>
                        </xsl:call-template>
                    </th>
                    <th>
                        <!-- Strength -->
                        <xsl:call-template name="show-eHDSIDisplayLabel">
                            <xsl:with-param name="code" select="'70'"/>
                        </xsl:call-template>
                    </th>
                </tr>
                <xsl:choose>
                    <xsl:when test="$manufacturedMaterial/pharm:part">
                        <xsl:for-each select="$manufacturedMaterial/pharm:part">
                            <xsl:variable name="partNumber" select="position()"/>
                            <xsl:for-each select="pharm:partProduct/pharm:ingredient">
                                <tr>
                                    <th>
                                        <xsl:if test="position()=1">
                                            <xsl:text>part </xsl:text>
                                            <xsl:value-of select="$partNumber"/>
                                        </xsl:if>
                                    </th>
                                    <xsl:call-template name="show-active-ingredient">
                                        <xsl:with-param name="code"
                                                        select="pharm:ingredientSubstance/pharm:code"/>
                                        <xsl:with-param name="name"
                                                        select="pharm:ingredientSubstance/pharm:name"/>
                                        <xsl:with-param name="strength"
                                                        select="pharm:quantity"/>
                                    </xsl:call-template>
                                </tr>
                            </xsl:for-each>
                        </xsl:for-each>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:for-each select="$manufacturedMaterial/pharm:ingredient">
                            <tr>
                                <xsl:call-template name="show-active-ingredient">
                                    <xsl:with-param name="code"
                                                    select="pharm:ingredientSubstance/pharm:code"/>
                                    <xsl:with-param name="name"
                                                    select="pharm:ingredientSubstance/pharm:name"/>
                                    <xsl:with-param name="strength"
                                                    select="pharm:quantity"/>
                                </xsl:call-template>
                            </tr>
                        </xsl:for-each>
                    </xsl:otherwise>
                </xsl:choose>
            </tbody>
        </table>
    </xsl:template>
</xsl:stylesheet>