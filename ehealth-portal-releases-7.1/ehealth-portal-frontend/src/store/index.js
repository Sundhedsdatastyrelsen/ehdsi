import { createStore } from "vuex";
import { useToast } from "vue-toastification";

const toast = useToast();

export const store = createStore({
  state() {
    return {
      loading: false,
      country: {},
      filters: {},
      classCodes: [],
      patient: {},
      documents: [],
      searchConfig: {},
      breadcrumb: null,
      purpose: null,
      isNok: false,
      psOrEp: null,
    };
  },
  getters: {
    loading(state) {
      return state.loading;
    },
    country(state) {
      return state.country;
    },
    patient(state) {
      return state.patient;
    },
    documents(state) {
      return state.documents;
    },
    ePrescriptionsMetas(state) {
      return state.ePrescriptionsMetas;
    },
    purpose(state) {
      return state.purpose;
    },
    searchConfig(state) {
      return state.searchConfig;
    },
    breadcrumb(state) {
      return state.breadcrumb;
    },
    classCodes(state) {
      return state.classCodes;
    },
    filters(state) {
      return state.filters;
    },
    documentTypes(state) {
      return state.documentTypes;
    },
    isNok(state) {
      return state.isNok;
    },
    psOrEp(state) {
      return state.psOrEp;
    },
  },
  mutations: {
    loading(state, value) {
      state.loading = value;
    },
    country(state, value) {
      state.country = value;
    },
    patient(state, value) {
      state.patient = value;
    },
    documents(state, value) {
      state.documents = value;
    },
    ePrescriptionsMetas(state, value) {
      state.ePrescriptionsMetas = value;
    },
    classCodes(state, value) {
      state.classCodes = value;
    },
    filters(state, value) {
      state.filters = value;
    },
    purpose(state, value) {
      state.purpose = value;
    },
    searchConfig(state, value) {
      state.searchConfig = parseConfig(value);
    },
    breadcrumb(state, value) {
      state.breadcrumb = value;
    },
    documentTypes(state, value) {
      state.documentTypes = value;
    },
    setNok(state, value) {
      state.isNok = value;
    },
    psOrEp(state, value) {
      state.psOrEp = value;
    },
  },
  actions: {
    error(state, response) {
      toast.error(response.errors.join("\n"));
    },
    info(state, str) {
      toast.success(str);
    },
  },
});

function parseConfig(data) {
  data = data.searchFields.country;
  return {
    documentSearch: parseDocumentSearch(data),
  };
}

function parseDocumentSearch(data) {
  if (data?.documentSearch?.length) {
    data = data.documentSearch.reduce((acc, item) => {
      acc[item["@_scope"]] = {
        label: item["@_friendlyName"],
        items: Object.keys(item)
          .map((key) => {
            switch (key) {
              case "@_scope":
              case "@_friendlyName":
              case "media":
                return null;
              default:
                return parseDocumentSearchField(item[key]);
            }
          })
          .filter((item) => {
            return item !== null;
          }),
      };
      return acc;
    }, {});
  } else if (data?.documentSearch) {
    let item = data.documentSearch;
    data[item["@_scope"]] = {
      label: item["@_friendlyName"],
      items: Object.keys(item)
        .map((key) => {
          switch (key) {
            case "@_scope":
            case "@_friendlyName":
            case "media":
              return null;
            default:
              return parseDocumentSearchField(item[key], key);
          }
        })
        .filter((item) => {
          return item !== null;
        }),
    };
  }
  return data;
}

function parseDocumentSearchField(data, key) {
  return {
    id: key,
    label: data["@_label"],
    format: data["@_format"],
    mandatory: data["@_mandatory"],
  };
}
