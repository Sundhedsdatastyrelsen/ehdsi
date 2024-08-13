<!-- ---------------------------------------  HTML -->

<template>
  <div class="filters-wrapper">
    <h3>Filter documents :</h3>
    <div class="filter">
      <div style="display:flex; margin-bottom: 1rem;gap:3rem">
        <div class="filter" style="">
          <label>Created after:
            <input type="date" v-model="createdAfter" @change="changed" /></label>
        </div>
        <div class="filter">
          <label>Created before:
            <input type="date" v-model="createdBefore" @change="changed" /></label>
        </div>
      </div>
      <div style="margin-bottom: 1rem;">
        <label>
          Types:
          <select name="docType" v-model="docType" @change="changed">
            <option value="18717-9"> Blood bank studies</option>
            <option value="18719-5"> Chemistry studies</option>
            <option value="18722-9"> Fertility studies</option>
            <option value="18723-7"> Hematology studies</option>
            <option value="18725-2"> Microbiology studies</option>
            <option value="18728-6"> Toxicology studies</option>
            <option value="26436-6"> Laboratory studies</option>
          </select>
        </label>
      </div>
    </div>
    <Button label="Search" primary @click="search" />
  </div>
  <div style="display:flex">
    <div v-for="item in data?.entry[0].resource.content ">
      <div v-if="item.attachment.contentType == 'application/pdf'" class="doc-type">PDF</div>
      <div v-if="item.attachment.contentType == 'application/fhir+json'" @click="displayFhirBundle(item.attachment)"
                                                                                      class="doc-type">
        JSON/FHIR</div>
    </div>
  </div>
  <div v-if:="bundle">
    <div v-for="entry in bundle.entry">
      <h3>{{ entry.resource.resourceType }}</h3>

      <div v-if="entry.resource.resourceType == 'Composition'" class="entry">
        <div class="label-value">
          <span>Title: </span>
          <strong>{{ entry.resource.title }}</strong>
        </div>
        <div class="label-value">
          <span>Language: </span>
          <strong>{{ entry.resource.language }}</strong>
        </div>
        <div class="label-value">
          <span>Identifier: </span>
          <strong>{{ entry.resource.identifier.assigner.display }}</strong>
        </div>
        <div class="label-value">
          <span>Status: </span>
          <strong>{{ entry.resource.status }}</strong>
        </div>
        <div class="label-value">
          <span>Type: </span>
          <strong>{{ entry.resource.type.coding[0].display }}</strong>
        </div>
        <div class="label-value" v-for="ext in entry.resource.extension" v-if="">
          <span>Extension: </span>
          <strong>{{ ext.valueReference.display }} {{ ext.valueReference.reference }}</strong>
        </div>
        <div class="label-value" v-for="cat in entry.resource.category">
          <span>Category: </span>
          <strong>{{ cat.coding[0].display }}</strong>
        </div>
        <div class="label-value" v-for="section in entry.resource.section">
          <span>Section: </span>
          <strong>{{ section.title }}</strong>
          <ul>
            <li v-for="sec in section.section">- {{ sec.title }}</li>
          </ul>
        </div>
      </div>
      <div v-if="entry.resource.resourceType == 'Patient'" class="entry">
        <div class="label-value">
          <span>Fullname: </span>
          <strong>{{ entry.resource.name[0].text }}</strong>
        </div>
        <div class="label-value">
          <span>Gender: </span>
          <strong>{{ entry.resource.gender }}</strong>
        </div>
        <div class="label-value">
          <span>Birthdate: </span>
          <strong>{{ entry.resource.birthDate }}</strong>
        </div>
        <div class="label-value" v-for="el in entry.resource.telecom">
          <span>{{ el.system }}: </span>
          <strong>{{ el.value }}</strong>
        </div>
        <div class="label-value" v-for="add in entry.resource.address">
          <span>Address - {{ add.use }}: </span>
          <strong>{{ add.text }}</strong><br />
          <strong>{{ add.country }}</strong>
        </div>
        <strong>Contacts</strong><br />
        <div class="label-value" v-for="el in entry.resource.contact">
          <span>{{ el.name.family }} {{ el.name.given.join(" ") }}</span><br />
          <span>{{ el.telecom[0].value }} </span>
        </div>
      </div>
      <div v-if="entry.resource.resourceType == 'Specimen'" class="entry">

      </div>
      <div v-if="entry.resource.resourceType == 'PractitionerRole'" class="entry">

      </div>
      <div v-if="entry.resource.resourceType == 'Practitioner'" class="entry">

      </div>
      <div v-if="entry.resource.resourceType == 'Organization'" class="entry">

      </div>
      <div v-if="entry.resource.resourceType == 'ServiceRequest'" class="entry">

      </div>
      <div v-if="entry.resource.resourceType == 'DiagnosticReport'" class="entry">

      </div>
      <div v-if="entry.resource.resourceType == 'Observation'" class="entry">

      </div>
      <div v-if="entry.resource.resourceType == 'DiagnosticReport'" class="entry">

      </div>
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { getFhirBundle, searchDocRefs } from "../../api"
import Button from "../ui/Button.vue"
export default {
  onMount() { },
  components: {
    Button,
  },
  beforeMount() { },
  data() {
    return {
      createdAfter: "",
      createdBefore: "",
      docType: null,
      data: null,
      bundle: null
    }
  },

  computed: {
    filters() {
      return {
        createdAfter:
          this.createdAfter != ""
            ? 'ge' + new Date(this.createdAfter).toISOString().split('T')[0]
            : null,
        createdBefore:
          this.createdBefore != ""
            ? 'le' + new Date(this.createdBefore).toISOString().split('T')[0]
            : null,
        docType: this.docType ? 'http://loinc.org/' + this.docType : null,
      }
    },
  },

  methods: {
    displayFhirBundle: function (args) {
      getFhirBundle(args).then((data) => {
        console.log('data', data)
        this.bundle = data
      })
    },
    search: function (e) {
      e.preventDefault()
      searchDocRefs().then((data) => {
        this.data = data
      })
    },
    changed: function (e) {
      this.$store.commit("filters", this.filters)
    },
  },
}
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.class-codes {}

.entry {
  margin-bottom: 1rem;
}

strong {
  font-weight: bold;
}

.doc-type {
  cursor: pointer;
  display: inline-block;
  margin-right: 1rem;
  margin-bottom: 1rem;
  padding: 1rem;
  border-radius: 4px;
  background-color: #fff;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.filters-wrapper {
  margin-bottom: 1rem;
  display: flex;
  flex-flow: column nowrap;
  padding: 0.25rem 0rem;
  font-size: 1rem;
  width: 100%;

  .filters {
    display: flex;
  }

  .filter {
    width: 30%;
  }



  input,
  select {
    display: inline-block;
    width: 10rem !important;
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 0.25rem 0.5rem;
    height: 2rem;
    margin-top: 0.5rem;
  }
}
</style>
