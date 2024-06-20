<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper patient-wrapper">
    <div class="patient-details">
      <div class="inner">
        <h2>Patient details</h2>
        <PatientLabelValue
          label="First name"
          :value="patient.firstName"
          index="0"
        />
        <PatientLabelValue
          label="Family name"
          :value="patient.familyName"
          index="1"
        />
        <PatientLabelValue
          label="Date of birth"
          :value="patient.birthDate"
          index="2"
        />
        <PatientLabelValue
          label="Street"
          :value="patient.addressStreet"
          index="3"
        />
        <PatientLabelValue
          label="Zip/Postal Code"
          :value="patient.addressPostalCode"
          index="4"
        />
        <PatientLabelValue
          label="City"
          :value="patient.addressCity"
          index="5"
        />
        <PatientLabelValue
          label="Country"
          :value="patient.addressCountry"
          index="6"
        />
      </div>
    </div>
    <div class="tabs">
      <div class="tab-links">
        <TabLink index="8">
          <router-link to="/spa/patient/patient-summary"
            >Patient Summary</router-link
          >
        </TabLink>
        <TabLink index="9">
          <router-link to="/spa/patient/eprescriptions"
            >ePrescriptions</router-link
          >
        </TabLink>
        <TabLink index="10">
          <router-link to="/spa/patient/medications-dispensed-list"
            >Medications Dispensed List</router-link
          >
        </TabLink>
        <TabLink index="11">
          <router-link to="/spa/patient/orcd">ORCD</router-link>
        </TabLink>
      </div>
      <div class="tab-content">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import PatientLabelValue from "./PatientLabelValue";
import PurposeOfUse from "./PurposeOfUse";
import TabLink from "./TabLink";

export default {
  components: {
    PatientLabelValue,
    PurposeOfUse,
    TabLink,
  },

  computed: {
    patient() {
      let { birthDate } = this.$store.getters.patient;
      return {
        ...this.$store.getters.patient,
        birthDate: new Date(birthDate).toDateString(),
      };
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.patient-wrapper {
  display: flex;
  flex-flow: row nowrap;
  height: 100%;
  h2 {
    text-align: center;
    color: var(--dark-blue);
  }
  .patient-details {
    background-color: #fff;
    height: 100%;
    box-shadow: 59px 0 59px -59px rgba(0, 0, 0, 0.15);
    padding: 1rem;
    width: 33%;
    flex-shrink: 0;
    border-right: 1px solid #adbacc;
  }
  .tabs {
    padding: 0;
    flex-grow: 1;
  }
  .tab-content {
    padding: 1rem;
    z-index: 2;
  }
  .tab-links {
    // border-bottom: 1px solid var(--light-blue);
  }
  .tab-links {
    display: flex;
    flex-flow: row nowrap;
    padding-left: 1rem;
    a {
      display: block;
      padding: 0.5rem 1rem;
      text-decoration: none;
      text-transform: uppercase;
      color: #666;
      border-radius: 4px;
      margin-right: 0.25rem;
      transition: all 0.5s;
      &:hover {
        background-color: #eee;
      }
      &.router-link-active {
        background-color: var(--light-blue);
        color: #fff;
      }
    }
  }
}
</style>
