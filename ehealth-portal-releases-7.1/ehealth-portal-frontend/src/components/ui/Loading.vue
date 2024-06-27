<!-- ---------------------------------------  HTML -->

<template>
  <div class="loading-wrapper" ref="loadingWrapper" :class="loading">
    <div class="far fa-spinner-third fa-spin"></div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { loading, stopLoading } from "./../../animations";

export default {
  computed: {
    loading() {
      if (this.$store.getters.loading) {
        loading(this.$refs.loadingWrapper);
      } else {
        stopLoading(this.$refs.loadingWrapper);
      }
      return this.$store.getters.loading;
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.loading-wrapper {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  opacity: 0;
  width: 0;
  background-color: rgba(0, 0, 0, 0.15);
  z-index: 10000000000;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}
.far {
  font-size: 5rem;
  margin-right: 1rem;
  color: white;
}
</style>
