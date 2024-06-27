<!-- ---------------------------------------  HTML -->

<template>
  <div class="" v-for="(item, idx) in identifiers">
    <div class="" v-if="!Array.isArray(item)">
      <Input
        :name="id + '---' + item.id + '---' + idx"
        v-model="form[id + '---' + item.id + '---' + idx]"
        :pattern="item.format"
        @validate="validate"
        :label="item.label"
        type="text"
        specialMandatory="true"
        :animation="{ delay: idx * 0.1, x: 200 }"
      />
    </div>
    <div class="" v-if="Array.isArray(item)">
      <div class="forms-wrapper">
        <div class="form-wrapper" v-for="(elem, idx2) in item">
          <h3>
            <span v-if="idx2 > 0" class="or">or </span>
            {{ elem.display }}
          </h3>
          <div class="" v-for="(el, idx3) in elem.items">
            <Input
              :name="id + '---' + el.id + '---' + idx + '-' + idx2 + '-' + idx3"
              v-model="
                form[id + '---' + el.id + '---' + idx + '-' + idx2 + '-' + idx3]
              "
              :pattern="el.format"
              @validate="validate"
              :label="el.label"
              type="text"
              specialMandatory="true"
              :animation="{
                delay: (idx + idx3 + idx2) * 0.2,
                x: 200,
              }"
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import Input from "../ui/Input";
import Button from "../ui/Button";

export default {
  props: ["identifiers", "validate", "form", "id"],
  components: {
    Input,
    Button,
  },

  data() {
    return {};
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
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

.or {
  color: var(--red);
  font-weight: 500;
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
