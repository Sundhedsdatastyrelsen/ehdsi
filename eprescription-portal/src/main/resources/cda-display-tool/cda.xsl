<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" version="2.0">

    <!-- global variable title -->
    <xsl:import href="common/cdaCommon.xsl"/>
    <xsl:import href="common/mvc.xsl"/>
    <xsl:import href="common/datatypes.xsl"/>
    <xsl:import href="ps/ps.xsl"/>
    <xsl:import href="ep/ep.xsl"/>

    <xsl:variable name="documentCode" select="/n1:ClinicalDocument/n1:code/@code"/>

    <xsl:output method="html" indent="yes" version="4.01" doctype-system="http://www.w3.org/TR/html4/strict.dtd"
                doctype-public="-//W3C//DTD HTML 4.01//EN"/>

    <!-- global variable title -->
    <xsl:variable name="title">
        <xsl:choose>
            <xsl:when test="/n1:ClinicalDocument/n1:code/@displayName">
                <xsl:value-of select="/n1:ClinicalDocument/n1:code/@displayName"/>
            </xsl:when>
            <xsl:when test="string-length(/n1:ClinicalDocument/n1:title)  &gt;= 1">
                <xsl:value-of select="/n1:ClinicalDocument/n1:title"/>
            </xsl:when>
            <xsl:otherwise>
                <xsl:text>Clinical Document</xsl:text>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:variable>

    <!-- Main -->
    <xsl:template match="/">
        <xsl:apply-templates select="n1:ClinicalDocument"/>
    </xsl:template>
    <!-- produce browser rendered, human readable clinical document	-->
    <xsl:template match="n1:ClinicalDocument">
        <html>
            <head>
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.9/css/all.css" integrity="sha384-5SOiIsAziJl6AWe0HWRKTXlfcSHKmYV4RBF18PPJ173Kzn7jzMyFuTtk8JA7QQG1" crossorigin="anonymous"/>
                <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet'/>
                <xsl:comment>Do NOT edit this HTML directly: it was generated via an XSLT transformation from a CDA
                    Release 2 XML document.
                </xsl:comment>
                <title>
                    <xsl:value-of select="$title"/>
                </title>
                <style type="text/css">
                    <xsl:value-of select="document('css/style.css')" disable-output-escaping="yes" />
                    <xsl:value-of select="document('css/ps.css')" disable-output-escaping="yes" />
                    <xsl:value-of select="document('css/ep.css')" disable-output-escaping="yes" />
                </style>
                <!--<script language="javascript">-->
                    <!--function showhide(id){-->
                    <!--if (document.getElementById){-->
                    <!--obj = document.getElementById(id);-->
                    <!--if (obj.style.display == "none"){-->
                    <!--obj.style.display = "";-->
                    <!--} else {-->
                    <!--obj.style.display = "none";-->
                    <!--}-->
                    <!--}-->
                    <!--}-->
                <!--</script>-->
            </head>
            <body>
                <h1 class="h1center">
                    <xsl:value-of select="$title"/>
                </h1>
                <!-- START display top portion of clinical document -->
                <!--- BASIC HEADER INFORMATION -->
                <xsl:choose>
                    <xsl:when test="$documentCode='60591-5'">
                        <xsl:call-template name="psCda"/>
                        <br/>
                        <br/>
                    </xsl:when>
                    <xsl:when test="$documentCode='57833-6'">
                        <xsl:call-template name="epCda"/>
                        <br/>
                        <br/>
                    </xsl:when>
                    <!--<xsl:when test="$documentCode='56445-0'">-->
                        <!--<xsl:call-template name="mroCda"/>-->
                        <!--<br/>-->
                        <!--<br/>-->
                    <!--</xsl:when>-->
                    <!--<xsl:when test="$documentCode='34133-9'">-->
                        <!--<xsl:call-template name="hcerCda"/>-->
                        <!--<br/>-->
                        <!--<br/>-->
                    <!--</xsl:when>-->
                </xsl:choose>
            </body>
        </html>
    </xsl:template>
    <!-- generate table of contents -->
</xsl:stylesheet>