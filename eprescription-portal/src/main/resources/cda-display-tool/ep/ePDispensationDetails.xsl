<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                xmlns:pharm="urn:hl7-org:pharm"
                version="2.0">

    <xsl:template name="dispensationDetailsBlock">
        <fieldset style="min-height:100px;">
            <legend>
                <!-- Dispensation details -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'130'"/>
                </xsl:call-template>
            </legend>
            <xsl:variable name="substitutionAllowed">
                <xsl:call-template name="pure-substitution-code"/>
            </xsl:variable>
            <table class="ep_table">
                <tbody>
                    <tr>
                        <th>
                            <span class="td_label"><!--  Substitute:-->
                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                    <xsl:with-param name="code" select="'71'"/>
                                </xsl:call-template>
                                *
                            </span>
                        </th>
                        <td colspan="2">
                            <input type="checkbox" id="substituted_0" name="substituted_0" style="display:inline">
                                <xsl:if test="$substitutionAllowed !='Yes'">
                                    <xsl:attribute name="disabled"/>
                                </xsl:if>
                            </input>
                            <!--  Substitution help text:-->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'202'"/>
                            </xsl:call-template>
                        </td>
                    </tr>
                    <tr>
                        <td rowspan="3"/>
                        <th>
                            <!--  Dispensed Product:-->
                            <span class="td_label">
                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                    <xsl:with-param name="code" select="'24'"/>
                                </xsl:call-template>
                            </span>
                        </th>
                        <td>
                            <input type="text" size="50">
                                <xsl:attribute name="id">
                                    <xsl:text>dispensedProductValue_</xsl:text>
                                    <xsl:value-of select="position()-1"/>
                                </xsl:attribute>
                                <xsl:attribute name="name">
                                    <xsl:text>dispensedProductValue_</xsl:text>
                                    <xsl:value-of select="position()-1"/>
                                </xsl:attribute>
                                <xsl:attribute name="value">
                                    <xsl:value-of
                                            select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:name"/>
                                </xsl:attribute>
                            </input>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <!-- Dispensed Package Size:-->
                            <span class="td_label">
                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                    <xsl:with-param name="code" select="'23'"/>
                                </xsl:call-template>
                            </span>
                        </th>
                        <td>
                            <xsl:call-template name="show-package-size-for-dispense">
                                <xsl:with-param name="quantity" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:quantity"/>
                                <xsl:with-param name="asContent_level1" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:asContent"/>
                                <xsl:with-param name="asContent_level2" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent"/>
                                <xsl:with-param name="asContent_level3" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent"/>
                                <xsl:with-param name="substitutionAllowed" select="$substitutionAllowed"/>
                            </xsl:call-template>
                            <xsl:if test="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:part">
                                <table>
                                    <tbody>
                                        <xsl:apply-templates select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:part" mode="packagingDispense"/>
                                    </tbody>
                                </table>
                            </xsl:if>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            <!-- Dispensed Number of Packages:-->
                            <span class="td_label">
                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                    <xsl:with-param name="code" select="'115'"/>
                                </xsl:call-template>
                            </span>
                        </th>
                        <td>
                            <input type="text" size="50">
                                <xsl:attribute name="id">
                                    <xsl:text>dispensedNumberOfPackages_</xsl:text>
                                    <xsl:value-of select="position()-1"/>
                                </xsl:attribute>
                                <xsl:attribute name="name">
                                    <xsl:text>dispensedNumberOfPackages_</xsl:text>
                                    <xsl:value-of select="position()-1"/>
                                </xsl:attribute>
                                <xsl:attribute name="value">
                                    <xsl:call-template name="number-of-packages">
                                        <xsl:with-param name="supply" select="n1:entryRelationship[@typeCode='COMP']"/>
                                    </xsl:call-template>
                                </xsl:attribute>
                                <xsl:if test="$substitutionAllowed !='Yes'">
                                    <xsl:attribute name="disabled"/>
                                </xsl:if>
                            </input>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <!-- HOTFIX - Has to be replaced with a value from the epSOSDisplayLabel value set -->
                            *  If substitution of brand name is marked as not allowed, pharmacists may still consider dispensing the national equivalent even though the brand name might be slightly different.<br/>
                            &#160;&#160;This is a known situation: the same pharmaceutical company is marketing the same medicinal product in different countries with slightly different names due to marketing reasons.<br/>
                            &#160;&#160;If the pharmacist is certain that this is the case, the systems allows the input of the new brand name.<br/>
                        </td>
                    </tr>
                    <tr>
                        <td style="text-align:center;vertical-align:middle;">
                            <!--  Dispense -->
                            <xsl:choose>
                                <xsl:when test="$allowDispense='true'">
                                    <input type="submit" class="button">
                                        <xsl:attribute name="value">
                                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                                <xsl:with-param name="code" select="'22'"/>
                                            </xsl:call-template>
                                        </xsl:attribute>
                                    </input>
                                </xsl:when>
                            </xsl:choose>
                            <input type="hidden" name="prescriptionID" id="prescriptionID">
                                <xsl:attribute name="value">
                                    <xsl:value-of select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.2.1']/n1:id/@extension"/>
                                </xsl:attribute>
                            </input>
                            <input type="hidden">
                                <xsl:call-template name="inputformID">
                                    <xsl:with-param name="txt" select="'dispensationid_'"/>
                                    <xsl:with-param name="root" select="n1:id/@root"/>
                                    <xsl:with-param name="extension" select="n1:id/@extension"/>
                                </xsl:call-template>
                            </input>
                            <xsl:variable name="currentPackageFormName">
                                <xsl:call-template name="show-formCode">
                                    <xsl:with-param name="parameter"
                                                    select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:formCode"/>
                                </xsl:call-template>
                            </xsl:variable>
                            <input type="hidden">
                                <xsl:call-template name="inputform">
                                    <xsl:with-param name="txt" select="'packaging1_'"/>
                                    <xsl:with-param name="val" select="$currentPackageFormName"/>
                                </xsl:call-template>
                            </input>
                            <xsl:variable name="currentDoseFormName">
                                <xsl:call-template name="show-formCode">
                                    <xsl:with-param name="parameter"
                                                    select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:asContent/pharm:containerPackagedProduct/pharm:formCode"/>
                                </xsl:call-template>
                            </xsl:variable>
                            <input type="hidden">
                                <xsl:call-template name="inputform">
                                    <xsl:with-param name="txt" select="'packaging2_'"/>
                                    <xsl:with-param name="val" select="$currentDoseFormName"/>
                                </xsl:call-template>
                            </input>
                            <input type="hidden">
                                <xsl:call-template name="inputform">
                                    <xsl:with-param name="txt" select="'packaging3_'"/>
                                    <xsl:with-param name="val"
                                                    select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:asContent/pharm:quantity/pharm:numerator/@value"/>
                                </xsl:call-template>
                            </input>

                            <xsl:variable name="currentIngredient">
                                <xsl:call-template name="show-formCode">
                                    <xsl:with-param name="parameter"
                                                    select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:ingredient[@classCode='ACTI']/pharm:ingredientSubstance/pharm:code"/>
                                </xsl:call-template>
                            </xsl:variable>
                            <input type="hidden">
                                <xsl:call-template name="inputform">
                                    <xsl:with-param name="txt" select="'ingredient_'"/>
                                    <xsl:with-param name="val" select="$currentIngredient"/>
                                </xsl:call-template>
                            </input>
                            <input type="hidden">
                                <xsl:attribute name="id">
                                    <xsl:text>name_</xsl:text>
                                    <xsl:value-of select="position()-1"/>
                                </xsl:attribute>
                                <xsl:attribute name="name">
                                    <xsl:text>name_</xsl:text>
                                    <xsl:value-of select="position()-1"/>
                                </xsl:attribute>
                                <xsl:attribute name="value">
                                    <xsl:call-template name="show-strength">
                                        <xsl:with-param name="node" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:ingredient[@classCode='ACTI'][position()-1]/pharm:quantity"/>
                                    </xsl:call-template>
                                </xsl:attribute>
                            </input>
                            <input type="hidden">
                                <xsl:attribute name="id">
                                    <xsl:text>nrOfPacks_</xsl:text>
                                    <xsl:value-of select="position()-1"/>
                                </xsl:attribute>
                                <xsl:attribute name="name">
                                    <xsl:text>nrOfPacks_</xsl:text>
                                    <xsl:value-of select="position()-1"/>
                                </xsl:attribute>
                                <xsl:attribute name="value">
                                    <xsl:call-template name="number-of-packages">
                                        <xsl:with-param name="supply" select="n1:entryRelationship[@typeCode='COMP']"/>
                                    </xsl:call-template>
                                </xsl:attribute>
                            </input>
                            <input type="hidden">
                                <xsl:attribute name="id">
                                    <xsl:text>country</xsl:text>
                                </xsl:attribute>
                                <xsl:attribute name="name">
                                    <xsl:text>country</xsl:text>
                                </xsl:attribute>
                                <xsl:attribute name="value">
                                    <xsl:value-of
                                            select=" /n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:addr/n1:country"/>
                                </xsl:attribute>
                            </input>
                        </td>
                    </tr>
                </tbody>
            </table>
        </fieldset>
    </xsl:template>

    <xsl:template name="show-package-size-for-dispense">
        <xsl:param name="quantity"/>
        <xsl:param name="asContent_level1"/>
        <xsl:param name="asContent_level2"/>
        <xsl:param name="asContent_level3"/>
        <xsl:param name="substitutionAllowed"/>
        <ul>
            <xsl:choose>
                <xsl:when test="$asContent_level3">
                    <li>
                        <xsl:call-template name="show-package-size-dispense-quantity">
                            <xsl:with-param name="quantity" select="$quantity"/>
                        </xsl:call-template>
                        <xsl:call-template name="show-formCode">
                            <xsl:with-param name="parameter"
                                            select="$asContent_level3/pharm:containerPackagedProduct/pharm:formCode"/>
                        </xsl:call-template>
                        <ul>
                            <li>
                                <input type="text" size="5">
                                    <xsl:attribute name="id">
                                        <xsl:text>packageSizeL3</xsl:text>
                                    </xsl:attribute>
                                    <xsl:attribute name="name">
                                        <xsl:text>packageSizeL3</xsl:text>
                                    </xsl:attribute>
                                    <xsl:attribute name="value">
                                        <xsl:value-of
                                                select="$asContent_level3/pharm:quantity/@value"/>
                                    </xsl:attribute>
                                    <xsl:if test="$substitutionAllowed !='Yes'">
                                        <xsl:attribute name="disabled"/>
                                    </xsl:if>
                                </input>
                                <xsl:call-template name="show_quantity">
                                    <xsl:with-param name="quantity" select="$asContent_level3/pharm:quantity"/>
                                    <xsl:with-param name="showValue" select="false()"/>
                                </xsl:call-template>
                                <xsl:text> </xsl:text>
                                <xsl:call-template name="show-formCode">
                                    <xsl:with-param name="parameter"
                                                    select="$asContent_level2/pharm:containerPackagedProduct/pharm:formCode"/>
                                </xsl:call-template>
                                <ul>
                                    <xsl:call-template name="show-package-size-dispense-level2">
                                        <xsl:with-param name="asContent_level1" select="$asContent_level1"/>
                                        <xsl:with-param name="asContent_level2" select="$asContent_level2"/>
                                        <xsl:with-param name="substitutionAllowed" select="$substitutionAllowed"/>
                                    </xsl:call-template>
                                </ul>
                            </li>
                        </ul>
                    </li>
                </xsl:when>
                <xsl:when test="$asContent_level2">
                    <li>
                        <xsl:call-template name="show-package-size-dispense-quantity">
                            <xsl:with-param name="quantity" select="$quantity"/>
                        </xsl:call-template>
                        <xsl:call-template name="show-package-size-dispense-level2">
                            <xsl:with-param name="asContent_level1" select="$asContent_level1"/>
                            <xsl:with-param name="asContent_level2" select="$asContent_level2"/>
                            <xsl:with-param name="substitutionAllowed" select="$substitutionAllowed"/>
                        </xsl:call-template>
                    </li>
                </xsl:when>
                <xsl:otherwise>
                    <ul>
                        <li>
                            <xsl:call-template name="show-package-size-dispense-quantity">
                                <xsl:with-param name="quantity" select="$quantity"/>
                            </xsl:call-template>
                            <xsl:text> </xsl:text>
                            <xsl:call-template name="show-package-size-dispense-level1">
                                <xsl:with-param name="asContent" select="$asContent_level1"/>
                                <xsl:with-param name="substitutionAllowed" select="$substitutionAllowed"/>
                            </xsl:call-template>
                        </li>
                    </ul>
                </xsl:otherwise>
            </xsl:choose>
        </ul>
    </xsl:template>

    <xsl:template name="show-package-size-dispense-level2">
        <xsl:param name="asContent_level1"/>
        <xsl:param name="asContent_level2"/>
        <xsl:param name="substitutionAllowed"/>
        <xsl:call-template name="show-formCode">
            <xsl:with-param name="parameter"
                            select="$asContent_level2/pharm:containerPackagedProduct/pharm:formCode"/>
        </xsl:call-template>
        <ul>
            <li>
                <input type="text" size="5">
                    <xsl:attribute name="id">
                        <xsl:text>packageSizeL2</xsl:text>
                    </xsl:attribute>
                    <xsl:attribute name="name">
                        <xsl:text>packageSizeL2</xsl:text>
                    </xsl:attribute>
                    <xsl:attribute name="value">
                        <xsl:value-of
                                select="$asContent_level2/pharm:quantity/@value"/>
                    </xsl:attribute>
                    <xsl:if test="$substitutionAllowed !='Yes'">
                        <xsl:attribute name="disabled"/>
                    </xsl:if>
                </input>
                <xsl:text> </xsl:text>
                <xsl:call-template name="show_quantity">
                    <xsl:with-param name="quantity" select="$asContent_level2/pharm:quantity"/>
                    <xsl:with-param name="showValue" select="false()"/>
                </xsl:call-template>
                <xsl:text> </xsl:text>
                <xsl:call-template name="show-package-size-dispense-level1">
                    <xsl:with-param name="asContent" select="$asContent_level1"/>
                    <xsl:with-param name="substitutionAllowed" select="$substitutionAllowed"/>
                </xsl:call-template>
            </li>
        </ul>
    </xsl:template>

    <xsl:template name="show-package-size-dispense-level1">
        <xsl:param name="asContent"/>
        <xsl:param name="substitutionAllowed"/>
        <xsl:call-template name="show-formCode">
            <xsl:with-param name="parameter"
                            select="$asContent/pharm:containerPackagedProduct/pharm:formCode"/>
        </xsl:call-template>
        <!-- Not applicable for the part case -->
        <xsl:if test="$asContent/pharm:quantity">
            <ul>
                <li>
                    <input type="text" size="5">
                        <xsl:attribute name="id">
                            <xsl:text>packageSizeL1</xsl:text>
                        </xsl:attribute>
                        <xsl:attribute name="name">
                            <xsl:text>packageSizeL1</xsl:text>
                        </xsl:attribute>
                        <xsl:attribute name="value">
                            <xsl:value-of
                                    select="$asContent/pharm:quantity/@value"/>
                        </xsl:attribute>
                        <xsl:if test="$substitutionAllowed !='Yes'">
                            <xsl:attribute name="disabled"/>
                        </xsl:if>
                    </input>
                    <xsl:text> </xsl:text>
                    <xsl:call-template name="show_quantity">
                        <xsl:with-param name="quantity" select="$asContent/pharm:quantity"/>
                        <xsl:with-param name="showValue" select="false()"/>
                    </xsl:call-template>
                    <xsl:text> </xsl:text>
                    <xsl:call-template name="show-formCode">
                        <xsl:with-param name="parameter"
                                        select="$asContent/../pharm:formCode"/>
                    </xsl:call-template>
                </li>
            </ul>
        </xsl:if>
    </xsl:template>

    <xsl:template name="show-package-size-dispense-quantity" >
        <xsl:param name="quantity"/>
        <xsl:if test="$quantity">
            <input type="text" size="5">
                <xsl:attribute name="id">
                    <xsl:text>quantity</xsl:text>
                </xsl:attribute>
                <xsl:attribute name="name">
                    <xsl:text>quantity</xsl:text>
                </xsl:attribute>
                <xsl:attribute name="value">
                    <xsl:value-of
                            select="$quantity/@value"/>
                </xsl:attribute>
            </input>
            <xsl:text> </xsl:text>
            <xsl:call-template name="show_quantity">
                <xsl:with-param name="quantity" select="$quantity"/>
                <xsl:with-param name="showValue" select="false()"/>
            </xsl:call-template>
            <xsl:text> </xsl:text>
        </xsl:if>
    </xsl:template>

    <xsl:template match="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/pharm:part" mode="packagingDispense">
        <tr>
            <th>
                <!-- Part -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'189'"/>
                </xsl:call-template>
                <xsl:text> </xsl:text>
                <xsl:value-of select="position()"/>
            </th>
            <td>
                <xsl:call-template name="show-parts-package-size-for-dispense">
                    <xsl:with-param name="quantity" select="pharm:quantity"/>
                    <xsl:with-param name="asContent_level1" select="pharm:partProduct/pharm:asContent"/>
                    <xsl:with-param name="asContent_level2" select="pharm:partProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent"/>
                    <xsl:with-param name="asContent_level3" select="pharm:partProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent/pharm:containerPackagedProduct/pharm:asContent"/>
                </xsl:call-template>
            </td>
        </tr>
    </xsl:template>

    <xsl:template name="show-parts-package-size-for-dispense">
        <xsl:param name="quantity"/>
        <xsl:param name="asContent_level1"/>
        <xsl:param name="asContent_level2"/>
        <xsl:param name="asContent_level3"/>
        <ul>
            <xsl:choose>
                <xsl:when test="$asContent_level3">
                    <li>
                        <xsl:call-template name="show-parts-package-size-dispense-quantity">
                            <xsl:with-param name="quantity" select="$quantity"/>
                        </xsl:call-template>
                        <xsl:call-template name="show-formCode">
                            <xsl:with-param name="parameter"
                                            select="$asContent_level3/pharm:containerPackagedProduct/pharm:formCode"/>
                        </xsl:call-template>
                        <ul>
                            <li>
                                <input type="text" size="5">
                                    <xsl:attribute name="id">
                                        <xsl:text>packageSizeL3_</xsl:text>
                                        <xsl:value-of select="position()-1"/>
                                    </xsl:attribute>
                                    <xsl:attribute name="data-dispense-field">
                                        <xsl:text>packageSizeL3_</xsl:text>
                                        <xsl:value-of select="position()-1"/>
                                    </xsl:attribute>
                                    <xsl:attribute name="name">
                                        <xsl:text>packageSizeL3_</xsl:text>
                                        <xsl:value-of select="position()-1"/>
                                    </xsl:attribute>
                                    <xsl:attribute name="value">
                                        <xsl:value-of
                                                select="$asContent_level3/pharm:quantity/@value"/>
                                    </xsl:attribute>
                                </input>
                                <xsl:call-template name="show_quantity">
                                    <xsl:with-param name="quantity" select="$asContent_level3/pharm:quantity"/>
                                    <xsl:with-param name="showValue" select="false()"/>
                                </xsl:call-template>
                                <xsl:text> </xsl:text>
                                <xsl:call-template name="show-formCode">
                                    <xsl:with-param name="parameter"
                                                    select="$asContent_level2/pharm:containerPackagedProduct/pharm:formCode"/>
                                </xsl:call-template>
                                <ul>
                                    <xsl:call-template name="show-parts-package-size-dispense-level2">
                                        <xsl:with-param name="asContent_level1" select="$asContent_level1"/>
                                        <xsl:with-param name="asContent_level2" select="$asContent_level2"/>
                                    </xsl:call-template>
                                </ul>
                            </li>
                        </ul>
                    </li>
                </xsl:when>
                <xsl:when test="$asContent_level2">
                    <li>
                        <xsl:call-template name="show-parts-package-size-dispense-quantity">
                            <xsl:with-param name="quantity" select="$quantity"/>
                        </xsl:call-template>
                        <xsl:call-template name="show-parts-package-size-dispense-level2">
                            <xsl:with-param name="asContent_level1" select="$asContent_level1"/>
                            <xsl:with-param name="asContent_level2" select="$asContent_level2"/>
                        </xsl:call-template>
                    </li>
                </xsl:when>
                <xsl:otherwise>
                    <ul>
                        <li>
                            <xsl:call-template name="show-parts-package-size-dispense-quantity">
                                <xsl:with-param name="quantity" select="$quantity"/>
                            </xsl:call-template>
                            <xsl:text> </xsl:text>
                            <xsl:call-template name="show-parts-package-size-dispense-level1">
                                <xsl:with-param name="asContent" select="$asContent_level1"/>
                            </xsl:call-template>
                        </li>
                    </ul>
                </xsl:otherwise>
            </xsl:choose>
        </ul>
    </xsl:template>

    <xsl:template name="show-parts-package-size-dispense-level2">
        <xsl:param name="asContent_level1"/>
        <xsl:param name="asContent_level2"/>
        <xsl:call-template name="show-formCode">
            <xsl:with-param name="parameter"
                            select="$asContent_level2/pharm:containerPackagedProduct/pharm:formCode"/>
        </xsl:call-template>
        <ul>
            <li>
                <input type="text" size="5">
                    <xsl:attribute name="id">
                        <xsl:text>packageSizeL2_</xsl:text>
                        <xsl:value-of select="position()-1"/>
                    </xsl:attribute>
                    <xsl:attribute name="data-dispense-field">
                        <xsl:text>packageSizeL2_</xsl:text>
                        <xsl:value-of select="position()-1"/>
                    </xsl:attribute>
                    <xsl:attribute name="name">
                        <xsl:text>packageSizeL2_</xsl:text>
                        <xsl:value-of select="position()-1"/>
                    </xsl:attribute>
                    <xsl:attribute name="value">
                        <xsl:value-of
                                select="$asContent_level2/pharm:quantity/@value"/>
                    </xsl:attribute>
                </input>
                <xsl:text> </xsl:text>
                <xsl:call-template name="show_quantity">
                    <xsl:with-param name="quantity" select="$asContent_level2/pharm:quantity"/>
                    <xsl:with-param name="showValue" select="false()"/>
                </xsl:call-template>
                <xsl:text> </xsl:text>
                <xsl:call-template name="show-parts-package-size-dispense-level1">
                    <xsl:with-param name="asContent" select="$asContent_level1"/>
                </xsl:call-template>
            </li>
        </ul>
    </xsl:template>

    <xsl:template name="show-parts-package-size-dispense-level1">
        <xsl:param name="asContent"/>
        <xsl:call-template name="show-formCode">
            <xsl:with-param name="parameter"
                            select="$asContent/pharm:containerPackagedProduct/pharm:formCode"/>
        </xsl:call-template>
        <!-- Not applicable for the part case -->
        <xsl:if test="$asContent/pharm:quantity">
            <ul>
                <li>
                    <input type="text" size="5">
                        <xsl:attribute name="id">
                            <xsl:text>packageSizeL1_</xsl:text>
                            <xsl:value-of select="position()-1"/>
                        </xsl:attribute>
                        <xsl:attribute name="data-dispense-field">
                            <xsl:text>packageSizeL1_</xsl:text>
                            <xsl:value-of select="position()-1"/>
                        </xsl:attribute>
                        <xsl:attribute name="name">
                            <xsl:text>packageSizeL1_</xsl:text>
                            <xsl:value-of select="position()-1"/>
                        </xsl:attribute>
                        <xsl:attribute name="value">
                            <xsl:value-of
                                    select="$asContent/pharm:quantity/@value"/>
                        </xsl:attribute>
                    </input>
                    <xsl:text> </xsl:text>
                    <xsl:call-template name="show_quantity">
                        <xsl:with-param name="quantity" select="$asContent/pharm:quantity"/>
                        <xsl:with-param name="showValue" select="false()"/>
                    </xsl:call-template>
                    <xsl:text> </xsl:text>
                    <xsl:call-template name="show-formCode">
                        <xsl:with-param name="parameter"
                                        select="$asContent/../pharm:formCode"/>
                    </xsl:call-template>
                </li>
            </ul>
        </xsl:if>
    </xsl:template>

    <xsl:template name="show-parts-package-size-dispense-quantity" >
        <xsl:param name="quantity"/>
        <xsl:if test="$quantity">
            <input type="text" size="5">
                <xsl:attribute name="id">
                    <xsl:text>quantity_</xsl:text>
                    <xsl:value-of select="position()-1"/>
                </xsl:attribute>
                <xsl:attribute name="data-dispense-field">
                    <xsl:text>quantity_</xsl:text>
                    <xsl:value-of select="position()-1"/>
                </xsl:attribute>
                <xsl:attribute name="name">
                    <xsl:text>quantity_</xsl:text>
                    <xsl:value-of select="position()-1"/>
                </xsl:attribute>
                <xsl:attribute name="value">
                    <xsl:value-of
                            select="$quantity/@value"/>
                </xsl:attribute>
            </input>
            <xsl:text> </xsl:text>
            <xsl:call-template name="show_quantity">
                <xsl:with-param name="quantity" select="$quantity"/>
                <xsl:with-param name="showValue" select="false()"/>
            </xsl:call-template>
            <xsl:text> </xsl:text>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>