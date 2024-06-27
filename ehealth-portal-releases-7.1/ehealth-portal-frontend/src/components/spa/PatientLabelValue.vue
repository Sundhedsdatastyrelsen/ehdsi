<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper" ref="wrapper">
    <div class="label">{{ label }}:</div>
    <div class="value">
      {{ value }}
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { fly } from "./../../animations";

export default {
  props: {
    index: String,
    label: String,
    value: String,
  },

  mounted() {
    fly(this.$refs.wrapper, { x: 100, delay: this.index * 0.05 });
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.wrapper {
  display: flex;
  padding: 0.25rem 1rem 0.25rem 0;
  flex-flow: row nowrap;
  align-items: center;
  .label {
    width: 140px;
    color: #888;
    text-align: right;
    padding-right: 1rem;
    font-style: italic;
    font-size: 0.8rem;
  }
  .value {
    font-family: var(--font-error);
    font-weight: 500;
    text-shadow: 1px 1px 1px rgba(0, 0, 0, 0.1);
  }
}
</style>
