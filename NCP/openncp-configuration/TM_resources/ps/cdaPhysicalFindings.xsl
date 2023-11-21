<?xml version="1.0"  ?>






<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:epsos="urn:epsos-org:ep:medication" >
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>


<!-- variable to check that at least one alert section exist -->
<xsl:variable name="physicalFindingsExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code='8716-3']"/>



	<!--alerts -->

<xsl:template name="physicalFindings" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">

	<!-- if we have at least one alert section -->
		<xsl:choose>
	<!-- if we have at least one alert section -->
			<xsl:when test="($physicalFindingsExist)">

					<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
							<xsl:call-template name="physicalFindingsSection"/>
					</xsl:for-each>
			<br/><br/>
 		 	</xsl:when>
  	<!-- else -->
			<xsl:otherwise>
  					<span class="sectionTitle">
  						<!--  The Medical devices and implants section is missing 
              				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'74'"/>
							</xsl:call-template>    	-->					 						
  					</span><br/><br/>
  
  		</xsl:otherwise>
		</xsl:choose>
  </xsl:template>


	<xsl:template name="physicalFindingsSection">

	<!-- Defing all needed variables -->

	<xsl:variable
	name="physicalFindingsSectionTitleCode"
	select="n1:code/@code"/>


		<xsl:variable
	name="physicalFindingsSectionTitle"
	select="n1:code[@code='8716-3']/@displayName"/>
	
	
	
		
		
		<xsl:variable
	name="systolicBLabel"
	select="n1:entry/n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8480-6']/@displayName"/>

		
		<xsl:variable
	name="diastolicBLabel"
	select="n1:entry/n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8462-4']/@displayName"/>

		
		


<xsl:variable
	name="nullEntry"
	select="n1:entry"/>


	<xsl:variable name="physAct"
	select="n1:entry/n1:observation"/>




	<!-- End definition of variables-->
<xsl:choose>
	<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
 	 	<xsl:when test=" ($physicalFindingsSectionTitleCode='8716-3')">
				<span class="sectionTitle">
					<xsl:value-of select="$physicalFindingsSectionTitle"/>
						
				</span><br/>
			 <!-- nullflavored act -->
  			<xsl:choose>
  				<xsl:when  test="not($physAct/@nullFlavor)">
  			
    					
    			<table>
            		<tbody>
            			<tr>
            				<th>
            					<!--  Date -->
            					<xsl:call-template  name="show-displayLabels">
									<xsl:with-param name="data" select="'17'"/>
								</xsl:call-template>    
            				</th>
            				<th>
            					<xsl:value-of select="$systolicBLabel"/>
            				</th>
            			<th>
            					<xsl:value-of select="$diastolicBLabel"/>
            				</th>
            			
            			
            			
            			</tr>	
            			
            			
            				<xsl:for-each select="n1:entry">
								<xsl:call-template name="physicalFindingsSectionEntry">
								</xsl:call-template>
							</xsl:for-each>
            			
            			</tbody>
            		</table>
    	
					</xsl:when>
					
					<xsl:otherwise>
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$physAct/@nullFlavor"/>
								</xsl:call-template>
					
					</xsl:otherwise>
					
				</xsl:choose>
		</xsl:when>
	</xsl:choose>


	
</xsl:template>
	<!-- FOR EACH ENTRY -->
	
	
	
	<xsl:template name="physicalFindingsSectionEntry">

	<!-- Defing all needed variables -->

	
	
		
		
		<xsl:variable
	name="systolicBLabel"
	select="n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8480-6']/@displayName"/>

		
		<xsl:variable
	name="diastolicBLabel"
	select="n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8462-4']/@displayName"/>

		
		
		<xsl:variable
	name="systolicBValue"
	select="n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8480-6']/../n1:value/@value"/>

		
			
		<xsl:variable
	name="diastolicBValue"
	select="n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8462-4']/../n1:value/@value"/>

			
		<xsl:variable
	name="systolicBUnit"
	select="n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8480-6']/../n1:value/@unit"/>

<xsl:variable
	name="diastolicBUnit"
	select="n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8462-4']/../n1:value/@unit"/>



			<xsl:variable
	name="systolicBNode"
	select="n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:component/n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.2']/../n1:code[@code='8480-6']/../n1:value"/>

		
		
		<xsl:variable
	name="physicalFindingsDateFrom"
	select="n1:organizer/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.1']/../n1:effectiveTime"/>
		



<xsl:variable
	name="nullEntry"
	select="."/>


	<xsl:variable name="physAct"
	select="n1:observation"/>




	<!-- End definition of variables-->

			 <!-- nullflavored act -->
  			<xsl:choose>
  				<xsl:when  test="not($physAct/@nullFlavor)">
  			
    					
    			
            			<tr>	
            			
            			<td>
            					<xsl:call-template name="show-time">
            						<xsl:with-param name="datetime" select="$physicalFindingsDateFrom"/>
								</xsl:call-template>&#160;
            				
            			</td>
            		
            			
            				<td>
            			
									<xsl:choose>
            							<xsl:when test="not ($systolicBNode/@nullFlavor)">
            								<xsl:choose>
            									<xsl:when test="$systolicBValue">
            										<xsl:value-of select ="$systolicBValue"/>&#160;<xsl:value-of select ="$systolicBUnit"/>
            									</xsl:when>
            									<xsl:otherwise>
            										<!-- uncoded element Problem -->
            										<xsl:if test ="$systolicBNode/n1:originalText/n1:reference/@value">	
            											<xsl:call-template name="show-uncodedElement">
															<xsl:with-param name="data" select="$systolicBNode/n1:originalText/n1:reference/@value"/>
														</xsl:call-template>
													</xsl:if> 
												</xsl:otherwise>
											</xsl:choose>
            							</xsl:when>
            							<xsl:otherwise>
            								<xsl:call-template name="show-noneFlavor">
												<xsl:with-param name="data" select="$systolicBNode/@nullFlavor"/>
											</xsl:call-template>
										</xsl:otherwise>
									</xsl:choose>
							
            				
            				
            				
            				</td>
            			<td>
            				<xsl:value-of select ="$diastolicBValue"/>&#160;<xsl:value-of select ="$diastolicBUnit"/> 
            				</td>
            				</tr>
            		
    	
					</xsl:when>
					
					<xsl:otherwise>
					<tr><td colspan="3">
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$physAct/@nullFlavor"/>
								</xsl:call-template>
					</td></tr>
					</xsl:otherwise>
					
				</xsl:choose>
		


	
</xsl:template>


</xsl:stylesheet>