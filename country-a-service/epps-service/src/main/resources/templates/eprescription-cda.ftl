<ClinicalDocument xmlns="urn:hl7-org:v3" classCode="DOCCLIN" moodCode="EVN" xmlns:pharm="urn:hl7-org:pharm" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <typeId extension="POCD_HD000040" root="2.16.840.1.113883.1.3"/>
    <templateId root="1.3.6.1.4.1.12559.11.10.1.3.1.1.1"/>
    <templateId root="1.3.6.1.4.1.19376.1.5.3.1.1.1"/>
    <id extension="44103" root="1.2.246.556.12001.4.93.1.1"/>
    <code code="57833-6" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="ePrescription"/>
    <title>ePrescription/Patient Summary (Denmark)</title>
    <effectiveTime value="${ effectiveTime }"/>
    <confidentialityCode code="N" codeSystem="2.16.840.1.113883.5.25" codeSystemName="Confidentiality" displayName="normal"/>
    <languageCode code="da-DK"/>
    <setId root="1.2.246.537.25.1.33134.93.2023.330.143319"/>
    <recordTarget contextControlCode="OP" typeCode="RCT">
        <patientRole classCode="PAT">
            <id extension="${ response.patient.person.personIdentifier.value }" root="1.2.246.21"/>
            <addr>
                <country>DK</country>
            </addr>
            <telecom nullFlavor="NI"/>
            <patient classCode="PSN" determinerCode="INSTANCE">
<#if response.patient.person.name??>
                <name>
<#list patientGivenNames as givenName>
                    <given>${ givenName }</given>
</#list>
<#if response.patient.person.name.surname??>
                    <family>${ response.patient.person.name.surname }</family>
</#if>
                </name>
</#if>
                <administrativeGenderCode code="${ administrativeGender.code }" codeSystem="2.16.840.1.113883.5.1" codeSystemName="AdministrativeGender" codeSystemVersion="913-20091020" displayName="${ administrativeGender.displayName }"/>
                <birthTime value="${ patientBirthDate }"/>
                <languageCommunication nullFlavor="NI"/>
            </patient>
        </patientRole>
    </recordTarget>
    <author>
        <functionCode code="221" codeSystem="2.16.840.1.113883.2.9.6.2.7" codeSystemName="ISCO" codeSystemVersion="2008" displayName="Medical doctors"/>
        <time value="20230330131115+0300"/>
        <assignedAuthor classCode="ASSIGNED">
            <id extension="000018" root="1.2.246.537.25"/>
            <id extension="00000000018" root="1.2.246.537.26"/>
            <addr nullFlavor="NI"/>
            <telecom nullFlavor="NI"/>
            <assignedPerson classCode="PSN" determinerCode="INSTANCE">
                <name>
<#list authorisedHealthcareProfessionalNames.givenNames as givenName>
                    <given>${ givenName }</given>
</#list>
                    <family>${ authorisedHealthcareProfessionalNames.surName }</family>
                </name>
            </assignedPerson>
            <representedOrganization classCode="ORG" determinerCode="INSTANCE">
                <id root="1.2.246.99.9999999.88.2"/>
                <name>${ representedOrganization.name }</name>
                <telecom/>
                <addr>
<#list representedOrganization.addressLine as addressLine>
                    <streetAddressLine>${ addressLine}</streetAddressLine>
</#list>
                    <state nullFlavor="UNK"/>
                    <country>DK</country>
                </addr>
            </representedOrganization>
        </assignedAuthor>
    </author>
    <custodian typeCode="CST">
        <assignedCustodian classCode="ASSIGNED">
            <representedCustodianOrganization classCode="ORG" determinerCode="INSTANCE">
                <id root="1.2.246.10.2462460.19.1"/>
                <name>${ representedOrganization.name }</name>
                <telecom/>
                <addr>
<#list representedOrganization.addressLine as addressLine>
                    <streetAddressLine>${ addressLine}</streetAddressLine>
</#list>
                    <state nullFlavor="UNK"/>
                    <country>DK</country>
                </addr>
            </representedCustodianOrganization>
        </assignedCustodian>
    </custodian>
    <legalAuthenticator contextControlCode="OP" typeCode="LA">
        <time value="20230330133319+0300"/>
        <signatureCode code="S"/>
        <assignedEntity classCode="ASSIGNED">
            <id root="1.2.246.10.2462460.19.1"/>
            <addr nullFlavor="NI"/>
            <telecom nullFlavor="NI"/>
            <assignedPerson nullFlavor="NI"/>
            <representedOrganization classCode="ORG" determinerCode="INSTANCE">
                <id root="1.2.246.10.2462460.19.1"/>
                <name>${ representedOrganization.name }</name>
                <telecom/>
                <addr>
<#list representedOrganization.addressLine as addressLine>
                    <streetAddressLine>${ addressLine}</streetAddressLine>
</#list>
                    <state nullFlavor="UNK"/>
                    <country>DK</country>
                </addr>
            </representedOrganization>
        </assignedEntity>
    </legalAuthenticator>
    <relatedDocument typeCode="XFRM">
        <parentDocument>
            <id root="1.2.246.537.25.1.33134.93.2023.330.143319"/>
        </parentDocument>
    </relatedDocument>
    <component>
        <structuredBody>
            <component>
                <section>
                    <templateId root="1.3.6.1.4.1.12559.11.10.1.3.1.2.1"/>
                    <id extension="1" root="1.2.246.556.12001.4.93.1.1.44103"/>
                    <code code="57828-6" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC" displayName="Prescriptions"/>
                    <title>Prescriptions</title>
                    <text ID="eP_as_text">
                        <paragraph>
                            <content>${ prescription.drug.name }</content>
                        </paragraph>
                        <paragraph>
                            <content>${ prescription.indication.text }</content>
                        </paragraph>
                        <paragraph>
                            <content ID="FINSTRUCT">Start of the medication use.</content>
                        </paragraph>
                        <paragraph>
                            <content ID="PINSTRUCT">${ prescription.dosageText }</content>
                        </paragraph>
                        <paragraph>
                            <content ID="nameAsText">${ prescription.drug.name }</content>
                        </paragraph>
                    </text>
                    <entry typeCode="COMP">
                        <substanceAdministration classCode="SBADM" moodCode="INT">
                            <templateId root="2.16.840.1.113883.10.20.1.24"/>
                            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7"/>
                            <templateId root="1.3.6.1.4.1.12559.11.10.1.3.1.3.2"/>
                            <templateId root="1.3.6.1.4.1.19376.1.5.3.1.4.7.1"/>
                            <id extension="1.1" root="1.2.246.556.12001.4.93.1.1.44103"/>
                            <text>
                                <reference value="#eP_as_text"/>
                            </text>
                            <statusCode code="active"/>
                            <effectiveTime nullFlavor="UNK" xsi:type="IVL_TS"/>
                            <effectiveTime nullFlavor="UNK" xsi:type="PIVL_TS"/>
                            <doseQuantity nullFlavor="UNK"/>
                            <consumable typeCode="CSM">
                                <manufacturedProduct classCode="MANU">
                                    <templateId root="1.3.6.1.4.1.12559.11.10.1.3.1.3.29"/>
                                    <manufacturedMaterial classCode="MMAT" determinerCode="KIND">
                                        <templateId root="1.3.6.1.4.1.12559.11.10.1.3.1.3.30"/>
                                        <code code="121277" codeSystem="1.2.246.537.6.55" codeSystemName="VNR" codeSystemVersion="2023.007" displayName="${ prescription.drug.name }">
                                            <originalText>
                                                <reference value="#nameAsText"/>
                                            </originalText>
                                        </code>
                                        <name>${ prescription.drug.name }</name>
                                        <pharm:desc>${ prescription.dosageText }</pharm:desc>
                                        <pharm:formCode code="11109000" codeSystem="0.4.0.127.0.16.1.1.2.1" codeSystemName="EDQM" codeSystemVersion="2023-01-03" displayName="${ prescription.drug.name }">
                                            <pharm:translation xmlns:pharm="urn:hl7-org:v3" code="11109000" codeSystem="0.4.0.127.0.16.1.1.2.1" codeSystemName="EDQM" displayName="${ prescription.drug.name }"/>
                                        </pharm:formCode>
                                        <pharm:asContent classCode="CONT">
                                            <pharm:quantity unit="{dose}" value="${ drugStrength.value }">
<#if prescription.drug.form?? && prescription.drug.form.text??>
                                                <pharm:translation>
                                                    <pharm:originalText>${ prescription.drug.form.text }</pharm:originalText>
                                                </pharm:translation>
</#if>
                                            </pharm:quantity>
                                            <pharm:containerPackagedProduct classCode="CONT" determinerCode="KIND">
                                                <pharm:formCode nullFlavor="NI"/>
                                            </pharm:containerPackagedProduct>
                                        </pharm:asContent>
                                        <pharm:asSpecializedKind classCode="GRIC">
                                            <pharm:generalizedMaterialKind classCode="MMAT" determinerCode="KIND">
                                                <pharm:code code="${ prescription.drug.ATC.code.value }" codeSystem="2.16.840.1.113883.6.73" codeSystemName="Anatomical Therapeutic Chemical" codeSystemVersion="2023-01" displayName="${ prescription.drug.ATC.text }">
                                                    <pharm:translation xmlns:pharm="urn:hl7-org:v3" code="${ prescription.drug.ATC.code.value }" codeSystem="1.2.246.537.6.32" codeSystemName="ATC" codeSystemVersion="1.2.246.537.6.32.2007" displayName="${ prescription.drug.ATC.text }"/>
                                                </pharm:code>
                                            </pharm:generalizedMaterialKind>
                                        </pharm:asSpecializedKind>
                                        <pharm:ingredient classCode="ACTI">
                                            <pharm:quantity>
                                                <numerator unit="${ drugStrength.unitText }" value="${ drugStrength.value }" xsi:type="PQ"/>
                                                <denominator unit="{dose}" value="1" xsi:type="PQ"/>
                                            </pharm:quantity>
                                            <pharm:ingredientSubstance classCode="MMAT" determinerCode="KIND">
                                                <pharm:code code="${ prescription.drug.ATC.code.value }" codeSystem="2.16.840.1.113883.6.73" codeSystemName="Anatomical Therapeutic Chemical" displayName="${ prescription.drug.ATC.text }">
                                                    <pharm:translation xmlns:pharm="urn:hl7-org:v3" code="${ prescription.drug.ATC.code.value }" codeSystem="1.2.246.537.6.32" codeSystemName="ATC" codeSystemVersion="1.2.246.537.6.32.2007" displayName="${ prescription.drug.ATC.text }"/>
                                                </pharm:code>
                                                <pharm:name>${ prescription.drug.ATC.text }</pharm:name>
                                            </pharm:ingredientSubstance>
                                        </pharm:ingredient>
                                    </manufacturedMaterial>
                                </manufacturedProduct>
                            </consumable>
                            <entryRelationship typeCode="COMP">
                                <supply classCode="SPLY" moodCode="RQO">
                                    <independentInd value="false"/>
                                    <quantity unit="1" value="${ prescription.order?size }"/>
                                </supply>
                            </entryRelationship>
<#if !prescription.substitutionAllowed>
                            <entryRelationship inversionInd="true" typeCode="SUBJ">
                                <observation classCode="OBS" moodCode="EVN">
                                    <code code="SUBST" codeSystem="2.16.840.1.113883.5.6" codeSystemName="ActClass" displayName="Substitution"/>
                                    <value code="N" codeSystem="2.16.840.1.113883.5.1070" codeSystemName="SubstanceAdminSubstitution" displayName="Generic" xsi:type="CE"/>
                                </observation>
                            </entryRelationship>
</#if>
                            <entryRelationship inversionInd="true" typeCode="SUBJ">
                                <act classCode="ACT" moodCode="INT">
                                    <templateId root="2.16.840.1.113883.10.20.1.43"/>
                                    <templateId root="1.3.6.1.4.1.12559.11.10.1.3.1.3.13"/>
                                    <code code="FINSTRUCT" codeSystem="1.3.6.1.4.1.19376.1.5.3.2" codeSystemName="IHEActCode" displayName="FINSTRUCT"/>
                                    <text>
                                        <reference value="#FINSTRUCT"/>
                                    </text>
                                    <statusCode code="completed"/>
                                </act>
                            </entryRelationship>
                            <entryRelationship inversionInd="true" typeCode="SUBJ">
                                <act classCode="ACT" moodCode="INT">
                                    <templateId root="2.16.840.1.113883.10.20.1.49"/>
                                    <templateId root="1.3.6.1.4.1.12559.11.10.1.3.1.3.12"/>
                                    <code code="PINSTRUCT" codeSystem="1.3.6.1.4.1.19376.1.5.3.2" codeSystemName="IHEActCode" displayName="PINSTRUCT"/>
                                    <text>
                                        <reference value="#PINSTRUCT"/>
                                    </text>
                                    <statusCode code="completed"/>
                                </act>
                            </entryRelationship>
                        </substanceAdministration>
                    </entry>
                </section>
            </component>
        </structuredBody>
    </component>
</ClinicalDocument>
