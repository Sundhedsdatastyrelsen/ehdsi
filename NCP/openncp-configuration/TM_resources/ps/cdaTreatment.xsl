<?xml version="1.0"  ?>






<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:epsos="urn:epsos-org:ep:medication" >
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>


<!-- variable to check that at least one alert section exist -->
<xsl:variable name="treatmentExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code='18776-5']"/>



	<!--alerts -->

<xsl:template name="treatment" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">

	<!-- if we have at least one alert section -->
		<xsl:choose>
	<!-- if we have at least one alert section -->
			<xsl:when test="($treatmentExist)">

					<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
							<xsl:call-template name="treatmentSection"/>
					</xsl:for-each>
			<br/><br/>
 		 	</xsl:when>
  	<!-- else -->
  	<!--
			<xsl:otherwise>
  					<span class="sectionTitle">
  						<xsl:text>The treatment section is missing</xsl:text>
  					</span><br/><br/>
  
  		</xsl:otherwise>
  		-->
		</xsl:choose>
  </xsl:template>


	<xsl:template name="treatmentSection">

	<!-- Defing all needed variables -->

	<xsl:variable
	name="treatmentSectionTitleCode"
	select="n1:code/@code"/>


		<xsl:variable
	name="treatmentSectionTitle"
	select="n1:code[@code='18776-5']/@displayName"/>
	
		<xsl:variable
	name="treatmentSectionText"
	select="n1:code[@code='18776-5']/../n1:text"/>
	

	<!-- End definition of variables-->
<xsl:choose>
	<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
 	 	<xsl:when test=" ($treatmentSectionTitleCode='18776-5')">
				<span class="sectionTitle">
					<xsl:value-of select="$treatmentSectionTitle"/>
						
				</span><br/>
	  				
    				<xsl:value-of select="$treatmentSectionText"/>
    				
    	
					
				
		</xsl:when>
	</xsl:choose>






	<xsl:for-each select="n1:component/n1:section">
		<xsl:call-template name="treatmentSection">
		</xsl:call-template>
	</xsl:for-each>


	
</xsl:template>
	<!-- top level section title -->



</xsl:stylesheet>