<template>
  <div class="app-wrapper">
    <router-view />
  </div>
</template>

<script>


export default {
  data() {
    return {
    };
  },

  computed: {
    count() {return this.$store.getters.count;}
 },

 methods: {
   inc() {this.$store.commit('inc')},
   dec() {this.$store.commit('dec')}
 }
};

</script>


<style lang='scss'>

.App {
  header {
    font-size: 2rem
  }
}

</style>
