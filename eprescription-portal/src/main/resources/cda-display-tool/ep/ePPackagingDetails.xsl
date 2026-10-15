<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                xmlns:pharm="urn:hl7-org:pharm"
                version="2.0">

    <xsl:template name="packagingDetailsBlock">
        <fieldset style="min-height:100px;">
            <legend>
                <!-- Packaging Details -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'191'"/>
                </xsl:call-template>
            </legend>
            <table class="ep_table">
                <tbody>
                    <xsl:variable name="asContent_level1" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:asContent"/>
                    <xsl:variable name="asContent_level2" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent"/>
                    <xsl:variable name="asContent_level3" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent"/>
                    <tr>
                        <th>
                            <!-- Medicinal Product Package Identifier Header -->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'185'"/>
                            </xsl:call-template>
                        </th>
                        <td>
                            <xsl:choose>
                                <xsl:when test="$asContent_level3">
                                    <xsl:value-of select="$asContent_level3/pharm:containerPackagedProduct/pharm:code/@code"/>
                                </xsl:when>
                                <xsl:when test="$asContent_level2">
                                    <xsl:value-of select="$asContent_level2/pharm:containerPackagedProduct/pharm:code/@code"/>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="$asContent_level1/pharm:containerPackagedProduct/pharm:code/@code"/>
                                </xsl:otherwise>
                            </xsl:choose>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <!-- Number of Packages -->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'43'"/>
                            </xsl:call-template>
                        </th>
                        <td>
                            <xsl:call-template name="number-of-packages">
                                <xsl:with-param name="supply" select="n1:entryRelationship[@typeCode='COMP']"/>
                            </xsl:call-template>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <!-- Package Size Header -->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'50'"/>
                            </xsl:call-template>
                        </th>
                        <td>
                            <xsl:call-template name="show-package-size">
                                <xsl:with-param name="asContent_level1" select="$asContent_level1"/>
                                <xsl:with-param name="asContent_level2" select="$asContent_level2"/>
                                <xsl:with-param name="asContent_level3" select="$asContent_level3"/>
                            </xsl:call-template>
                        </td>
                    </tr>
                    <xsl:if test="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:part">
                        <table class="ep_table" width="100%">
                            <colgroup>
                                <col span="1" style="width: 15%;"/>
                                <col span="1" style="width: 15%;"/>
                                <col span="1" style="width: 10%;"/>
                                <col span="1" style="width: 20%;"/>
                                <col span="1" style="width: 20%;"/>
                                <col span="1" style="width: 20%;"/>
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th/>
                                    <th>
                                        <!-- Code Header -->
                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                            <xsl:with-param name="code" select="'113'"/>
                                        </xsl:call-template>
                                    </th>
                                    <th>
                                        <!-- Name Header -->
                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                            <xsl:with-param name="code" select="'114'"/>
                                        </xsl:call-template>
                                    </th>
                                    <th>
                                        <!-- Description Header -->
                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                            <xsl:with-param name="code" select="'192'"/>
                                        </xsl:call-template>
                                    </th>
                                    <th>
                                        <!-- Form Code Header -->
                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                            <xsl:with-param name="code" select="'25'"/>
                                        </xsl:call-template>
                                    </th>
                                    <th>
                                        <!-- package Size Header -->
                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                            <xsl:with-param name="code" select="'50'"/>
                                        </xsl:call-template>
                                    </th>
                                </tr>
                                <xsl:apply-templates select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:part" mode="packaging"/>
                            </tbody>
                        </table>
                    </xsl:if>
                </tbody>
            </table>
        </fieldset>
    </xsl:template>

    <xsl:template match="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:part" mode="packaging">
        <tr>
            <td>
                <center>
                    <!-- Part -->
                    <xsl:text>Part </xsl:text>
                    <xsl:value-of select="position()"/>
                </center>
            </td>
            <td>
                <xsl:value-of select="pharm:partProduct/pharm:code"/>
            </td>
            <td>
                <xsl:value-of select="pharm:partProduct/pharm:name"/>
            </td>
            <td>
                <xsl:value-of select="pharm:partProduct/pharm:desc"/>
            </td>
            <td>
                <xsl:call-template name="show-eHDSIDoseForm">
                    <xsl:with-param name="node"
                                    select="pharm:partProduct/pharm:formCode"/>
                </xsl:call-template>
            </td>
            <td>
                <xsl:call-template name="show-package-size">
                    <xsl:with-param name="quantity" select="pharm:quantity"/>
                    <xsl:with-param name="asContent_level1" select="pharm:partProduct/pharm:asContent"/>
                    <xsl:with-param name="asContent_level2" select="pharm:partProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent"/>
                    <xsl:with-param name="asContent_level3" select="pharm:partProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent"/>
                </xsl:call-template>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>