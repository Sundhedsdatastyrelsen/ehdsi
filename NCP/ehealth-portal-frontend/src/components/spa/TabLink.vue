<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper" ref="wrapper" @click="clicked">
    <slot></slot>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { fly } from "../../animations";

export default {
  props: { index: String },

  mounted() {
    fly(this.$refs.wrapper, { x: 100, delay: this.index * 0.1 });
  },

  methods: {
    clicked() {
      this.$store.commit("purpose", null);
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.wrapper {
  transition: background-color 0.2s;

  padding: 0.25rem 0;
}
</style>
