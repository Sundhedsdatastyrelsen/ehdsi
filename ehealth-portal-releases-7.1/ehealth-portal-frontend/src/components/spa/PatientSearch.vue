<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper">
    <h1>
      Find a patient in:
      <div class="selected-country">
        <img :src="flag" alt="" />
        {{ country.key }}
      </div>
    </h1>
    <h2>Patient details:</h2>
    <form @submit="submit">
      <PatientSearchIdentifiers
        id="patient"
        :identifiers="identifiers"
        :validate="validate"
        :form="form"
      />
      <OtherFields
        :fields="patientOtherFields"
        :form="form"
        :validate="validate"
      />
      <h2 v-if="nokIdentifiers.length > 0">
        <label>
          <input type="checkbox" v-model="isNok" @change="setNok" />
          <span v-if="!isNok">Next Of Kin?</span>
          <span v-if="isNok">Next Of Kin details :</span>
        </label>
      </h2>
      <PatientSearchIdentifiers
        v-if="isNok"
        id="nok"
        :identifiers="nokIdentifiers"
        :validate="validate"
        :form="form"
      />
      <OtherFields
        v-if="isNok"
        :fields="nokOtherFields"
        :form="form"
        :validate="validate"
      />
      <Button primary :disabled="!isValid" label="Submit" />
    </form>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { loadPatient } from "./../../api";
import Input from "../ui/Input";
import Button from "../ui/Button";
import PatientSearchIdentifiers from "./PatientSearchIdentifiers";
import OtherFields from "./OtherFields";
import parser from "fast-xml-parser";
function parseIdentifier(item, type, idx = 0) {
  return {
    type: type,
    idx: idx,
    label: item["@_label"],
    id: item["@_domain"] + "",
    format: item["@_format"],
  };
}
function parseField(item, id, idx = 0) {
  return {
    idx: idx,
    id: id,
    label: item["@_label"],
    format: item["@_format"],
    mandatory: item["@_mandatory"],
    contextualDescription: item["@_contextualDescription"],
  };
}

function parseJSON(source) {
  let identifiers = Array.from(source).length > 0 ? source : [source];
  identifiers = identifiers.map((item, index) => {
    if (item["@_type"] === "NORMAL") {
      return parseIdentifier(item.id, "NORMAL", 0);
    } else {
      return item.ids.map((item, idx) => {
        let choice = "CHOICE_" + index;
        let obj = {
          display: item["@_display"],
          items: [],
        };
        if (Array.isArray(item.id)) {
          item.id.map((item) => {
            obj.items.push(parseIdentifier(item, choice, idx));
          });
        } else {
          obj.items.push(parseIdentifier(item.id, choice, idx));
        }
        return obj;
      });
    }
  });
  return identifiers;
}

function parseOtherFields(source, id) {
  let textFields = [];
  let dateFields = [];
  let genderFields = [];
  if (source.textField) {
    textFields =
      Array.from(source.textField).length > 0
        ? source.textField
        : [source.textField];
    textFields = textFields.map((item) => parseField(item, id));
  }
  if (source.birthDate) {
    dateFields =
      Array.from(source.birthDate).length > 0
        ? source.birthDate
        : [source.birthDate];
    dateFields = dateFields.map((item) => parseField(item, id));
  }
  if (source.gender) {
    genderFields =
      Array.from(source.gender).length > 0 ? source.gender : [source.gender];
    genderFields = genderFields.map((item) => parseField(item, id));
  }

  return {
    textFields: textFields,
    dateFields: dateFields,
    genderFields: genderFields,
  };
}

function validateIdentifiers(id, data, errors) {
  return data
    .map((item, i) => {
      if (Array.isArray(item)) {
        return item
          .map((el, ii) => {
            return el.items
              .map((e, iii) => {
                return errors[
                  id + "---" + e.id + "---" + i + "-" + ii + "-" + iii
                ];
              })
              .every((e) => {
                return e;
              });
          })
          .some((e) => {
            return e;
          });
      } else {
        return errors[id + "---" + item.id + "---" + i];
      }
    })
    .every((e) => {
      return e;
    });
}

function validateOtherFields(data, errors) {
  return Object.values(data)
    .flat()
    .map((e, i) => {
      return (
        errors[e.id + "---" + e.label + "---" + i] || !Boolean(e.mandatory)
      );
    })
    .filter((n) => n != null)
    .every((e) => {
      return e;
    });
}

export default {
  components: {
    Input,
    Button,
    PatientSearchIdentifiers,
    OtherFields,
  },

  data() {
    return {
      identifiers: [],
      nokIdentifiers: [],
      patientOtherFields: [],
      nokOtherfields: [],
      form: {},
      errors: {},
      isValid: false,
      isNok: false,
    };
  },

  beforeMount() {
    if (this.country.form.indexOf("An internal error") > -1) {
      this.invalidXml();
      return;
    }
    let data = parser.parse(this.country.form, {
      parseAttributeValue: true,
      ignoreNameSpace: true,
      ignoreAttributes: false,
      arrayMode: false,
    });
    if (data.searchFields.country) {
      this.$store.commit("searchConfig", data);
      if (data.searchFields.country.patientSearch) {
        this.identifiers = parseJSON(
          data.searchFields.country.patientSearch.identifier
        );
        this.patientOtherFields = parseOtherFields(
          data.searchFields.country.patientSearch,
          "patient"
        );
      }
      if (data.searchFields.country.nextOfKinSearch) {
        this.nokIdentifiers = parseJSON(
          data.searchFields.country.nextOfKinSearch.identifier
        );
        this.nokOtherFields = parseOtherFields(
          data.searchFields.country.nextOfKinSearch,
          "nok"
        );
      }
      // this.nokOtherfields = parseOtherFields(
      //   data.searchFields.country.patientSearch
      // );
    } else {
      this.invalidXml();
    }
  },

  computed: {
    country() {
      return this.$store.getters.country;
    },
    flag() {
      return `flags/${this.country?.value}.webp`;
    },
  },

  methods: {
    invalidXml() {
      this.$store.dispatch("error", {
        errors: ["The current XML is not valid"],
      });
      this.$router.push("/spa/countries");
    },
    submit(e) {
      e.preventDefault();
      let nokData = { livingSubjectIds: [] };
      let patientData = { livingSubjectIds: [] };
      let parseKey = (key) => {
        return key.split("---")[1];
      };
      let addKey = (data, key, value) => {
        let k = parseKey(key);
        if (k.startsWith("label")) {
          data[k.split("label.ism.")[1]] = value;
        } else {
          data.livingSubjectIds.push({ extension: value, root: k });
        }
      };
      for (const [key, value] of Object.entries(this.form)) {
        if (key.startsWith("nok")) {
          addKey(nokData, key, value);
        } else if (key.startsWith("patient")) {
          addKey(patientData, key, value);
        }
      }
      let all = {
        countryCode: this.$store.getters.country.value,
        patientIdentityTrait: patientData,
      };
      if (this.isNok) {
        all["nextOfKinTrait"] = nokData;
      }

      loadPatient(all).then((data) => {
        if (data.errorCode) {
          document
            .querySelectorAll("input")
            .forEach((item) => (item.value = ""));
          document.querySelector("input").focus();
          this.$store.dispatch("error", data);
        } else {
          this.$store.commit("patient", data);
          this.$router.push("/spa/patient/patient-summary");
        }
      });
    },
    validate(key, value) {
      this.errors[key] = value;
      let valid =
        validateIdentifiers("patient", this.identifiers, this.errors) &&
        validateOtherFields(this.patientOtherFields, this.errors);
      this.isValid = valid;
    },
    setNok() {
      this.$store.commit("setNok", this.isNok);
      this.isValid = false;
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.wrapper {
  padding: 1rem 2rem;
  input {
  }
}
h2 {
  label {
    cursor: pointer;
    display: flex;
    flex-flow: row nowrap;
    align-items: center;
    &:hover {
      transition: all 0.5s;
      text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.15);
      color: var(--blue);
      top: 1px;
      position: relative;
    }
    input {
      width: 3rem !important;
      height: 1.5rem;
      font-size: 3rem;
    }
  }
}
h1 {
  display: flex;
  align-items: center;
  .selected-country {
    background-color: #fff;
    padding: 0.5rem;
    border-radius: 2rem;
    margin-left: 1rem;
    padding-right: 1rem;
    border: 1px solid #eee;
  }
}
img {
  margin: 0 1rem 0 0.25rem;
  width: 75px;
  height: 50px;
  border-radius: 3rem;
  vertical-align: middle;
  border: 1px solid #ddd;
  box-shadow: 0 5px 10px -2px rgba(0, 0, 0, 0.15);
}

.forms-wrapper {
  display: flex;
  flex-flow: row nowrap;
  .form-wrapper {
    margin-right: 2rem;
  }
}
</style>

<style lang="scss">
.wrapper {
  input {
    width: 300px !important;
  }
}
</style>
