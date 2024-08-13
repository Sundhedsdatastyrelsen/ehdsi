<!-- ---------------------------------------  HTML -->

<template>
  <div class="home-wrapper">
    <header>
      <h1 ref="logo">eHealth OpenNCP Portal</h1>
      <UserButton />
    </header>
    <Breadcrumb />
    <div class="content">
      <router-view></router-view>
    </div>
    <Loading />
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { fly } from "../../animations";
import UserButton from "./UserButton";
import Breadcrumb from "./Breadcrumb";
import Loading from "../ui/Loading";

export default {
  components: {
    UserButton,
    Breadcrumb,
    Loading,
  },

  mounted() {
    fly(this.$refs.logo, { x: 400, delay: 0 });
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.home-wrapper {
  display: flex;
  flex-flow: column nowrap;
  height: 100%;
  overflow: hidden;
}
header {
  padding: 0rem 2rem;
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
  justify-content: space-between;
  border-bottom: 1px solid black;
  box-shadow: 0 3px 5px -2px var(--light-blue);
  background-color: var(--dark-blue);
  z-index: 10;

  h1 {
    display: block;
    cursor: pointer;
    margin-bottom: 0;
    color: white;
  }
}
.content {
  height: 100%;
  overflow-y: auto;
}
</style>
