<?xml version="1.0"  ?>






<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  xmlns:epsos="urn:epsos-org:ep:medication" >
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>


<!-- variable to check that at least one alert section exist -->
<xsl:variable name="pregnancyHistoryExist" select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section/n1:code[@code='10162-6']"/>



	<!--alerts -->

<xsl:template name="pregnancyHistory" match="/n1:ClinicalDocument/n1:component/n1:structuredBody">

	<!-- if we have at least one alert section -->
		<xsl:choose>
	<!-- if we have at least one alert section -->
			<xsl:when test="($pregnancyHistoryExist)">

					<xsl:for-each select="/n1:ClinicalDocument/n1:component/n1:structuredBody/n1:component/n1:section">
							<xsl:call-template name="pregnancyHistorySection"/>
					</xsl:for-each>
			<br/><br/>
 		 	</xsl:when>
  	<!-- 
			<xsl:otherwise>
  					<span class="sectionTitle">
  						
              				<xsl:call-template  name="show-displayLabels">
								<xsl:with-param name="data" select="'74'"/>
							</xsl:call-template>    						
  						
  					</span><br/><br/>
   
  		</xsl:otherwise>
  		-->
		</xsl:choose>
  </xsl:template>


	<xsl:template name="pregnancyHistorySection">

	<!-- Defing all needed variables -->

	<xsl:variable
	name="pregnancyHistorySectionTitleCode"
	select="n1:code/@code"/>


		<xsl:variable
	name="pregnancyHistorySectionTitle"
	select="n1:code[@code='10162-6']/@displayName"/>
	
	
	
	
<xsl:variable
	name="nullEntry"
	select="n1:entry"/>


	<xsl:variable name="pregHistAct"
	select="n1:entry/n1:observation"/>

	<!-- End definition of variables-->
<xsl:choose>
	<!-- if sectionTitle is not missing for alerts  (Exception alerts section is missing)-->
 	 	<xsl:when test=" ($pregnancyHistorySectionTitleCode='10162-6')">
				<span class="sectionTitle">
					<xsl:value-of select="$pregnancyHistorySectionTitle"/>
						
				</span><br/>
			 <!-- nullflavored act -->
  			<xsl:choose>
  				<xsl:when  test="not($pregHistAct/@nullFlavor)">
  						
            			<xsl:for-each select="n1:entry">
							<xsl:call-template name="pregnancyHistorySectionEntry">
						</xsl:call-template>
						<br/>
					</xsl:for-each>
            				
            			
    	
					</xsl:when>
					
					<xsl:otherwise>
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$pregHistAct/@nullFlavor"/>
								</xsl:call-template>
					
					</xsl:otherwise>
					
				</xsl:choose>
		</xsl:when>
	</xsl:choose>






	


	
</xsl:template>
	<!-- FOR EACH ENTRY -->





	<xsl:template name="pregnancyHistorySectionEntry">

	<!-- Defing all needed variables -->

	
		

	<xsl:variable
	name="pregnancyTypeDate"
	select="n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.5']/../n1:code/@displayName"/>

<xsl:variable
	name="pregnancyExpectedDate"
	select="n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.5']/../n1:value[@xsi:type='TS']"/>

<xsl:variable
	name="pregnancyExpectedDateNode"
	select="n1:observation/n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.13.5']/../n1:value"/>


<xsl:variable
	name="nullEntry"
	select="."/>


	<xsl:variable name="pregHistAct"
	select="n1:observation"/>

	<!-- End definition of variables-->

			 <!-- nullflavored act -->
			 <xsl:choose>
  				<xsl:when  test="not($pregHistAct/@nullFlavor)">
  						<xsl:choose>
            					<xsl:when test="not ($pregnancyExpectedDateNode/@nullFlavor)">
            						<xsl:choose>
            							<xsl:when  test="$pregnancyTypeDate">
  										<span >	<!--  Delivery date estimated -->
            			        	 <!-- xsl:call-template  name="show-displayLabels">
										<xsl:with-param name="data" select="'Delivery date estimated'"/>
									</xsl:call-template--> 	<xsl:value-of select ="$pregnancyTypeDate"/> : 	<xsl:call-template name="show-time">
            									<xsl:with-param name="datetime" select="$pregnancyExpectedDate"/>
													</xsl:call-template>&#160;
            						</span>
            						</xsl:when>
            						<xsl:otherwise>					
            							<!-- uncoded element Problem -->
            							<xsl:if test ="$pregnancyExpectedDateNode/n1:originalText/n1:reference/@value">	
            								<xsl:call-template name="show-uncodedElement">
												<xsl:with-param name="data" select="$pregnancyExpectedDateNode/n1:originalText/n1:reference/@value"/>
											</xsl:call-template>
										</xsl:if>
									</xsl:otherwise>
									</xsl:choose>
            					</xsl:when>
            					<xsl:otherwise>
            						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$pregnancyExpectedDateNode/@nullFlavor"/>
									</xsl:call-template>
								</xsl:otherwise>
							</xsl:choose>
            		</xsl:when>
					
					<xsl:otherwise>
					
						<xsl:call-template name="show-noneFlavor">
										<xsl:with-param name="data" select="$pregHistAct/@nullFlavor"/>
								</xsl:call-template>
					
					</xsl:otherwise>
					
				</xsl:choose>
		






	<xsl:for-each select="n1:component/n1:section">
		<xsl:call-template name="pregnancyHistorySection">
		</xsl:call-template>
	</xsl:for-each>


	
</xsl:template>


</xsl:stylesheet>