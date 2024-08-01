<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper">
    <transition name="fade">
      <DocumentSearch tab="PS" @showContent="showContent" v-if="!content" />
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
    this.$store.commit("psOrEp", "PS");
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
.wrapper {
}
</style>
