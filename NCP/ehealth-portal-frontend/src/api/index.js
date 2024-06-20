import { store } from "../store";
import axios from "axios";
import parser from "fast-xml-parser";
console.log("MODE=", import.meta.env.MODE);
const { SNOWPACK_PUBLIC_API_URL: api } = __SNOWPACK_ENV__;
console.log("SNOWPACK_PUBLIC_API_URL", api);

function basicErrorMessage(status) {
  return {
    errorCode: status,
    errors: [`An error has occured: ${status}`],
  };
}

function errorMessage(errorCode, message, description) {
  const errors = [`OpenNCP Error Code: ${errorCode}`];
  if (description != null) {
    errors.push(`Description:  ${description}`);
  }
  if (message != null) {
    errors.push(`Location: ${message}`);
  }

  return {
    errorCode: errorCode,
    errors,
  };
}

async function returnJsonData(res) {
  let data;
  if (!res.ok) {
    if (res.status !== 400) {
      data = await res.json();
      let errorCode = data.errorCode;
      let message =  data.message;
      let description = data.errors[0];
      data = errorMessage(errorCode, message , description);
    }

  } else {
    data = res.json();
  }
  return new Promise((resolve) => {
    store.commit("loading", false);
    resolve(data);
  });
}
async function returnTextData(res) {
  let data;
  if (!res.ok) {
    let errorCode = status;
    if (res.status !== 400) {
      data = await res.text();
      errorCode = data;
    }
    data = basicErrorMessage(errorCode);
  } else {
    data = res.json();
  }
  return new Promise((resolve) => {
    store.commit("loading", false);
    resolve(data);
  });
}

export const login = async () => {
  store.commit("loading", true);
  return new Promise((resolve) => {
    setTimeout((_) => {
      store.commit("loading", false);
      resolve(true);
    }, 1000);
  });
};

export const loadCountries = async () => {
  store.commit("loading", true);
  const res = await fetch(`${api}/admin/ism/fetch_all`, {
    method: "GET",
    mode: "cors",
    // credentials: "include",
  });




  const res2 = await fetch(`data/countries.json`, {
    method: "GET",
  });
  const data = await res.json();
  const countries = await res2.json();

  return new Promise((resolve) => {
    store.commit("loading", false);
    resolve([data, countries]);
  });
};

export const loadPatient = async (d) => {
  let country = store.getters.country;
  store.commit("loading", true);
  const res = await fetch(`${api}/patient`, {
    method: "POST",
    redirect: "follow",
    body: JSON.stringify(d),
    headers: {
      "Content-Type": "application/json",
    },
  });
  return await returnJsonData(res);
};

export const loadPatientSummary = async (form) => {
  store.commit("loading", true);
  const res = await fetch(`${api}/ps`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      nextOfKin: store.getters.isNok,
      countryCode: store.getters.country.value,
      patientIdentifier: store.getters.patient.identifier,
      purposeOfUse: store.getters.purpose,
    }),
  });
  return await returnJsonData(res);
};
export const loadDispenses = async (form) => {
  store.commit("loading", true);
  let formData = new FormData();
  formData.append("classCodes", JSON.stringify(["x-clinical-image"]));
  formData.append(
    "patientTrait",
    JSON.stringify({
      nextOfKin: store.getters.isNok,
      countryCode: store.getters.country.value,
      patientIdentifier: store.getters.patient.identifier,
      purposeOfUse: store.getters.purpose,
    })
  );
  const res = await fetch(`${api}/orcd`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: formData,
  });
  return await returnJsonData(res);
};
export const loadOrcd = async (form) => {
  store.commit("loading", true);
  const res = await fetch(`${api}/orcd`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      classCodes: store.getters.classCodes,
      filterParameters: Object.fromEntries(
        Object.entries(store.getters.filters).filter(([_, v]) => v != null)
      ),
      countryCode: store.getters.country.value,
      patientTrait: {
        nextOfKin: store.getters.isNok,
        countryCode: store.getters.country.value,
        patientIdentifier: store.getters.patient.identifier,
        purposeOfUse: store.getters.purpose,
      },
    }),
  });
  return await returnJsonData(res);
};
export const loadMedicationDispensedList = async () => {
  store.commit("loading", true);
  const res = await fetch(`${api}/ed/list`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });
  return await returnJsonData(res);
};

export const discardDispense = async (item) => {
  store.commit("loading", true);
  // alert("discardDispense");
  const res = await fetch(`${api}/ed/discard`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      countryCode: store.getters.country.value,
      nextOfKin: store.getters.isNok,
      patientDemographics: store.getters.patient.patientDemographics,
      dispenseId: item.dispensedId,
      dispenseName: item.document,
      patientIdentifier: store.getters.patient.identifier,
      purposeOfUse: store.getters.purpose,
    }),
  });

  if(!res.ok){
    return await returnJsonData(res);
  }
  return await returnTextData(res);
};

export const loadEPrescriptions = async (form) => {
  store.commit("loading", true);
  const res = await fetch(`${api}/ep`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      nextOfKin: store.getters.isNok,
      countryCode: store.getters.country.value,
      patientIdentifier: store.getters.patient.identifier,
      purposeOfUse: store.getters.purpose,
      prescriptionId: form.EP?.prescriptionId,
      dispensationPinCode: form.EP?.dispensationPINCode,
    }),
  });
  return await returnJsonData(res);
};

export const getPdfDocument = async (doc) => {
  store.commit("loading", true);
  const psOrEp = store.getters.psOrEp.toLowerCase();
  const res = await fetch(`${api}/${psOrEp}/retrieve`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      nextOfKin: store.getters.isNok,
      countryCode: store.getters.country.value,
      patientIdentifier: store.getters.patient.identifier,
      purposeOfUse: store.getters.purpose,
      repositoryId: doc.repositoryId,
      documentIdentifier: doc.identifier,
      homeCommunityId: doc.homeCommunityId,
    }),
  });
  if(!res.ok){
    return await returnJsonData(res);
  }
  const data = await res.json();
  return new Promise((resolve) => {
    store.commit("loading", false);
    let pdf = parser.parse(atob(data.content), {
      parseAttributeValue: true,
      parseNodeValue: false,
      ignoreNameSpace: true,
      ignoreAttributes: false,
      arrayMode: false,
    }).ClinicalDocument.component.nonXMLBody.text["#text"];
    let pdfWindow = window.open("");
    while (!pdfWindow) {}
    pdfWindow.document.write(
      "<iframe width='100%' height='100%' src='data:application/pdf;base64, " +
        encodeURI(pdf) +
        "'></iframe>"
    );
    resolve(data);
  });
};

const sendDispense = async function (obj) {
  const res = await fetch(`${api}/ep/dispense`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json; UTF-8",
    },
    // credentials: "include",
    body: JSON.stringify(obj),
  });

  if(!res.ok){
    return await returnJsonData(res);
  }

  const data = await res.json();
  if (
    data.status === "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success"
  ) {
    store.dispatch(
      "info",
      `Prescription: "${data.prescriptionId}"" has been dispensed with reference: "${data.dispenseId}"`
    );
  } else {
    store.dispatch("error", ["An error occured on the server"]);
  }
};

export const loadDocumentTypes = async function (obj) {
  store.commit("loading", true);
  const res = await fetch(`${api}/document/type/list`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json; UTF-8",
    },
  });
  const data = await res.json();
  return new Promise((resolve) => {
    store.commit("loading", false);
    resolve(data.filter((item) => item.scope === "ORCD"));
  });
};

export const loadOrcdDoc = async function (item) {
  store.commit("loading", true);
  const res = await fetch(`${api}/orcd/retrieve`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json; UTF-8",
    },
    body: JSON.stringify({
      classCode: item.classCode,
      countryCode: store.getters.country.value,
      documentIdentifier: item.identifier,
      homeCommunityId: item.homeCommunityId,
      nextOfKin: store.getters.isNok,
      patientIdentifier: store.getters.patient.identifier,
      purposeOfUse: store.getters.purpose,
      repositoryId: item.repositoryId,
    }),
  });
  return await returnJsonData(res);
};

export const getHtmlDocument = async (doc) => {
  store.commit("loading", true);
  let docData = {
    nextOfKin: store.getters.isNok,
    countryCode: store.getters.country.value,
    patientIdentifier: store.getters.patient.identifier,
    purposeOfUse: store.getters.purpose,
    repositoryId: doc.repositoryId,
    documentIdentifier: doc.identifier,
    homeCommunityId: doc.homeCommunityId,
  };
  const psOrEp = store.getters.psOrEp.toLowerCase();
  const res2 = await fetch(`${api}/${psOrEp}/retrieve`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json; UTF-8",
    },
    body: JSON.stringify(docData),
  });

  if(!res2.ok){
    return await returnJsonData(res2);
  }

  const cdc = await res2.json();
  docData.clinicalDocumentContent = cdc;
  docData.patientDemographics = store.getters.patient.patientDemographics;

  const res = await fetch(`${api}/${psOrEp}/display`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json; UTF-8",
    },
    body: JSON.stringify(docData),
  });

  if(!res.ok){
    return await returnJsonData(res);
  }

  const data = await res.text();

  return new Promise((resolve) => {
    store.commit("loading", false);

    let displayWindow = window.open("");
    while (!displayWindow) {}
    displayWindow.document.write(data);
    let s = displayWindow.document.createElement("script");
    s.type = "text/javascript";
    s.src = "dispense.js";
    displayWindow.callback = function (obj) {
      let merged = { ...obj, ...docData };
      sendDispense(merged);
      displayWindow.close();
    };
    displayWindow.document.body.appendChild(s);
    resolve(data);
  });
};
