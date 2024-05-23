<?xml version="1.0"  ?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:epsos="urn:epsos-org:ep:medication" >
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>
	<!-- variable to check that at least one alert section exist -->
	<xsl:variable name="vaccinationsExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code='11369-6']"/>

	<!--alerts -->

	<xsl:template name="vaccinations" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">

		<!-- if we have at least one alert section -->
		<xsl:choose>
			<!-- if we have at least one alert section -->
			<xsl:when test="($vaccinationsExist)">

				<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
					<xsl:call-template name="vaccinationsSection"/>
				</xsl:for-each>
				<br/>
				<br/>
			</xsl:when>
			<!-- else -->
			<!--
			<xsl:otherwise>
  					<span class="sectionTitle">
  						<xsl:text>The Vaccinations section is missing</xsl:text>
  					</span><br/><br/>
  
  		</xsl:otherwise>
  		-->
		</xsl:choose>
	</xsl:template>

	<xsl:template name="vaccinationsSection">

		<!-- Defing all needed variables -->

		<xsl:variable
	name="vaccinationsSectionTitleCode"
	select="n1:code/@code"/>

		<xsl:variable
	name="vaccinationsSectionTitle"
	select="n1:code[@code='11369-6']/@displayName"/>

		<xsl:variable
	name="nullEntry"
	select="n1:entry"/>

		<xsl:variable name="vacAct"
	select="n1:entry/n1:substanceAdministration"/>

		<!-- End definition of variables-->
		<xsl:choose>
			<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
			<xsl:when test=" ($vaccinationsSectionTitleCode='11369-6')">
				<span class="sectionTitle">
					<xsl:value-of select="$vaccinationsSectionTitle"/>
				</span>
				<br/>
				<xsl:choose>
					<!-- null flavor entry-->
					<xsl:when test="not ($vacAct/@nullFlavor)">
						<table>
							<tbody>
								<tr>
									<th>
										<!--  Vaccination -->
										<xsl:call-template  name="show-displayLabels">
											<xsl:with-param name="data" select="'79'"/>
										</xsl:call-template>                					
									</th>
									<th>
										<!--  BrandName -->
										<xsl:call-template  name="show-displayLabels">
											<xsl:with-param name="data" select="'9'"/>
										</xsl:call-template>              					
									</th>
									<th>
										<!--  Vaccination Date -->
										<xsl:call-template  name="show-displayLabels">
											<xsl:with-param name="data" select="'80'"/>
										</xsl:call-template>  
									</th>
								</tr>	
								<xsl:for-each select="n1:entry">
									<xsl:call-template name="vaccinationsSectionEntry">
									</xsl:call-template>
								</xsl:for-each>
							</tbody>
						</table>
					</xsl:when>
					<xsl:otherwise>
						<xsl:call-template name="show-noneFlavor">
							<xsl:with-param name="data" select="$vacAct/@nullFlavor"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
		</xsl:choose>
	</xsl:template>

	<!--  foreach entry of the section -->	

	<xsl:template name="vaccinationsSectionEntry">

		<!-- Defing all needed variables -->

		<xsl:variable
	name="vaccinationsNode"
	select="n1:substanceAdministration/n1:templateId[@root= '2.16.840.1.113883.10.20.1.24']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:code"/>
		<xsl:variable
	name="vaccinations"
	select="n1:substanceAdministration/n1:templateId[@root= '2.16.840.1.113883.10.20.1.24']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:code/@displayName"/>
		<xsl:variable
	name="vaccinationsBrandName"
	select="n1:substanceAdministration/n1:templateId[@root= '2.16.840.1.113883.10.20.1.24']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:code/n1:translation/@displayName"/>
		<xsl:variable
	name="vaccinationsDate"
	select="n1:substanceAdministration/n1:templateId[@root= '2.16.840.1.113883.10.20.1.24']/../n1:effectiveTime"/>
		<xsl:variable
	name="nullEntry"
	select="."/>
		<xsl:variable name="vacAct"
	select="n1:substanceAdministration"/>

		<!-- End definition of variables-->

		<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->

		<!-- nullflavored act -->
		<xsl:choose>
			<xsl:when  test="not($vacAct/@nullFlavor)">
				<tr>	
					<td>
						<xsl:choose>
							<xsl:when test="not ($vaccinationsNode/@nullFlavor)">
								<xsl:choose>
									<xsl:when test="$vaccinations">
										<xsl:value-of select ="$vaccinations"/>
									</xsl:when>
									<xsl:otherwise>
										<!-- uncoded element Problem -->
										<xsl:if test ="$vaccinationsNode/n1:originalText/n1:reference/@value">	
											<xsl:call-template name="show-uncodedElement">
												<xsl:with-param name="data" select="$vaccinationsNode/n1:originalText/n1:reference/@value"/>
											</xsl:call-template>
										</xsl:if>
									</xsl:otherwise>
								</xsl:choose>
							</xsl:when>
							<xsl:otherwise>
								<xsl:call-template name="show-noneFlavor">
									<xsl:with-param name="data" select="$vaccinationsNode/@nullFlavor"/>
								</xsl:call-template>
							</xsl:otherwise>
						</xsl:choose>
					</td>
					<td>
						<xsl:value-of select ="$vaccinationsBrandName"/>
					</td>
					<td>
						<xsl:call-template name="show-time">
							<xsl:with-param name="datetime" select="$vaccinationsDate"/>
						</xsl:call-template>&#160;
					</td>
				</tr>
			</xsl:when>

			<xsl:otherwise>
				<tr>
					<td colspan="3">
						<xsl:call-template name="show-noneFlavor">
							<xsl:with-param name="data" select="$vacAct/@nullFlavor"/>
						</xsl:call-template>
					</td>
				</tr>	
			</xsl:otherwise>

		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>