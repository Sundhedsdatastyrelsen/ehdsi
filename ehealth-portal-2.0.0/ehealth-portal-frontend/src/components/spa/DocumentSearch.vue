<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper document-search-wrapper">
    <form @submit="submit">
      <PurposeOfUse @choosePurpose="choosePurpose" :tab="tab" />
      <div class="document-search-group">
        <DocumentSearchGroup
          v-if="purposeChoosen"
          :label="item.label"
          :tab="tab"
          :items="item.items"
          @groupIsValid="groupIsValid"
          v-for="item in documentSearch"
        />
      </div>
      <Button
        v-if="purposeChoosen"
        :disabled="!isValid"
        label="Confirm"
        primary
      />
    </form>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import PurposeOfUse from "./PurposeOfUse";
import DocumentSearchGroup from "./DocumentSearchGroup";
import Button from "./../ui/Button";
import {
  loadPatientSummary,
  loadEPrescriptions,
  loadDispenses,
  loadOrcd,
  loadMedicationDispensedList,
} from "./../../api";

export default {
  components: {
    PurposeOfUse,
    DocumentSearchGroup,
    Button,
  },

  props: {
    tab: String,
  },

  data() {
    return {
      purposeChoosen: false,
      documentSearch: [],
      valids: {},
      groupValues: {},
      isValid: true,
    };
  },

  mounted() {
    this.documentSearch = [
      this.$store.getters.searchConfig.documentSearch[this.tab],
      this.$store.getters.searchConfig.documentSearch["ALL"],
    ].filter((item) => {
      return item && Object.values(item).length > 0 ? true : false;
    });
  },

  methods: {
    submit(e) {
      e.preventDefault();
      switch (this.tab) {
        case "PS":
          loadPatientSummary(this.purposeChoosen).then((data) => {
            if (data.errorCode) {
              this.$store.dispatch("error", data);
            } else {
              this.$store.commit("documents", data.clinicalDocuments);
              this.$emit("showContent");
            }
          });
          break;
        case "EP":
          loadEPrescriptions(this.groupValues).then((data) => {
            if (data.errorCode) {
              this.$store.dispatch("error", data);
            } else {
              this.$store.commit("documents", data.clinicalDocuments);
              this.$store.commit("ePrescriptionsMetas", data);
              this.$emit("showContent");
            }
          });
          break;
        case "ML":
          loadMedicationDispensedList(this.purposeChoosen).then((data) => {
            if (data.errorCode) {
              this.$store.dispatch("error", data);
            } else {
              this.$store.commit("documents", data);
              this.$emit("showContent");
            }
          });
          break;
        case "ORCD":
          loadOrcd(this.purposeChoosen).then((data) => {
            if (data.errorCode) {
              this.$store.dispatch("error", data);
            } else {
              this.$store.commit("documents", data.clinicalDocuments);
              this.$emit("showContent");
            }
          });
          break;
      }
    },
    choosePurpose() {
      this.purposeChoosen = true;
    },
    groupIsValid(group, isValid, values) {
      this.valids[group] = isValid;
      this.groupValues[group] = values;
      let vals = Object.values(this.valids);
      this.isValid =
        vals.every((t) => t) && vals.length === this.documentSearch.length;
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.document-search-wrapper {
  width: 100%;
}
.document-search-group {
  display: flex;
  flex-flow: row wrap;
  margin-top: 2rem;
  & > div {
    margin-right: 2rem;
  }
}
</style>
