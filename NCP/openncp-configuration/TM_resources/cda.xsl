<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:epsos="urn:epsos-org:ep:medication" >

	<xsl:import href="ps/ps.xsl"/>
	<xsl:import href="ep/ep.xsl"/>
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>
	<!-- global variable title -->
	<xsl:variable name="documentCode" select="/n1:ClinicalDocument/n1:code/@code"/>

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
				<xsl:comment> Do NOT edit this HTML directly: it was generated via an XSLT transformation from a CDA Release 2 XML document. </xsl:comment>
				<title>
					<xsl:value-of select="$title"/>
				</title>
				<style type="text/css">
					<xsl:text>
				body {
/*	color: #003366;*/
	font-family: Verdana, Tahoma, sans-serif;
	font-size: 12px;
}

a {
	color: #003366;
	background-color: #FFFFFF;
}

h1 {
	font-size: 12pt;
	font-weight: bold;
}

h2 {
	font-size: 11pt;
	font-weight: bold;
}

h3 {
	font-size: 10pt;
	font-weight: bold;
}

h4 {
	font-size: 8pt;
	font-weight: bold;
}

div {}
table {
	/*line-height: 10pt;*/
	width: 100%;
}
tr {}
td {
	background-color: #F0F0F0;
	padding:5px;
	border-width:1px;
	border-color:#E1E1E1;
	border-style:solid;
	vertical-align: top;
	text-align: left;
/*	color:green;*/
	font-weight: normal;
}
th {
	background-color:#D8D8D8;
	color:#4A4A4A;
	font-size:13px;
	padding:6px;
	border-bottom-width:2px;
	border-bottom-color:#4A4A4A;
	border-bottom-style:solid;
	vertical-align: top;
	text-align: left;
}

.h1center {
	font-size: 18px;
	font-weight: bold;
	text-align: center;
}
.header_table{
	border-bottom:0px solid #666;
}
.td_creation_date{
	background-color:#FFF;
	border:0;
}
.header_table th {
	border:0;
	background-color:#9FBFDF;
	color:#FFF;
}
.header_table td {
	color:#039;
	font-size:12px;
}
.tdtext {
	color:#666;
}
.sectionTitle{
	color:#3871A9;
	font-weight: bold;
	font-size:16px;
}

/*
.narr_table {
	width: 100%;
}

.narr_tr {
	background-color: #ffffcc;
}

.narr_th {
	background-color: #ffd700;
}

.td_label{
	font-weight: bold;
	color: white;
}
*/
					</xsl:text>
				</style>
				<script language="javascript">
function showhide(id){
	if (document.getElementById){
		obj = document.getElementById(id);
		if (obj.style.display == "none"){
			obj.style.display = "";
		} else {
			obj.style.display = "none";
		}
	}
} 
				</script>
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
				</xsl:choose>
				<br/>
				<br/>
			</body>
		</html>
	</xsl:template>
	<!-- generate table of contents -->
</xsl:stylesheet>