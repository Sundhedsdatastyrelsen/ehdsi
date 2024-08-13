<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper" ref="wrapper">
    <div class="content">
      <img :src="countryFlag" alt="" />
      {{ country.key }}
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { fly } from "../../animations";

export default {
  props: {
    country: Object,
    index: Number,
  },

  computed: {
    countryFlag() {
      return `flags/${this.country.value}.webp`;
    },
  },

  mounted() {
    fly(this.$refs.wrapper, { x: 100, delay: this.index * 0.05 });
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.wrapper {
  &:hover {
    z-index: 1000;
  }
}
.content {
  border: 1px solid #ccc;
  display: flex;
  align-items: center;
  cursor: pointer;
  margin: 0.15rem;
  background-color: #f5f5f5;
  padding: 0.5rem;
  border-radius: 2rem;
  box-shadow: 0 3px 3px -3px rgba(0, 0, 0, 0.15);
  transition: background-color 0.3s, color 0.3s, box-shadow 0.3s,
    transform 0.15s;
  transition-timing-function: cubic-bezier(0.64, 0.57, 0.67, 1.53);
  &:hover {
    background-color: var(--blue);
    color: #fff;
    box-shadow: 0 10px 10px -10px rgba(0, 0, 0, 0.5);
    transform: scale(1.1);
    z-index: 1000;
  }
  &:active {
    transform: scale(0.8);
  }
  img {
    width: 40px;
    height: 30px;
    border: 1px solid #fff;
    margin-right: 0.5rem;
    border-radius: 2rem;
  }
}
</style>
