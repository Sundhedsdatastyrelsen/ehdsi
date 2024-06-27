<!-- ---------------------------------------  HTML -->

<template>
  <div class="other-fields-wrapper">
    <div class="other-field-wrapper" v-for="(item, idx) in fields.textFields">
      <Input
        :name="item.id + '---' + item.label + '---' + idx"
        v-model="form[item.id + '---' + item.label + '---' + idx]"
        :pattern="item.format"
        @validate="validate"
        :label="item.label"
        :mandatory="item.mandatory"
        type="text"
        :animation="{ delay: idx * 0.1, x: 200 }"
      />
    </div>
    <div class="other-field-wrapper" v-for="(item, idx) in fields.dateFields">
      <Input
        :name="item.id + '---' + item.label + '---' + idx"
        v-model="form[item.id + '---' + item.label + '---' + idx]"
        :pattern="item.format"
        @validate="validate"
        :label="item.label"
        type="date"
        :mandatory="item.mandatory"
        :animation="{ delay: idx * 0.1, x: 200 }"
      />
    </div>
    <div class="other-field-wrapper" v-for="(item, idx) in fields.genderFields">
      <Radio
        :options="genders"
        :name="item.id + '---' + item.label + '---' + idx"
        v-model="form[item.id + '---' + item.label + '---' + idx]"
        :pattern="item.format"
        @validate="validate"
        :label="item.label"
        type="date"
        :mandatory="item.mandatory"
        :animation="{ delay: idx * 0.1, x: 200 }"
      />
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import Input from "../ui/Input";
import Button from "../ui/Button";
import Radio from "../ui/Radio";

export default {
  props: ["fields", "validate", "form"],
  components: {
    Input,
    Button,
    Radio,
  },

  data() {
    return { genders: ["Male", "Female", "Undifferentiated"] };
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.other-fields-wrapper {
  display: flex;
  flex-flow: row wrap;
}
.other-field-wrapper {
  margin-right: 2rem;
}
.wrapper {
  padding: 1rem 2rem;
  input {
  }
}
h2 {
  display: flex;
  align-items: center;
  .selected-country {
    background-color: #fff;
    padding: 0.5rem;
    border-radius: 2rem;
    margin-left: 1rem;
    padding-right: 1rem;
    border: 1px solid #eee;
  }
}
img {
  margin: 0 1rem 0 0.25rem;
  width: 75px;
  height: 50px;
  border-radius: 3rem;
  vertical-align: middle;
  border: 1px solid #ddd;
  box-shadow: 0 5px 10px -2px rgba(0, 0, 0, 0.15);
}

.forms-wrapper {
  display: flex;
  flex-flow: row nowrap;
  .form-wrapper {
    margin-right: 2rem;
  }
}
</style>

<style lang="scss">
.wrapper {
  input {
    width: 300px !important;
  }
}
</style>
