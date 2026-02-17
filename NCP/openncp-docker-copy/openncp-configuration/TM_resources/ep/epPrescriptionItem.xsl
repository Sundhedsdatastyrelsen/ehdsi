<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
		xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
		xmlns:n1="urn:hl7-org:v3" 
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xmlns:epsos="urn:epsos-org:ep:medication">

<xsl:output method="html" 
			indent="yes" 
			version="4.01" 
			doctype-system="http://www.w3.org/TR/html4/strict.dtd" 
			doctype-public="-//W3C//DTD HTML 4.01//EN"/>


<xsl:template name="strength">
	<xsl:call-template name="show-strength">
		<xsl:with-param name="medStrengthUnit1" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:numerator/@unit"/>
		<xsl:with-param name="medStrengthUnit2" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:denominator/@unit"/>
		<xsl:with-param name="medStrengthValue1" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:numerator/@value"/>
		<xsl:with-param name="medStrengthValue2" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:denominator/@value"/>
		<xsl:with-param name="medStrength1" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:numerator"/>
		<xsl:with-param name="medStrength2" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:quantity/epsos:denominator"/>
	</xsl:call-template> 
</xsl:template>

<xsl:template name="package">
	<xsl:call-template name="show-package">
		<xsl:with-param name="medPackage" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:asContent/epsos:containerPackagedMedicine/epsos:capacityQuantity"/>
	</xsl:call-template>
</xsl:template>

<xsl:template name="frequency">
	<xsl:call-template name="show-frequencyIntake">
		<xsl:with-param name="medFrequencyIntakeType" select="n1:effectiveTime[2]/@xsi:type"/>						
		<xsl:with-param name="medFrequencyIntake" select="n1:effectiveTime[2]"/>						
	</xsl:call-template>
</xsl:template>

<xsl:template name="intakes">
	<xsl:call-template name="show-numberUnitIntake" >   
		<xsl:with-param name="medUnitIntakeLow" select="n1:doseQuantity/n1:low/@value"/>
		<xsl:with-param name="medUnitIntakeHigh" select="n1:doseQuantity/n1:high/@value"/>
		<xsl:with-param name="medUnitIntakeUnitLow" select="n1:doseQuantity/n1:low/@unit"/>
		<xsl:with-param name="medUnitIntakeUnitHigh" select="n1:doseQuantity/n1:high/@unit"/>
	</xsl:call-template>			
</xsl:template>


<xsl:template name="add-underscore">
	<xsl:param name="variable"/>
	<xsl:value-of select="$variable"/>
	<xsl:value-of select="'_'"/>
	<xsl:value-of select="position()-1"/>			
</xsl:template>

<xsl:template name="add-javascript-variable">
	<xsl:param name="variable"/>
	<xsl:param name="name"/>
	<xsl:value-of select="'var '"/>
	<xsl:value-of select="$variable"/>
	<xsl:value-of select="'_'"/>
	<xsl:value-of select="position()-1"/>			
	<xsl:value-of select="'=document.getElementById('"/>
	<xsl:text>&apos;</xsl:text>

	<xsl:value-of select="$name"/>
	<xsl:value-of select="'_'"/>
	<xsl:value-of select="position()-1"/>			
	<xsl:text>&apos;</xsl:text>

	<xsl:value-of select="');'"/>
</xsl:template>


<xsl:template name="number-of-packages">
   <xsl:param name="supply"/>
   <xsl:choose>
   	  <xsl:when test="$supply/n1:supply/@moodCode='RQO'">

		<xsl:choose>
			<xsl:when test="$supply/n1:supply/n1:independentInd/@value='false'">
				<xsl:value-of select="$supply/n1:supply/n1:quantity/@value"/>
			</xsl:when>
		</xsl:choose>

   	  </xsl:when>
   </xsl:choose>
</xsl:template>

<xsl:template name="substitution-code">
	<xsl:param name="code"/>
		<xsl:choose>
			<xsl:when test="$code/n1:code/@code='SUBST'">
				<xsl:choose>
					<xsl:when test="$code/n1:code/@codeSystem='2.16.840.1.113883.5.6'">
						<xsl:choose>
							<xsl:when test="$code/n1:value/@code='N'">
								<xsl:value-of select="'No'"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="'Yes'"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="'Yes'"/>
					</xsl:otherwise>
				</xsl:choose>										
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="'Yes'"/>
			</xsl:otherwise>
		</xsl:choose>
</xsl:template>

<xsl:variable name="scValue">
		<xsl:call-template name="substitution-code">
			<xsl:with-param name="code" select="n1:entryRelationship[@typeCode='SUBJ'][@inversionInd='true']/n1:observation[@classCode='OBS']"/>
		</xsl:call-template>
</xsl:variable>

<xsl:variable name="strengthValue">
		<xsl:call-template name="strength"/>
</xsl:variable>


<xsl:variable name="numberOfPacks">
	<xsl:call-template name="number-of-packages">
		<xsl:with-param name="supply" select="n1:entryRelationship[@typeCode='COMP']"/>
	</xsl:call-template>
</xsl:variable>


<xsl:template name="inputform">
	<xsl:param name="txt"/>
	<xsl:param name="val"/>
	
					<xsl:attribute name="id">
						<xsl:value-of select="$txt"/>
						<xsl:value-of select="position()-1" />
					</xsl:attribute>
					<xsl:attribute name="name">
						<xsl:value-of select="$txt"/>
						<xsl:value-of select="position()-1" />
					</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="$val"/>
					</xsl:attribute>
	
</xsl:template>

<xsl:template name="add-newUnitMeasure">
	<xsl:param name="unit"/>
		<xsl:text disable-output-escaping="yes">&lt;option value=&quot;</xsl:text>
		<xsl:value-of select="$unit"/>
		<xsl:text disable-output-escaping="yes">&quot; selected=&quot;selected&quot;&gt;</xsl:text>
		<xsl:value-of select="$unit"/>
		<xsl:text disable-output-escaping="yes">&lt;/option&gt;</xsl:text>
</xsl:template>

<xsl:template name="check-Parameter">
	<xsl:param name="parameter"/>
	<xsl:value-of select="''"/>	
	<xsl:choose>
		<xsl:when test="$parameter">
			<xsl:value-of select="'false'"/>
		</xsl:when>
	</xsl:choose>
</xsl:template>


<xsl:template name="check-EffectiveTime">
	<xsl:value-of select="''"/>	
	<xsl:choose>
		<xsl:when test="n1:effectiveTime[2]/n1:period/@nullFlavor">
			<xsl:value-of select="'false'"/>
		</xsl:when>
	</xsl:choose>
</xsl:template>


<xsl:template name="check-FrequencyOfIntakes">
	<xsl:value-of select="''"/>	
	<xsl:choose>
		<xsl:when test="n1:effectiveTime[2]/@nullFlavor">
			<xsl:value-of select="'false'"/>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<xsl:template name="epPrescriptionItemDetails">
	
 
	<table>
			<xsl:attribute name="id">
			<xsl:text>mytable</xsl:text>
			<xsl:value-of select="position()" />
			</xsl:attribute>
		<form name="DISPENCE_FORM" method="post">
		<xsl:attribute name="action">
			<xsl:value-of select="$actionpath"/>
		</xsl:attribute> 
		<tr>
    		<th>
<!-- 				 Active Ingredient -->
				<xsl:call-template  name="show-displayLabels">
					<xsl:with-param name="data" select="'1'"/>
				</xsl:call-template>
			</th>
			<th>
<!-- 				Strength -->
				<xsl:call-template  name="show-displayLabels">
					<xsl:with-param name="data" select="'70'"/>
				</xsl:call-template>
			</th>
			<th>
<!-- 				Dispensed Package Size -->
				<xsl:call-template  name="show-displayLabels">
					<xsl:with-param name="data" select="'50'"/>
				</xsl:call-template>   							
			</th> 
			<th>
<!-- 				    Dose Form -->
				<xsl:call-template  name="show-displayLabels">
					<xsl:with-param name="data" select="'25'"/>
				</xsl:call-template>   					
			</th>
			<th>
<!-- 				Other Active Ingredients -->
				<xsl:call-template  name="show-displayLabels">
					<xsl:with-param name="data" select="'48'"/>
				</xsl:call-template>  
			</th>
		</tr>
		<tr>
			<td>
					
				<xsl:call-template name="show-formCode">
					<xsl:with-param name="parameter" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:ingredient/epsos:code"/>
				</xsl:call-template>
				
			</td>
			<td>
				<xsl:call-template name="strength"/>
	
	
			</td>
			<td>
		
				<xsl:variable name="hasPackage">
					<xsl:call-template name="check-Parameter">
						<xsl:with-param name="parameter" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:asContent/epsos:containerPackagedMedicine/epsos:capacityQuantity/@nullFlavor"/>
					</xsl:call-template>
				</xsl:variable>
					
				<xsl:choose>
					<xsl:when test="$hasPackage = 'false'">
					    <xsl:call-template name="show-noneFlavor">
									<xsl:with-param name="data" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:asContent/epsos:containerPackagedMedicine/epsos:capacityQuantity/@nullFlavor"/>
						</xsl:call-template>
					</xsl:when>
					<xsl:otherwise>
							<xsl:call-template name="package"/>
					</xsl:otherwise>
				</xsl:choose>	
							
			</td>
			<td>
				<xsl:call-template name="show-formCode">
					<xsl:with-param name="parameter" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:formCode"/>
				</xsl:call-template>
			</td>
			<td rowspan="2">
				&#160;
			</td>
		</tr>
		
		<tr>
			<th colspan="1">
<!-- 				Country A Medicinal Product Code -->
				<xsl:call-template  name="show-displayLabels">
						<xsl:with-param name="data" select="'14'"/>
					</xsl:call-template>
			</th>
			<td colspan="2">
				<xsl:value-of select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:code/@code"/>						
			</td>
		</tr>
		<tr>
			<th>
<!-- 				Onset Date -->
				<xsl:call-template name="show-displayLabels">
					<xsl:with-param name="data" select="'45'" />
				</xsl:call-template>   				
			</th>
			<th>
<!-- 				End Date -->
				<xsl:call-template  name="show-displayLabels">
					<xsl:with-param name="data" select="'26'"/>
				</xsl:call-template>
			</th>
			<th>
<!-- 				Frequency of Intakes -->
				<xsl:call-template  name="show-displayLabels">
					<xsl:with-param name="data" select="'32'"/>
				</xsl:call-template> 
			</th>
			<th>
<!-- 				Units per intake -->
				<xsl:call-template  name="show-displayLabels">
					<xsl:with-param name="data" select="'78'"/>
				</xsl:call-template> 
			</th>
			<th>
<!-- 				Route of Administration -->
				<xsl:call-template  name="show-displayLabels">
					<xsl:with-param name="data" select="'67'"/>
				</xsl:call-template> 
			</th>
		</tr>	
		<tr>
			<td>
				<xsl:call-template name="show-time">
				<xsl:with-param name="datetime" select="n1:effectiveTime[1][@xsi:type='IVL_TS']/n1:low"/>
				</xsl:call-template>
			</td>
			<td>
				<xsl:call-template name="show-time">
					<xsl:with-param name="datetime" select="n1:effectiveTime[1][@xsi:type='IVL_TS']/n1:high"/>
				</xsl:call-template>
			</td>
			<td>
				<xsl:variable name="hasFrequency">
					<xsl:call-template name="check-Parameter">
						<xsl:with-param name="parameter" select="n1:effectiveTime[2]/n1:period/@nullFlavor"/>
					</xsl:call-template>
				</xsl:variable>
				
				<xsl:variable name="intakesFrequency">
						<xsl:call-template name="check-Parameter">
							<xsl:with-param name="parameter" select="n1:effectiveTime[2]/@nullFlavor"/>
						</xsl:call-template>
					</xsl:variable>
				
				<xsl:choose>
					<xsl:when test ="$intakesFrequency = 'false'">
						<xsl:call-template name="show-noneFlavor">
							<xsl:with-param name="data" select="n1:effectiveTime[2]/@nullFlavor"/>
						</xsl:call-template>

					</xsl:when>
					<xsl:otherwise>
						<xsl:choose>
							<xsl:when test="$hasFrequency = 'false'">
								<xsl:call-template name="show-noneFlavor">
									<xsl:with-param name="data" select="n1:effectiveTime[2]/n1:period/@nullFlavor"/>
								</xsl:call-template>
							</xsl:when>
							<xsl:otherwise>
								<xsl:call-template name="frequency"/>
							</xsl:otherwise>
						</xsl:choose>	
					
					</xsl:otherwise>
				</xsl:choose>
				
				
			</td>
			<td>
				<xsl:variable name="hasIntakes">
					<xsl:call-template name="check-Parameter">
						<xsl:with-param name="parameter" select="n1:doseQuantity/@nullFlavor"/>
					</xsl:call-template>
				</xsl:variable>
			
			    <xsl:choose>
			    	<xsl:when test="$hasIntakes = 'false'">
			    	  <xsl:call-template name="show-noneFlavor">
								<xsl:with-param name="data" select="n1:doseQuantity/@nullFlavor"/>
					  </xsl:call-template>
			    	</xsl:when>
			    	<xsl:otherwise>
							<xsl:call-template name="intakes"/>
			    	</xsl:otherwise>
			    </xsl:choose>
			</td>
			<td>
				<xsl:call-template name="show-formCode">
					<xsl:with-param name="parameter" select="n1:routeCode"/>
				</xsl:call-template>
			</td>
		</tr>
		
		<tr>
			<th colspan="2">   
			    <xsl:call-template  name="show-displayLabels">
				<xsl:with-param name="data" select="'43'"/>
				</xsl:call-template>           		
			</th>
			<th colspan="2">
				<xsl:call-template  name="show-displayLabels">
					<xsl:with-param name="data" select="'38'"/>
				</xsl:call-template>
			</th>
			<th>
				<xsl:call-template  name="show-displayLabels">
					<xsl:with-param name="data" select="'60'"/>
				</xsl:call-template>
			</th>
		</tr>
		<tr>
			<td colspan="2">
				<xsl:call-template name="number-of-packages">
					<xsl:with-param name="supply" select="n1:entryRelationship[@typeCode='COMP']"/>
				</xsl:call-template>
			</td>
			<td colspan="2">
			<xsl:variable name="customVar">
				<xsl:call-template name="substitution-code">
					<xsl:with-param name="code" select="n1:entryRelationship[@typeCode='SUBJ'][@inversionInd='true']/n1:observation[@classCode='OBS']"/>
				</xsl:call-template>
			</xsl:variable>
			<xsl:value-of select="$customVar"/>
			</td>
			<td>
				<xsl:value-of select="n1:id/@extension"/>
			</td>
		</tr>
		
		
		<tr>
			<td colspan="5">
        		<span class="td_label"><!--   Instructions to patient:-->
        		    <xsl:call-template  name="show-displayLabels">
						<xsl:with-param name="data" select="'37'"/>
					</xsl:call-template>  
        		</span> &#160;

				<xsl:call-template name="show-text">
					<xsl:with-param name="txt" select="n1:entryRelationship[@typeCode='SUBJ']/n1:act[n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.3']]/n1:text"/>
				</xsl:call-template>
			</td>
		</tr>
		<tr>
			<td colspan="5">
        		<span class="td_label"><!--  Advise to the dispencer:-->
        		    <xsl:call-template  name="show-displayLabels">
						<xsl:with-param name="data" select="'4'"/>
					</xsl:call-template>          		
        		</span> &#160;

				<xsl:call-template name="show-text">
					<xsl:with-param name="txt" select="n1:entryRelationship[@typeCode='SUBJ']/n1:act[n1:templateId[@root='1.3.6.1.4.1.19376.1.5.3.1.4.3.1']]/n1:text"/>
				</xsl:call-template>
			</td>
		</tr>
		<tr>
			<td colspan="2">
        		<span class="td_label"><!--  Dispenced Package Size:-->
        		    <xsl:call-template  name="show-displayLabels">
						<xsl:with-param name="data" select="'23'"/>
					</xsl:call-template>          		
        		</span> 
        		<input type="text">
					<xsl:attribute name="id">
						<xsl:text>dispensedPackageSize_</xsl:text>
						<xsl:value-of select="position()-1"/>
					</xsl:attribute>
					<xsl:attribute name="name">
						<xsl:text>dispensedPackageSize_</xsl:text>
						<xsl:value-of select="position()-1"/>
					</xsl:attribute>
        			<xsl:attribute name="value">
        				<xsl:value-of select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:asContent/epsos:containerPackagedMedicine/epsos:capacityQuantity/@value"/>
        			</xsl:attribute>
        		</input>
        		
					<xsl:variable name="unitMeasure">
						<xsl:value-of select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:asContent/epsos:containerPackagedMedicine/epsos:capacityQuantity/@unit"/>
					</xsl:variable>
	        		
					<xsl:variable name="measureExists">
						<xsl:call-template name="check-unitMeasures">
							<xsl:with-param name="val" select="$unitMeasure"/>
						</xsl:call-template>
	        		</xsl:variable>
        	
	            <xsl:variable name="substitutionValue">
				<xsl:call-template name="substitution-code">
					<xsl:with-param name="code" select="n1:entryRelationship[@typeCode='SUBJ'][@inversionInd='true']/n1:observation[@classCode='OBS']"/>
				</xsl:call-template>
				</xsl:variable>
				    		
				<select>
					<xsl:attribute name="id">
						<xsl:text>measures_</xsl:text>
						<xsl:value-of select="position()-1"/>
					</xsl:attribute>
					<xsl:attribute name="name">
						<xsl:text>measures_</xsl:text>
						<xsl:value-of select="position()-1"/>
					</xsl:attribute>
					<xsl:choose>
						<xsl:when test="$substitutionValue !='Yes'">
							<xsl:attribute name="disabled"/>
						</xsl:when>
					</xsl:choose>
					<xsl:call-template name="show-unitMeasures">
						<xsl:with-param name="val" select="$unitMeasure"/>
					</xsl:call-template>
					
					<xsl:choose>
							<xsl:when test="$measureExists=''">
								<xsl:choose>							
									<xsl:when test="$unitMeasure = '1'">
										<xsl:call-template name="add-newUnitMeasure">
											<xsl:with-param name="unit" select="'unit(s)'"/>
										</xsl:call-template>
									</xsl:when>
									<xsl:otherwise>
										<xsl:call-template name="add-newUnitMeasure">
											<xsl:with-param name="unit" select="' '"/>
										</xsl:call-template>
									</xsl:otherwise>	
								</xsl:choose>		
							</xsl:when>
					</xsl:choose>
				</select>
			</td>
			<td colspan="3">
        		<span class="td_label"><!--  Dispenced Product:-->
        		    <xsl:call-template  name="show-displayLabels">
						<xsl:with-param name="data" select="'24'"/>
					</xsl:call-template>    
        		</span> &#160;
        		<xsl:variable name="dispensedProductValue">
				<xsl:call-template name="substitution-code">
					<xsl:with-param name="code" select="n1:entryRelationship[@typeCode='SUBJ'][@inversionInd='true']/n1:observation[@classCode='OBS']"/>
				</xsl:call-template>
				</xsl:variable>  

				<input type="text">
					<xsl:attribute name="id">
						<xsl:text>dispensedProductValue_</xsl:text>
						<xsl:value-of select="position()-1"/>
     				</xsl:attribute>
     				<xsl:attribute name="name">
						<xsl:text>dispensedProductValue_</xsl:text>
						<xsl:value-of select="position()-1"/>
     				</xsl:attribute>
					
					<xsl:choose>
						<xsl:when test="not($dispensedProductValue='Yes')">
							<xsl:attribute name="readonly"/>
						</xsl:when>
					</xsl:choose>
					<xsl:attribute name="value">
						<xsl:value-of select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:name"/>
					</xsl:attribute>
				</input>
			</td>
		</tr>
		<tr>
			<td>
				<xsl:variable name="substitutionValueCheckBox">
				<xsl:call-template name="substitution-code">
					<xsl:with-param name="code" select="n1:entryRelationship[@typeCode='SUBJ'][@inversionInd='true']/n1:observation[@classCode='OBS']"/>
				</xsl:call-template>
				</xsl:variable>
        		<span class="td_label"><!--  Substitute:-->
        		    <xsl:call-template  name="show-displayLabels">
						<xsl:with-param name="data" select="'71'"/>
					</xsl:call-template>    
        		</span>  &#160; 
				<input type="checkbox">
					<xsl:attribute name="id">
						<xsl:text>dispense_</xsl:text>
						<xsl:value-of select="position()-1"/>
     				</xsl:attribute>
     				<xsl:attribute name="name">
						<xsl:text>dispense_</xsl:text>
						<xsl:value-of select="position()-1"/>
     				</xsl:attribute>
					<xsl:attribute name="onClick">
						<xsl:text>enableValues_</xsl:text>
						<xsl:value-of select="position()-1"/>
						<xsl:text>()</xsl:text>
						
					</xsl:attribute>
				</input>
			</td>
			<td/>
			<td>
				<!--  Dispence -->
				<input type="submit">
					<xsl:attribute name="value">
						<xsl:call-template  name="show-displayLabels">
							<xsl:with-param name="data" select="'22'"/>
						</xsl:call-template>
					</xsl:attribute>
				</input>

				<input type="hidden" name="prescriptionID" id="prescriptionID">
					<xsl:attribute name="value">
						<xsl:value-of select="$prescriptionID"/>
		        	</xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:call-template name="inputform">
						<xsl:with-param name="txt" select="'dispensationid_'"/>
						<xsl:with-param name="val" select="n1:id/@extension"/>
					</xsl:call-template>
				</input>
				<xsl:variable name="currentPackageFormName">
					<xsl:call-template name="show-formCode">
						<xsl:with-param name="parameter" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:formCode"/>
					</xsl:call-template>
				</xsl:variable>
				<input type="hidden">
					<xsl:call-template name="inputform">
						<xsl:with-param name="txt" select="'packaging1_'"/>
						<xsl:with-param name="val" select="$currentPackageFormName"/>
					</xsl:call-template>
				</input>
				<xsl:variable name="currentDoseFormName">
					<xsl:call-template name="show-formCode">
						<xsl:with-param name="parameter" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:asContent/epsos:containerPackagedMedicine/epsos:formCode"/>
					</xsl:call-template>
				</xsl:variable>				
				<input type="hidden">
					<xsl:call-template name="inputform">
						<xsl:with-param name="txt" select="'packaging2_'"/>
						<xsl:with-param name="val" select="$currentDoseFormName"/>
					</xsl:call-template>
				</input>
				<input type="hidden">
					<xsl:call-template name="inputform">
						<xsl:with-param name="txt" select="'packaging3_'"/>
						<xsl:with-param name="val" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:asContent/epsos:quantity/epsos:numerator/@value"/>
					</xsl:call-template>
				</input>
				
				<xsl:variable name="currentIngredient">
					<xsl:call-template name="show-formCode">
						<xsl:with-param name="parameter" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:ingredient/epsos:code"/>
					</xsl:call-template>
				</xsl:variable>
				<input type="hidden">
     				<xsl:call-template name="inputform">
						<xsl:with-param name="txt" select="'ingredient_'"/>
						<xsl:with-param name="val" select="$currentIngredient"/>
					</xsl:call-template>
				</input>
				<input type="hidden">
     				<xsl:attribute name="id">
						<xsl:text>name_</xsl:text>
						<xsl:value-of select="position()-1"/>
     				</xsl:attribute>
     				<xsl:attribute name="name">
						<xsl:text>name_</xsl:text>
						<xsl:value-of select="position()-1"/>
     				</xsl:attribute>
     				<xsl:attribute name="value">
     					<xsl:call-template name="strength"/>
     				</xsl:attribute>
				</input>
				<input type="hidden">
     				<xsl:attribute name="id">
						<xsl:text>nrOfPacks_</xsl:text>
						<xsl:value-of select="position()-1"/>
     				</xsl:attribute>
     				<xsl:attribute name="name">
						<xsl:text>nrOfPacks_</xsl:text>
						<xsl:value-of select="position()-1"/>
     				</xsl:attribute>
     				<xsl:attribute name="value">
     				<xsl:call-template name="number-of-packages">
						<xsl:with-param name="supply" select="n1:entryRelationship[@typeCode='COMP']"/>
					</xsl:call-template>
     				</xsl:attribute>
				</input>
				<input type="hidden">
					<xsl:attribute name="id">
						<xsl:text>country</xsl:text>
					</xsl:attribute>
					<xsl:attribute name="name">
						<xsl:text>country</xsl:text>
					</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select=" /n1:ClinicalDocument/n1:recordTarget/n1:patientRole/n1:addr/n1:country"/>
					</xsl:attribute>
				</input>
			</td>
		</tr>
		</form>
	</table>

		<script>
			<xsl:attribute name="type">
				<xsl:text>text/javascript</xsl:text>
			</xsl:attribute>
	 		<xsl:if test="position()>1"> 
			<xsl:text disable-output-escaping="yes">showhide(&apos;mytable</xsl:text>
				<xsl:value-of select="position()" />
			<xsl:text disable-output-escaping="yes">&apos;);</xsl:text>&#160;
			</xsl:if>
			
		<!-- Check for the value of the checkbox -->
				<xsl:call-template name="add-javascript-variable">
					<xsl:with-param name="variable" select="'cb'"/>
					<xsl:with-param name="name" select="'dispense'"/>
				</xsl:call-template>&#160;
				<xsl:call-template name="add-javascript-variable">
					<xsl:with-param name="variable" select="'productValue'"/>
					<xsl:with-param name="name" select="'dispensedProductValue'"/>
				</xsl:call-template>&#160;
				<xsl:call-template name="add-javascript-variable">
					<xsl:with-param name="variable" select="'packageSize'"/>
					<xsl:with-param name="name" select="'dispensedPackageSize'"/>
				</xsl:call-template>&#160;	
	            <xsl:variable name="substitutionValueCheck">
					<xsl:call-template name="substitution-code">
						<xsl:with-param name="code" select="n1:entryRelationship[@typeCode='SUBJ'][@inversionInd='true']/n1:observation[@classCode='OBS']"/>
					</xsl:call-template>
				</xsl:variable>
				<xsl:choose>
					<xsl:when test="$substitutionValueCheck ='Yes'">
						<xsl:call-template name="add-javascript-variable">
							<xsl:with-param name="variable" select="'measures'"/>
							<xsl:with-param name="name" select="'measures'"/>
							</xsl:call-template>&#160;	
					</xsl:when>
				</xsl:choose>
			<xsl:text disable-output-escaping="yes">if  (</xsl:text>
			<xsl:call-template name="add-underscore">
				<xsl:with-param name="variable" select="'cb'"/>
			</xsl:call-template>
			<xsl:text disable-output-escaping="yes">.checked == 0 ) { </xsl:text>	&#160;
			<xsl:call-template name="add-underscore">
				<xsl:with-param name="variable" select="'productValue'"/>
			</xsl:call-template>
			<xsl:text disable-output-escaping="yes">.disabled=&apos;true&apos;;</xsl:text>&#160;
			<xsl:call-template name="add-underscore">
				<xsl:with-param name="variable" select="'packageSize'"/>
			</xsl:call-template>
			<xsl:text disable-output-escaping="yes">.disabled=&apos;true&apos;;</xsl:text>&#160;
				<xsl:choose>
					<xsl:when test="$substitutionValueCheck ='Yes'">
					<xsl:call-template name="add-underscore">
							<xsl:with-param name="variable" select="'measures'"/>
					</xsl:call-template>
					<xsl:text disable-output-escaping="yes">.disabled=&apos;true&apos;;</xsl:text>&#160;
					</xsl:when>
				</xsl:choose>
			<xsl:text disable-output-escaping="yes">};</xsl:text>&#160;
			<xsl:text disable-output-escaping="yes">function </xsl:text>
			<xsl:call-template name="add-underscore">
				<xsl:with-param name="variable" select="'enableValues'"/>
			</xsl:call-template>		
			<xsl:text disable-output-escaping="yes">(){</xsl:text>&#160;
			<xsl:text disable-output-escaping="yes">if  (</xsl:text>
			<xsl:call-template name="add-underscore">
				<xsl:with-param name="variable" select="'cb'"/>
			</xsl:call-template>
			<xsl:text disable-output-escaping="yes">.checked == 0 ) { </xsl:text>	&#160;
			<xsl:call-template name="add-underscore">
				<xsl:with-param name="variable" select="'productValue'"/>
			</xsl:call-template>
			<xsl:text disable-output-escaping="yes">.disabled=&apos;true&apos;;</xsl:text>&#160;
			<xsl:call-template name="add-underscore">
				<xsl:with-param name="variable" select="'packageSize'"/>
			</xsl:call-template>
			<xsl:text disable-output-escaping="yes">.disabled=&apos;true&apos;; </xsl:text>
			<xsl:choose>
				<xsl:when test="$substitutionValueCheck ='Yes'">
				<xsl:call-template name="add-underscore">
						<xsl:with-param name="variable" select="'measures'"/>
				</xsl:call-template>
				<xsl:text disable-output-escaping="yes">.disabled=&apos;true&apos;;</xsl:text>&#160;
				</xsl:when>
			</xsl:choose>
			<xsl:text> } else {</xsl:text>&#160;
			<xsl:call-template name="add-underscore">
				<xsl:with-param name="variable" select="'productValue'"/>
			</xsl:call-template>		
			<xsl:text disable-output-escaping="yes">.disabled=!&apos;true&apos;;</xsl:text>&#160;	
			<xsl:call-template name="add-underscore">
				<xsl:with-param name="variable" select="'packageSize'"/>
			</xsl:call-template>		
			<xsl:text disable-output-escaping="yes">.disabled=!&apos;true&apos;;</xsl:text>&#160;
			<xsl:choose>
				<xsl:when test="$substitutionValueCheck ='Yes'">
				<xsl:call-template name="add-underscore">
						<xsl:with-param name="variable" select="'measures'"/>
				</xsl:call-template>
				<xsl:text disable-output-escaping="yes">.disabled=!&apos;true&apos;;</xsl:text>&#160;
				</xsl:when>
			</xsl:choose>
			<xsl:text disable-output-escaping="yes">};</xsl:text>&#160;
			<xsl:text disable-output-escaping="yes">};</xsl:text>
		</script>	
	</xsl:template>

	<xsl:template name="epPrescriptionItem">
	 <script>
		<xsl:attribute name="type">
			<xsl:text>text/javascript</xsl:text>
		</xsl:attribute>
		<xsl:text>i=0;</xsl:text> 
	 </script>
		<table style="width : 100%">
			<xsl:for-each select="$entryNode">

			<xsl:variable name="activeIngredient">
				<xsl:call-template name="show-formCode">
					<xsl:with-param name="parameter" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:ingredient[@classCode='ACTI']/epsos:ingredient/epsos:code"/>
				</xsl:call-template>
			</xsl:variable>	

			<xsl:variable name="manufacturedMaterialFormCode">
				<xsl:call-template name="show-formCode">
					<xsl:with-param name="parameter" select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/epsos:formCode"/>
				</xsl:call-template>
			</xsl:variable>

			<tr>
				<th><countNo><xsl:value-of select="position()" />.</countNo>
				 	&#160;
					<xsl:value-of select="$activeIngredient"/>
					&#160;
					<xsl:call-template name="strength"/>
					&#160;
					<xsl:call-template name="package"/>
					&#160;
					<xsl:value-of select="$manufacturedMaterialFormCode"/>
					&#160;
					<xsl:text>(</xsl:text>
						<xsl:value-of select="n1:consumable/n1:manufacturedProduct/n1:manufacturedMaterial/n1:name"/>
					<xsl:text>)</xsl:text>
					<a>
						<xsl:attribute name="href">
							<xsl:text disable-output-escaping="yes">javascript:void();</xsl:text>
						</xsl:attribute>
						<xsl:attribute name="onclick">
							<xsl:text disable-output-escaping="yes">showhide(&apos;mytable</xsl:text>
								<xsl:value-of select="position()" />
							<xsl:text disable-output-escaping="yes">&apos;)</xsl:text>
						</xsl:attribute>
						<xsl:text>+/-</xsl:text>
					</a>

				</th>
			</tr>
			<table>
				<tr>
					<td><xsl:call-template name="epPrescriptionItemDetails"/></td>
				</tr>
			</table>
			
			 <script>
				<xsl:attribute name="type">
					<xsl:text>text/javascript</xsl:text>
				</xsl:attribute>
				<xsl:text>i++;</xsl:text> 
			 </script>
			
			</xsl:for-each>
		</table>
		<script>
			<xsl:attribute name="type">
				<xsl:text>text/javascript</xsl:text>
			</xsl:attribute>
			<xsl:text disable-output-escaping="yes">if(i>1){</xsl:text>
			<xsl:text disable-output-escaping="yes">showhide(&apos;mytable1&apos;);</xsl:text>
			<xsl:text disable-output-escaping="yes">};</xsl:text>
		</script>
	</xsl:template>

</xsl:stylesheet>