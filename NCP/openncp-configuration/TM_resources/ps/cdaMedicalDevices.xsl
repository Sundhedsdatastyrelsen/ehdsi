<?xml version="1.0"  ?>






<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:epsos="urn:epsos-org:ep:medication" >
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>


<!-- variable to check that at least one alert section exist -->
<xsl:variable name="medicalDevicesExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code='46264-8']"/>



	<!--alerts -->

<xsl:template name="medicalDevices" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">

	<!-- if we have at least one alert section -->
		<xsl:choose>
	<!-- if we have at least one alert section -->
			<xsl:when test="($medicalDevicesExist)">

					<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
							<xsl:call-template name="medicalDevicesSection"/>
					</xsl:for-each>
			<br/><br/>
 		 	</xsl:when>
  	<!-- else -->
			<xsl:otherwise>
  					<span class="sectionTitle">
  						<!-- xsl:text>The Medical devices and implants section is missing</xsl:text-->
  						
  							<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'74'"/>
							</xsl:call-template>
  					</span><br/><br/>
  
  		</xsl:otherwise>
		</xsl:choose>
  </xsl:template>


	<xsl:template name="medicalDevicesSection">

	<!-- Defing all needed variables -->

	<xsl:variable
	name="medicalDevicesSectionTitleCode"
	select="n1:code/@code"/>


		<xsl:variable
	name="medicalDevicesSectionTitle"
	select="n1:code[@code='46264-8']/@displayName"/>
	

<xsl:variable
	name="nullEntry"
	select="n1:entry"/>


	<xsl:variable name="medDevAct"
	select="n1:entry/n1:supply"/>

	<!-- End definition of variables-->
<xsl:choose>
	<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
 	 	<xsl:when test=" ($medicalDevicesSectionTitleCode='46264-8')">
				<span class="sectionTitle">
					<xsl:value-of select="$medicalDevicesSectionTitle"/>
						
				</span><br/>
			<xsl:choose>
  				<xsl:when  test="not($medDevAct/@nullFlavor)">
  			
    					
    					
    					<table>
            		<tbody>
            			<tr>
            				<th>
            					<!-- xsl:text>Device Implant </xsl:text-->
            					<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'21'"/>
							</xsl:call-template>
            				</th>
            				<th>
            					<!--xsl:text>Implant Date</xsl:text-->
            					<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'36'"/>
							</xsl:call-template>
            				</th>
            			
            			</tr>	
            		<xsl:for-each select="n1:entry">
						<xsl:call-template name="medicalDevicesSectionEntry">
						</xsl:call-template>
					</xsl:for-each>
            		</tbody></table>
            </xsl:when>
           		<xsl:otherwise>
				
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$medDevAct/@nullFlavor"/>
								</xsl:call-template>
				
					</xsl:otherwise>
					
				</xsl:choose>
	
		</xsl:when>
	</xsl:choose>






	

	
</xsl:template>
	

<!--  FOR EACH ENTRY -->



	<xsl:template name="medicalDevicesSectionEntry">

	<!-- Defing all needed variables -->

	
	
	<xsl:variable
	name="medDeviceImplantDescriptionNode"
	select="n1:supply/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.5']/../n1:participant[@typeCode='DEV']/n1:participantRole/n1:playingDevice/n1:code"/>

		

	<xsl:variable
	name="medDeviceImplantDescription"
	select="n1:supply/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.5']/../n1:participant[@typeCode='DEV']/n1:participantRole/n1:playingDevice/n1:code/@displayName"/>
<xsl:variable
	name="medDeviceImplantDate"
	select="n1:supply/n1:templateId[@root= '1.3.6.1.4.1.12559.11.10.1.3.1.3.5']/../n1:effectiveTime"/>


<xsl:variable
	name="nullEntry"
	select="."/>


	<xsl:variable name="medDevAct"
	select="n1:supply"/>

	<!-- End definition of variables-->

			 <!-- nullflavored act -->
  			<xsl:choose>
  				<xsl:when  test="not($medDevAct/@nullFlavor)">
  			
    					
    				
            			<tr>	
            				<td>
            				
            				
									<xsl:choose>
										<xsl:when test="not ($medDeviceImplantDescriptionNode/@nullFlavor)">
            								<xsl:choose>
            									<xsl:when test="$medDeviceImplantDescription">
	            									<xsl:value-of select ="$medDeviceImplantDescription"/>
            									</xsl:when>
            								<xsl:otherwise>
            									<!-- uncoded element Problem -->
            									<xsl:if test ="$medDeviceImplantDescriptionNode/n1:originalText/n1:reference/@value">	
            										<xsl:call-template name="show-uncodedElement">
														<xsl:with-param name="data" select="$medDeviceImplantDescriptionNode/n1:originalText/n1:reference/@value"/>
													</xsl:call-template>
												</xsl:if>
											</xsl:otherwise>
											</xsl:choose>
								
								
            							</xsl:when>
            				
            							<xsl:otherwise>
											
										
		            						<xsl:call-template name="show-noneFlavor">
												<xsl:with-param name="data" select="$medDeviceImplantDescriptionNode/@nullFlavor"/>
											</xsl:call-template>
											</xsl:otherwise>
								
							</xsl:choose>
							
            				
            				
            				</td>
            				<td>
            					<xsl:call-template name="show-time">
            								<xsl:with-param name="datetime" select="$medDeviceImplantDate"/>
													</xsl:call-template>&#160;
            				</td>
            				</tr>
            			
    	
					</xsl:when>
					
					<xsl:otherwise>
					<tr><td colspan="3">
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$medDevAct/@nullFlavor"/>
								</xsl:call-template>
					</td></tr>
					</xsl:otherwise>
					
				</xsl:choose>
	
	
</xsl:template>



</xsl:stylesheet>