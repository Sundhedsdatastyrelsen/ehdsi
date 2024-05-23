<?xml version="1.0"  ?>






<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:epsos="urn:epsos-org:ep:medication" >
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>


<!-- variable to check that at least one alert section exist -->
<xsl:variable name="socialHistoryExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code='29762-2']"/>



	<!--alerts -->

<xsl:template name="socialHistory" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">

	<!-- if we have at least one alert section -->
		<xsl:choose>
	<!-- if we have at least one alert section -->
			<xsl:when test="($socialHistoryExist)">

					<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
							<xsl:call-template name="socialHistorySection"/>
					</xsl:for-each>
			<br/><br/>
 		 	</xsl:when>
  	<!-- else -->
  	<!-- 
			<xsl:otherwise>
  					<span class="sectionTitle">
  						<xsl:text>The Medical devices and implants section is missing</xsl:text>
  					</span><br/><br/>
  
  		</xsl:otherwise>
  		 -->
		</xsl:choose>
  </xsl:template>


	<xsl:template name="socialHistorySection">

	<!-- Defing all needed variables -->

	<xsl:variable
	name="socialHistorySectionTitleCode"
	select="n1:code/@code"/>


		<xsl:variable
	name="socialHistorySectionTitle"
	select="n1:code[@code='29762-2']/@displayName"/>
	
	
	
		



<xsl:variable
	name="nullEntry"
	select="n1:entry"/>


	<xsl:variable name="socHistAct"
	select="n1:entry/n1:observation"/>

	<!-- End definition of variables-->
<xsl:choose>
	<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
 	 	<xsl:when test=" ($socialHistorySectionTitleCode='29762-2')">
				<span class="sectionTitle">
					<xsl:value-of select="$socialHistorySectionTitle"/>
						
				</span><br/>
						<xsl:choose>
  				<xsl:when  test="not($socHistAct/@nullFlavor)">
  			
    		
    					<table>
            		<tbody>
            			<tr>
            				<th>
            				<!--  Observation Type -->
            				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'44'"/>
							</xsl:call-template>
            				</th>
            				<th>
            					<!--   Date From  -->
            				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'85'"/>
							</xsl:call-template>            					
            				</th>
            			
            			
            			<th>
            					<!--  Date To -->
            				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'18'"/>
							</xsl:call-template>            					
            					
            				</th>
            			
            			
            			<th>
            					<!--  Observation Value -->
            				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'84'"/>
							</xsl:call-template>              				
            				</th>
            			
            			</tr>	
            	
				
				
				
					<xsl:for-each select="n1:entry">
						<xsl:call-template name="socialHistorySectionEntry">
						</xsl:call-template>
				</xsl:for-each>
				</tbody>
        	</table>	
				</xsl:when>
					
					<xsl:otherwise>
					<tr><td colspan="3">
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$socHistAct/@nullFlavor"/>
								</xsl:call-template>
					</td></tr>
					</xsl:otherwise>
					
				</xsl:choose>





				
		</xsl:when>
	</xsl:choose>






	

	
</xsl:template>
	<!--Social  History  Section Entry-->



	<xsl:template name="socialHistorySectionEntry">

	<!-- Defing all needed variables -->

	
	<xsl:variable
	name="socialHistoryObservationType"
	select="n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.4']/../n1:code/@displayName"/>

<xsl:variable
	name="socialHistoryDateFrom"
	select="n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.4']/../n1:effectiveTime/n1:low"/>

<xsl:variable
	name="socialHistoryDateTo"
	select="n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.4']/../n1:effectiveTime/n1:high"/>


<xsl:variable
	name="socialHistoryObservationValueXsi"
	select="n1:observation/n1:templateId[@root= '1.3.6.1.4.1.19376.1.5.3.1.4.13.4']/../n1:value"/>

<xsl:variable
	name="socialHistoryObservationValue"
	select="n1:observation/n1:templateId[@root= '1.3.6.1.4.1.19376.1.5.3.1.4.13.4']/../n1:value[@xsi:type='PQ']/@value"/>


<xsl:variable
	name="socialHistoryObservationValueUnit"
	select="n1:observation/n1:templateId[@root= '1.3.6.1.4.1.19376.1.5.3.1.4.13.4']/../n1:value[@xsi:type='PQ']/@unit"/>




<xsl:variable
	name="nullEntry"
	select="."/>


	<xsl:variable name="socHistAct"
	select="n1:observation"/>

	<!-- End definition of variables-->

			 <!-- nullflavored act -->
  			<xsl:choose>
  				<xsl:when  test="not($socHistAct/@nullFlavor)">
  			
    				<tr>	
            			
            			<td>
            				<xsl:value-of select ="$socialHistoryObservationType"/>
            			</td>
            			<td>
            					<xsl:call-template name="show-time">
            								<xsl:with-param name="datetime" select="$socialHistoryDateFrom"/>
													</xsl:call-template>&#160;
            				</td>
            			
            				<td>
            					<xsl:call-template name="show-time">
            								<xsl:with-param name="datetime" select="$socialHistoryDateTo"/>
													</xsl:call-template>&#160;
            				</td>
            			
            				<td>
            				
									<xsl:choose>
            				      		<xsl:when test="not ($socialHistoryObservationValueXsi/@nullFlavor)">
            				      		<xsl:choose>
            								<xsl:when test="$socialHistoryObservationValue and $socialHistoryObservationValueUnit">
            									<xsl:value-of select ="$socialHistoryObservationValue"/> &#160;	<xsl:value-of select ="$socialHistoryObservationValueUnit"/>
            								</xsl:when>
            								<xsl:otherwise>
            								<!-- uncoded element Problem -->
            									<xsl:if test ="$socialHistoryObservationValueXsi/n1:originalText/n1:reference/@value">	
            										<xsl:call-template name="show-uncodedElement">
														<xsl:with-param name="data" select="$socialHistoryObservationValueXsi/n1:originalText/n1:reference/@value"/>
													</xsl:call-template>
												</xsl:if>
											</xsl:otherwise>
										</xsl:choose>
            								
            							</xsl:when>
            							<xsl:otherwise>
            								<xsl:call-template name="show-noneFlavor">
												<xsl:with-param name="data" select="$socialHistoryObservationValueXsi/@nullFlavor"/>
											</xsl:call-template>
										</xsl:otherwise>
								</xsl:choose>
						
            				
            				
            				
            				</td>
            			
            				</tr>
            		
					</xsl:when>
					
					<xsl:otherwise>
					<tr><td colspan="3">
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$socHistAct/@nullFlavor"/>
								</xsl:call-template>
					</td></tr>
					</xsl:otherwise>
					
				</xsl:choose>







	
</xsl:template>



</xsl:stylesheet>