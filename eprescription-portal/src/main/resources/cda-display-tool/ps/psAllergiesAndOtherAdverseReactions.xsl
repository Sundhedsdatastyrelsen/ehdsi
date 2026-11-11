<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" version="2.0">

    <xsl:variable name="allergiesAndIntolerancesSectionCode"
                  select="'48765-2'"/>

    <!-- eHDSI Allergies and Other Adverse Reactions -->
    <xsl:template name="allergiesAndIntolerances" match="/">
        <xsl:choose>
            <xsl:when test="not(/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$allergiesAndIntolerancesSectionCode]])">
                <span class="sectionTitle">
                    <!-- The Allergies, adverse reactions, alerts section is missing ! -->
                    <xsl:call-template name="show-eHDSIDisplayLabel">
                        <xsl:with-param name="code" select="'73'"/>
                    </xsl:call-template>
                </span>
            </xsl:when>
            <xsl:otherwise>
                <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$allergiesAndIntolerancesSectionCode]]"/>
            </xsl:otherwise>
        </xsl:choose>
        <br/>
        <br/>
    </xsl:template>

    <xsl:template match = "/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$allergiesAndIntolerancesSectionCode]]">
        <!-- Defining all needed variables -->
        <xsl:variable name="act"
                      select="n1:entry/n1:act"/>
        <xsl:variable name="observation"
                      select="$act/n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.16']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.17']/.."/>
        <xsl:variable name="observationValue"
                      select="$observation/n1:value"/>
        <!-- End definition of variables-->
        <div class="wrap-collapsible">
            <input id="collapsible-allergies-and-intolerances-section" class="toggle" type="checkbox" checked="true" />
            <label for="collapsible-allergies-and-intolerances-section" class="lbl-toggle-title">
                <!-- Section title -->
                <xsl:call-template name="show-eHDSISection">
                    <xsl:with-param name="code" select="$allergiesAndIntolerancesSectionCode"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-title">
                <div class="content-inner-title">
                    <div class="wrap-collapsible">
                        <input id="collapsible-allergies-and-intolerances-original" class="toggle" type="checkbox"/>
                        <label for="collapsible-allergies-and-intolerances-original" class="lbl-toggle">
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
                        <input id="collapsible-allergies-and-intolerances-translated" class="toggle" type="checkbox" checked="true"/>
                        <label for="collapsible-allergies-and-intolerances-translated" class="lbl-toggle">
                            <xsl:value-of select="$translatedCodedTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <table class="translation_table">
                                    <xsl:choose>
                                        <xsl:when test="not($act/@nullFlavor)">
                                            <table class="translation_table">
                                                <tbody>
                                                    <xsl:choose>
                                                        <xsl:when test="$observationValue/@code='no-known-allergies' or $observationValue/@code='no-allergy-info'">
                                                            <tr>
                                                                <td colspan="5">
                                                                    <xsl:call-template name="show-eHDSIAbsentOrUnknownAllergy">
                                                                        <xsl:with-param name="node" select="$observationValue"/>
                                                                    </xsl:call-template>
                                                                </td>
                                                            </tr>
                                                        </xsl:when>
                                                        <xsl:otherwise>
                                                            <tr>
                                                                <th>
                                                                    <!-- Reaction Type header -->
                                                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                        <xsl:with-param name="code" select="'65'"/>
                                                                    </xsl:call-template>
                                                                </th>
                                                                <th>
                                                                    <!-- Clinical Manifestation header -->
                                                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                        <xsl:with-param name="code" select="'10'"/>
                                                                    </xsl:call-template>
                                                                </th>
                                                                <th>
                                                                    <!-- Agent header -->
                                                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                        <xsl:with-param name="code" select="'5'"/>
                                                                    </xsl:call-template>
                                                                </th>
                                                                <th>
                                                                    <!-- Duration header -->
                                                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                        <xsl:with-param name="code" select="'155'"/>
                                                                    </xsl:call-template>
                                                                </th>
                                                                <th>
                                                                    <!-- Severity header -->
                                                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                        <xsl:with-param name="code" select="'123'"/>
                                                                    </xsl:call-template>
                                                                </th>
                                                                <th>
                                                                    <!-- Criticality header -->
                                                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                        <xsl:with-param name="code" select="'156'"/>
                                                                    </xsl:call-template>
                                                                </th>
                                                                <th>
                                                                    <!-- Allergy Status header -->
                                                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                        <xsl:with-param name="code" select="'157'"/>
                                                                    </xsl:call-template>
                                                                </th>
                                                                <th>
                                                                    <!-- Certainty header -->
                                                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                                                        <xsl:with-param name="code" select="'158'"/>
                                                                    </xsl:call-template>
                                                                </th>
                                                                <xsl:apply-templates select="n1:entry/n1:act" mode="allergiesAndIntolerances"/>
                                                            </tr>
                                                        </xsl:otherwise>
                                                    </xsl:choose>
                                                </tbody>
                                            </table>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <xsl:call-template name="show-eHDSINullFlavor">
                                                <xsl:with-param name="code" select="$act/@nullFlavor"/>
                                            </xsl:call-template>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="n1:entry/n1:act" mode="allergiesAndIntolerances">
        <xsl:choose>
            <xsl:when test="not(@nullFlavor)">
                <xsl:apply-templates select="n1:entryRelationship[@typeCode='SUBJ']/n1:observation"/>
            </xsl:when>
            <xsl:otherwise>
                <tr>
                    <td colspan="5">
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="./@nullFlavor"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="n1:entryRelationship[@typeCode='SUBJ']/n1:observation">
        <tr>
            <td>
                <!-- Reaction Type -->
                <xsl:apply-templates select="n1:code" mode="allergiesAndIntolerances"/>
            </td>
            <td>
                <!-- Clinical Manifestation -->
                <xsl:apply-templates select="n1:entryRelationship[@typeCode='MFST']/n1:observation" mode="clinicalManifestation" />
            </td>
            <td>
                <!-- Agent -->
                <xsl:apply-templates select="n1:participant[@typeCode='CSM']/n1:participantRole[@classCode='MANU']/n1:playingEntity[@classCode='MMAT']" />
            </td>
            <td>
                <!-- OnSet Date -->
                <xsl:call-template name="show-IVL_TS">
                    <xsl:with-param name="node" select="n1:effectiveTime"/>
                </xsl:call-template>
            </td>
            <td>
                <!-- Severity -->
                <xsl:apply-templates select="n1:entryRelationship[@typeCode='MFST']/n1:observation/n1:entryRelationship[@typeCode='SUBJ']/n1:observation" mode="severity" />
            </td>
            <td>
                <!-- Criticality -->
                <xsl:apply-templates select="n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:code[@code='82606-5']" mode="criticality" />
            </td>
            <td>
                <!-- Allergy Status -->
                <xsl:apply-templates select="n1:entryRelationship[@typeCode='REFR']/n1:observation/n1:code[@code='33999-4']" mode="allergyStatus" />
            </td>
            <td>
                <!-- Certainty -->
                <xsl:apply-templates select="n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:code[@code='66455-7']" mode="certainty" />
            </td>
        </tr>
    </xsl:template>

    <xsl:template match="n1:code" mode="allergiesAndIntolerances">
        <xsl:call-template name="show-eHDSIAdverseEventType">
            <xsl:with-param name="node" select="."/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="n1:entryRelationship[@typeCode='MFST']/n1:observation" mode="clinicalManifestation">
        <xsl:if test="position() > 1">
            <xsl:text>, </xsl:text>
        </xsl:if>
        <xsl:choose>
            <xsl:when test="not(n1:value/@nullFlavor)">
                <xsl:call-template name="show-eHDSIReactionAllergy">
                    <xsl:with-param name="node" select="n1:value"/>
                </xsl:call-template>
                <xsl:call-template name="show-eHDSIIllnessandDisorder">
                    <xsl:with-param name="node" select="n1:value"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="n1:value/@nullFlavor"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="n1:participant[@typeCode='CSM']/n1:participantRole[@classCode='MANU']/n1:playingEntity[@classCode='MMAT']">
        <xsl:choose>
            <xsl:when test="not(n1:code/@nullFlavor)">
                <xsl:call-template name="show-eHDSIAllergenNoDrug">
                    <xsl:with-param name="node" select="n1:code"/>
                </xsl:call-template>
                <xsl:call-template name="show-eHDSIActiveIngredient">
                    <xsl:with-param name="node" select="n1:code"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="handle-nullFlavor">
                    <xsl:with-param name="node" select="n1:code"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="n1:entryRelationship[@typeCode='SUBJ']/n1:observation" mode="severity">
        <xsl:call-template name="show-eHDSISeverity">
            <xsl:with-param name="node" select="n1:value"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="n1:entryRelationship[@typeCode='REFR']/n1:observation/n1:code[@code='33999-4']" mode="allergyStatus">
        <xsl:call-template name="show-eHDSIAllergyStatus">
            <xsl:with-param name="node" select="../n1:value"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:code[@code='82606-5']" mode="criticality">
        <xsl:call-template name="show-eHDSICriticality">
            <xsl:with-param name="node" select="../n1:value"/>
        </xsl:call-template>
    </xsl:template>

    <xsl:template match="n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:code[@code='66455-7']" mode="certainty">
        <xsl:call-template name="show-eHDSICertainty">
            <xsl:with-param name="node" select="../n1:value"/>
        </xsl:call-template>
    </xsl:template>
</xsl:stylesheet>