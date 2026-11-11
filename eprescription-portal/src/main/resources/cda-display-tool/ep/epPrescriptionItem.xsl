<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:pharm="urn:hl7-org:pharm"
                version="2.0">

    <xsl:import href="ePMedicinalProduct.xsl"/>
    <xsl:import href="ePPrescriptionDetails.xsl"/>
    <xsl:import href="ePPackagingDetails.xsl"/>
    <xsl:import href="ePDispensationDetails.xsl"/>

    <xsl:template match="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.2.1']/n1:entry/n1:substanceAdministration">
        <fieldset style="min-height:100px;">
            <legend>
                <!-- Pharmaceutical Substance Header -->
                <b><xsl:apply-templates select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial"/></b></legend>
            <table>
                <tr>
                    <td>
                        <xsl:call-template name="epPrescriptionItemDetails"/>
                    </td>
                </tr>
            </table>
        </fieldset>
    </xsl:template>

    <!-- Pharmaceutical Substance Header -->
    <xsl:template match="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial">
        &#160;
        <xsl:value-of select="n1:name"/>
        <xsl:choose>
            <xsl:when test="pharm:asSpecializedKind">
                <xsl:variable name="code" select="pharm:asSpecializedKind/pharm:generalizedMaterialKind/pharm:code"/>
                <xsl:choose>
                    <xsl:when test="not($code/@nullFlavor)">
                        <xsl:if test="$code/@codeSystem='2.16.840.1.113883.6.73'">
                            (<span class="codeSystem">ATC</span>
                            &#160;
                            <span class="codeSystem">
                                <xsl:value-of select="$code/@code"/>
                            </span>
                            &#160;
                            <xsl:call-template name="show-eHDSIActiveIngredient">
                                <xsl:with-param name="node" select="$code"/>
                            </xsl:call-template>
                            )
                        </xsl:if>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:text> - </xsl:text>
                        <xsl:value-of select="pharm:asSpecializedKind/pharm:generalizedMaterialKind/pharm:name"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
        &#160;
    </xsl:template>

    <xsl:template name="frequency">
        <xsl:call-template name="show-frequencyIntake">
            <xsl:with-param name="medFrequencyIntakeType" select="n1:effectiveTime[2]/@xsi:type"/>
            <xsl:with-param name="medFrequencyIntake" select="n1:effectiveTime[2]"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template name="add-underscore">
        <xsl:param name="variable"/>
        <xsl:value-of select="$variable"/>
        <xsl:value-of select="'_'"/>
        <xsl:value-of select="position()-1"/>
    </xsl:template>

    <xsl:template name="add-javascript-variable">
        <xsl:param name="variable"/>
        <xsl:param name="name"/>
        <xsl:value-of select="'var '"/>
        <xsl:value-of select="$variable"/>
        <xsl:value-of select="'_'"/>
        <xsl:value-of select="position()-1"/>
        <xsl:value-of select="'=document.getElementById('"/>
        <xsl:text>&apos;</xsl:text>
        <xsl:value-of select="$name"/>
        <xsl:value-of select="'_'"/>
        <xsl:value-of select="position()-1"/>
        <xsl:text>&apos;</xsl:text>
        <xsl:value-of select="');'"/>
    </xsl:template>

    <xsl:template name="number-of-packages">
        <xsl:param name="supply"/>
        <xsl:choose>
            <xsl:when test="$supply/n1:supply/@moodCode='RQO'">
                <xsl:choose>
                    <xsl:when test="$supply/n1:supply/n1:independentInd/@value='false'">
                        <xsl:value-of select="$supply/n1:supply/n1:quantity/@value"/>
                    </xsl:when>
                </xsl:choose>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="pure-substitution-code">
        <xsl:variable name="code" select="n1:entryRelationship[@typeCode='SUBJ'][@inversionInd='true']/n1:observation[@classCode='OBS']"/>
        <xsl:choose>
            <xsl:when test="$code/n1:code/@code='SUBST'">
                <xsl:choose>
                    <xsl:when test="$code/n1:code/@codeSystem='2.16.840.1.113883.5.6'">
                        <xsl:choose>
                            <xsl:when test="$code/n1:value/@code='N'">
                                <xsl:value-of select="'No'"/>
                            </xsl:when>
                            <xsl:otherwise>
                                <xsl:value-of select="'Yes'"/>
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="'Yes'"/>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="'Yes'"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="substitution-code">
        <xsl:variable name="code">
            <xsl:call-template name="pure-substitution-code"/>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="$code='No'">
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'42'"/>
                </xsl:call-template>
                <!-- <xsl:value-of select="'No'"/>  -->
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'81'"/>
                </xsl:call-template>
                <!--  <xsl:value-of select="'Yes'"/> -->
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="show-active-ingredient">
        <xsl:param name="code"/>
        <xsl:param name="name"/>
        <xsl:param name="strength"/>
        <td>
            <xsl:if test="$code/@codeSystem and $code/@codeSystem='2.16.840.1.113883.6.73'">ATC</xsl:if>
            <xsl:if test="$code/@codeSystem and $code/@codeSystem='2.16.840.1.113883.3.6905.2'">EMA SMS</xsl:if>
        </td>
        <td>
            <xsl:if test="$code/@code and ($code/@codeSystem='2.16.840.1.113883.6.73' or $code/@codeSystem='2.16.840.1.113883.3.6905.2')">
                <xsl:choose>
                    <xsl:when test="not($code/@nullFlavor)">
                        <xsl:value-of select="$code/@code"/>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="$code/@nullFlavor"/>
                        </xsl:call-template>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:if>
        </td>
        <td>
            <xsl:choose>
                <xsl:when test="not($code/@nullFlavor)">
                    <xsl:call-template name="show-eHDSIActiveIngredient">
                        <xsl:with-param name="node" select="$code"/>
                    </xsl:call-template>
                    <xsl:call-template name="show-eHDSISubstance">
                        <xsl:with-param name="node" select="$code"/>
                    </xsl:call-template>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:choose>
                        <xsl:when test="not($name)">
                            <xsl:call-template name="show-eHDSINullFlavor">
                                <xsl:with-param name="code" select="code/@nullFlavor"/>
                            </xsl:call-template>
                        </xsl:when>
                        <xsl:otherwise>
                            <div class="tooltip">
                                <span class="narrative">
                                    <xsl:value-of select="$name"/>
                                </span>
                                <xsl:text>&#10;</xsl:text>
                                <span class="tooltiptext">Narrative text</span>
                            </div>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:otherwise>
            </xsl:choose>
        </td>
        <td>
            <xsl:call-template name="show-strength">
                <xsl:with-param name="node" select="$strength"/>
            </xsl:call-template>
        </td>
    </xsl:template>

    <xsl:variable name="numberOfPacks">
        <xsl:call-template name="number-of-packages">
            <xsl:with-param name="supply" select="n1:entryRelationship[@typeCode='COMP']"/>
        </xsl:call-template>
    </xsl:variable>

    <xsl:template name="inputform">
        <xsl:param name="txt"/>
        <xsl:param name="val"/>
        <xsl:attribute name="id">
            <xsl:value-of select="$txt"/>
            <xsl:value-of select="position()-1"/>
        </xsl:attribute>
        <xsl:attribute name="name">
            <xsl:value-of select="$txt"/>
            <xsl:value-of select="position()-1"/>
        </xsl:attribute>
        <xsl:attribute name="value">
            <xsl:value-of select="$val"/>
        </xsl:attribute>
    </xsl:template>

    <xsl:template name="inputformID">
        <xsl:param name="txt"/>
        <xsl:param name="root"/>
        <xsl:param name="extension"/>
        <xsl:attribute name="id">
            <xsl:value-of select="$txt"/>
            <xsl:value-of select="position()-1"/>
        </xsl:attribute>
        <xsl:attribute name="name">
            <xsl:value-of select="$txt"/>
            <xsl:value-of select="position()-1"/>
        </xsl:attribute>
        <xsl:attribute name="value">
            <xsl:choose>
                <xsl:when test="$extension">
                    <xsl:value-of select="concat($root, '^', $extension)"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select="$root"/>
                </xsl:otherwise>
            </xsl:choose>
        </xsl:attribute>
    </xsl:template>

    <xsl:template name="add-newUnitMeasure">
        <xsl:param name="unit"/>
        <xsl:text disable-output-escaping="yes">&lt;option value=&quot;</xsl:text>
        <xsl:value-of select="$unit"/>
        <xsl:text disable-output-escaping="yes">&quot; selected=&quot;selected&quot;&gt;</xsl:text>
        <xsl:value-of select="$unit"/>
        <xsl:text disable-output-escaping="yes">&lt;/option&gt;</xsl:text>
    </xsl:template>

    <xsl:template name="check-Parameter">
        <xsl:param name="parameter"/>
        <xsl:value-of select="''"/>
        <xsl:choose>
            <xsl:when test="$parameter">
                <xsl:value-of select="'false'"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="check-EffectiveTime">
        <xsl:value-of select="''"/>
        <xsl:choose>
            <xsl:when test="n1:effectiveTime[2]/n1:period/@nullFlavor">
                <xsl:value-of select="'false'"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="epPrescriptionItemDetails">
        <xsl:attribute name="id">
            <xsl:text>mytable</xsl:text>
            <xsl:value-of select="position()"/>
        </xsl:attribute>
        <form name="DISPENSE_FORM" method="post">
            <xsl:attribute name="action">
                <xsl:value-of select="$actionpath"/>
            </xsl:attribute>
            <table>
                <tr>
                    <td>
                        <xsl:call-template name="medicinalProductBlock"/>
                    </td>
                    <td>
                        <xsl:call-template name="prescriptionDetailsBlock"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <xsl:call-template name="packagingDetailsBlock"/>
                    </td>
                    <td>
                        <xsl:call-template name="dispensationDetailsBlock"/>
                    </td>
                </tr>
            </table>
        </form>
        <script>
            <xsl:attribute name="type">
                <xsl:text>text/javascript</xsl:text>
            </xsl:attribute>
            <xsl:if test="position()>1">
                <xsl:text disable-output-escaping="yes">showhide(&apos;mytable</xsl:text>
                <xsl:value-of select="position()"/>
                <xsl:text disable-output-escaping="yes">&apos;);</xsl:text>&#160;
            </xsl:if>

            <!-- Check for the value of the checkbox -->
            <xsl:call-template name="add-javascript-variable">
                <xsl:with-param name="variable" select="'cb'"/>
                <xsl:with-param name="name" select="'dispense'"/>
            </xsl:call-template>&#160;
            <xsl:call-template name="add-javascript-variable">
                <xsl:with-param name="variable" select="'productValue'"/>
                <xsl:with-param name="name" select="'dispensedProductValue'"/>
            </xsl:call-template>&#160;
            <xsl:variable name="substitutionValueCheck">
                <xsl:call-template name="pure-substitution-code"/>
            </xsl:variable>
            <xsl:choose>
                <xsl:when test="$substitutionValueCheck ='Yes'">
                    <xsl:call-template name="add-javascript-variable">
                        <xsl:with-param name="variable" select="'measures'"/>
                        <xsl:with-param name="name" select="'measures'"/>
                    </xsl:call-template>&#160;
                </xsl:when>
            </xsl:choose>
            <xsl:text disable-output-escaping="yes">if  (</xsl:text>
            <xsl:call-template name="add-underscore">
                <xsl:with-param name="variable" select="'cb'"/>
            </xsl:call-template>
            <xsl:text disable-output-escaping="yes">.checked == 0 ) { </xsl:text> &#160;
            <xsl:call-template name="add-underscore">
                <xsl:with-param name="variable" select="'productValue'"/>
            </xsl:call-template>
            <xsl:text disable-output-escaping="yes">.disabled=&apos;true&apos;;</xsl:text>&#160;
            <xsl:choose>
                <xsl:when test="$substitutionValueCheck ='Yes'">
                    <xsl:call-template name="add-underscore">
                        <xsl:with-param name="variable" select="'measures'"/>
                    </xsl:call-template>
                    <xsl:text disable-output-escaping="yes">.disabled=&apos;true&apos;;</xsl:text>&#160;
                </xsl:when>
            </xsl:choose>
            <xsl:text disable-output-escaping="yes">};</xsl:text>&#160;
            <xsl:text disable-output-escaping="yes">function </xsl:text>
            <xsl:call-template name="add-underscore">
                <xsl:with-param name="variable" select="'enableValues'"/>
            </xsl:call-template>
            <xsl:text disable-output-escaping="yes">(){</xsl:text>&#160;
            <xsl:text disable-output-escaping="yes">if  (</xsl:text>
            <xsl:call-template name="add-underscore">
                <xsl:with-param name="variable" select="'cb'"/>
            </xsl:call-template>
            <xsl:text disable-output-escaping="yes">.checked == 0 ) { </xsl:text> &#160;
            <xsl:call-template name="add-underscore">
                <xsl:with-param name="variable" select="'productValue'"/>
            </xsl:call-template>
            <xsl:text disable-output-escaping="yes">.disabled=&apos;true&apos;; </xsl:text>
            <xsl:choose>
                <xsl:when test="$substitutionValueCheck ='Yes'">
                    <xsl:call-template name="add-underscore">
                        <xsl:with-param name="variable" select="'measures'"/>
                    </xsl:call-template>
                    <xsl:text disable-output-escaping="yes">.disabled=&apos;true&apos;;</xsl:text>&#160;
                </xsl:when>
            </xsl:choose>
            <xsl:text> } else {</xsl:text>&#160;
            <xsl:call-template name="add-underscore">
                <xsl:with-param name="variable" select="'productValue'"/>
            </xsl:call-template>
            <xsl:text disable-output-escaping="yes">};</xsl:text>&#160;
            <xsl:text disable-output-escaping="yes">};</xsl:text>
        </script>
    </xsl:template>

    <xsl:template name="check-FrequencyOfIntakes">
        <xsl:value-of select="''"/>
        <xsl:choose>
            <xsl:when test="n1:effectiveTime[2]/@nullFlavor">
                <xsl:value-of select="'false'"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="show_quantity">
        <xsl:param name="quantity"/>
        <xsl:param name="showValue"/>
        <xsl:variable name="hasPackage">
            <xsl:call-template name="check-Parameter">
                <xsl:with-param name="parameter"
                                select="$quantity/@nullFlavor"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="$hasPackage = 'false'">
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code"
                                    select="$quantity/@nullFlavor"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="show-package">
                    <xsl:with-param name="medPackage"
                                    select="$quantity"/>
                    <xsl:with-param name="showValue" select="$showValue"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="show-package-size">
        <xsl:param name="quantity"/>
        <xsl:param name="asContent_level1"/>
        <xsl:param name="asContent_level2"/>
        <xsl:param name="asContent_level3"/>
        <ul>
            <xsl:choose>
                <xsl:when test="$asContent_level3">
                    <li>
                        <xsl:call-template name="show-package-size-quantity">
                            <xsl:with-param name="quantity" select="$quantity"/>
                        </xsl:call-template>
                        <xsl:call-template name="show-formCode">
                            <xsl:with-param name="parameter"
                                            select="$asContent_level3/pharm:containerPackagedProduct/pharm:formCode"/>
                        </xsl:call-template>
                        <ul>
                            <li>
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
                                    <xsl:call-template name="show-package-size-level2">
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
                        <xsl:call-template name="show-package-size-quantity">
                            <xsl:with-param name="quantity" select="$quantity"/>
                        </xsl:call-template>
                        <xsl:call-template name="show-package-size-level2">
                            <xsl:with-param name="asContent_level1" select="$asContent_level1"/>
                            <xsl:with-param name="asContent_level2" select="$asContent_level2"/>
                        </xsl:call-template>
                    </li>
                </xsl:when>
                <xsl:otherwise>
                    <ul>
                        <li>
                            <xsl:call-template name="show-package-size-quantity">
                                <xsl:with-param name="quantity" select="$quantity"/>
                            </xsl:call-template>
                            <xsl:call-template name="show-package-size-level1">
                                <xsl:with-param name="asContent" select="$asContent_level1"/>
                            </xsl:call-template>
                        </li>
                    </ul>
                </xsl:otherwise>
            </xsl:choose>
        </ul>
    </xsl:template>

    <xsl:template name="show-package-size-level2">
        <xsl:param name="asContent_level1"/>
        <xsl:param name="asContent_level2"/>
        <xsl:call-template name="show-formCode">
            <xsl:with-param name="parameter"
                            select="$asContent_level2/pharm:containerPackagedProduct/pharm:formCode"/>
        </xsl:call-template>
        <ul>
            <li>
                <xsl:call-template name="show_quantity">
                    <xsl:with-param name="quantity" select="$asContent_level2/pharm:quantity"/>
                    <xsl:with-param name="showValue" select="true()"/>
                </xsl:call-template>
                <xsl:text> </xsl:text>
                <xsl:call-template name="show-package-size-level1">
                    <xsl:with-param name="asContent" select="$asContent_level1"/>
                </xsl:call-template>
            </li>
        </ul>
    </xsl:template>

    <xsl:template name="show-package-size-level1">
        <xsl:param name="asContent"/>
        <xsl:call-template name="show-formCode">
            <xsl:with-param name="parameter"
                            select="$asContent/pharm:containerPackagedProduct/pharm:formCode"/>
        </xsl:call-template>
        <!-- Not applicable for the part case -->
        <xsl:if test="$asContent/pharm:quantity">
            <ul>
                <li>
                    <xsl:call-template name="show_quantity">
                        <xsl:with-param name="quantity" select="$asContent/pharm:quantity"/>
                        <xsl:with-param name="showValue" select="true()"/>
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

    <xsl:template name="show-package-size-quantity">
        <xsl:param name="quantity"/>
        <xsl:if test="$quantity">
            <xsl:call-template name="show_quantity">
                <xsl:with-param name="quantity" select="$quantity"/>
                <xsl:with-param name="showValue" select="true()"/>
            </xsl:call-template>
            <xsl:text> </xsl:text>
        </xsl:if>
    </xsl:template>
</xsl:stylesheet>