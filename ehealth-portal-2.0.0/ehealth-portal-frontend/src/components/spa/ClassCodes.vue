<!-- ---------------------------------------  HTML -->

<template>
  <div class="class-codes">
    <h3>Please choose one or more class codes:</h3>
    <div
      class="class-code"
      v-for="(classCode, index) of classCodes"
      :index="index"
    >
      <label
        ><input
          type="checkbox"
          @change="changed"
          :id="classCode.classCode"
          v-model="selected"
          :value="classCode.classCode"
        />{{ classCode.value }}</label
      >
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { loadDocumentTypes } from "./../../api";
export default {
  onMount() {},

  beforeMount() {
    loadDocumentTypes().then((data) => {
      this.classCodes = data;
    });
  },
  data() {
    return {
      classCodes: [],
      selected: [],
    };
  },

  computed: {},

  methods: {
    changed: function (e) {
      this.$store.commit("classCodes", this.selected);
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.class-codes {
  margin-bottom: 1rem;
}
.class-code label {
  display: flex;
  padding: 0.25rem 0rem;
  align-items: center;
  font-size: 1rem;
  width: 100%;
  input {
    display: inline-block;
    width: 2rem !important;
    height: 1rem;
  }
}
</style>
