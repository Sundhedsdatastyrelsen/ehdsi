<?xml version="1.0"  ?>





<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>


<!-- variable to check that at least one alert section exist -->
<xsl:variable name="diagnosticExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code='30954-2']"/>



	<!--alerts -->

<xsl:template name="diagnosticTests" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">

	<!-- if we have at least one alert section -->
		<xsl:if test=" ($diagnosticExist)">

		<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
			<xsl:call-template name="diagnosticSection"/>
		</xsl:for-each>
			<br/><br/>
  	</xsl:if>
  </xsl:template>


	<xsl:template name="diagnosticSection">

	<!-- Defing all needed variables -->

	<xsl:variable
	name="diagSectionTitleCode"
	select="n1:code/@code"/>


		<xsl:variable
	name="diagSsectionTitle"
	select="n1:code[@code='30954-2']/@displayName"/>

<xsl:variable
	name="nullEntry"
	select="n1:entry"/>

	<!-- End definition of variables-->
<xsl:choose>
	<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
 	 	<xsl:when test=" ($diagSectionTitleCode='30954-2')">
				<span class="sectionTitle">
				<!-- xsl:text>Relevantné diagnostické testy / laboratórne údaje</xsl:text-->
				<!-- xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'RelevantnéDiagnostickéTesty'"/>
							</xsl:call-template-->
							
				<xsl:value-of select="$diagSsectionTitle"/>&#160;&#160;
				</span><br/>
			<xsl:for-each select="n1:entry">
					<xsl:call-template name="diagnosticSectionEntry"/>
					<br/>		
			</xsl:for-each>
		</xsl:when>
	</xsl:choose>


	


	
</xsl:template>
	
	<!-- FOR EACH ENTRY -->



	<xsl:template name="diagnosticSectionEntry">

	<!-- Defing all needed variables -->

<!-- 
	<xsl:variable
	name="diagnosticDate"
	select="n1:observation/n1:templateId[@root= '1.3.6.1.4.1.19376.1.5.3.1.4.13.6']/../n1:code[@code='34530-6']/../n1:effectiveTime"/>

<xsl:variable
	name="bloodGroup"
	select="n1:observation/n1:templateId[@root= '1.3.6.1.4.1.19376.1.5.3.1.4.13.6']/../n1:code[@code='34530-6']/../n1:value/@displayName"/>
 -->
<!--  release constraint with no root templateid -->
	<xsl:variable
	name="diagnosticDate"
	select="n1:observation/n1:code[@code='34530-6']/../n1:effectiveTime"/>

<xsl:variable
	name="bloodGroup"
	select="n1:observation/n1:code[@code='34530-6']/../n1:value/@displayName"/>


<xsl:variable
	name="nullEntry"
	select="."/>

	<!-- End definition of variables-->
<xsl:choose>
	<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
 	 
				<xsl:when test="not($nullEntry/@nullFlavor)">
					<span >
						<xsl:call-template name="show-time">
							<xsl:with-param name="datetime" select="$diagnosticDate"/>
						</xsl:call-template>&#160;:
					
						
				 	 	<xsl:value-of select="$bloodGroup"/>
					</span><br/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:call-template name="show-noneFlavor">
							<xsl:with-param name="data" select="$nullEntry/@nullFlavor"/>
					</xsl:call-template>
				</xsl:otherwise>
			
	</xsl:choose>


</xsl:template>


</xsl:stylesheet>