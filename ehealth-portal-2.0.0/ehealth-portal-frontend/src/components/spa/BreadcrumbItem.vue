<!-- ---------------------------------------  HTML -->

<template>
  <div
    v-if="show"
    class="item"
    :class="{ active: selected }"
    :style="idx"
    @click="activate(id, index)"
  >
    <span class="item-content">
      <i :class="icon"></i>
      <div>{{ label }}</div>
    </span>
    <div class="arrow-right"></div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import router from "./../../router";
export default {
  props: {
    id: String,
    index: String,
    label: String,
    icon: String,
  },
  computed: {
    active() {
      return this.$store.getters.breadcrumb;
    },
    selected() {
      return this.active?.id === this.id;
    },
    idx() {
      return `z-index: ${this.index}`;
    },
    show() {
      return this.active?.idx <= this.index;
    },
  },
  methods: {
    activate(id, index) {
      router.push(`/spa/${this.id}`);
      this.$store.commit("breadcrumb", { idx: index, id: id });
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.arrow-right {
  width: 0;
  height: 0;
  border-top: calc(2rem + 0.5px) solid transparent;
  border-bottom: calc(2rem + 0.5px) solid transparent;
  transition: all 0.2s;
  border-left: calc(2rem + 0.5px) solid white;
  position: relative;
  left: -1px;
}
.item {
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  text-transform: uppercase;
  margin-left: -2rem;
  .item-content {
    display: flex;
    transition: all 0.2s;
    align-items: center;
    height: 100%;
    display: inline-flex;
    padding: 0.75rem;
  }
  i {
    font-size: 1.5rem;
    margin-right: 0.5rem;
    color: var(--light-blue);
    transition: all 0.2s;
    padding-left: 3rem;
  }
  &:hover {
    .item-content {
      background-color: var(--very-light-blue);
      color: white;
      i {
        color: white;
      }
    }
    .arrow-right {
      border-left-color: var(--very-light-blue);
    }
  }
  &:active,
  &.active {
    .item-content {
      background-color: var(--light-blue);
      color: white;
      i {
        color: white;
      }
    }
    .arrow-right {
      border-left-color: var(--light-blue);
    }
  }
}
</style>
