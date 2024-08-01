import { createApp } from "vue";
import { store } from "./store";
import router from "./router";
import Toast, { POSITION } from "vue-toastification";

import "vue-toastification/dist/index.css";

import App from "./App.vue";

const app = createApp(App);
app.use(store);
app.use(router);

const toastOptions = {
  position: POSITION.TOP_RIGHT,
  timeout: 10000,
  pauseOnHover: true,
};

app.use(Toast, toastOptions);
app.mount("#app");

// Hot Module Replacement (HMR) - Remove this snippet to remove HMR.
// Learn more: https://www.snowpack.dev/#hot-module-replacement
if (import.meta.hot) {
  import.meta.hot.accept();
  import.meta.hot.dispose(() => {
    app.unmount();
  });
}
