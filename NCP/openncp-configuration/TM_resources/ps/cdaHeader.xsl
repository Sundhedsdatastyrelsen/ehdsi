<?xml version="1.0"  ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:n1="urn:hl7-org:v3" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<xsl:output method="html" indent="yes" version="4.01"   doctype-system="http://www.w3.org/TR/html4/strict.dtd" doctype-public="-//W3C//DTD HTML 4.01//EN"/>
	<!--- BASIC HEADER ELEMENT -->
	<xsl:variable
name="familyName"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:name/n1:family"/>
	<xsl:variable
name="givenName"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:name/n1:given"/>
	<xsl:variable
name="prefix"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:name/n1:prefix"/>
	<xsl:variable
name="primaryPatientId"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:id[1]"/>
	<xsl:variable
name="secondaryPatientId"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:id[2]"/>
	<xsl:variable
name="birthdate"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:birthTime"/>
	<xsl:variable
name="gender"
select="/n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:patient/n1:administrativeGenderCode/@displayName"/>
	<xsl:variable
name="creationDate"
select="/n1:ClinicalDocument/n1:effectiveTime"/>
	<xsl:variable
name="lastUpdate"
select="/n1:ClinicalDocument/n1:documentationOf/n1:serviceEvent/n1:effectiveTime/n1:high"/>
	<xsl:template name="basicCdaHeader">
		<table class="header_table">
			<tbody>
				<tr class="td_creation_date">
					<th class="td_creation_date">
						<!-- Creation Date: -->
						<xsl:call-template  name="show-displayLabels">
							<xsl:with-param name="data" select="'15'"/>
						</xsl:call-template>:
						<span class="tdtext">
							<xsl:call-template name="show-time">
								<xsl:with-param name="datetime" select="$creationDate"/>
							</xsl:call-template>
						</span>
					</th>
					<th class="td_creation_date">
						<!-- Last Update:--> 
						<xsl:call-template  name="show-displayLabels">
							<xsl:with-param name="data" select="'39'"/>
						</xsl:call-template>:
						<span class="tdtext">
							<xsl:call-template name="show-time">
								<xsl:with-param name="datetime" select="$lastUpdate"/>
							</xsl:call-template>
						</span>
					</th>
				</tr>
				<tr> 
					<th colspan="3">
						<!-- Patient-->
						<xsl:call-template  name="show-displayLabels">
							<xsl:with-param name="data" select="'51'"/>
						</xsl:call-template>
					</th>
				</tr>
				<tr> 
					<table class="header_table">
						<tbody>
							<tr>
								<th>
									<!-- Prefix-->
									<xsl:call-template  name="show-displayLabels">
										<xsl:with-param name="data" select="'55'"/>
									</xsl:call-template> 
								</th>
								<th>
									<!-- Family Name-->
									<xsl:call-template  name="show-displayLabels">
										<xsl:with-param name="data" select="'30'"/>
									</xsl:call-template>
								</th>
								<th>
									<!-- Given Name-->
									<xsl:call-template  name="show-displayLabels">
										<xsl:with-param name="data" select="'34'"/>
									</xsl:call-template>
								</th>
							</tr>
							<tr> 
								<td>
									<xsl:value-of select="$prefix" />&#160;
								</td>
								<td>
									<xsl:value-of select="$familyName"/>
								</td>
								<td>
									<xsl:value-of select="$givenName" />&#160;
								</td>

							</tr>	
						</tbody>
					</table>
				</tr>
				<tr> 
					<table class="header_table">
						<tbody>
							<tr>	
								<th style="width:100px;">
									<!-- Patient Ids--> 
									<xsl:call-template  name="show-displayLabels">
										<xsl:with-param name="data" select="'52'"/>
									</xsl:call-template>
								</th>
								<td>
									<xsl:call-template name="show-id">
										<xsl:with-param name="id" select="$primaryPatientId"/>
									</xsl:call-template>
								</td>
								<td>
									<xsl:call-template name="show-id">
										<xsl:with-param name="id" select="$secondaryPatientId"/>
									</xsl:call-template>&#160;
								</td>
							</tr>
						</tbody>
					</table>
				</tr>
				<tr> 
					<table class="header_table">
						<tbody>
							<tr>	
								<th style="width:100px;">
									<!-- Gender-->
									<xsl:call-template  name="show-displayLabels">
										<xsl:with-param name="data" select="'33'"/>
									</xsl:call-template>
								</th>
								<td>
									<xsl:value-of select="$gender" />
								</td>
								<th style="width:100px;">
									<!-- Date Of Birth-->
									<xsl:call-template  name="show-displayLabels">
										<xsl:with-param name="data" select="'19'"/>
									</xsl:call-template>
								</th>
								<td>

									<xsl:call-template name="show-time">
										<xsl:with-param name="datetime" select="$birthdate"/>
									</xsl:call-template>&#160;
								</td>
							</tr>
						</tbody>
					</table>
				</tr>
			</tbody>
		</table>
	</xsl:template>
</xsl:stylesheet>