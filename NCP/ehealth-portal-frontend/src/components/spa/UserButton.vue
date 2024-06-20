<!-- ---------------------------------------  HTML -->

<template>
  <div
    class="user-button-wrapper"
    ref="button"
    @mouseover="over()"
    @mouseout="out()"
    @mouseup="up()"
    @mousedown="down()"
  >
    <div class="username">user</div>
    <div class="sign-out"><i class="far fa-sign-out"></i></div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { fly } from "../../animations";
import { onMounted, ref } from "vue";
import { buttonIn, buttonOver, buttonOut, buttonDown } from "../../animations";

export default {
  setup(props) {
    const button = ref(null);
    let hover = false;
    onMounted(() => {
      buttonIn(button.value, 0);
    });
    function over() {
      buttonOver(button.value);
      hover = true;
    }
    function out() {
      buttonOut(button.value);
      hover = false;
    }
    function down() {
      buttonDown(button.value);
      this.$store.commit("loading", true);
      this.$router.push("/login");
    }
    function up() {
      hover ? over() : out();
      this.$store.commit("loading", false);
    }
    return {
      over,
      out,
      down,
      up,
      button,
    };
  },
  mounted() {
    fly(this.$refs.button, { x: -300 });
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.user-button-wrapper {
  color: white;
  padding: 1rem;
  border-radius: 1rem;
  display: flex;
  align-items: center;
  cursor: pointer;
}
.username {
  margin-right: 1rem;
  margin-left: 0.5rem;
}
.far {
  font-size: 2rem;
}
.sign-out {
}
</style>
