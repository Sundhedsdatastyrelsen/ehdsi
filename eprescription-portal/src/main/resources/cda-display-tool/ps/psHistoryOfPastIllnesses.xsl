<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" version="2.0">

    <xsl:variable name="historyOfPastIllnessesSectionCode"
                  select="'11348-0'"/>

    <!-- history of past illnesses -->
    <xsl:template name="historyOfPastIllnesses" match="/">
        <xsl:apply-templates select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$historyOfPastIllnessesSectionCode]]"/>
    </xsl:template>

    <xsl:template match = "/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section[n1:code[@code=$historyOfPastIllnessesSectionCode]]">
        <div class="wrap-collapsible">
            <input id="collapsible-history-of-past-illnesses-section" class="toggle" type="checkbox" checked="true" />
            <label for="collapsible-history-of-past-illnesses-section" class="lbl-toggle-title">
                <!-- Section title -->
                <xsl:call-template name="show-eHDSISection">
                    <xsl:with-param name="code" select="$historyOfPastIllnessesSectionCode"/>
                </xsl:call-template>
            </label>
            <div class="collapsible-content-title">
                <div class="content-inner-title">
                    <div class="wrap-collapsible">
                        <input id="collapsible-history-of-past-illnesses-original" class="toggle" type="checkbox"/>
                        <label for="collapsible-history-of-past-illnesses-original" class="lbl-toggle">
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
                        <input id="collapsible-history-of-past-illnesses-translated" class="toggle" type="checkbox" checked="true"/>
                        <label for="collapsible-history-of-past-illnesses-translated" class="lbl-toggle">
                            <xsl:value-of select="$translatedCodedTableTitle"/>
                        </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <table class="translation_table" width="100%">
                                    <colgroup>
                                        <col span="1" style="width: 15%;"/>
                                        <col span="1" style="width: 8%;"/>
                                        <col span="1" style="width: 8%;"/>
                                        <col span="1" style="width: 10%;"/>
                                        <col span="1" style="width: 10%;"/>
                                        <col span="1" style="width: 30%;"/>
                                        <col span="1" style="width: 19%;"/>
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>
                                                <!-- Closed Inactive Problem Header -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'11'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!-- OnSet Date Header -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'45'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!-- End Date Header -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'26'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!-- Problem Status Code Header -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'167'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!-- Diagnosis Assertion Status Header -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'199'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!-- Related Health Professional Header -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'200'"/>
                                                </xsl:call-template>
                                            </th>
                                            <th>
                                                <!-- Related External Resource Header -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'201'"/>
                                                </xsl:call-template>
                                            </th>
                                        </tr>
                                        <!-- General Problems -->
                                        <xsl:apply-templates select="n1:entry/n1:act/n1:entryRelationship[@typeCode='SUBJ']/n1:observation[n1:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.7']/n1:value[not(@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.44.5')]" mode="historyOfPastIllnesses"/>
                                        <!-- Rare Diseases -->
                                        <tr>
                                            <th class="subtitle" colspan="7">
                                                <!-- Rare Diseases -->
                                                <xsl:call-template name="show-eHDSIDisplayLabel">
                                                    <xsl:with-param name="code" select="'198'"/>
                                                </xsl:call-template>
                                            </th>
                                        </tr>
                                        <xsl:apply-templates select="n1:entry/n1:act/n1:entryRelationship[@typeCode='SUBJ']/n1:observation[n1:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.7']/n1:value[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.44.5']" mode="historyOfRareDiseases"/>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br />
    </xsl:template>

    <xsl:template match="n1:entry/n1:act/n1:entryRelationship[@typeCode='SUBJ']/n1:observation[n1:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.7']/n1:value[@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.44.5']" mode="historyOfRareDiseases">

        <xsl:variable name="problemCondition"
                      select="."/>
        <xsl:variable name="probOnSetDate"
                      select="../../../n1:effectiveTime/n1:low"/>
        <xsl:variable name="probEndDate"
                      select="../../../n1:effectiveTime/n1:high"/>
        <xsl:variable name="problemStatusCode"
                      select="../n1:entryRelationship[@typeCode='REFR']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.9']/../n1:value"/>
        <xsl:variable name="diagnosisAssertionStatus"
                      select="../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.49']/../n1:value"/>
        <xsl:choose>
            <xsl:when test="@nullFlavor and not(@nullFlavor='OTH')">
                <tr>
                    <td colspan="7">
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="./@nullFlavor"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </xsl:when>
            <xsl:otherwise>
                <tr>
                    <td>
                        <!-- History Of Past Problem -->
                        <xsl:call-template name="show-eHDSIRareDiseases">
                            <xsl:with-param name="node" select="$problemCondition"/>
                        </xsl:call-template>
                        <xsl:choose>
                            <xsl:when test="not($problemCondition/@nullFlavor)">
                                <xsl:text> (</xsl:text>
                                <xsl:value-of select="$problemCondition/@code"/>
                                <xsl:text>)</xsl:text>
                            </xsl:when>
                            <xsl:when test="$problemCondition/@nullFlavor='OTH'">
                                <i>
                                    <xsl:text> (</xsl:text>
                                    <xsl:value-of select="$problemCondition/n1:translation/@code"/>
                                    <xsl:if test="$problemCondition/n1:translation/@codeSystemName">
                                        <xsl:text> - </xsl:text>
                                        <xsl:value-of select="$problemCondition/n1:translation/@codeSystemName"/>
                                    </xsl:if>
                                    <xsl:text>)</xsl:text>
                                </i>
                            </xsl:when>
                        </xsl:choose>
                    </td>
                    <td>
                        <!-- OnSet Date -->
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="$probOnSetDate"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <!-- End Date -->
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="$probEndDate"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <!-- Problem Status code -->
                        <xsl:call-template name="show-eHDSIStatusCode">
                            <xsl:with-param name="node" select="$problemStatusCode"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <!-- Diagnosis Assertion Status -->
                        <xsl:call-template name="show-eHDSICertainty">
                            <xsl:with-param name="node" select="$diagnosisAssertionStatus"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <!-- Related Health Professional -->
                        <xsl:for-each select="../n1:entryRelationship/n1:act/n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.48']/..">
                            <xsl:apply-templates select="n1:performer"/>
                        </xsl:for-each>
                    </td>
                    <td>
                        <!-- Related External Resource -->
                        <xsl:for-each select="../n1:reference[@typeCode='REFR']">
                            <xsl:apply-templates select="n1:externalDocument"/>
                        </xsl:for-each>
                    </td>
                </tr>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="n1:entry/n1:act/n1:entryRelationship[@typeCode='SUBJ']/n1:observation[n1:templateId/@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.7']/n1:value[not(@codeSystem='1.3.6.1.4.1.12559.11.10.1.3.1.44.5')]" mode="historyOfPastIllnesses">

        <xsl:variable name="problemCondition"
                      select="."/>
        <xsl:variable name="probOnSetDate"
                      select="../../../n1:effectiveTime/n1:low"/>
        <xsl:variable name="probEndDate"
                      select="../../../n1:effectiveTime/n1:high"/>
        <xsl:variable name="problemStatusCode"
                      select="../n1:entryRelationship[@typeCode='REFR']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.9']/../n1:value"/>
        <xsl:variable name="diagnosisAssertionStatus"
                      select="../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.49']/../n1:value"/>
        <xsl:choose>
            <xsl:when test="@nullFlavor and not(@nullFlavor='OTH')">
                <tr>
                    <td colspan="7">
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="./@nullFlavor"/>
                        </xsl:call-template>
                    </td>
                </tr>
            </xsl:when>
            <xsl:otherwise>
                <tr>
                    <td>
                        <!-- Active Problem -->
                        <xsl:call-template name="show-eHDSIIllnessandDisorder">
                            <xsl:with-param name="node" select="$problemCondition"/>
                        </xsl:call-template>
                        <xsl:choose>
                            <xsl:when test="not($problemCondition/@nullFlavor)">
                                <xsl:text> (</xsl:text>
                                <xsl:value-of select="$problemCondition/@code"/>
                                <xsl:text>)</xsl:text>
                            </xsl:when>
                            <xsl:when test="$problemCondition/@nullFlavor='OTH'">
                                <i>
                                    <xsl:text> (</xsl:text>
                                    <xsl:value-of select="$problemCondition/n1:translation/@code"/>
                                    <xsl:if test="$problemCondition/n1:translation/@codeSystemName">
                                        <xsl:text> - </xsl:text>
                                        <xsl:value-of select="$problemCondition/n1:translation/@codeSystemName"/>
                                    </xsl:if>
                                    <xsl:text>)</xsl:text>
                                </i>
                            </xsl:when>
                        </xsl:choose>
                    </td>
                    <td>
                        <!-- OnSet Date -->
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="$probOnSetDate"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <!-- End Date -->
                        <xsl:call-template name="show-TS">
                            <xsl:with-param name="node" select="$probEndDate"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <!-- Problem Status code -->
                        <xsl:call-template name="show-eHDSIStatusCode">
                            <xsl:with-param name="node" select="$problemStatusCode"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <!-- Diagnosis Assertion Status -->
                        <xsl:call-template name="show-eHDSICertainty">
                            <xsl:with-param name="node" select="$diagnosisAssertionStatus"/>
                        </xsl:call-template>
                    </td>
                    <td>
                        <!-- Related Health Professional -->
                        <xsl:for-each select="../n1:entryRelationship/n1:act/n1:templateId[@root='1.3.6.1.4.1.12559.11.10.1.3.1.3.48']/..">
                            <xsl:apply-templates select="n1:performer"/>
                        </xsl:for-each>
                    </td>
                    <td>
                        <!-- Related External Resource -->
                        <xsl:for-each select="../n1:reference[@typeCode='REFR']">
                            <xsl:apply-templates select="n1:externalDocument"/>
                        </xsl:for-each>
                    </td>
                </tr>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:template match="n1:externalDocument">
        <xsl:value-of select="n1:text/n1:reference/@value"/>
    </xsl:template>

    <xsl:template match="n1:performer">
        <div>
            <table>
                <xsl:choose>
                    <xsl:when test="n1:assignedEntity/n1:assignedPerson">
                        <xsl:call-template name="displayAssignedPerson">
                            <xsl:with-param name="assignedPerson" select="n1:assignedEntity/n1:assignedPerson"/>
                            <xsl:with-param name="contactInfoRoot" select="n1:assignedEntity"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:when test="n1:assignedEntity/n1:representedOrganization">
                        <xsl:call-template name="displayRepresentedOrganization">
                            <xsl:with-param name="representedOrganization" select="n1:assignedEntity/n1:representedOrganization"/>
                        </xsl:call-template>
                    </xsl:when>
                </xsl:choose>
            </table>
        </div>
    </xsl:template>
</xsl:stylesheet>