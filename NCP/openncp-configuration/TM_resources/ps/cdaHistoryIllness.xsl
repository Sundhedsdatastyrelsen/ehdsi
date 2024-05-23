<?xml version="1.0"  ?>






<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:epsos="urn:epsos-org:ep:medication" >
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>


<!-- variable to check that at least one alert section exist -->
<xsl:variable name="historyIllnessExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code='11348-0']"/>



	<!--alerts -->

<xsl:template name="historyIllness" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">

	<!-- if we have at least one alert section -->
		<xsl:choose>
	<!-- if we have at least one alert section -->
			<xsl:when test="($historyIllnessExist)">

					<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
							<xsl:call-template name="historyIllnessSection"/>
					</xsl:for-each>
			<br/><br/>
 		 	</xsl:when>
  	<!-- else -->
			<xsl:otherwise>
  					<span class="sectionTitle">
  						<!--xsl:text>The Procedures section is missing</xsl:text-->
  					</span><br/><br/>
  
  		</xsl:otherwise>
		</xsl:choose>
  </xsl:template>


	<xsl:template name="historyIllnessSection">

	<!-- Defing all needed variables -->

	<xsl:variable
	name="historyIllnessSectionTitleCode"
	select="n1:code/@code"/>


		<xsl:variable
	name="historyIllnessSectionTitle"
	select="n1:code[@code='11348-0']/@displayName"/>
	
	
	

<xsl:variable
	name="nullEntry"
	select="n1:entry"/>


	<xsl:variable name="historycAct"
	select="n1:entry/n1:entry"/>




	<!-- End definition of variables-->
<xsl:choose>
	<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
 	 	<xsl:when test=" ($historyIllnessSectionTitleCode='11348-0')">
				<span class="sectionTitle">
					<xsl:value-of select="$historyIllnessSectionTitle"/>
						
				</span><br/>
			 <!-- nullflavored act -->
  				
  			<xsl:choose>
  				<xsl:when  test="not($historycAct/@nullFlavor)">
    					<table>
            		<tbody>
            			<tr>
            				<th>
            					<!-- xsl:text>Closed Inactive Problem</xsl:text-->
            					<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'11'"/>
							</xsl:call-template>
            				</th>
            				<th>
            					<!-- xsl:text>Onset Date</xsl:text-->
            					<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'45'"/>
							</xsl:call-template>
            				</th>
            			
            			<th>
            					<!-- xsl:text>End Date</xsl:text-->
            					<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'26'"/>
							</xsl:call-template>
            				</th>
            			
            			</tr>	

					<xsl:for-each select="n1:entry">
						<xsl:call-template name="historyIllnessSectionEntry">
						</xsl:call-template>
						</xsl:for-each>
				</tbody>
				</table>
            			
  					

			</xsl:when>
		
					
					<xsl:otherwise>
		
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$historycAct/@nullFlavor"/>
								</xsl:call-template>
					
		
					</xsl:otherwise>
					
			
		</xsl:choose>
		</xsl:when>
	</xsl:choose>






	
</xsl:template>
	
	
	<!-- FOR EACH ENTRY -->
	
	
	
	<xsl:template name="historyIllnessSectionEntry">

	<!-- Defing all needed variables -->

	
	
		<xsl:variable
	name="historyIllnessClosedProblemNode"
	select="n1:act/n1:templateId[@root= '1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5']/../n1:value"/>


	<xsl:variable
	name="historyIllnessClosedProblem"
	select="n1:act/n1:templateId[@root= '1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5']/../n1:value/@displayName"/>

<xsl:variable
	name="historyIllnessClosedProblemID"
	select="n1:act/n1:templateId[@root= '1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5']/../n1:value/@code"/>



<xsl:variable
	name="historyIllnessOnSetDate"
	select="n1:act/n1:templateId[@root= '1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:effectiveTime/n1:low"/>


<xsl:variable
	name="historyIllnessEndDate"
	select="n1:act/n1:templateId[@root= '1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:effectiveTime/n1:high"/>


<xsl:variable
	name="nullEntry"
	select="."/>


	<xsl:variable name="historycAct"
	select="n1:act"/>



	<!-- End definition of variables-->

			 <!-- nullflavored act -->
  			<xsl:choose>
  				<xsl:when  test="not($historycAct/@nullFlavor)">
  				
  				<xsl:choose>
    					<xsl:when  test="($historyIllnessClosedProblemID='396782006' or $historyIllnessClosedProblemID='407559004' or   $historyIllnessClosedProblemID='160243008' or  $historyIllnessClosedProblemID='160245001' )">
    						
    						<!-- display the relevant code from the v40_unknowInformarion -->
    				
    				
    				  				<xsl:call-template name="show-unknownInfo">
							<xsl:with-param name="data" select="$historyIllnessClosedProblemID"/>
					</xsl:call-template>
    				
    						   					
 							
  					</xsl:when>
  					<xsl:otherwise>
    				
            			<tr>	
            				<td>
            					<xsl:choose>
            						<xsl:when test="not ($historyIllnessClosedProblemNode/@nullFlavor)">
            							<xsl:choose>
            								<xsl:when test="$historyIllnessClosedProblem">
            									<xsl:value-of select ="$historyIllnessClosedProblem"/>
            								</xsl:when>
            								<xsl:otherwise>	
            								<!-- uncoded element Problem -->
            									<xsl:if test ="$historyIllnessClosedProblemNode/n1:originalText/n1:reference/@value">	
            										<xsl:call-template name="show-uncodedElement">
														<xsl:with-param name="data" select="$historyIllnessClosedProblemNode/n1:originalText/n1:reference/@value"/>
													</xsl:call-template>
												</xsl:if>
            							
            								</xsl:otherwise>
            								</xsl:choose>
            							</xsl:when>
            						<xsl:otherwise>
            							<xsl:call-template name="show-noneFlavor">
											<xsl:with-param name="data" select="$historyIllnessClosedProblemNode/@nullFlavor"/>
										</xsl:call-template>
									</xsl:otherwise>
								</xsl:choose>
							
            				
            				
            				</td>
            				<td>
            					<xsl:call-template name="show-time">
            								<xsl:with-param name="datetime" select="$historyIllnessOnSetDate"/>
													</xsl:call-template>&#160;
            				</td>
            					<td>
            					<xsl:call-template name="show-time">
            								<xsl:with-param name="datetime" select="$historyIllnessEndDate"/>
													</xsl:call-template>&#160;
            				</td>
            				</tr>
            		
    					</xsl:otherwise>
  				</xsl:choose>
    			
					</xsl:when>
					
					<xsl:otherwise>
					<tr><td colspan="3">
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$historycAct/@nullFlavor"/>
								</xsl:call-template>
					
					</td></tr>
					</xsl:otherwise>
					
			
	</xsl:choose>


</xsl:template>



</xsl:stylesheet>