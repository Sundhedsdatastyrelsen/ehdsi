<?xml version="1.0" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3" version="2.0">

    <xsl:variable name="immunizationsSectionCode"
                  select="'11369-6'"/>

    <!-- immunizations -->
    <xsl:template name="immunizations" match="/">
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$immunizationsSectionCode]]"/>
    </xsl:template>

    <xsl:template match = "/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$immunizationsSectionCode]]">
        <div class="wrap-collapsible">
            <input id="collapsible-immunizations-section" class="toggle" type="checkbox" checked="true" />
            <label for="collapsible-immunizations-section" class="lbl-toggle-title">
                <!-- Section title -->
                <xsl:call-template name="show-eHDSISection">
                    <xsl:with-param name="code" select="$immunizationsSectionCode"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-title">
                <div class="content-inner-title">
                    <div class="wrap-collapsible">
                        <input id="collapsible-immunizations-original" class="toggle" type="checkbox"/>
                        <label for="collapsible-immunizations-original" class="lbl-toggle">
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
                        <input id="collapsible-immunizationsSectionCode-translated" class="toggle" type="checkbox" checked="true"/>
                        <label for="collapsible-immunizationsSectionCode-translated" class="lbl-toggle">
                            <xsl:value-of select="$translatedCodedTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <xsl:choose>
                                    <xsl:when test="not (n1:entry/n1:substanceAdministration/@nullFlavor)">
                                        <table class="translation_table">
                                            <tbody>
                                                <tr>
                                                    <th>
                                                        <!-- Vaccination header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'79'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Brand name header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'9'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Vaccination Date header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'80'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Agent Header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'5'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Marketing Authorization Holder Header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'124'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Dose number in series Header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'152'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Batch/lot number Header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'169'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Administering Center Header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'170'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Health Professional Identification Header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'171'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Country of Vaccination Header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'172'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                    <th>
                                                        <!-- Administered header header -->
                                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                                            <xsl:with-param name="code" select="'122'"/>
                                                        </xsl:call-template>
                                                    </th>
                                                </tr>
                                                <xsl:apply-templates select="n1:entry/n1:substanceAdministration" mode="immunizations"/>
                                            </tbody>
                                        </table>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:call-template name="show-eHDSINullFlavor">
                                            <xsl:with-param name="code" select="n1:entry/n1:substanceAdministration/@nullFlavor"/>
                                        </xsl:call-template>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br />
        <br />
    </xsl:template>

    <xsl:template match="n1:entry/n1:substanceAdministration" mode="immunizations">
        <!-- Defining all needed variables -->
        <xsl:variable name="vaccination"
                      select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:code"/>

        <xsl:variable name="vaccinationsBrandName"
                      select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:name"/>

        <xsl:variable name="vaccinationsDate"
                      select="n1:effectiveTime"/>

        <xsl:variable name="vaccinationsPosition"
                      select="n1:entryRelationship/n1:observation[@classCode='OBS'][@moodCode='EVN']/n1:code[@codeSystem='2.16.840.1.113883.6.1'][@code ='30973-2']"/>

        <xsl:variable name="vaccinationMarketingAuthorizationHolder"
                      select="n1:consumable/n1:manufacturedProduct/n1:manufacturerOrganization"/>

        <xsl:variable name="vaccinationBatchNumber"
                      select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:lotNumberText"/>
        <!-- End definition of variables -->

        <xsl:choose>
            <xsl:when test="not(./@nullFlavor)">
                <tr>
                    <td>
                        <!-- Vaccination -->
                        <xsl:call-template name="show-eHDSIVaccine">
                            <xsl:with-param name="node" select="$vaccination"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <!-- Brand Name -->
                        <xsl:choose>
                            <xsl:when test="$vaccinationsBrandName/@nullFlavor">
                                <xsl:call-template name="show-eHDSINullFlavor">
                                    <xsl:with-param name="code"
                                                    select="$vaccinationsBrandName/@nullFlavor"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="$vaccinationsBrandName"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </td>
                    <td>
                        <!-- Vaccination Date -->
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="$vaccinationsDate"/>
                        </xsl:call-template>
                        &#160;
                    </td>
                    <td>
                        <!-- Agent -->
                        <xsl:for-each select="n1:participant">
                            <xsl:choose>
                                <xsl:when test="n1:participantRole/n1:code/@nullFlavor">
                                    <xsl:call-template name="show-eHDSINullFlavor">
                                        <xsl:with-param name="code" select="n1:participantRole/n1:code/@nullFlavor"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:call-template name="show-eHDSIIllnessandDisorder">
                                        <xsl:with-param name="node" select="n1:participantRole/n1:code"/>
                                    </xsl:call-template>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:for-each>
                    </td>
                    <td>
                        <!-- Marketing Authorization Holder -->
                        <xsl:choose>
                            <xsl:when test="$vaccinationMarketingAuthorizationHolder/n1:name/@nullFlavor">
                                <xsl:call-template name="show-eHDSINullFlavor">
                                    <xsl:with-param name="code" select="$vaccinationMarketingAuthorizationHolder/n1:name/@nullFlavor"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="$vaccinationMarketingAuthorizationHolder/n1:name"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </td>
                    <td>
                        <!-- Dose number in series -->
                        <xsl:choose>
                            <xsl:when test="$vaccinationsPosition/@nullFlavor">
                                <xsl:call-template name="show-eHDSINullFlavor">
                                    <xsl:with-param name="code" select="$vaccinationsPosition/@nullFlavor"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="$vaccinationsPosition/../n1:value/@value"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </td>
                    <td>
                        <!-- Batch/lot number -->
                        <xsl:choose>
                            <xsl:when test="$vaccinationBatchNumber/@nullFlavor">
                                <xsl:call-template name="show-eHDSINullFlavor">
                                    <xsl:with-param name="code" select="$vaccinationBatchNumber/@nullFlavor"/>
                                </xsl:call-template>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="$vaccinationBatchNumber"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </td>
                    <td>
                        <!-- Administering Center -->
                        <xsl:for-each select="n1:performer">
                            <xsl:choose>
                                <xsl:when test="n1:assignedEntity/@nullFlavor">
                                    <xsl:call-template name="show-eHDSINullFlavor">
                                        <xsl:with-param name="code" select="n1:assignedEntity/@nullFlavor"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:for-each select="n1:assignedEntity/n1:representedOrganization/n1:name">
                                        <xsl:value-of select="text()"/>
                                    </xsl:for-each>
                                </xsl:otherwise>
                            </xsl:choose>
                            &#160;
                        </xsl:for-each>
                    </td>
                    <td>
                        <!-- Health Professional Identification -->
                        <xsl:for-each select="n1:performer">
                            <xsl:choose>
                                <xsl:when test="n1:assignedEntity/@nullFlavor">
                                    <xsl:call-template name="show-eHDSINullFlavor">
                                        <xsl:with-param name="code" select="n1:assignedEntity/@nullFlavor"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:call-template name="show-name">
                                        <xsl:with-param name="name" select="n1:assignedEntity/n1:assignedPerson/n1:name"/>
                                    </xsl:call-template>
                                </xsl:otherwise>
                            </xsl:choose>
                            &#160;
                        </xsl:for-each>
                    </td>
                    <td>
                        <!-- Country of Vaccination -->
                        <xsl:for-each select="n1:performer">
                            <xsl:choose>
                                <xsl:when test="n1:assignedEntity/@nullFlavor">
                                    <xsl:call-template name="show-eHDSINullFlavor">
                                        <xsl:with-param name="code" select="n1:assignedEntity/@nullFlavor"/>
                                    </xsl:call-template>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:value-of select="n1:assignedEntity/n1:representedOrganization/n1:addr"/>
                                </xsl:otherwise>
                            </xsl:choose>
                            &#160;
                        </xsl:for-each>
                    </td>
                    <td>
                        <!-- Administered -->
                        <xsl:choose>
                            <xsl:when test="n1:statusCode/@code='completed'">
                                <font color="green">&#10004;</font>
                            </xsl:when>
                            <xsl:otherwise>
                                <font color="red">&#10006;</font>
                            </xsl:otherwise>
                        </xsl:choose>
                    </td>
                </tr>
            </xsl:when>
            <xsl:otherwise>
                <tr>
                    <td colspan="3">
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="n1:substanceAdministration/@nullFlavor"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
