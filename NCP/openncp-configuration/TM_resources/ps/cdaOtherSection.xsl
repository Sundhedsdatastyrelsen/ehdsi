<?xml version="1.0"  ?>


<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:epsos="urn:epsos-org:ep:medication" >
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>


<!-- variable to check that at least one alert section exist -->
<xsl:variable name="otherExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code!='48765-2'][@code!='47420-5'][@code!='11450-4'][@code!='30954-2'][@code!='11348-0'][@code!='46264-8'][@code!='10160-0'][@code!='8716-3'][@code!='10162-6'][@code!='29762-2'][@code!='47519-4'][@code!='18776-5'][@code!='11369-6']"/>



	<!--alerts -->

<xsl:template name="otherSections" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">

	<!-- if we have at least one alert section -->
		<xsl:choose>
	<!-- if we have at least one alert section -->
			<xsl:when test="($otherExist)">

					<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
							<xsl:call-template name="otherSection"/>
					</xsl:for-each>
			<br/><br/>
 		 	</xsl:when>
  	
		</xsl:choose>
  </xsl:template>


	<xsl:template name="otherSection">

	<!-- Defing all needed variables -->

	<xsl:variable
	name="otherSectionTitleCode"
	select="n1:code/@code"/>


		<xsl:variable
	name="otherSectionTitle"
	select="n1:code[@code!='48765-2'][@code!='47420-5'][@code!='11450-4'][@code!='30954-2'][@code!='11348-0'][@code!='46264-8'][@code!='10160-0'][@code!='8716-3'][@code!='10162-6'][@code!='29762-2'][@code!='47519-4'][@code!='18776-5'][@code!='11369-6']/@displayName"/>
	
		<xsl:variable
	name="otherSectionText"
	select="n1:code[@code!='48765-2'][@code!='47420-5'][@code!='11450-4'][@code!='30954-2'][@code!='11348-0'][@code!='46264-8'][@code!='10160-0'][@code!='8716-3'][@code!='10162-6'][@code!='29762-2'][@code!='47519-4'][@code!='18776-5'][@code!='11369-6']/../n1:text"/>
	

	<!-- End definition of variables-->
<xsl:choose>
	<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
 	 	<xsl:when test=" ($otherSectionTitleCode!='48765-2' and $otherSectionTitleCode!='47420-5' and $otherSectionTitleCode!='11450-4' and $otherSectionTitleCode!='30954-2' and $otherSectionTitleCode!='11348-0' and $otherSectionTitleCode!='46264-8' and $otherSectionTitleCode!='10160-0' and $otherSectionTitleCode!='8716-3' and $otherSectionTitleCode!='10162-6' and $otherSectionTitleCode!='29762-2' and $otherSectionTitleCode!='47519-4' and $otherSectionTitleCode!='18776-5' and $otherSectionTitleCode!='11369-6')">
				<span class="sectionTitle">
					<xsl:value-of select="$otherSectionTitle"/>
						
				</span><br/>
	  				
    				<xsl:value-of select="$otherSectionText"/>
    				<br/>
    	</xsl:when>
	</xsl:choose>






	<xsl:for-each select="n1:component/n1:section">
		<xsl:call-template name="otherSection">
		</xsl:call-template>
	</xsl:for-each>


	
</xsl:template>
	<!-- top level section title -->



</xsl:stylesheet>