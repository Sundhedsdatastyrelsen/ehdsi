<?xml version="1.0"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>
	<!-- variable to check that at least one alert section exist -->
	<xsl:variable name="alertsExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code='48765-2']"/>
	<!--alerts -->

	<xsl:template name="alerts" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">
		<xsl:choose>
			<!-- if we have at least one alert section -->
			<xsl:when test=" ($alertsExist)">
				<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
					<xsl:call-template name="alertSection"/>
				</xsl:for-each>
				<br/>
				<br/>
			</xsl:when>
			<!-- else -->
			<xsl:otherwise>
				<span class="sectionTitle">
					<!-- xsl:text>The Allergies, adverse reactions, alerts section is missing !
  					La sezione Allergie, reazioni avverse ed allarmi non è presente</xsl:text-->

					<xsl:call-template  name="show-displayLabels">
						<xsl:with-param name="data" select="'73'"/>
					</xsl:call-template>
				</span>
				<br/>
				<br/>

			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>


	<xsl:template name="alertSection">

		<!-- Defing all needed variables -->

		<xsl:variable
	name="sectionTitleCode"
	select="n1:code/@code"/>


		<xsl:variable
	name="sectionTitle"
	select="n1:code[@code='48765-2']/@displayName"/>




		<xsl:variable name="act"
	select="n1:entry/n1:act"/>


		<xsl:variable name="obsCode"
	select="n1:entry/n1:act/n1:templateId[@root='2.16.840.1.113883.10.20.1.27']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.6']/../n1:value/@code"/>
		<!-- in case of no info scenarion the following displayName will be displayed -->
		<xsl:variable name="obsDisplay"
	select="n1:entry/n1:act/n1:templateId[@root='2.16.840.1.113883.10.20.1.27']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.6']/../n1:value/@displayName"/>




		<!-- End definition of variables-->


		<!--- table Display -->
		<xsl:choose>
			<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
			<xsl:when test=" ($sectionTitleCode='48765-2')">
				<span class="sectionTitle">
					<xsl:value-of select="$sectionTitle"/>
				</span>
				<br/>
				<!-- nullflavored act -->
				<xsl:choose>
					<xsl:when  test="not($act/@nullFlavor)">

						<table>
							<tbody>
								<xsl:if test="	not ($obsCode='160244002' or $obsCode='409137002')">
									<tr>
										<th>
											<!-- xsl:text>Reaction Type </xsl:text -->
											<xsl:call-template  name="show-displayLabels">
												<xsl:with-param name="data" select="'65'"/>
											</xsl:call-template>
										</th>
										<th>
											<!-- xsl:text>Clinical Manifestation </xsl:text -->
											<xsl:call-template  name="show-displayLabels">
												<xsl:with-param name="data" select="'10'"/>
											</xsl:call-template>
										</th>
										<th>
											<!-- xsl:text>Agent </xsl:text-->
											<xsl:call-template  name="show-displayLabels">
												<xsl:with-param name="data" select="'5'"/>
											</xsl:call-template>
										</th>
										<th>
											<!-- xsl:text>OnSet Date </xsl:text-->
											<xsl:call-template  name="show-displayLabels">
												<xsl:with-param name="data" select="'45'"/>
											</xsl:call-template>
										</th>
									</tr>
								</xsl:if>
								<xsl:for-each select="n1:entry">
									<xsl:call-template name="alertSectionEntry">
									</xsl:call-template>
								</xsl:for-each>

							</tbody>
						</table>	
					</xsl:when>	

					<xsl:otherwise>
						<xsl:call-template name="show-noneFlavor">
							<xsl:with-param name="data" select="$act/@nullFlavor"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>	
		</xsl:choose>	

	</xsl:template>
	<!-- alert section entry -->


	<xsl:template name="alertSectionEntry">

		<!-- Defing all needed variables -->



		<xsl:variable
	name="reactionType"
	select="n1:act/n1:templateId[@root= '2.16.840.1.113883.10.20.1.27']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.6']/../n1:code/@displayName"/>

		<xsl:variable name="clinicalManifestation"
	select="n1:act/n1:templateId[@root='2.16.840.1.113883.10.20.1.27']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.6']/../n1:entryRelationship[@typeCode='MFST']/n1:observation/n1:templateId[@root='2.16.840.1.113883.10.20.1.54']/../n1:value/@displayName"/>


		<xsl:variable name="agentID"
	select="n1:act/n1:templateId[@root='2.16.840.1.113883.10.20.1.27']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.6']/../n1:participant[@typeCode='CSM']/n1:participantRole[@classCode='MANU']/n1:playingEntity[@classCode='MMAT']/n1:code/@code"/>


		<xsl:variable name="agentDescription"
	select="n1:act/n1:templateId[@root='2.16.840.1.113883.10.20.1.27']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.6']/../n1:participant[@typeCode='CSM']/n1:participantRole[@classCode='MANU']/n1:playingEntity[@classCode='MMAT']/n1:code"/>

		<xsl:variable name="onSetDate"
	select="n1:act/n1:templateId[@root='2.16.840.1.113883.10.20.1.27']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.6']/../n1:effectiveTime/n1:low"/>



		<!-- observation value code -->
		<!-- not sure if we have to filter with root and template ids-->
		<!---
	answer by Giorgio for checking the no info scenario
	
	entry/act[templateId/@root= ‘2.16.840.1.113883.10.20.1.27’]/entryRelationship[@typeCode=’SUBJ’]/observation[templateId/@root=’1.3.6.1.4.1.19376.1.5.3.1.4.6’]/code/@ displayName -->




		<xsl:variable name="obsCode"
	select="n1:act/n1:templateId[@root='2.16.840.1.113883.10.20.1.27']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.6']/../n1:value/@code"/>
		<!-- in case of no info scenarion the following displayName will be displayed -->
		<xsl:variable name="obsDisplay"
	select="n1:act/n1:templateId[@root='2.16.840.1.113883.10.20.1.27']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.6']/../n1:value/@displayName"/>




		<xsl:variable name="act"
	select="n1:act"/>



		<!-- End definition of variables-->



		<!--- table Display -->

		<!-- nullflavored act -->
		<xsl:choose>
			<xsl:when  test="not($act/@nullFlavor)">

				<!-- No info Scenario... observarion.value@code is one of the two values -->

				<xsl:choose>

					<xsl:when  test="($obsCode='160244002' or $obsCode='409137002')">
						<tr>
							<td coslpan="4">
								<!-- xsl:call-template name="show-unknownInfo">
							<xsl:with-param name="data" select="$obsCode"/>
					</xsl:call-template-->

								<span class="tdtext">
									<xsl:value-of select="$obsDisplay"/>
								</span>
								<br/>
							</td>
						</tr>

					</xsl:when>

					<xsl:otherwise>


						<tr>
							<td>
								<xsl:value-of select="$reactionType"/>
								<xsl:call-template name="show-noneFlavor">
									<xsl:with-param name="data" select="$reactionType/@nullFlavor"/>
								</xsl:call-template>
							</td>
							<td>
								<xsl:value-of select="$clinicalManifestation"/>
							</td>
							<td>

								<xsl:choose>	
									<xsl:when test=" not($agentDescription/@nullFlavor)">
										<xsl:choose>
											<xsl:when test="$agentDescription/@displayName">
												<xsl:value-of select="$agentDescription/@displayName"/>
												<br/>(<xsl:value-of select="$agentID"/>)
											</xsl:when>
											<xsl:otherwise>
												<!--  uncoded element -->
												<xsl:if test ="$agentDescription/n1:originalText/n1:reference/@value">	
													<xsl:call-template name="show-uncodedElement">
														<xsl:with-param name="data" select="$agentDescription/n1:originalText/n1:reference/@value"/>
													</xsl:call-template>
												</xsl:if>
											</xsl:otherwise>
										</xsl:choose>
									</xsl:when>
									<xsl:otherwise>
										<xsl:call-template name="show-noneFlavor">
											<xsl:with-param name="data" select="$agentDescription/@nullFlavor"/>
										</xsl:call-template>
									</xsl:otherwise>

								</xsl:choose>
							</td>
							<td>

								<xsl:call-template name="show-time">
									<xsl:with-param name="datetime" select="$onSetDate"/>
								</xsl:call-template>&#160;

							</td>
						</tr>

					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>

			<xsl:otherwise>
				<tr>
					<td colspan="3">
						<xsl:call-template name="show-noneFlavor">
							<xsl:with-param name="data" select="$act/@nullFlavor"/>
						</xsl:call-template>
					</td>
				</tr>		
			</xsl:otherwise>
		</xsl:choose>





	</xsl:template>



</xsl:stylesheet>