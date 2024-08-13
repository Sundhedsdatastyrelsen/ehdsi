<!-- ---------------------------------------  HTML -->

<template>
  <div class="input-wrapper" ref="inputWrapper">
    <label ref="labelTxt">
      {{ label }}
      <span
        v-if="mandatory || specialMandatory"
        class="star fas fa-star-of-life"
      ></span>
    </label>
    <input
      :type="type"
      :pattern="regexp ? regexp : undefined"
      :class="{ error: hasError }"
      @keyup="validate"
      @blur="validate"
      v-model="value"
      :name="name"
      ref="input"
      :required="mandatory"
    />
    <div class="error" v-if="error">
      {{ error }}
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { onMounted, ref } from "vue";
import { store } from "../../store";
import { fly } from "../../animations";

export default {
  setup(props) {
    const input = ref(null);
    const labelTxt = ref(null);
    onMounted(() => {
      fly(input.value, props.animation);
      input.value.onpaste = (event) => {
        var event = new Event("change");
        input.value.dispatchEvent(event);
      };
      let animationLabel = props.animation;
      animationLabel.delay += 0.2;
      fly(labelTxt.value, animationLabel);
    });

    return {
      input,
      labelTxt,
    };
  },

  props: {
    animation: Object,
    label: String,
    name: String,
    disabled: Boolean,
    type: String,
    modelValue: String,
    pattern: String,
    mandatory: String,
    specialMandatory: String,
  },

  data() {
    return {
      error: null,
    };
  },

  computed: {
    loading() {
      return this.$store.getters.loading;
    },
    hasError() {
      if (this.error) return this.input.validity.valid;
    },
    value: {
      get() {
        return this.modelValue;
      },
      set(value) {
        this.$emit("update:modelValue", value);
      },
    },
    regexp() {
      let regParts = this.pattern.match(/^\/(.*?)\/([gim]*)$/);
      if (regParts) {
        // the parsed pattern had delimiters and modifiers. handle them.
        return regParts[1];
      } else {
        return this.pattern;
      }
    },
  },
  methods: {
    validate(v) {
      this.valid = this.input.validity.valid;
      this.error = this.$refs.input?.validationMessage;
      this.$emit("validate", this.name, this.$refs.input.validity.valid);
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.input-wrapper {
  margin-bottom: 1rem;
  label {
    text-transform: uppercase;
    font-weight: 500;
    color: #666;
    margin-bottom: 0.15rem;
    display: block;
    font-size: 0.9rem;
  }
  input {
    padding: 0.75rem;
    border: 1px solid var(--green);
    width: 100%;
    outline: none;
    border-radius: 3px;
    background-color: var(--light-green);
    transition: border-color 0.5s, background-color 0.5s, box-shadow 0.25s;
    &.error {
      border-color: var(--red);
      background-color: var(--red);
      color: #fff;
    }
    &:focus {
      box-shadow: 0 2px 5px -1px rgba(0, 0, 0, 0.25);
      background-color: white;
      &.error {
        background-color: var(--red);
      }
    }
  }

  .error {
    font-family: var(--font-error);
    color: var(--red);
    font-size: 0.8rem;
  }
  .star {
    color: var(--red);
  }
}
</style>
