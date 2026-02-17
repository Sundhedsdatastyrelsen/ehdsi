<?xml version="1.0"  ?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:epsos="urn:epsos-org:ep:medication" >
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>
	<!-- variable to check that at least one alert section exist -->
<xsl:variable name="medicationSummaryExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code='10160-0']"/>

<!--alerts -->

<xsl:template name="medicationSummary" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">

	<!-- if we have at least one alert section -->
		<xsl:choose>
	<!-- if we have at least one alert section -->
			<xsl:when test="($medicationSummaryExist)">

					<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
							<xsl:call-template name="medicationSummarySection"/>
					</xsl:for-each>
			<br/><br/>
 		 	</xsl:when>
  	<!-- else -->
			<xsl:otherwise>
  					<span class="sectionTitle">
  						<!--  Medications Summary non presente ! -->
              				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'75'"/>
							</xsl:call-template>    						
  					</span><br/><br/>
  
  		</xsl:otherwise>
		</xsl:choose>
  </xsl:template>


	<xsl:template name="medicationSummarySection">

	<!-- Defing all needed variables -->

	<xsl:variable
	name="medSectionTitleCode"
	select="n1:code/@code"/>


		<xsl:variable
	name="medSsectionTitle"
	select="n1:code[@code='10160-0']/@displayName"/>
	
	
	

<xsl:variable
	name="nullEntry"
	select="n1:entry"/>


	<xsl:variable name="medAct"
	select="n1:entry/n1:act"/>

	<!-- End definition of variables-->
<xsl:choose>
	<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
 	 	<xsl:when test=" ($medSectionTitleCode='10160-0')">
				<span class="sectionTitle">
					<xsl:value-of select="$medSsectionTitle"/>
						
				</span><br/>
			 <!-- nullflavored act -->
  			<xsl:choose>
  				<xsl:when  test="not($medAct/@nullFlavor)">
  		
    					
    					<table>
            		<tbody>
            			<tr>
            				<th>
            					<!-- Active ingredient-->
            				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'1'"/>
							</xsl:call-template>
            				</th>
            				<th>
            				<!-- Strength -->
              				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'70'"/>
							</xsl:call-template>
            					
            				</th>
            				<th>
            					<!-- Dose form -->
              				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'25'"/>
							</xsl:call-template>            					
            				</th>
            				<th>
            					<!-- Units per intake -->
              				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'78'"/>
							</xsl:call-template>            				       					
	           				</th>
           					<th>
            					<!--  Frequency of intakes -->
	              				<xsl:call-template  name="show-displayLabels">
									<xsl:with-param name="data" select="'32'"/>
								</xsl:call-template>            			            					
            				</th>
           					<th>
            					<!-- Route of Administration-->
	              				<xsl:call-template  name="show-displayLabels">
									<xsl:with-param name="data" select="'67'"/>
								</xsl:call-template>            			            					            					
            				</th>
            				<th>
            					<!--  Onset Date -->
	              				<xsl:call-template  name="show-displayLabels">
									<xsl:with-param name="data" select="'45'"/>
								</xsl:call-template>            			            					            					
            				</th>
            				<th>
            					<!--  End Date -->
	              				<xsl:call-template  name="show-displayLabels">
									<xsl:with-param name="data" select="'26'"/>
								</xsl:call-template>            			            					
            					
            				</th>
            			</tr>	
            		
            		
						<xsl:for-each select="n1:entry">
							<xsl:call-template name="medicationSummarySectionEntry">
						</xsl:call-template>
					</xsl:for-each>

            		
            		
            			</tbody>
            		</table>
    	
    				
					</xsl:when>
					
					<xsl:otherwise>
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$medAct/@nullFlavor"/>
								</xsl:call-template>
					
					</xsl:otherwise>
					
				</xsl:choose>
		</xsl:when>
	</xsl:choose>


</xsl:template>
	<!--FOR EACH ENTRY-->
	
	
	
	
	
	<xsl:template name="medicationSummarySectionEntry">

	<!-- Defing all needed variables -->

	
	
	<xsl:variable
	name="medActiveIgredientNode"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:ingredient/epsos:code"/>



		

	<xsl:variable
	name="medActiveIgredient"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:ingredient/epsos:code/@displayName"/>


	<xsl:variable
	name="medActiveIgredientID"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:ingredient/epsos:code/@code"/>
	
	<xsl:variable
	name="medStrengthValue1"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:numerator/@value"/>
	<xsl:variable
	name="medStrengthValue2"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:denominator/@value"/>

<xsl:variable
	name="medStrengthUnit1"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:numerator/@unit"/>
	<xsl:variable
	name="medStrengthUnit2"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:denominator/@unit"/>


<xsl:variable
	name="medStrength1"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:numerator"/>
	<xsl:variable
	name="medStrength2"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:denominator"/>


<xsl:variable
	name="medDose"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:formCode/@displayName"/>

<xsl:variable
	name="medUnitIntakeLow"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:doseQuantity/n1:low/@value"/>
<xsl:variable
	name="medUnitIntakeHigh"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:doseQuantity/n1:high/@value"/>
<xsl:variable
	name="medUnitIntakeUnitLow"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:doseQuantity/n1:low/@unit"/>
<xsl:variable
	name="medUnitIntakeUnitHigh"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:doseQuantity/n1:high/@unit"/>


<xsl:variable
	name="medFrequencyIntake"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:effectiveTime[2]"/>


<xsl:variable
	name="medFrequencyIntakeType"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:effectiveTime[2]/@xsi:type"/>



<xsl:variable
	name="medRouteAdministration"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:routeCode/@displayName"/>


<xsl:variable
	name="medOnSetDate"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:effectiveTime[1][@xsi:type='IVL_TS']/n1:low"/>


<xsl:variable
	name="medEndDate"
	select="n1:substanceAdministration/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.4']/../n1:effectiveTime[1][@xsi:type='IVL_TS']/n1:high"/>



<xsl:variable
	name="nullEntry"
	select="."/>


	<xsl:variable name="medAct"
	select="n1:act"/>

	<!-- End definition of variables-->

			 <!-- nullflavored act -->
  			<xsl:choose>
  				<xsl:when  test="not($medAct/@nullFlavor)">
  		
    					
    			
            			<tr>	
            					<td>
            				
            				
										<xsl:choose>
            								<xsl:when test="not ($medActiveIgredientNode/@nullFlavor)">
            								<xsl:choose>
            									<xsl:when test="$medActiveIgredient">
            										<xsl:value-of select="$medActiveIgredient"/><br/>(<xsl:value-of select="$medActiveIgredientID"/>)
            									</xsl:when>
            				   				<xsl:otherwise>
            										<!-- uncoded element Problem -->
            									<xsl:if test ="$medActiveIgredientNode/n1:originalText/n1:reference/@value">	
            										<xsl:call-template name="show-uncodedElement">
														<xsl:with-param name="data" select="$medActiveIgredientNode/n1:originalText/n1:reference/@value"/>
													</xsl:call-template>
												</xsl:if>
											</xsl:otherwise>
											</xsl:choose>
            									
            									 
            								</xsl:when>
            				   				<xsl:otherwise>
            									<xsl:call-template name="show-noneFlavor">
													<xsl:with-param name="data" select="$medActiveIgredientNode/@nullFlavor"/>
												</xsl:call-template>
											</xsl:otherwise>
										</xsl:choose>
								
            					
            					
            					</td>
            					<td>
            							<xsl:call-template name="show-strength">
            									<xsl:with-param name="medStrengthUnit1" select="$medStrengthUnit1"/>
            									<xsl:with-param name="medStrengthUnit2" select="$medStrengthUnit2"/>
            									<xsl:with-param name="medStrengthValue1" select="$medStrengthValue1"/>
            									<xsl:with-param name="medStrengthValue2" select="$medStrengthValue2"/>
            									<xsl:with-param name="medStrength1" select="$medStrength1"/>
            									<xsl:with-param name="medStrength2" select="$medStrength2"/>
													</xsl:call-template>
            					
            				

            					</td>
            					<td>
            						<xsl:value-of select="$medDose"/>
            					</td>
            					<td>
            					
            					<xsl:call-template name="show-numberUnitIntake">
            									<xsl:with-param name="medUnitIntakeLow" select="$medUnitIntakeLow"/>
            									<xsl:with-param name="medUnitIntakeHigh" select="$medUnitIntakeHigh"/>
            									<xsl:with-param name="medUnitIntakeUnitLow" select="$medUnitIntakeUnitLow"/>
            									<xsl:with-param name="medUnitIntakeUnitHigh" select="$medUnitIntakeUnitHigh"/>
            								
													</xsl:call-template>
            					</td>
            					<td>
            					<xsl:call-template name="show-frequencyIntake">
            									<xsl:with-param name="medFrequencyIntakeType" select="$medFrequencyIntakeType"/>
            									<xsl:with-param name="medFrequencyIntake" select="$medFrequencyIntake"/>
            								
													</xsl:call-template>
            						
            					</td>
            					<td>
            						<xsl:value-of select="$medRouteAdministration"/>
            					</td>
            					<td>
            						
            							<xsl:call-template name="show-time">
            								<xsl:with-param name="datetime" select="$medOnSetDate"/>
													</xsl:call-template>&#160;
            					</td>
            					<td>
            						
            							<xsl:call-template name="show-time">
            								<xsl:with-param name="datetime" select="$medEndDate"/>
													</xsl:call-template>&#160;
            					</td>
            				</tr>
            		
    				
					</xsl:when>
					
					<xsl:otherwise>
					<tr><td colspan="5">
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$medAct/@nullFlavor"/>
								</xsl:call-template>
					</td></tr>
					</xsl:otherwise>
					
				</xsl:choose>
		







	
</xsl:template>
	



</xsl:stylesheet>