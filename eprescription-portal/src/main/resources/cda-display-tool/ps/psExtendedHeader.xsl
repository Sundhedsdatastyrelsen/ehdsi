<?xml version="1.0"  ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" version="2.0">

    <!--- Extended HEADER ELEMENT -->
    <!-- patient address -->
    <xsl:variable
            name="patientRole"
            select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole"/>

    <xsl:variable
            name="patientPreferLang"
            select="$patientRole/n1:patient/n1:languageCommunication/n1:languageCode"/>

    <!-- guardian -->
    <xsl:variable
            name="patientGuardian"
            select="$patientRole/n1:patient/n1:guardian"/>

    <!--Preferred HCP/ Legal Organization -->
    <xsl:variable
            name="participantPRS"
            select="/n1:ClinicalDocument/n1:participant/n1:functionCode[@code='PCP'][@codeSystem='2.16.840.1.113883.5.88']/../n1:associatedEntity[@classCode='PRS']"/>

    <!-- Legal Authenticator -->
    <xsl:variable
            name="legalAuthenticatorAssignedPerson"
            select="/n1:ClinicalDocument/n1:legalAuthenticator/n1:assignedEntity/n1:assignedPerson"/>

    <xsl:variable
            name="legalAuthenticatorRepresentedOrganization"
            select="/n1:ClinicalDocument/n1:legalAuthenticator/n1:assignedEntity/n1:representedOrganization"/>

    <!--Custodian-->
    <xsl:variable
            name="patientCustodian"
            select="/n1:ClinicalDocument/n1:custodian/n1:assignedCustodian/n1:representedCustodianOrganization"/>


    <xsl:template name="extendedCdaHeader">
        <div class="container">
            <div class="item"><xsl:call-template name="displayPatientContactInformation"/></div>
            <div class="item"><xsl:call-template name="displayPreferredHCPAndLegalOrganization"/></div>
            <div class="item"><xsl:call-template name="displayAuthors"/></div>
            <div class="item"><xsl:call-template name="displayLegalAuthenticator"/></div>
            <div class="item"><xsl:call-template name="displayOtherContacts"/></div>
            <div class="item"><xsl:call-template name="displayGuardian"/></div>
            <div class="item"><xsl:call-template name="displayCustodian"/></div>
        </div>
    </xsl:template>

    <xsl:template name="displayPatientContactInformation">
        <div class="extended_header_block">
            <table class="extended_header_table">
                <tbody>
                    <tr>
                        <th colspan="2">
                            <!-- Patient -->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'51'"/>
                            </xsl:call-template>
                        </th>
                    </tr>
                    <xsl:call-template name="displayAssignedPerson">
                        <xsl:with-param name="assignedPerson" select="$patientRole"/>
                        <xsl:with-param name="contactInfoRoot" select="$patientRole"/>
                    </xsl:call-template>
                </tbody>
            </table>
        </div>
    </xsl:template>

    <xsl:template name="displayPreferredHCPAndLegalOrganization">
        <div class="extended_header_block">
            <table class="extended_header_table">
                <tbody>
                    <tr>
                        <th colspan="2">
                            <!-- Preferred HCP/Legal organization to contact-->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'54'"/>
                            </xsl:call-template>
                        </th>
                    </tr>
                    <xsl:for-each select="$participantPRS">
                        <xsl:call-template name="displayAssignedPerson">
                            <xsl:with-param name="assignedPerson" select="n1:associatedPerson"/>
                            <xsl:with-param name="contactInfoRoot" select="."/>
                        </xsl:call-template>
                        <xsl:call-template name="displayRepresentedOrganization">
                            <xsl:with-param name="representedOrganization" select="../n1:scopingOrganization"/>
                        </xsl:call-template>
                    </xsl:for-each>
                </tbody>
            </table>
        </div>
    </xsl:template>

    <xsl:template name="displayAuthors">
        <xsl:for-each select="/n1:ClinicalDocument/n1:author">
            <xsl:variable
                    name="hcpCounter"
                    select="position()"/>
            <xsl:variable
                    name="assignedAuthor"
                    select="/n1:ClinicalDocument/n1:author[$hcpCounter]/n1:assignedAuthor"/>
            <xsl:variable
                    name="assignedPerson"
                    select="$assignedAuthor/n1:assignedPerson"/>
            <xsl:variable
                    name="assignedEntity"
                    select="/n1:ClinicalDocument/n1:documentationOf[$hcpCounter]/n1:serviceEvent/n1:performer/n1:assignedEntity"/>
            <xsl:variable
                    name="representedOrganization"
                    select="/n1:ClinicalDocument/n1:author[$hcpCounter]/n1:assignedAuthor/n1:representedOrganization"/>
            <xsl:variable
                    name="functionCode"
                    select="/n1:ClinicalDocument/n1:author[$hcpCounter]/n1:functionCode"/>
            <div class="extended_header_block">
                <table class="extended_header_table">
                    <tbody>
                        <tr>
                            <th colspan="2">
                                <xsl:choose>
                                    <xsl:when test="$assignedPerson">
                                        <!-- Author (HCP) -->
                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                            <xsl:with-param name="code" select="'7'"/>
                                        </xsl:call-template>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <!-- Authoring Device -->
                                        <xsl:call-template name="show-eHDSIDisplayLabel">
                                            <xsl:with-param name="code" select="'8'"/>
                                        </xsl:call-template>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </th>
                        </tr>
                        <tr>
                            <td>
                                <xsl:choose>
                                    <xsl:when test="$assignedPerson">
                                        <xsl:choose>
                                            <xsl:when test="not($assignedPerson/n1:name/@nullFlavor)">
                                                <xsl:call-template name="show-name">
                                                    <xsl:with-param name="name" select="$assignedPerson/n1:name"/>
                                                </xsl:call-template>
                                                &#160;
                                            </xsl:when>
                                            <xsl:otherwise>
                                                <xsl:call-template name="show-name">
                                                    <xsl:with-param name="name" select="$assignedEntity/n1:assignedPerson/n1:name"/>
                                                </xsl:call-template>
                                                <xsl:value-of select="$assignedEntity/n1:representedOrganization/n1:name"/>
                                                <xsl:call-template name="show-contactInfo">
                                                    <xsl:with-param name="contact" select="$assignedEntity/n1:representedOrganization"/>
                                                </xsl:call-template>&#160;
                                            </xsl:otherwise>
                                        </xsl:choose>
                                        <xsl:if test="$functionCode and not($functionCode/@nullFlavor)">
                                            <span class="label otherContacts-roleClass">
                                                <xsl:call-template name="show-eHDSIHealthcareProfessionalRole">
                                                    <xsl:with-param name="node" select="$functionCode"/>
                                                </xsl:call-template>
                                            </span>
                                        </xsl:if>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <!-- Manufacturer Model Name -->
                                        <xsl:value-of select="$assignedAuthor/n1:assignedAuthoringDevice/n1:manufacturerModelName"/>&#160;
                                        <!-- Software Name -->
                                        (<xsl:value-of select="$assignedAuthor/n1:assignedAuthoringDevice/n1:softwareName"/>)
                                    </xsl:otherwise>
                                </xsl:choose>
                            </td>
                            <td>
                                <xsl:call-template name="show-contactInformation">
                                    <xsl:with-param name="contactInfoRoot" select="$assignedAuthor"/>
                                </xsl:call-template>
                            </td>
                        </tr>
                        <xsl:call-template name="displayRepresentedOrganization">
                            <xsl:with-param name="representedOrganization" select="$representedOrganization"/>
                        </xsl:call-template>
                    </tbody>
                </table>
            </div>
        </xsl:for-each>
    </xsl:template>

    <xsl:template name="displayLegalAuthenticator">
        <div class="extended_header_block">
            <table class="extended_header_table">
                <tbody>
                    <tr>
                        <th colspan="2">
                            <!-- Legal Authenticator-->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'40'"/>
                            </xsl:call-template>
                        </th>
                    </tr>
                    <xsl:call-template name="displayAssignedPerson">
                        <xsl:with-param name="assignedPerson" select="$legalAuthenticatorAssignedPerson"/>
                        <xsl:with-param name="contactInfoRoot" select="n1:legalAuthenticator/n1:assignedEntity"/>
                    </xsl:call-template>
                    <xsl:call-template name="displayRepresentedOrganization">
                        <xsl:with-param name="representedOrganization" select="$legalAuthenticatorRepresentedOrganization"/>
                    </xsl:call-template>
                </tbody>
            </table>
        </div>
    </xsl:template>

    <xsl:template name="displayOtherContacts">
        <div class="extended_header_block">
            <table class="extended_header_table">
                <tbody>
                    <tr>
                        <th colspan="2">
                            <!-- Other Contacts-->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'49'"/>
                            </xsl:call-template>
                        </th>
                    </tr>
                    <xsl:for-each select="/n1:ClinicalDocument/n1:participant/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.2.4']/../n1:associatedEntity">
                        <tr>
                            <xsl:if test="not(../n1:functionCode) or not(../n1:functionCode/@code='PCP')">
                                <xsl:if test="n1:associatedPerson/n1:name/* or n1:scopingOrganization">
                                    <td>
                                        <xsl:if test="not(n1:associatedPerson/n1:name/@nullFlavor)">
                                            <xsl:call-template name="show-name">
                                                <xsl:with-param name="name" select="n1:associatedPerson/n1:name"/>
                                            </xsl:call-template>
                                        </xsl:if>
                                        <xsl:value-of select="n1:scopingOrganization/n1:name"/>&#160;
                                        <xsl:if test="@classCode">
                                            <span class="label otherContacts-roleClass">
                                                <xsl:call-template name="show-eHDSIRoleClass">
                                                    <xsl:with-param name="code" select="@classCode"/>
                                                </xsl:call-template>
                                            </span>&#160;
                                        </xsl:if>
                                        <xsl:if test="../n1:functionCode and not(../n1:functionCode/@nullFlavor)">
                                            <span class="label otherContacts-personalRelationship">
                                                <xsl:call-template name="show-eHDSIPersonalRelationship">
                                                    <xsl:with-param name="node" select="../n1:functionCode"/>
                                                </xsl:call-template>
                                            </span>
                                        </xsl:if>
                                        <xsl:if test="../n1:associatedEntity/n1:code and not(../n1:associatedEntity/n1:code/@nullFlavor)">
                                            <span class="label otherContacts-personalRelationship">
                                                <xsl:call-template name="show-eHDSIPersonalRelationship">
                                                    <xsl:with-param name="node" select="../n1:associatedEntity/n1:code"/>
                                                </xsl:call-template>
                                            </span>
                                        </xsl:if>
                                    </td>
                                    <td>
                                        <table class="contact_information_table">
                                            <tr>
                                                <th colspan="2">
                                                    <!-- Contact Information -->
                                                    <xsl:call-template name="show-eHDSIDisplayLabel">
                                                        <xsl:with-param name="code" select="'12'"/>
                                                    </xsl:call-template>
                                                </th>
                                            </tr>
                                            <xsl:call-template name="show-contactInfo">
                                                <xsl:with-param name="contact" select="."/>
                                            </xsl:call-template>
                                        </table>
                                    </td>
                                </xsl:if>
                            </xsl:if>
                        </tr>
                    </xsl:for-each>
                </tbody>
            </table>
        </div>
    </xsl:template>

    <xsl:template name="displayGuardian">
        <div class="extended_header_block">
            <table class="extended_header_table">
                <tbody>
                    <tr>
                        <th colspan="2">
                            <!-- Guardian-->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'35'"/>
                            </xsl:call-template>
                        </th>
                    </tr>
                    <xsl:for-each select="$patientGuardian">
                        <xsl:call-template name="displayAssignedPerson">
                            <xsl:with-param name="assignedPerson" select="n1:guardianPerson"/>
                            <xsl:with-param name="contactInfoRoot" select="."/>
                        </xsl:call-template>
                    </xsl:for-each>
                </tbody>
            </table>
        </div>
    </xsl:template>

    <xsl:template name="displayCustodian">
        <div class="extended_header_block">
            <table class="extended_header_table">
                <tbody>
                    <tr>
                        <th colspan="2">
                            <!-- Custodian-->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'16'"/>
                            </xsl:call-template>
                        </th>
                    </tr>
                    <xsl:call-template name="displayRepresentedOrganization">
                        <xsl:with-param name="representedOrganization" select="$patientCustodian"/>
                    </xsl:call-template>
                </tbody>
            </table>
        </div>
    </xsl:template>

    <xsl:template name="displayAssignedPerson">
        <xsl:param name="assignedPerson"/>
        <xsl:param name="contactInfoRoot"/>
        <tr>
            <td>
                <xsl:call-template name="show-name">
                    <xsl:with-param name="name" select="$assignedPerson/n1:name"/>
                </xsl:call-template>
            </td>
            <td>
                <xsl:call-template name="show-contactInformation">
                    <xsl:with-param name="contactInfoRoot" select="$contactInfoRoot"/>
                </xsl:call-template>
            </td>
        </tr>
    </xsl:template>

    <xsl:template name="displayRepresentedOrganization">
        <xsl:param name="representedOrganization"/>
        <tr>
            <td>
                <xsl:choose>
                    <xsl:when test="$representedOrganization/n1:name/@nullFlavor">
                        <xsl:call-template name="show-eHDSINullFlavor">
                            <xsl:with-param name="code" select="$representedOrganization/n1:name/@nullFlavor"/>
                        </xsl:call-template>
                    </xsl:when>
                    <xsl:otherwise>
                        <xsl:value-of select="$representedOrganization/n1:name"/>
                    </xsl:otherwise>
                </xsl:choose>
            </td>
            <td>
                <table class="contact_information_table">
                    <tr>
                        <th colspan="2">
                            <!-- Represented organization -->
                            <xsl:call-template name="show-eHDSIDisplayLabel">
                                <xsl:with-param name="code" select="'127'"/>
                            </xsl:call-template>
                        </th>
                    </tr>
                    <tr>
                        <xsl:call-template name="show-contactInfo">
                            <xsl:with-param name="contact" select="$representedOrganization"/>
                        </xsl:call-template>
                    </tr>
                </table>
            </td>
        </tr>
    </xsl:template>
</xsl:stylesheet>