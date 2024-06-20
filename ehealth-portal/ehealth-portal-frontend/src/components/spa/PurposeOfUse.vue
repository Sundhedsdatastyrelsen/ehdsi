<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper" ref="wrapper">
    <h3>Purpose of use</h3>
    <div class="choices">
      <div
        @click="activate('EMERGENCY')"
        class="choice"
        :class="{ active: active == 'EMERGENCY' }"
      >
        <i class="fas fa-check-circle" v-if="active === 'EMERGENCY'"></i>
        <i class="far fa-circle" v-if="active !== 'EMERGENCY'"></i>
        Emergency Department (EMERGENCY)
      </div>
      <div
        @click="activate('TREATMENT')"
        class="choice"
        :class="{ active: active == 'TREATMENT' }"
      >
        <i class="fas fa-check-circle" v-if="active === 'TREATMENT'"></i>
        <i class="far fa-circle" v-if="active !== 'TREATMENT'"></i>
        Healthcare Facility (TREATMENT)
      </div>
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { fly } from "../../animations";

export default {
  props: {
    index: Number,
    tab: String
  },

  mounted() {
    fly(this.$refs.wrapper, { x: 300, delay: this.index * 0.1 });
  },

  computed: {
    active() {
      return this.$store.getters.purpose;
    }
  },

  methods: {
    activate(id) {
      this.$store.commit("purpose", id);
      this.$emit("choosePurpose");
    }
  }
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.wrapper {
  display: inline-flex;
  flex-flow: column nowrap;
  .choices {
  }
  .choice {
    padding: 0.5rem 1rem;
    border-radius: 0.5rem;
    cursor: pointer;
    transition: background-color 0.3s, transform 0.1s, color 0.2s;
    transition-timing-function: cubic-bezier(0.64, 0.57, 0.67, 1.53);
    &:hover {
      background-color: #eee;
      transform: scale(1.05);
    }
    &:active {
      background-color: var(--blue);
      transform: scale(0.95);
      color: #fff;
    }
    &.active {
      color: #fff;
      background-color: var(--blue);
    }
  }
}
</style>
