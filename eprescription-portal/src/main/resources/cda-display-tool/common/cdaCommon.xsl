<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                version="2.0">

    <xsl:output method="html" indent="yes" version="4.01" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
                doctype-public="-//W3C//DTD HTML 4.01//EN"/>

    <xsl:param name="userLang" select="'en-GB'"/>
    <xsl:param name="epsosLangDir" select="'../EpsosRepository'"/>
    <xsl:param name="defaultUserLang" select="'en-GB'"/>
    <xsl:param name="actionpath" select="''"/>
    <xsl:param name="allowDispense" select="'false'"/>
    <xsl:param name="shownarrative" select="''"/>

    <!-- show-id -->
    <xsl:template name="show-id">
        <xsl:param name="id"/>
        <xsl:if test="not($id/@nullFlavor)">
            <xsl:if test="$id/@extension">
                <xsl:value-of select="$id/@extension"/>
                <xsl:text> </xsl:text>
            </xsl:if>
            <xsl:value-of select="$id/@root"/>
        </xsl:if>
    </xsl:template>

    <!-- show-patient-id -->
    <xsl:template name="show-patient-id">
        <xsl:param name="id"/>
        <xsl:if test="not($id/@nullFlavor)">
            <xsl:if test="$id/@extension">
                <xsl:value-of select="$id/@extension"/>
            </xsl:if>
        </xsl:if>
    </xsl:template>


    <!-- show-performer -->
    <xsl:template name="show-performer">
        <xsl:param name="node"/>
        <xsl:call-template name="show-name">
            <xsl:with-param name="name" select="$node/n1:assignedEntity/n1:assignedPerson/n1:name"/>
        </xsl:call-template>
    </xsl:template>

    <!-- show-author -->
    <xsl:template name="show-author">
        <xsl:param name="node"/>
        <xsl:choose>
            <xsl:when test="$node/n1:assignedAuthor/n1:assignedPerson">
                <xsl:call-template name="show-name">
                    <xsl:with-param name="name" select="$node/n1:assignedAuthor/n1:assignedPerson/n1:name"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$node/n1:assignedAuthor/n1:assignedAuthoringDevice">
                <xsl:value-of select="$node/n1:assignedAuthor/n1:assignedAuthoringDevice/n1:manufacturerModelName"/>
                ,&#160;
                <xsl:value-of select="$node/n1:assignedAuthor/n1:assignedAuthoringDevice/n1:softwareName"/>
            </xsl:when>
        </xsl:choose>
        <xsl:if test="$node/n1:assignedAuthor/n1:representedOrganization">
            ,&#160;
            <xsl:value-of select="$node/n1:assignedAuthor/n1:representedOrganization/n1:name"/>
        </xsl:if>
    </xsl:template>

    <!-- show-name -->
    <xsl:template name="show-name">
        <xsl:param name="name"/>
        <xsl:choose>
            <xsl:when test="$name/n1:family">
                <xsl:if test="$name/n1:prefix">
                    <xsl:value-of select="$name/n1:prefix"/>
                    <xsl:text> </xsl:text>
                </xsl:if>
                <xsl:value-of select="$name/n1:given"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$name/n1:family"/>
                <xsl:if test="$name/n1:suffix">
                    <xsl:text>, </xsl:text>
                    <xsl:value-of select="$name/n1:suffix"/>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$name"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- show-contactInfo -->
    <xsl:template name="show-contactInfo">
        <xsl:param name="contact"/>
        <tr>
            <th>
                <!-- Address -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'187'"/>
                </xsl:call-template>
            </th>
            <td>
                <xsl:for-each select="$contact/n1:addr">
                    <xsl:call-template name="show-address">
                        <xsl:with-param name="address" select="."/>
                    </xsl:call-template>
                </xsl:for-each>
            </td>
        </tr>
        <tr>
            <th>
                <!-- Telecom -->
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'188'"/>
                </xsl:call-template>
            </th>
            <td>
                <xsl:for-each select="$contact/n1:telecom">
                    <xsl:call-template name="show-telecom">
                        <xsl:with-param name="telecom" select="."/>
                    </xsl:call-template>
                </xsl:for-each>
            </td>
        </tr>
        <br/>
    </xsl:template>

    <!-- show-address -->
    <xsl:template name="show-address">
        <xsl:param name="address"/>
        <xsl:choose>
            <xsl:when test="$address">
                <xsl:if test="$address/@nullFlavor">
                    <xsl:call-template name="show-eHDSINullFlavor">
                        <xsl:with-param name="code" select="$address/@nullFlavor"/>
                    </xsl:call-template>
                </xsl:if>
                <xsl:if test="$address/@use">
                    <xsl:text> </xsl:text>
                    <xsl:call-template name="show-eHDSITelecomAddress">
                        <xsl:with-param name="code" select="$address/@use"/>
                    </xsl:call-template>
                    <xsl:text>:</xsl:text>
                    <br/>
                </xsl:if>
                <xsl:for-each select="$address/n1:streetAddressLine">
                    <xsl:value-of select="."/>
                    <br/>
                </xsl:for-each>
                <xsl:if test="$address/n1:streetName">
                    <xsl:value-of select="$address/n1:streetName"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="$address/n1:houseNumber"/>
                    <br/>
                </xsl:if>
                <xsl:if test="string-length($address/n1:city)&gt;0">
                    <xsl:value-of select="$address/n1:city"/>
                </xsl:if>
                <xsl:if test="string-length($address/n1:state)&gt;0">
                    <xsl:text>, </xsl:text>
                    <xsl:value-of select="$address/n1:state"/>
                </xsl:if>
                <xsl:if test="string-length($address/n1:postalCode)&gt;0">
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="$address/n1:postalCode"/>
                </xsl:if>
                <xsl:if test="string-length($address/n1:country)&gt;0">
                    <xsl:text>, </xsl:text>
                    <xsl:value-of select="$address/n1:country"/>
                </xsl:if>
            </xsl:when>
            <xsl:otherwise>
                <!-- xsl:text>address not available</xsl:text -->
            </xsl:otherwise>
        </xsl:choose>
        <br/>
    </xsl:template>

    <!-- show-contactInformation -->
    <xsl:template name="show-contactInformation">
        <xsl:param name="contactInfoRoot"/>
        <table class="contact_information_table">
            <colgroup>
                <col span="1" style="width: 30%;"/>
                <col span="1" style="width: 70%;"/>
            </colgroup>
            <tr>
                <th colspan="2">
                    <!-- Contact Information -->
                    <xsl:call-template name="show-eHDSIDisplayLabel">
                        <xsl:with-param name="code" select="'12'"/>
                    </xsl:call-template>
                </th>
            </tr>
            <tr/>
            <xsl:call-template name="show-contactInfo">
                <xsl:with-param name="contact" select="$contactInfoRoot"/>
            </xsl:call-template>
        </table>
    </xsl:template>

    <!-- show-telecom -->
    <xsl:template name="show-telecom">
        <xsl:param name="telecom"/>
        <xsl:choose>
            <xsl:when test="$telecom">
                <xsl:if test="$telecom/@nullFlavor">
                    <xsl:call-template name="show-eHDSINullFlavor">
                        <xsl:with-param name="code" select="$telecom/@nullFlavor"/>
                    </xsl:call-template>
                </xsl:if>
                <xsl:variable name="type"
                              select="substring-before($telecom/@value, ':')"/>
                <xsl:variable name="value"
                              select="substring-after($telecom/@value, ':')"/>
                <xsl:if test="$type">
                    <xsl:call-template name="translateTelecomCode">
                        <xsl:with-param name="code" select="$type"/>
                    </xsl:call-template>
                </xsl:if>
                <xsl:if test="@use">
                    <xsl:text> (</xsl:text>
                    <xsl:call-template name="show-eHDSITelecomAddress">
                        <xsl:with-param name="code" select="@use"/>
                    </xsl:call-template>
                    <xsl:text>)</xsl:text>
                    <xsl:text>: </xsl:text>
                    <xsl:text> </xsl:text>
                </xsl:if>
                <xsl:value-of select="$value"/>
            </xsl:when>
            <xsl:otherwise>
                <!-- xsl:text>Telecom information not available</xsl:text -->
            </xsl:otherwise>
        </xsl:choose>
        <br/>
    </xsl:template>

    <!-- show-recipientType -->
    <xsl:template name="show-recipientType">
        <xsl:param name="typeCode"/>
        <xsl:choose>
            <xsl:when test="$typeCode='PRCP'">
                Primary Recipient:
            </xsl:when>
            <xsl:when test="$typeCode='TRC'">
                Secondary Recipient:
            </xsl:when>
            <xsl:otherwise>
                Recipient:
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Convert Telecom URL to display text -->
    <xsl:template name="translateTelecomCode">
        <xsl:param name="code"/>
        <xsl:choose>
            <!-- lookup table Telecom URI -->
            <xsl:when test="$code='tel'">
                <xsl:text>Tel</xsl:text>
            </xsl:when>
            <xsl:when test="$code='fax'">
                <xsl:text>Fax</xsl:text>
            </xsl:when>
            <xsl:when test="$code='http'">
                <xsl:text>Web</xsl:text>
            </xsl:when>
            <xsl:when test="$code='mailto'">
                <xsl:text>Mail</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$code"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- convert RoleClassAssociative code to display text -->
    <xsl:template name="translateRoleAssoCode">
        <xsl:param name="code"/>
        <xsl:choose>
            <xsl:when test="$code='AFFL'">
                <xsl:text>affiliate</xsl:text>
            </xsl:when>
            <xsl:when test="$code='AGNT'">
                <xsl:text>agent</xsl:text>
            </xsl:when>
            <xsl:when test="$code='ASSIGNED'">
                <xsl:text>assigned entity</xsl:text>
            </xsl:when>
            <xsl:when test="$code='COMPAR'">
                <xsl:text>commissioning party</xsl:text>
            </xsl:when>
            <xsl:when test="$code='CON'">
                <xsl:text>contact</xsl:text>
            </xsl:when>
            <xsl:when test="$code='ECON'">
                <xsl:text>emergency contact</xsl:text>
            </xsl:when>
            <xsl:when test="$code='NOK'">
                <xsl:text>next of kin</xsl:text>
            </xsl:when>
            <xsl:when test="$code='SGNOFF'">
                <xsl:text>signing authority</xsl:text>
            </xsl:when>
            <xsl:when test="$code='GUARD'">
                <xsl:text>guardian</xsl:text>
            </xsl:when>
            <xsl:when test="$code='GUAR'">
                <xsl:text>guardian</xsl:text>
            </xsl:when>
            <xsl:when test="$code='CIT'">
                <xsl:text>citizen</xsl:text>
            </xsl:when>
            <xsl:when test="$code='COVPTY'">
                <xsl:text>covered party</xsl:text>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text>{$code='</xsl:text>
                <xsl:value-of select="$code"/>
                <xsl:text>'?}</xsl:text>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- show assignedEntity -->
    <xsl:template name="show-assignedEntity">
        <xsl:param name="asgnEntity"/>
        <xsl:choose>
            <xsl:when test="$asgnEntity/n1:assignedPerson/n1:name">
                <xsl:call-template name="show-name">
                    <xsl:with-param name="name"
                                    select="$asgnEntity/n1:assignedPerson/n1:name"/>
                </xsl:call-template>
                <xsl:if test="$asgnEntity/n1:representedOrganization/n1:name">
                    <xsl:text> of </xsl:text>
                    <xsl:value-of select="$asgnEntity/n1:representedOrganization/n1:name"/>
                </xsl:if>
            </xsl:when>
            <xsl:when test="$asgnEntity/n1:representedOrganization">
                <xsl:value-of select="$asgnEntity/n1:representedOrganization/n1:name"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:for-each select="$asgnEntity/n1:id">
                    <xsl:call-template name="show-id"/>
                    <xsl:choose>
                        <xsl:when test="position()!=last()">
                            <xsl:text>, </xsl:text>
                        </xsl:when>
                        <xsl:otherwise>
                            <br/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:for-each>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- show relatedEntity -->
    <xsl:template name="show-relatedEntity">
        <xsl:param name="relatedEntity"/>
        <xsl:choose>
            <xsl:when test="$relatedEntity/n1:relatedPerson/n1:name">
                <xsl:call-template name="show-name">
                    <xsl:with-param name="name"
                                    select="$relatedEntity/n1:relatedPerson/n1:name"/>
                </xsl:call-template>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <!-- show associatedEntity -->
    <xsl:template name="show-associatedEntity">
        <xsl:param name="assoEntity"/>
        <xsl:choose>
            <xsl:when test="$assoEntity/n1:associatedPerson">
                <xsl:for-each select="$assoEntity/n1:associatedPerson/n1:name">
                    <xsl:call-template name="show-name">
                        <xsl:with-param name="name" select="."/>
                    </xsl:call-template>
                    <br/>
                </xsl:for-each>
            </xsl:when>
            <xsl:when test="$assoEntity/n1:scopingOrganization">
                <xsl:for-each select="$assoEntity/n1:scopingOrganization">
                    <xsl:if test="n1:name">
                        <xsl:call-template name="show-name">
                            <xsl:with-param name="name" select="n1:name"/>
                        </xsl:call-template>
                        <br/>
                    </xsl:if>
                    <xsl:if test="n1:standardIndustryClassCode">
                        <xsl:value-of select="n1:standardIndustryClassCode/@displayName"/>
                        <xsl:text> code:</xsl:text>
                        <xsl:value-of select="n1:standardIndustryClassCode/@code"/>
                    </xsl:if>
                </xsl:for-each>
            </xsl:when>
            <xsl:when test="$assoEntity/n1:code">
                <xsl:call-template name="show-code">
                    <xsl:with-param name="code" select="$assoEntity/n1:code"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$assoEntity/n1:id">
                <xsl:value-of select="$assoEntity/n1:id/@extension"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$assoEntity/n1:id/@root"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <!-- show code if originalText present, return it, otherwise, check and
        return attribute: display name -->
    <xsl:template name="show-code">
        <xsl:param name="code"/>
        <xsl:variable name="this-codeSystem">
            <xsl:value-of select="$code/@codeSystem"/>
        </xsl:variable>
        <xsl:variable name="this-code">
            <xsl:value-of select="$code/@code"/>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="$code/n1:originalText">
                <xsl:value-of select="$code/n1:originalText"/>
            </xsl:when>
            <xsl:when test="$code/@displayName">
                <xsl:value-of select="$code/@displayName"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$this-code"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- show classCode -->
    <xsl:template name="show-actClassCode">
        <xsl:param name="clsCode"/>
        <xsl:choose>
            <xsl:when test=" $clsCode = 'ACT' ">
                <xsl:text>healthcare service</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'ACCM' ">
                <xsl:text>accommodation</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'ACCT' ">
                <xsl:text>account</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'ACSN' ">
                <xsl:text>accession</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'ADJUD' ">
                <xsl:text>financial adjudication</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'CONS' ">
                <xsl:text>consent</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'CONTREG' ">
                <xsl:text>container registration</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'CTTEVENT' ">
                <xsl:text>clinical trial timepoint event</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'DISPACT' ">
                <xsl:text>disciplinary action</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'ENC' ">
                <xsl:text>encounter</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'INC' ">
                <xsl:text>incident</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'INFRM' ">
                <xsl:text>inform</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'INVE' ">
                <xsl:text>invoice element</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'LIST' ">
                <xsl:text>working list</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'MPROT' ">
                <xsl:text>monitoring program</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'PCPR' ">
                <xsl:text>care provision</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'PROC' ">
                <xsl:text>procedure</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'REG' ">
                <xsl:text>registration</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'REV' ">
                <xsl:text>review</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'SBADM' ">
                <xsl:text>substance administration</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'SPCTRT' ">
                <xsl:text>speciment treatment</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'SUBST' ">
                <xsl:text>substitution</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'TRNS' ">
                <xsl:text>transportation</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'VERIF' ">
                <xsl:text>verification</xsl:text>
            </xsl:when>
            <xsl:when test=" $clsCode = 'XACT' ">
                <xsl:text>financial transaction</xsl:text>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <!-- show participationType -->
    <xsl:template name="show-participationType">
        <xsl:param name="ptype"/>
        <xsl:choose>
            <xsl:when test=" $ptype='PPRF' ">
                <xsl:text>primary performer</xsl:text>
            </xsl:when>
            <xsl:when test=" $ptype='PRF' ">
                <xsl:text>performer</xsl:text>
            </xsl:when>
            <xsl:when test=" $ptype='VRF' ">
                <xsl:text>verifier</xsl:text>
            </xsl:when>
            <xsl:when test=" $ptype='SPRF' ">
                <xsl:text>secondary performer</xsl:text>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <!-- show participationFunction -->
    <xsl:template name="show-participationFunction">
        <xsl:param name="pFunction"/>
        <xsl:choose>
            <xsl:when test=" $pFunction = 'ADMPHYS' ">
                <xsl:text>admitting physician</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'ANEST' ">
                <xsl:text>anesthesist</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'ANRS' ">
                <xsl:text>anesthesia nurse</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'ATTPHYS' ">
                <xsl:text>attending physician</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'DISPHYS' ">
                <xsl:text>discharging physician</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'FASST' ">
                <xsl:text>first assistant surgeon</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'MDWF' ">
                <xsl:text>midwife</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'NASST' ">
                <xsl:text>nurse assistant</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'PCP' ">
                <xsl:text>primary care physician</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'PRISURG' ">
                <xsl:text>primary surgeon</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'RNDPHYS' ">
                <xsl:text>rounding physician</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'SASST' ">
                <xsl:text>second assistant surgeon</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'SNRS' ">
                <xsl:text>scrub nurse</xsl:text>
            </xsl:when>
            <xsl:when test=" $pFunction = 'TASST' ">
                <xsl:text>third assistant</xsl:text>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <!-- Present DateTime according to ISO 8601 specification -->
    <xsl:template name="formatDateTime">
        <xsl:param name="date"/>
        <!-- year -->
        <xsl:variable name="year" select="substring ($date, 1, 4)"/>
        <xsl:value-of select="$year"/>
        <xsl:if test="string-length($date) &gt; 4">
            <xsl:text>-</xsl:text>
            <!-- month -->
            <xsl:variable name="month" select="substring ($date, 5, 2)"/>
            <xsl:value-of select="$month"/>
            <xsl:if test="string-length($date) &gt; 6">
                <xsl:text>-</xsl:text>
                <!-- day -->
                <xsl:variable name="day" select="substring ($date, 7, 2)"/>
                <xsl:value-of select="$day"/>

                <xsl:if test="string-length($date) &gt; 8">
                    <xsl:text>&#x9;</xsl:text>
                    <!-- time -->
                    <xsl:variable name="time">
                        <xsl:value-of select="substring($date,9,6)"/>
                    </xsl:variable>
                    <xsl:variable name="hh">
                        <xsl:value-of select="substring($time,1,2)"/>
                    </xsl:variable>
                    <xsl:variable name="mm">
                        <xsl:value-of select="substring($time,3,2)"/>
                    </xsl:variable>
                    <xsl:variable name="ss">
                        <xsl:value-of select="substring($time,5,2)"/>
                    </xsl:variable>
                    <xsl:if test="string-length($hh)&gt;1">
                        <xsl:value-of select="$hh"/>
                        <xsl:if
                                test="string-length($mm)&gt;1 and not(contains($mm,'-')) and not (contains($mm,'+'))">
                            <xsl:text>:</xsl:text>
                            <xsl:value-of select="$mm"/>
                            <xsl:if
                                    test="string-length($ss)&gt;1 and not(contains($ss,'-')) and not (contains($ss,'+'))">
                                <xsl:text>:</xsl:text>
                                <xsl:value-of select="$ss"/>
                            </xsl:if>
                        </xsl:if>
                    </xsl:if>
                    <!-- time zone -->
                    <xsl:variable name="tzon">
                        <xsl:choose>
                            <xsl:when test="contains($date,'+')">
                                <xsl:text>(</xsl:text>
                                <xsl:text>+</xsl:text>
                                <xsl:value-of select="substring(substring-after($date, '+'),1,2)"/>
                                <xsl:text>:</xsl:text>
                                <xsl:value-of select="substring(substring-after($date, '+'),3,2)"/>
                                <xsl:text>)</xsl:text>
                            </xsl:when>
                            <xsl:when test="contains($date,'-')">
                                <xsl:text>(</xsl:text>
                                <xsl:text>-</xsl:text>
                                <xsl:value-of select="substring(substring-after($date, '-'),1,2)"/>
                                <xsl:text>:</xsl:text>
                                <xsl:value-of select="substring(substring-after($date, '-'),3,2)"/>
                                <xsl:text>)</xsl:text>
                            </xsl:when>
                        </xsl:choose>
                    </xsl:variable>
                    <xsl:choose>
                        <!-- reference: http://www.timeanddate.com/library/abbreviations/timezones/na/ -->
                        <xsl:when test="$tzon = '-0500' ">
                            <xsl:text>, EST</xsl:text>
                        </xsl:when>
                        <xsl:when test="$tzon = '-0600' ">
                            <xsl:text>, CST</xsl:text>
                        </xsl:when>
                        <xsl:when test="$tzon = '-0700' ">
                            <xsl:text>, MST</xsl:text>
                        </xsl:when>
                        <xsl:when test="$tzon = '-0800' ">
                            <xsl:text>, PST</xsl:text>
                        </xsl:when>
                        <xsl:otherwise>
                            <xsl:text> </xsl:text>
                            <xsl:value-of select="$tzon"/>
                        </xsl:otherwise>
                    </xsl:choose>
                </xsl:if>
            </xsl:if>
        </xsl:if>
    </xsl:template>

    <xsl:template name="show-strength">
        <xsl:param name="node"/>

        <xsl:variable name="numerator" select="$node/n1:numerator"/>
        <xsl:variable name="denominator" select="$node/n1:denominator"/>
        <xsl:variable name="numeratorValue" select="$numerator/@value"/>
        <xsl:variable name="numeratorUnit">
            <xsl:call-template name="show-eHDSIUnit">
                <xsl:with-param name="code" select="$numerator/@unit"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:variable name="denominatorValue" select="$denominator/@value"/>
        <xsl:variable name="medStrengthOriginalText" select="$node/n1:translation/n1:originalText"/>
        <xsl:variable name="denominatorUnit">
            <xsl:call-template name="supportUCUMAnnotations">
                <xsl:with-param name="value" select="$denominator/@unit"/>
            </xsl:call-template>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="($numerator/@nullFlavor)">
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="$numerator/@nullFlavor"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="($denominator/@nullFlavor)">
                <xsl:value-of select="$numeratorValue"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$numeratorUnit"/>
                <xsl:text> </xsl:text>
                /
                <xsl:text> </xsl:text>
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="$denominator/@nullFlavor"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$denominatorUnit='1'">
                <xsl:value-of select="$numeratorValue"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$numeratorUnit"/>
                <xsl:text> </xsl:text>
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'53'"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="not($numeratorValue) and not($denominatorValue)">
                /
            </xsl:when>
            <xsl:when test="not($denominatorValue)">
                <xsl:value-of select="$numeratorValue"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$numeratorUnit"/>
                <xsl:text> </xsl:text>
                /
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$numeratorValue"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$numeratorUnit"/>
                <xsl:text> </xsl:text>
                /
                <xsl:text> </xsl:text>
                <xsl:value-of select="$denominatorValue"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="$denominatorUnit"/>
            </xsl:otherwise>
        </xsl:choose>
        <xsl:if test="$medStrengthOriginalText">
            <tr>
                <td>
                    <div class="tooltip">
                        <div class="additionalInfo">
                            <xsl:value-of select="$medStrengthOriginalText"/>
                        </div>
                        <span class="tooltiptext">Additional info</span>
                    </div>
                </td>
            </tr>
        </xsl:if>
    </xsl:template>

    <!-- Number of Unit per Intake Low value -->
    <xsl:template name="show-numberUnitIntakeLow">
        <xsl:param name="medUnitIntake"/>
        <xsl:call-template name="show-numberUnitIntakeIntervalEndpoint">
            <xsl:with-param name="medUnitIntakeGlobal" select="$medUnitIntake"/>
            <xsl:with-param name="medUnitIntakeEndpoint" select="$medUnitIntake/n1:low"/>
        </xsl:call-template>
    </xsl:template>

    <!-- Number of Unit per Intake High value -->
    <xsl:template name="show-numberUnitIntakeHigh">
        <xsl:param name="medUnitIntake"/>
        <xsl:call-template name="show-numberUnitIntakeIntervalEndpoint">
            <xsl:with-param name="medUnitIntakeGlobal" select="$medUnitIntake"/>
            <xsl:with-param name="medUnitIntakeEndpoint" select="$medUnitIntake/n1:high"/>
        </xsl:call-template>
    </xsl:template>

    <!-- Number of Unit per Intake Interval Endpoint-->
    <xsl:template name="show-numberUnitIntakeIntervalEndpoint">
        <xsl:param name="medUnitIntakeGlobal"/>
        <xsl:param name="medUnitIntakeEndpoint"/>
        <xsl:choose>
            <xsl:when test="$medUnitIntakeEndpoint/@value">
                <xsl:call-template name="show-numberUnitIntake">
                    <xsl:with-param name="medUnitIntake" select="$medUnitIntakeEndpoint/@value"/>
                    <xsl:with-param name="medUnitIntakeUnit" select="$medUnitIntakeEndpoint/@unit"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$medUnitIntakeGlobal/@value">
                <xsl:call-template name="show-numberUnitIntake">
                    <xsl:with-param name="medUnitIntake" select="$medUnitIntakeGlobal/@value"/>
                    <xsl:with-param name="medUnitIntakeUnit" select="$medUnitIntakeGlobal/@unit"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$medUnitIntakeEndpoint/@nullFlavor">
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="$medUnitIntakeEndpoint/@nullFlavor"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$medUnitIntakeGlobal/@nullFlavor">
                <xsl:call-template name="show-eHDSINullFlavor">
                    <xsl:with-param name="code" select="$medUnitIntakeGlobal/@nullFlavor"/>
                </xsl:call-template>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <!--Support the usage of UCUM annotations (e.g. {tablet})-->
    <xsl:template name="supportUCUMAnnotations">
        <xsl:param name="value"/>
        <xsl:choose>
            <xsl:when test="contains($value, '{')">
                <xsl:call-template name="show-eHDSIUnit">
                    <xsl:with-param name="code" select="substring-before($value, '{')"/>
                </xsl:call-template>
                <xsl:if test = "substring-before($value, '{')">
                    <xsl:text>&#160;</xsl:text>
                </xsl:if>
                <xsl:value-of select="substring-before(substring-after($value, '{'), '}')"/>
                <xsl:if test = "substring-after($value, '}')">
                    <xsl:text>&#160;</xsl:text>
                </xsl:if>
                <xsl:call-template name="show-eHDSIUnit">
                    <xsl:with-param name="code" select="substring-after($value, '}')"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$value='1'">
                <xsl:value-of select="$value"/>
            </xsl:when>
            <xsl:when test="not($value)">
                <xsl:value-of select="1"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:call-template name="show-eHDSIUnit">
                    <xsl:with-param name="code" select="$value"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <xsl:variable name="denominatorUnit">

    </xsl:variable>

    <!-- Number of Unit per Intake -->
    <xsl:template name="show-numberUnitIntake">
        <xsl:param name="medUnitIntake"/>
        <xsl:param name="medUnitIntakeUnit"/>
        <xsl:value-of select="$medUnitIntake"/>
        <xsl:choose>
            <xsl:when test="not($medUnitIntakeUnit) or $medUnitIntakeUnit='1'">
                <xsl:text> </xsl:text>
                <xsl:call-template name="show-eHDSIDisplayLabel">
                    <xsl:with-param name="code" select="'77'"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text> </xsl:text>
                <xsl:call-template name="supportUCUMAnnotations">
                    <xsl:with-param name="value" select="$medUnitIntakeUnit"/>
                </xsl:call-template>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>

    <!-- Frequency of Intake -->
    <xsl:template name="show-frequencyIntake">
        <xsl:param name="medFrequencyIntakeType"/>
        <xsl:param name="medFrequencyIntake"/>
        <xsl:choose>
            <xsl:when test="$medFrequencyIntakeType='TS' or substring-after($medFrequencyIntakeType, ':')='TS'">
                <!-- a point in time just one value -->
                <xsl:call-template name="show-TS">
                    <xsl:with-param name="node" select="$medFrequencyIntake"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$medFrequencyIntakeType='IVL_TS' or substring-after($medFrequencyIntakeType, ':')='IVL_TS'">
                <!-- time interval -->
                <xsl:call-template name="show-IVL_TS">
                    <xsl:with-param name="node" select="$medFrequencyIntake"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$medFrequencyIntakeType='PIVL_TS' or substring-after($medFrequencyIntakeType, ':')='PIVL_TS'">
                <xsl:call-template name="show-PIVL_TS">
                    <xsl:with-param name="node" select="$medFrequencyIntake"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$medFrequencyIntakeType='EIVL_TS' or substring-after($medFrequencyIntakeType, ':')='EIVL_TS'">
                <xsl:call-template name="show-EIVL_TS">
                    <xsl:with-param name="node" select="$medFrequencyIntake"/>
                </xsl:call-template>
            </xsl:when>
            <xsl:when test="$medFrequencyIntakeType='SXPR_TS' or substring-after($medFrequencyIntakeType, ':')='SXPR_TS'">
                <!-- composite -->
                <xsl:for-each select="$medFrequencyIntake/n1:comp">
                    <xsl:call-template name="frequencyComp"/>
                </xsl:for-each>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <xsl:template name="frequencyComp">
        <xsl:variable name="compType" select="./@xsi:type"/>
        <xsl:variable name="comp" select="."/>
        <xsl:call-template name="show-frequencyIntake">
            <xsl:with-param name="medFrequencyIntakeType" select="$compType"/>
            <xsl:with-param name="medFrequencyIntake" select="$comp"/>
        </xsl:call-template>
        <br/>
    </xsl:template>

    <!-- uncoded element -->
    <xsl:template name="show-uncodedElement">
        <xsl:param name="code"/>
        <xsl:variable name="refText" select="$code"/>
        <xsl:variable name="refAttrText" select="//*[@ID=substring($refText,2)]"/>
        <xsl:variable name="refAttrText1" select="//*[@id=substring($refText,2)]"/>
        <xsl:variable name="refAttrText3" select="//*[@id=$refText]"/>
        <xsl:variable name="refAttrText4" select="//*[@ID=$refText]"/>
        <xsl:choose>
            <xsl:when test="$refAttrText">
                <xsl:value-of select="$refAttrText/."/>
            </xsl:when>
            <xsl:when test="$refAttrText1">
                <xsl:value-of select="$refAttrText1/."/>
            </xsl:when>
            <xsl:when test="$refAttrText3">
                <xsl:value-of select="$refAttrText3/."/>
            </xsl:when>
            <xsl:when test="$refAttrText4">
                <xsl:value-of select="$refAttrText4/."/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <!-- show one of two elemenets -->
    <xsl:template name="show-OrgOrPersonData">
        <xsl:param name="orgData"/>
        <xsl:param name="personData"/>
        <xsl:choose>
            <xsl:when test="not ($personData)">
                <xsl:value-of select="$orgData"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:value-of select="$personData"/>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:template>


    <xsl:template name="section-text">
        <div>
            <xsl:apply-templates select="n1:text"/>
        </div>
    </xsl:template>
    <!-- nested component/section -->
    <xsl:template name="nestedSection">
        <xsl:param name="margin"/>
        <!-- <h4 style="margin-left : {$margin}em;"> <xsl:value-of select="n1:title"/>
            </h4> -->
        <div style="margin-left : {$margin}em;">
            <xsl:apply-templates select="n1:text"/>
        </div>
        <xsl:for-each select="n1:component/n1:section">
            <xsl:call-template name="nestedSection">
                <xsl:with-param name="margin" select="2*$margin"/>
            </xsl:call-template>
        </xsl:for-each>
    </xsl:template>

    <!-- show narrative -->
    <xsl:template name="show-narrative">
        <xsl:param name="node"/>
        <div class="narrative_wrapper">
            <xsl:apply-templates select="$node/self::*"/>
        </div>
    </xsl:template>

    <!-- paragraph -->
    <xsl:template match="n1:paragraph">
        <p>
            <xsl:apply-templates/>
        </p>
    </xsl:template>

    <!-- pre format -->
    <xsl:template match="n1:pre">
        <pre>
            <xsl:apply-templates/>
        </pre>
    </xsl:template>

    <!-- Content w/ deleted text is hidden -->
    <xsl:template match="n1:content[@revised='delete']"/>

    <!-- content -->
    <xsl:template match="n1:content">
        <xsl:apply-templates/>
    </xsl:template>

    <!-- line break -->
    <xsl:template match="n1:br">
        <xsl:element name='br'>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

    <!-- list -->
    <xsl:template match="n1:list">
        <xsl:if test="n1:caption">
            <p>
                <b>
                    <xsl:apply-templates select="n1:caption"/>
                </b>
            </p>
        </xsl:if>
        <ul>
            <xsl:for-each select="n1:item">
                <li>
                    <xsl:apply-templates/>
                </li>
            </xsl:for-each>
        </ul>
    </xsl:template>

    <xsl:template match="n1:list[@listType='ordered']">
        <xsl:if test="n1:caption">
            <span style="font-weight:bold; ">
                <xsl:apply-templates select="n1:caption"/>
            </span>
        </xsl:if>
        <ol>
            <xsl:for-each select="n1:item">
                <li>
                    <xsl:apply-templates/>
                </li>
            </xsl:for-each>
        </ol>
    </xsl:template>

    <!-- caption -->
    <xsl:template match="n1:caption">
        <xsl:apply-templates/>
        <xsl:text>: </xsl:text>
    </xsl:template>

    <!-- Tables -->
    <xsl:template match="n1:table">
        <table class="narr_table">
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </table>
    </xsl:template>
    <xsl:template match="n1:thead">
        <thead>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </thead>
    </xsl:template>
    <xsl:template match="n1:tfoot">
        <tfoot>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </tfoot>
    </xsl:template>
    <xsl:template match="n1:tbody">
        <tbody>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </tbody>
    </xsl:template>
    <xsl:template match="n1:colgroup">
        <colgroup>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </colgroup>
    </xsl:template>
    <xsl:template match="n1:col">
        <col>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </col>
    </xsl:template>
    <xsl:template match="n1:tr">
        <tr class="narr_tr">
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </tr>
    </xsl:template>
    <xsl:template match="n1:th">
        <th class="narr_th">
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </th>
    </xsl:template>
    <xsl:template match="n1:td">
        <td>
            <xsl:copy-of select="@*"/>
            <xsl:apply-templates/>
        </td>
    </xsl:template>
    <xsl:template match="n1:table/n1:caption">
        <span style="font-weight:bold; ">
            <xsl:apply-templates/>
        </span>
    </xsl:template>
</xsl:stylesheet>
