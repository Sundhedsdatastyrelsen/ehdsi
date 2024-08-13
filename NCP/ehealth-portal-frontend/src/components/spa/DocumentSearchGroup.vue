<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper">
    <h3>{{ label }}</h3>
    <Input
      :name="item.id"
      v-for="(item, index) in items"
      v-model="form[item.id]"
      :label="item.label"
      :pattern="item.format"
      @validate="validate"
      :mandatory="item.mandatory"
      :animation="{ delay: index * 0.1, x: 300 }"
    />
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import Input from "./../ui/Input";
import Button from "./../ui/Button";

export default {
  components: {
    Input,
    Button,
  },

  props: {
    label: String,
    items: Array,
    tab: String,
  },

  computed: {
    loading() {
      return this.$store.getters.loading;
    },
    isDisabled() {
      return this.disabled && this.loading;
    },
  },

  data() {
    return {
      form: {},
      valids: {},
      disabled: true,
      errors: {},
      isValid: false,
    };
  },

  methods: {
    validate(field, isValid) {
      if (Object.values(field).length === 0) {
        this.valids[field] = true;
        this.$emit("groupIsValid", this.tab, true, this.form);
      } else {
        this.valids[field] = isValid;
        let allValids = Object.values(this.valids).every((t) => t);
        this.disabled = !allValids;
        this.$emit("groupIsValid", this.tab, allValids, this.form);
      }
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.wrapper {
}
</style>
