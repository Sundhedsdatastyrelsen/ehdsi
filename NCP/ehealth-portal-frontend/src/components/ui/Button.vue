<!-- ---------------------------------------  HTML -->

<template>

<div class="button-wrapper">
  <button
    @mouseover='over()'
    @mouseout='out()'
    @mouseup='up()'
    @mousedown='down()'
    :class='{primary}'
    :disabled='disabled'
    ref="button">
    {{label}}
  </button>
</div>

</template>

<!-- ---------------------------------------  SCRIPT -->

<script>

import { onMounted, ref } from 'vue';
import { buttonIn, buttonOver, buttonOut, buttonDown } from '../../animations';

export default {
  setup(props) {
    const button = ref(null);
    let hover = false
    onMounted(() => {
      buttonIn(button.value, 0)
    })
    function over() {
      buttonOver(button.value)
      hover = true
    }
    function out() {
      buttonOut(button.value)
      hover = false
    }
    function down() {
      buttonDown(button.value)
      this.$store.commit('loading', true)
    }
    function up() {
      hover ? over() : out()
      this.$store.commit('loading', false)
    }
    return {
      over, out,
      down, up,
      button
    }
  },

  props: {
    label: String,
    primary: Boolean,
    handleClick: Function,
    disabled: Boolean
  },

  computed: {
    loading()  {return this.$store.getters.loading}
  },
  methods: {
  }
}


</script>



<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>

button {
  padding: 0.55rem 1rem;
  border-radius: 5px;
  box-shadow: 0 2px 2px -1px rgba(0,0,0,.15);
  border: 1px solid #FFF;
  font-family: var(--font-error);
  cursor: pointer;
  transition: background-color 0.25s;
  &.primary {
    background-color: var(--blue);
    color: #FFF
  }
  &[disabled] {
    background-color: #CCC;
    font-style: italic;
    color: #EEE;
    cursor: default;
  }
}


</style>
