import {
  createWebHistory,
  createRouter,
  createWebHashHistory,
} from "vue-router";
import Login from "../components/login/Login";
import { store } from "../store";
import Home from "../components/spa/Home";
import Countries from "../components/spa/Countries";
import PatientSearch from "../components/spa/PatientSearch";
import Patient from "../components/spa/Patient";
import EPrescriptions from "../components/spa/EPrescriptions";
import MedicationDispensedList from "../components/spa/MedicationDispensedList";
import PatientSummary from "../components/spa/PatientSummary";
import Orcd from "../components/spa/Orcd";

function home() {
  document.location = "./";
}
const routes = [
  {
    path: "/",
    redirect: (to) => {
      return "/login";
    },
  },
  {
    path: "/login",
    component: Login,
  },
  {
    path: "/spa",
    component: Home,
    children: [
      {
        path: "",
        redirect: "/spa/countries",
      },
      {
        path: "countries",
        component: Countries,
      },
      {
        path: "patient-search",
        component: PatientSearch,
      },
      {
        path: "patient",
        component: Patient,
        children: [
          {
            path: "eprescriptions",
            component: EPrescriptions,
          },
          {
            path: "medications-dispensed-list",
            component: MedicationDispensedList,
          },
          {
            path: "orcd",
            component: Orcd,
          },
          {
            path: "patient-summary",
            component: PatientSummary,
          },
        ],
      },
    ],
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  if (from.path.indexOf("/spa") === 0 && from.path !== "/spa/countries") {
    if (!!store.getters.country.value) {
      next();
    } else {
      home();
    }
  } else {
    if (from.path == "/" && to.path !== "/login") {
      home();
    } else {
      next();
    }
  }
});

router.afterEach((to, from) => {
  let id = to.fullPath.split("/")[2];
  let idx;
  switch (id) {
    case "countries":
      idx = 3;
      break;
    case "patient-search":
      idx = 2;
      break;
    default:
      idx = 1;
  }
  store.commit("breadcrumb", { id: id, idx: idx });
});

export default router;
