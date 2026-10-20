<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:pharm="urn:hl7-org:pharm"
                version="2.0">

    <xsl:variable name="medicationSummarySectionCode"
                  select="'10160-0'"/>

    <xsl:template name="medicationSummary" match="/">
        <xsl:choose>
            <xsl:when test="not(/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code=$medicationSummarySectionCode])">
                <span class="sectionTitle">
                    <!-- Medications Summary non present! -->
                    <xsl:call-template name="show-eHDSIDisplayLabel">
                        <xsl:with-param name="code" select="'75'"/>
                    </xsl:call-template>
                </span>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$medicationSummarySectionCode]]"/>
            </xsl:otherwise>
        </xsl:choose>
        <br/>
        <br/>
    </xsl:template>

    <xsl:template match = "/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$medicationSummarySectionCode]]">
        <xsl:variable name="medAct" select="n1:entry/n1:act"/>
        <xsl:variable name="medCode" select="n1:entry/n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:code"/>
        <div class="wrap-collapsible">
            <input id="collapsible-medication-summary-section-original" class="toggle" type="checkbox" checked="true" />
            <label for="collapsible-medication-summary-section-original" class="lbl-toggle-title">
                <!-- Section title -->
                <xsl:call-template name="show-eHDSISection">
                    <xsl:with-param name="code" select="$medicationSummarySectionCode"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-title">
                <div class="content-inner-title">
                    <div class="wrap-collapsible">
                        <input id="collapsible-medication-summary-original" class="toggle" type="checkbox"/>
                        <label for="collapsible-medication-summary-original" class="lbl-toggle">
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
                        <input id="collapsible-medication-summary-translated" class="toggle" type="checkbox" checked="true"/>
                        <label for="collapsible-medication-summary-translated" class="lbl-toggle">
                            <xsl:value-of select="$translatedCodedTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <xsl:choose>
                                    <xsl:when test="not($medAct/@nullFlavor)">
                                        <table class="medication_summary_table">
                                            <colgroup class="medicinalproduct"/>
                                            <colgroup class="activeingredient"/>
                                            <colgroup class="strength"/>
                                            <colgroup class="productclassification"/>
                                            <colgroup class="medicinalproductpackagedescription"/>
                                            <colgroup class="medicinalproductpackageidentifier"/>
                                            <colgroup class="packagetype"/>
                                            <colgroup class="packagesize"/>
                                            <colgroup class="doseform"/>
                                            <colgroup class="unitsperintake"/>
                                            <colgroup class="frequencyofintakes"/>
                                            <colgroup class="routeofadministration"/>
                                            <colgroup class="duration"/>
                                            <colgroup class="medicationreason"/>
                                            <xsl:choose>
                                                <xsl:when test="$medCode/@code='no-known-medications' or $medCode/@code='no-medication-info'">
                                                    <tr bgcolor="#E6F2FF">
                                                        <td colspan="8">
                                                            <span class="tdtext">
                                                                <xsl:call-template name="show-eHDSIAbsentOrUnknownMedication">
                                                                    <xsl:with-param name="node" select="$medCode"/>
                                                                </xsl:call-template>
                                                            </span>
                                                            <br/>
                                                        </td>
                                                    </tr>
                                                </xsl:when>
                                                <xsl:otherwise>
                                                    <tr bgcolor="#E6F2FF">
                                                        <th>
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'128'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                        <th>
                                                            <!-- Active ingredient Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'1'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                        <th>
                                                            <!-- Strength Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'70'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                        <th>
                                                            <!-- Product classification -->
                                                            <!-- TODO Add concept to eHDSIDisplayLabel value set -->
                                                            Product classification
                                                        </th>
                                                        <th>
                                                            <!-- Medicinal Product Package Description -->
                                                            <!-- TODO Add Medicinal Product Package Description to eHDSIDisplayLabel value set -->
                                                            Medicinal Product Package Description
                                                        </th>
                                                        <th>
                                                            <!-- Medicinal Product Package identifier -->
                                                            <!-- TODO Add Medicinal Product Package identifier to eHDSIDisplayLabel value set -->
                                                            Medicinal Product Package identifier
                                                        </th>
                                                        <th>
                                                            <!-- Package type -->
                                                            <!-- TODO Add Package type to eHDSIDisplayLabel value set -->
                                                            Package type
                                                        </th>
                                                        <th>
                                                            <!-- Package size -->
                                                            <!-- TODO Add Package size to eHDSIDisplayLabel value set -->
                                                            Package size
                                                        </th>
                                                        <th>
                                                            <!-- Dose form Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'25'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                        <th>
                                                            <!-- Units per intake Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'78'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                        <th>
                                                            <!-- Frequency of intakes Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'32'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                        <th>
                                                            <!-- Route of Administration Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'67'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                        <th>
                                                            <!-- Duration of treatment Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'150'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                        <th>
                                                            <!-- Medication reason Header -->
                                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                <xsl:with-param name="code" select="'173'"/>
                                                            </xsl:call-template>
                                                        </th>
                                                    </tr>
                                                    <xsl:apply-templates select="n1:entry/n1:substanceAdministration" mode="medicationSummary"/>
                                                </xsl:otherwise>
                                            </xsl:choose>
                                        </table>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <xsl:call-template name="show-eHDSINullFlavor">
                                            <xsl:with-param name="code" select="$medAct/@nullFlavor"/>
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

    <xsl:template match="n1:entry/n1:substanceAdministration" mode="medicationSummary">
        <xsl:variable name="material"
                      select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial"/>
        <xsl:variable name="medDose"
                      select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:formCode"/>
        <xsl:variable name="medUnitIntake"
                      select="n1:doseQuantity"/>
        <xsl:variable name="medFrequencyIntake"
                      select="n1:effectiveTime[2]"/>
        <xsl:variable name="medFrequencyIntakeType"
                      select="n1:effectiveTime[2]/@xsi:type"/>
        <xsl:variable name="medRouteAdministration"
                      select="n1:routeCode"/>
        <xsl:variable name="medRegimen"
                      select="n1:effectiveTime[1][@xsi:type='IVL_TS' or substring-after(@xsi:type, ':')='IVL_TS']"/>
        <xsl:variable name="medCode"
                      select="n1:code/@code"/>
        <xsl:variable name="medReason"
                      select="n1:entryRelationship[@typeCode='RSON']"/>


        <!-- nullflavored act -->
        <xsl:choose>
            <xsl:when test="not(./@nullFlavor)">
                <!-- no info scenario code is one of the three values -->
                <xsl:if test="not($medCode='no-known-medications' or $medCode='no-medication-info')">
                    <xsl:variable name="backgroundColor"
                                  select="'#E6F2FF'"/>
                    <xsl:for-each
                            select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial">
                        <tbody>
                            <xsl:variable name="containerPackagedProduct"
                                          select="pharm:asContent/pharm:containerPackagedProduct"/>
                            <tr>
                                <td>
                                    <b><xsl:call-template name="show-medicinalProduct"/></b>
                                </td>
                                <td>
                                    <xsl:for-each select="pharm:ingredient[@classCode='ACTI']">
                                        <xsl:if test="position()=1">
                                            <xsl:call-template name="show-activeIngredient"/>
                                        </xsl:if>
                                    </xsl:for-each>
                                </td>
                                <td>
                                    <!-- Strength -->
                                    <xsl:choose>
                                        <xsl:when test="not(pharm:ingredient[@classCode='ACTI'])">
                                            <xsl:value-of select="pharm:desc"/>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:for-each select="pharm:ingredient[@classCode='ACTI']">
                                                <xsl:if test="position()=1">
                                                    <xsl:variable name="medStrength" select="pharm:quantity"/>
                                                    <xsl:call-template name="show-strength">
                                                        <xsl:with-param name="node" select="$medStrength"/>
                                                    </xsl:call-template>
                                                </xsl:if>
                                            </xsl:for-each>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                                <!-- Medicinal Product classification -->
                                <td>
                                    <xsl:value-of select="pharm:asSpecializedKind[@classCode='GEN']/pharm:generalizedMaterialKind/pharm:code/@code"/>
                                </td>
                                <!-- Medicinal Product Package Description -->
                                <td>
                                    <xsl:for-each select="$containerPackagedProduct/pharm:name">
                                        <xsl:value-of select="."/>
                                    </xsl:for-each>
                                </td>
                                <!-- Medicinal Product Package identifier -->
                                <td>
                                    <xsl:value-of select="$containerPackagedProduct/pharm:code[@code and not(@codeSystem='2.16.840.1.113883.6.73')]/@code"/>
                                </td>
                                <!-- 4. Package type -->
                                <td>
                                    <xsl:call-template name="show-eHDSIPackage">
                                        <xsl:with-param name="node"
                                                        select="$containerPackagedProduct/pharm:formCode"/>
                                    </xsl:call-template>
                                </td>
                                <!-- 5. Package size -->
                                <td>
                                    <xsl:call-template name="show-PQ">
                                        <xsl:with-param name="node" select="$containerPackagedProduct/pharm:asContent/pharm:quantity"/>
                                    </xsl:call-template>
                                </td>
                                <td>
                                    <!-- Dose form -->
                                    <xsl:call-template name="show-eHDSIDoseForm">
                                        <xsl:with-param name="node" select="$medDose"/>
                                    </xsl:call-template>
                                </td>
                                <td>
                                    <!-- Units per intake -->
                                    <xsl:call-template name="show-IVL_PQ">
                                        <xsl:with-param name="node" select="$medUnitIntake"/>
                                    </xsl:call-template>
                                </td>
                                <td>
                                    <!-- Frequency of intakes -->
                                    <xsl:choose>
                                        <xsl:when test="not ($medFrequencyIntake/@nullFlavor)">
                                            <xsl:call-template name="show-frequencyIntake">
                                                <xsl:with-param name="medFrequencyIntakeType" select="$medFrequencyIntakeType"/>
                                                <xsl:with-param name="medFrequencyIntake" select="$medFrequencyIntake"/>
                                            </xsl:call-template>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:call-template name="show-eHDSINullFlavor">
                                                <xsl:with-param name="code" select="$medFrequencyIntake/@nullFlavor"/>
                                            </xsl:call-template>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </td>
                                <td>
                                    <!-- Route of Administration -->
                                    <xsl:call-template name="show-eHDSIRouteOfAdministration">
                                        <xsl:with-param name="node" select="$medRouteAdministration"/>
                                    </xsl:call-template>
                                </td>
                                <td>
                                    <!-- Duration of treatment -->
                                    <xsl:call-template name="show-IVL_TS">
                                        <xsl:with-param name="node"
                                                        select="$medRegimen"/>
                                    </xsl:call-template>
                                </td>
                                <td>
                                    <!-- Medication Reason -->
                                    <xsl:for-each select="$medReason">
                                        <xsl:choose>
                                            <xsl:when test="n1:observation">
                                                <xsl:call-template name="show-eHDSIIllnessandDisorder">
                                                    <xsl:with-param name="node"
                                                                    select="n1:observation/n1:value"/>
                                                </xsl:call-template>
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:call-template name="show-id">
                                                    <xsl:with-param name="id" select="n1:act/n1:id"/>
                                                </xsl:call-template>
                                            </xsl:otherwise>
                                        </xsl:choose>
                                    </xsl:for-each>
                                </td>
                            </tr>
                            <xsl:for-each select="pharm:ingredient[@classCode='ACTI']">
                                <xsl:if test="position()!=1">
                                    <tr>
                                        <td/>
                                        <td>
                                            <xsl:call-template name="show-activeIngredient"/>
                                        </td>
                                        <td>
                                            <xsl:variable name="medStrength" select="pharm:quantity"/>
                                            <xsl:call-template name="show-strength">
                                                <xsl:with-param name="node" select="$medStrength"/>
                                            </xsl:call-template>
                                        </td>
                                        <td/>
                                        <td/>
                                        <td/>
                                        <td/>
                                        <td/>
                                        <td/>
                                    </tr>
                                </xsl:if>
                            </xsl:for-each>
                        </tbody>
                    </xsl:for-each>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                <tr>
                    <td colspan="8">
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="./@nullFlavor"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="show-medicinalProduct">
        <xsl:variable name="generalizedMaterialKindCode"
                      select="pharm:asSpecializedKind/pharm:generalizedMaterialKind/pharm:code"/>
        <xsl:choose>
            <xsl:when test="$generalizedMaterialKindCode and not($generalizedMaterialKindCode/@nullFlavor)">
                <xsl:call-template name="show-eHDSIActiveIngredient">
                    <xsl:with-param name="node" select="$generalizedMaterialKindCode"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="n1:name and not (n1:name/@nullFlavor)">
                        <xsl:value-of select="n1:name"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:if test="pharm:asContent/pharm:containerPackagedMedicine/pharm:name and not (pharm:asContent/pharm:containerPackagedMedicine/pharm:name/@nullFlavor)">
                            <xsl:value-of select="pharm:asContent/pharm:containerPackagedMedicine/pharm:name"/>
                        </xsl:if>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="show-activeIngredient">
        <xsl:variable name="medActiveIngredientNode" select="pharm:ingredientSubstance"/>
        <xsl:variable name="medActiveIngredientNodeCode" select="$medActiveIngredientNode/pharm:code"/>
        <xsl:variable name="medActiveIngredient" select="$medActiveIngredientNodeCode"/>
        <xsl:variable name="medActiveIngredientID" select="$medActiveIngredientNodeCode/@code"/>
        <xsl:variable name="medStrength" select="pharm:quantity"/>
        <!-- Active ingredient -->
        <xsl:choose>
            <xsl:when test="not ($medActiveIngredientNodeCode/@nullFlavor)">
                <xsl:choose>
                    <xsl:when test="$medActiveIngredient">
                        <xsl:call-template name="show-eHDSIActiveIngredient">
                            <xsl:with-param name="node" select="$medActiveIngredientNodeCode"/>
                        </xsl:call-template>
                        <xsl:call-template name="show-eHDSISubstance">
                            <xsl:with-param name="node" select="$medActiveIngredientNodeCode"/>
                        </xsl:call-template>
                        <xsl:text> (</xsl:text>
                        <xsl:value-of select="$medActiveIngredientID"/>
                        <xsl:text>)</xsl:text>
                    </xsl:when>
                    <xsl:otherwise>
                        <!-- uncoded element Problem -->
                        <xsl:if test="$medActiveIngredientNodeCode/n1:originalText/n1:reference/@value">
                            <xsl:call-template name="show-uncodedElement">
                                <xsl:with-param name="code"
                                                select="$medActiveIngredientNodeCode/n1:originalText/n1:reference/@value"/>
                            </xsl:call-template>
                        </xsl:if>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:choose>
                    <xsl:when test="$medActiveIngredientNode/pharm:name">
                        <xsl:value-of select="$medActiveIngredientNode/pharm:name"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code"
                                            select="$medActiveIngredientNodeCode/@nullFlavor"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>
