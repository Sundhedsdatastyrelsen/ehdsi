<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper">
    <transition name="fade">
      <ClassCodes v-if="!content" />
    </transition>
    <transition name="fade">
      <Filters v-if="!content" />
    </transition>

    <transition name="fade">
      <DocumentSearch tab="ORCD" @showContent="showContent" v-if="!content" />
    </transition>

    <transition name="fade">
      <OrcdList :documents="documents" v-if="content" />
    </transition>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import DocumentSearch from "./DocumentSearch";
import OrcdList from "./OrcdList";
import ClassCodes from "./ClassCodes";
import Filters from "./Filters";
export default {
  components: {
    DocumentSearch,
    OrcdList,
    ClassCodes,
    Filters,
  },

  onMount() {},

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
