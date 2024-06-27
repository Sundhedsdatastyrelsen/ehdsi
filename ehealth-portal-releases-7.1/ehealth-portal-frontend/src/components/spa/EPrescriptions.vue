<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper">
    <transition name="fade">
      <DocumentSearch tab="EP" @showContent="showContent" v-if="!content" />
    </transition>

    <transition name="fade">
      <div class="e-prescriptions-metas" v-if="content">
        <div class="label-value">
          <div class="label">ATC:</div>
          <div class="value">
            {{ ePrescriptionsMetas.atcCode }} -
            {{ ePrescriptionsMetas.atcText }}
          </div>
        </div>
        <div class="label-value">
          <div class="label">Description:</div>
          <div class="value">{{ ePrescriptionsMetas.description }}</div>
        </div>
        <div class="label-value">
          <div class="label">Dipensable:</div>
          <div class="value">{{ ePrescriptionsMetas.dispensable }}</div>
        </div>
        <div class="label-value">
          <div class="label">Dose Form :</div>
          <div class="value">
            {{ ePrescriptionsMetas.doseFormCode }} -
            {{ ePrescriptionsMetas.doseFormText }}
          </div>
        </div>
        <div class="label-value">
          <div class="label">Strength:</div>
          <div class="value">{{ ePrescriptionsMetas.strength }}</div>
        </div>
        <div class="label-value">
          <div class="label">Substitution:</div>
          <div class="value">{{ ePrescriptionsMetas.substitution }}</div>
        </div>
        <div class="label-value">
          <div class="label">Dispensable:</div>
          <div class="value">
            <span
              class="far fa-check"
              v-if="ePrescriptionsMetas.dispensable"
            ></span>
            <span
              class="far fa-times"
              v-if="!ePrescriptionsMetas.dispensable"
            ></span>
          </div>
        </div>
      </div>
    </transition>
    <transition name="fade">
      <DocumentList :documents="documents" v-if="content" />
    </transition>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import DocumentSearch from "./DocumentSearch";
import DocumentList from "./DocumentList";
export default {
  components: {
    DocumentSearch,
    DocumentList,
  },

  mounted() {
    this.$store.commit("psOrEp", "EP");
  },

  data() {
    return {
      content: false,
    };
  },

  computed: {
    documents() {
      return this.$store.getters.documents;
    },
    ePrescriptionsMetas() {
      return this.$store.getters.ePrescriptionsMetas;
    },
  },

  methods: {
    showContent() {
      this.content = true;
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.e-prescriptions-metas {
  margin-bottom: 1rem;
  .label-value {
    display: flex;
    .label {
      color: #999;
      width: 9rem;
      text-align: right;
      padding-right: 1rem;
    }
    .value {
      font-weight: 500;
    }
  }
}
</style>
