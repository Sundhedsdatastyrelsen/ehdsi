<!-- ---------------------------------------  HTML -->

<template>
  <div class="filters-wrapper">
    <h3>Filter documents :</h3>
    <div class="filters">
      <div class="filter">
        <label
          >Created after:
          <input type="date" v-model="createdAfter" @change="changed"
        /></label>
      </div>
      <div class="filter">
        <label
          >Created before:
          <input type="date" v-model="createdBefore" @change="changed"
        /></label>
      </div>
      <div class="filter">
        <label
          >Maximum size (Mo):
          <input type="number" v-model="maximumSize" @change="changed"
        /></label>
      </div>
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
export default {
  onMount() {},

  beforeMount() {},
  data() {
    return {
      createdAfter: "",
      createdBefore: "",
      maximumSize: null,
    };
  },

  computed: {
    filters() {
      return {
        createdAfter:
          this.createdAfter != ""
            ? new Date(this.createdAfter).toISOString()
            : null,
        createdBefore:
          this.createdBefore != ""
            ? new Date(this.createdBefore).toISOString()
            : null,
        maximumSize: this.maximumSize,
      };
    },
  },

  methods: {
    changed: function (e) {
      this.filters.maximumSize = this.filters.maximumSize * 1024 * 1024;
      this.$store.commit("filters", this.filters);
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.class-codes {
}
.filters-wrapper {
  margin-bottom: 1rem;
  display: flex;
  flex-flow: column nowrap;
  padding: 0.25rem 0rem;
  font-size: 1rem;
  width: 100%;
  .filters {
    display: flex;
  }
  .filter {
    width: 30%;
  }
  input {
    display: inline-block;
    width: 10rem !important;
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 0.25rem 0.5rem;
    height: 2rem;
    margin-top: 0.5rem;
  }
}
</style>
