<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper">
    <BreadcrumbItem
      id="countries"
      label="Choose Country"
      index="3"
      icon="fad fa-map-marker"
    />
    <BreadcrumbItem
      id="patient-search"
      label="Search Patient"
      index="2"
      icon="fad fa-address-book"
    />
    <BreadcrumbItem
      id="patient"
      label="Patient Details"
      index="1"
      icon="fad fa-address-book"
    />
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import BreadcrumbItem from "./BreadcrumbItem";
export default {
  components: {
    BreadcrumbItem,
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.wrapper {
  display: flex;
  background-color: white;
  align-items: center;
  flex-flow: row nowrap;
  // border-bottom: 1px solid var(--blue);
  z-index: 100;
  box-shadow: 15px 15px 15px -15px rgba(0, 0, 0, 0.3);
}
</style>
