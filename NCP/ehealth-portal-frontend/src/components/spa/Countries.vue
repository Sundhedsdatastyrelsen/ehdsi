<!-- ---------------------------------------  HTML -->

<template>
  <transition @leave="leave">
    <div class="countries-wrapper">
      <h2>Please choose the patient country {{ search }}</h2>
      <input type="search" v-model="search" @click="reset" ref="searchInput" />
      <div class="country-list">
        <Country
          v-for="(country, index) of filteredCountries"
          :country="country"
          :index="index"
          @click="handleClick(country)"
        />
      </div>
    </div>
  </transition>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { fly } from "./../../animations";
import { loadCountries } from "./../../api";
import router from "./../../router";
import Country from "./Country";

function parseCountries(data, countries) {
  return data.map((item) => {
    return {
      value: item.countryCode.toUpperCase(),
      key: countries[item.countryCode.toUpperCase()],
      form: item.form,
    };
  });
}

export default {
  components: {
    Country,
  },

  data() {
    return {
      countries: [],
      search: "",
    };
  },

  computed: {
    filteredCountries() {
      return this.countries.filter((item) => {
        return item.key.toLowerCase().indexOf(this.search) > -1;
      });
    },
  },

  beforeMount() {
    loadCountries().then(([data, countries]) => {
      let parsed = parseCountries(data, countries);
      this.countries = parsed;
      this.filteredCountries = parsed;
    });
  },

  mounted() {
    this.$refs.searchInput.focus();
  },

  methods: {
    handleClick(country) {
      this.$store.commit("country", country);
      router.push("/spa/patient-search");
    },
    filterCountries(e) {},
    reset() {
      this.search = "";
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.countries-wrapper {
  padding: 1rem;
  .country-list {
    display: flex;
    flex-flow: row wrap;
  }
}
</style>
