package dk.nsp.epps.mocks.fmk.data;

import dk.dkma.medicinecard.xml_schema._2015._06._01.ATCCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ATCType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ActiveSubstanceTextType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ActiveSubstanceType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatedType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatedWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.CreatedWithoutPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DeliveryPriorityType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DeliveryType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugFormCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugFormType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthTextType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugStrengthUnitCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.DrugType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.GenderType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.IndicationCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.IndicationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.IsPrivatePrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorWithOptionalAuthorisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ModificatorWithoutPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.OrganisationType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PackageNumberType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PackageSizeUnitCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PatientType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PersonIdentifierType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PersonNameType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.PrescriptionStatusType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.ReimbursementClauseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.SimpleCPRPersonType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.SpecialityCodeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.SubstancesType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.EffectuationOnPrescriptionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.OrderType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageDispensedType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageRestrictionType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e2.PackageSizeType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.GetPrescriptionResponseType;
import dk.dkma.medicinecard.xml_schema._2015._06._01.e6.PrescriptionType;
import jakarta.xml.bind.JAXBElement;
import lombok.experimental.UtilityClass;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

@UtilityClass
public class FmkMockDataFactory {
    public static final String NAMESPACE_URI = "http://www.dkma.dk/medicinecard/xml.schema/2015/06/01";

    public static GetPrescriptionResponseType getPrescriptionResponse() {
        return apply(new GetPrescriptionResponseType(), response -> {
            response.setPatient(patient());
            response.getPrescription().add(prescription());
        });
    }

    public static PatientType patient() {
        return apply(new PatientType(), patient -> {
            patient.setPerson(apply(new SimpleCPRPersonType(), person -> {
                person.setName(apply(new PersonNameType(), name -> {
                    name.setGivenName("Sonja");
                    name.setMiddleName("Dalgård");
                    name.setSurname("Knudsen");
                }));
                person.setPersonIdentifier(apply(new PersonIdentifierType(), personId -> {
                    personId.setSource("CPR");
                    personId.setValue("1111111118");
                }));
                person.setBirthDate(xmldate(1998, 11, 14));
                person.setGender(GenderType.FEMALE);
            }));
        });
    }

    public static PrescriptionType prescription() {
        return apply(new PrescriptionType(), prescription -> {
            prescription.setIdentifier(21298478L);
            prescription.setAttachedToDrugMedicationIdentifier(1341404069114002004L);
            prescription.setCreatedFromDrugMedicationVersion(1341404069112005004L);
            prescription.setOrderedEffectuationIdentifier(21297322L);
            prescription.setAuthorisationDateTime(xmldatetime(2012, 3, 29, 9, 30, 47));
            prescription.setCreated(apply(new CreatedWithOptionalAuthorisationIdentifierType(), created -> {
                created.setBy(apply(new ModificatorWithOptionalAuthorisationIdentifierType(), createdBy -> {
                    addCreatedByFields(createdBy.getContent(), "2013-11-18");
                }));
                created.setDateTime(xmldatetime(2013, 11, 19, 9, 30, 47));
            }));
            prescription.setLatestEffectuationDateTime(xmldatetime(2013, 11, 20, 15, 59, 12));
            prescription.setTerminatedDateTime(xmldatetime(2013, 11, 20, 15, 59, 12));
            prescription.setReimbursementClause(ReimbursementClauseType.KLAUSULBETINGELSE_OPFYLDT);
            prescription.setValidFromDate(xmldate(2013, 11, 19));
            prescription.setValidToDate(xmldate(2015, 11, 19));
            prescription.setPackageRestriction(apply(new PackageRestrictionType(), packageRestriction -> {
                packageRestriction.setPackageNumber(apply(new PackageNumberType(), packageNumber -> {
                    packageNumber.setSource("Medicinpriser");
                    packageNumber.setDate("2012-02-06");
                    packageNumber.setValue("84194");
                }));
                packageRestriction.setPackageQuantity(1);
            }));
            prescription.setIndication(apply(new IndicationType(), indication -> {
                indication.setCode(apply(new IndicationCodeType(), code -> {
                    code.setSource("Medicinpriser");
                    code.setDate("2011-04-09");
                    code.setValue(122);
                }));
                indication.setText("mod halsbetændelse");
            }));
            prescription.setDrug(drug(28100902676L, "Primcillin"));
            prescription.setDosageText("1 tablet morgen og aften ved måltid");
            prescription.setSubstitutionAllowed(true);
            prescription.setStatus(PrescriptionStatusType.AFSLUTTET);
            prescription.getOrder().add(order());
            prescription.setVersion(34567L);
            prescription.setIsPrivatePrescription(new IsPrivatePrescriptionType());
        });
    }

    private static OrderType order() {
        return apply(new OrderType(), order -> {
            order.setIdentifier(1341404069112002004L);
            order.setCreated(apply(new CreatedType(), created -> {
                created.setBy(apply(new ModificatorType(), createdBy -> {
                    addCreatedByFields(createdBy.getContent(), "2014-11-18");
                }));
                created.setDateTime(xmldatetime(2014, 11, 19, 9, 30, 47));
            }));
            order.setOrderedAtPharmacy(skanderborgApotek());
            order.setStatus("Udført");
            order.getDeliveryInstructionText().add("Til plejehjemmet");
            order.getDeliveryInstructionText().add("Leveres sammen med øvrige bestillinger,");
            order.getDeliveryInstructionText().add("se separat besked");
            order.setDelivery(apply(new DeliveryType(), deliveryType -> {
                deliveryType.setPriority(DeliveryPriorityType.SEND_TIL_ANDEN_ADRESSE_PR_POST);
                deliveryType.setStreetName("Søkildevej 6");
                deliveryType.setPostCode("8660");
                deliveryType.setContactName("Anders Andersen");
            }));
            order.setEffectuation(apply(new EffectuationOnPrescriptionType(), eff -> {
                eff.setIdentifier(21298478);
                eff.setDateTime(xmldatetime(2013, 11, 20,15, 59, 12));
                eff.setCreatedWithoutPerson(apply(new CreatedWithoutPersonType(), created -> {
                    created.setBy(apply(new ModificatorWithoutPersonType(), modificator -> {
                        modificator.setOrganisation(skanderborgApotek());
                    }));
                    created.setDateTime(xmldatetime(2013, 11, 20,15, 59, 12));
                }));
                eff.setPackageDispensed(apply(new PackageDispensedType(), dispense -> {
                    dispense.setPackageQuantity(1);
                    dispense.setPackageNumber(apply(new PackageNumberType(), number -> {
                        number.setSource("Medicinpriser");
                        number.setDate("2012-02-06");
                        number.setValue("84194");
                    }));
                    dispense.setPackageSize(apply(new PackageSizeType(), size -> {
                        size.setValue(BigDecimal.valueOf(20));
                        size.setUnitCode(apply(new PackageSizeUnitCodeType(), unitCode -> {
                            unitCode.setSource("Medicinpriser");
                            unitCode.setDate("2012-02-06");
                            unitCode.setValue("ST");
                        }));
                        size.setUnitText("stk");
                    }));
                    dispense.setSubstitutedDrug(drug(28101524892L, "Pancillin"));
                }));
                eff.setExpectedDeliveryDateTime(xmldatetime(2014, 03, 03, 9, 30 ,0));
            }));
        });
    }

    private static DrugType drug(long id, String name) {
        return apply(new DrugType(), drug -> {
            drug.setATC(apply(new ATCType(), atc -> {
                atc.setCode(apply(new ATCCodeType(), code -> {
                    code.setSource("Medicinpriser");
                    code.setDate("2012-08-06");
                    code.setValue("J01CE02");
                }));
                atc.setText("Phenoxymethylpenicillin");
            }));
            drug.setIdentifier(apply(new DrugIdentifierType(), drugId -> {
                drugId.setSource("Medicinpriser");
                drugId.setDate("2012-08-06");
                drugId.setValue(id);
            }));
            drug.setName(name);
            drug.setForm(apply(new DrugFormType(), form -> {
                form.setCode(apply(new DrugFormCodeType(), code -> {
                    code.setSource("Medicinpriser");
                    code.setDate("2012-08-06");
                    code.setValue("TABFILM");
                }));
                form.setText("filmovertrukne tabletter");
            }));
            drug.setStrength(apply(new DrugStrengthType(), strength -> {
                var content = strength.getContent();
                content.add(jaxbElement("Value", "250.0")); // Should really be some sort of number type
                content.add(jaxbElement("UnitCode", apply(new DrugStrengthUnitCodeType(), code -> {
                    code.setSource("Medicinpriser");
                    code.setDate("2012-08-06");
                    code.setValue("MG");
                })));
                content.add(jaxbElement("UnitText", "mg"));
                content.add(jaxbElement("Text", apply(new DrugStrengthTextType(), text -> {
                    text.setSource("Medicinpriser");
                    text.setDate("2012-08-06");
                    text.setValue("250 mg");
                })));
            }));
            drug.setSubstances(apply(new SubstancesType(), substance -> {
                substance.getActiveSubstance().add(apply(new ActiveSubstanceType(), active -> {
                    active.setSubstanceText(apply(new ActiveSubstanceTextType(), text -> {
                        text.setSource("Medicinpriser");
                        text.setDate("2012-08-06");
                        text.setValue("Phenoxymethylpenicillinkalium");
                    }));
                }));
            }));
        });
    }

    private static void addCreatedByFields(List<JAXBElement<?>> target, String date) {
        target.add(jaxbElement("AuthorisedHealthcareProfessional", apply(new AuthorisedHealthcareProfessionalWithOptionalAuthorisationIdentifierType(), ahp -> {
            ahp.setAuthorisationIdentifier("2Q5TK");
            ahp.setName("Tess Christoffersen");
            ahp.setSpecialityCode(apply(new SpecialityCodeType(), specialityCode -> {
                specialityCode.setSource("Medicinpriser");
                specialityCode.setDate(date);
                specialityCode.setValue("PSYK");
            }));
        })));
        target.add(jaxbElement("Organisation", apply(new OrganisationType(), organization -> {
            organization.setName("Lægerne Vestergade");
            organization.getAddressLine().add("Vestergade 2");
            organization.getAddressLine().add("8660 Skanderborg");
            organization.setTelephoneNumber("86521348");
            organization.setEmailAddress("kontakt@laegernevestergade.dk");
            organization.setType("Yder");
            organization.setIdentifier(apply(new OrganisationIdentifierType(), orgId -> {
                orgId.setSource("Yder");
                orgId.setValue("66974");
            }));
        })));
    }

    private static OrganisationType skanderborgApotek() {
        return apply(new OrganisationType(), organization -> {
            organization.setName("Skanderborg Apotek");
            organization.getAddressLine().add("Adelgade 27");
            organization.getAddressLine().add("8660 Skanderborg");
            organization.setType("Apotek");
            organization.setIdentifier(apply(new OrganisationIdentifierType(), id -> {
                id.setSource("EAN-Lokationsnummer");
                id.setValue("5790000170609");
            }));
        });
    }

    private XMLGregorianCalendar xmldate(int year, int month, int day) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendarDate(year, month, day, DatatypeConstants.FIELD_UNDEFINED);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private XMLGregorianCalendar xmldatetime(int year, int month, int day, int hour, int minute, int second) {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(year, month, day, hour, minute, second, 0, 0);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> JAXBElement<T> jaxbElement(String name, T value) {
        return jaxbElement(NAMESPACE_URI, name, value);
    }

    private static <T> JAXBElement<T> jaxbElement(String namespace, String name, T value) {
        return new JAXBElement<>(new QName(namespace, name), (Class<T>) value.getClass(), value);
    }

    /**
     * Utility function to make it possible to in-line initialize builder-less nested classes.
     * Basically a poor man's version of kotlin's apply method.
     */
    private static <T> T apply(T value, Consumer<T> initializer) {
        initializer.accept(value);
        return value;
    }
}
