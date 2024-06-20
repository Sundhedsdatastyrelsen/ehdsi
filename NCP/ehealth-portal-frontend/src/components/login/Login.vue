<!-- ---------------------------------------  HTML -->

<template>
  <div class="login-wrapper">
    <div class="login-content" ref="loginContent">
      <h1>eHealth OpenNCP Portal</h1>
      <Input
        name="login"
        ref="login_ref"
        v-model="login"
        @validate="validate"
        pattern="^.{3,}$"
        @paste="validate"
        label="Login"
        type="text"
        :animation="{ delay: 0, x: 200 }"
      />

      <Input
        name="password"
        ref="password_ref"
        v-model="password"
        pattern="^.{3,}$"
        @validate="validate"
        @paste="validate"
        type="password"
        label="Password"
        :animation="{ delay: 0.3, x: 300 }"
      />
      <Button primary :disabled="isDisabled" label="Submit" @click="submit" />
    </div>
    <Loading />
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import Button from "./../ui/Button";
import Input from "./../ui/Input";
import Loading from "./../ui/Loading";
import { login } from "./../../api";
import { fadeOut } from "./../../animations";

export default {
  components: {
    Button,
    Input,
    Loading,
  },

  data() {
    return {
      login: "",
      password: "",
      login: null,
      password: null,
      login_ref: null,
      password_ref: null,
      valids: { login: false, password: false },
      disabled: true,
    };
  },
  mounted() {
    document.querySelectorAll("input").forEach((i, n) => {
      setTimeout(() => {
        i.value = i.value;
        this[i.name] = i.value;
        i.focus();
        i.blur();
      }, 1000);
    });
  },
  computed: {
    loading() {
      return this.$store.getters.loading;
    },
    isDisabled() {
      return this.disabled && this.loading;
    },
  },

  methods: {
    submit() {
      login({ login: this.login, password: this.password }).then((data) => {
        fadeOut(this.$refs.loginContent);
        setTimeout(() => this.$router.push("spa"), 1000);
      });
      return false;
    },
    validate(field, isValid) {
      this.valids[field] = isValid;
      this.disabled = !Object.values(this.valids).every((t) => {
        return t;
      });
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.login-wrapper {
  width: 100%;
  height: 100%;
  display: flex;
  flex-flow: row nowrap;
  align-items: center;
  justify-content: center;
  .login-content {
    background-color: #fff;
    padding: 2rem;
    width: 400px;
  }
  h1 {
    margin-bottom: 2rem;
  }
}
</style>
