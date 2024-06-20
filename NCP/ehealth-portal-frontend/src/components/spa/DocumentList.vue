<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper">
    <input type="search" v-if="showSearch" @keyup="search" @click="reset" />
    <div class="documents-wrapper">
      <Document
        :document="item"
        :index="index"
        v-for="(item, index) in filteredDocuments"
      />
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import Document from "./Document";

export default {
  components: {
    Document,
  },

  props: {
    documents: [],
  },

  data() {
    return {
      searchTxt: "",
    };
  },

  computed: {
    showSearch() {
      return this.documents.length > 3;
    },
    filteredDocuments() {
      return this.documents.filter((item) => {
        return item.name.toLowerCase().indexOf(this.searchTxt) > -1;
      });
    },
  },

  methods: {
    search(event) {
      this.searchTxt = event.target.value.toLowerCase();
    },
    reset() {
      this.searchTxt = "";
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.documents-wrapper {
  display: flex;
  flex-flow: row wrap;
  width: 100%;
}
</style>
