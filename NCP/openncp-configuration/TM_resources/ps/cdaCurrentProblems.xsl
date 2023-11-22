<?xml version="1.0"  ?>




<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>


<!-- variable to check that at least one problem section exist -->
<xsl:variable name="problemExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code='11450-4']"/>


	<!--current problems -->

<xsl:template name="currentProblems" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">
	<xsl:choose>
	<!-- if we have at least one alert section -->
			<xsl:when test=" ($problemExist)">

		<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
			<xsl:call-template name="currentProblemsSection"/>
		</xsl:for-each>
			<br/><br/>
  	</xsl:when>
  	
  	
  		<!-- else -->
	<xsl:otherwise>
  				<span class="sectionTitle">
  					<!-- xsl:text>TThe Active Problem section is missing !</xsl:text-->
  					
  					<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'72'"/>
							</xsl:call-template>
  					
  				</span><br/><br/>
  
  			</xsl:otherwise>
</xsl:choose>
  	
  </xsl:template>

<!--  section -->
	<xsl:template name="currentProblemsSection">

	<!-- Defing all needed variables -->


	<xsl:variable
	name="probSectionTitleCode"
	select="n1:code/@code"/>


		<xsl:variable
	name="probSectionTitle"
	select="n1:code[@code='11450-4']/@displayName"/>




<xsl:variable
	name="nullEntry"
	select="n1:entry"/>


<xsl:variable name="probObsCode"
	select="n1:entry/n1:act/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5']/../n1:value/@code"/>

<!-- null flavor entry act -->

	<xsl:variable name="probAct"
	select="n1:entry/n1:act"/>
	<!-- End definition of variables-->


	<!--- table Display -->
	<xsl:choose>
	<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
 	 	<xsl:when test=" ($probSectionTitleCode='11450-4')">
  		<span class="sectionTitle">
  			<xsl:value-of select="$probSectionTitle"/>
  		</span><br/>
  <!-- nullflavored act -->
  			<xsl:choose>
  			<xsl:when  test="not($probAct/@nullFlavor)">
            	<table>
            		<tbody>
            			<xsl:if  test="not ($probObsCode='396782006' or $probObsCode='407559004' or   $probObsCode='160243008' or  $probObsCode='160245001' )">
    				
            			<tr>
            				<th>
            					<!-- xsl:text>Active Problem </xsl:text-->
            					
            				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'2'"/>
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
									<xsl:call-template name="currentProblemsSectionEntry"/>
								
								</xsl:for-each>
            			
            			
            		</tbody>
            	</table>
  				
  			</xsl:when>
  			
  			
  			
  			<xsl:otherwise>
  			
  				<xsl:call-template name="show-noneFlavor">
							<xsl:with-param name="data" select="$probAct/@nullFlavor"/>
					</xsl:call-template>
			
				</xsl:otherwise>
	</xsl:choose>
  			</xsl:when>
  	</xsl:choose>





	
</xsl:template>


<!-- - FOR EACH ENTRY -->



<xsl:template name="currentProblemsSectionEntry">

	<!-- Defing all needed variables -->




<xsl:variable name="activeProblemNode"
	select="n1:act/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5']/../n1:value"/>



<xsl:variable name="activeProblem"
	select="n1:act/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5']/../n1:value/@displayName"/>


<xsl:variable name="activeProblemID"
	select="n1:act/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5']/../n1:value/@code"/>



	<xsl:variable
	name="probOnSetDate"
	select="n1:act/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:effectiveTime/n1:low"/>

<xsl:variable name="probObsCode"
	select="n1:act/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5']/../n1:value/@code"/>
<!-- in case of no info scenarion the following displayName will be displayed -->
	<xsl:variable name="probObsDisplay"
	select="n1:act/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5.2']/../n1:entryRelationship[@typeCode='SUBJ']/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.5']/../n1:value/@displayName"/>



<xsl:variable
	name="nullEntry"
	select="."/>

<!-- null flavor entry act -->

	<xsl:variable name="probAct"
	select="n1:act"/>
	<!-- End definition of variables-->


	<!--- table Display -->
	
  <!-- nullflavored act -->
  		<xsl:choose>
  			<xsl:when  test="not($probAct/@nullFlavor)">
  				
  				<!-- No info Scenario... observarion.value@code is one of the two values -->
  				
  				<xsl:choose>
  				
  				
    					<xsl:when  test="($probObsCode='396782006' or $probObsCode='407559004' or   $probObsCode='160243008' or  $probObsCode='160245001' )">
    						
    						<!-- display the relevant code from the v40_unknowInformarion -->
    				
    			<tr>
            		<td colspan="3">	
    				  				<xsl:call-template name="show-unknownInfo">
							<xsl:with-param name="data" select="$probObsCode"/>
					</xsl:call-template>
    				
    			</td></tr>			   					
 								<!--xsl:value-of select="document('v40_unknownInfo.xml')/UnknownInformation/UnknownEntry[@code=$probObsCode]/displayName"/-->  				
  					</xsl:when>
  					<xsl:otherwise>
            
            			
            			<tr>
            				<td>
            					<xsl:choose>
            							<xsl:when test="not ($activeProblemNode/@nullFlavor)">
            								<xsl:choose>
            									<xsl:when test="$activeProblem">
            										<xsl:value-of select="$activeProblem"/>(	<xsl:value-of select="$activeProblemID"/>)
            									</xsl:when>
            									<xsl:otherwise>
            										<!-- uncoded element Problem -->
            										<xsl:if test ="$activeProblemNode/n1:originalText/n1:reference/@value">	
            											<xsl:call-template name="show-uncodedElement">
															<xsl:with-param name="data" select="$activeProblemNode/n1:originalText/n1:reference/@value"/>
														</xsl:call-template>
													</xsl:if>
            									</xsl:otherwise>
            								</xsl:choose>
            							</xsl:when>
            						<xsl:otherwise>
            							<xsl:call-template name="show-noneFlavor">
											<xsl:with-param name="data" select="$activeProblemNode/@nullFlavor"/>
										</xsl:call-template>
									</xsl:otherwise>
								
            			</xsl:choose>	
            				</td>
            				
            				<td>
            				
            					<xsl:call-template name="show-time">
												<xsl:with-param name="datetime" select="$probOnSetDate"/>
											</xsl:call-template>&#160;
            					
            				</td>
            			</tr>
            	
  					</xsl:otherwise>
  				</xsl:choose>
  			</xsl:when>
  			
  			<xsl:otherwise>
  			<tr><td colspan="3">
  				<xsl:call-template name="show-noneFlavor">
							<xsl:with-param name="data" select="$probAct/@nullFlavor"/>
					</xsl:call-template>
			</td></tr>	
				</xsl:otherwise>
			</xsl:choose>
 
  		
	
	
</xsl:template>



</xsl:stylesheet>