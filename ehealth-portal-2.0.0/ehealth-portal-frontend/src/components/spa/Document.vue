<!-- ---------------------------------------  HTML -->

<template>
  <div
    class="wrapper"
    ref="wrapper"
    @click="open"
    :class="{ loading: loading }"
  >
    <div class="icon">
      <i class="far fa-file-medical-alt" v-if="level === 'Level 3'"></i>
      <i class="fas fa-file-pdf" v-if="level === 'Level 1'"></i>
    </div>
    <div class="content">
      <h4>{{ document.name }}</h4>
      <h5>{{ level }}</h5>
      <div v-if="loading" class="loading-wrapper">
        <i class="far fa-spinner-third fa-spin"></i>
      </div>
      <div class="date">{{ document.creationDate }}</div>
    </div>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { fly } from "./../../animations";
import { getPdfDocument, getHtmlDocument } from "../../api";

export default {
  props: {
    document: Object,
    index: String,
  },

  mounted() {
    fly(this.$refs.wrapper, { y: 100, delay: this.index * 0.1 });
  },

  data() {
    return {
      opening: false,
    };
  },

  computed: {
    level() {
      switch (this.document.formatCode) {
        case "urn:ihe:iti:xds-sd:pdf:2008":
          return "Level 1";
        default:
          return "Level 3";
      }
    },
    loading() {
      return this.$store.getters.loading && this.opening;
    },
  },

  methods: {
    open() {
      this.opening = true;
      if (this.level === "Level 1") {
        getPdfDocument(this.document).then((data) => {
          if (data.errorCode) {
            this.$store.dispatch("error", data);
          } else {
            this.opening = false;
          }
        });
      } else {
        getHtmlDocument(this.document).then((data) => {
          if (data.errorCode) {
            this.$store.dispatch("error", data);
          } else {
            this.opening = false;
          }
        });
      }
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.wrapper {
  padding: 1rem;
  border-radius: 1rem;
  width: calc(33.3% - 1rem);
  border: 1px solid #ccc;
  margin: 0 1rem 1rem 0;
  display: flex;
  flex-flow: row nowrap;
  transition: all 0.1s;
  background-color: #fff;
  cursor: pointer;
  h4 {
    margin-bottom: 0.5rem;
  }
  h5 {
    text-transform: uppercase;
  }
  &.loading {
    color: #ccc;
    font-style: italic;
  }
  &:hover {
    transform: scale(1.1) !important;
    border-color: var(--light-blue);
    box-shadow: 0 30px 30px -30px rgba(0, 0, 0, 0.15);
  }
  &:active {
    transform: scale(0.9) !important;
    border-color: var(--dark-blue);
  }
  .loading-wrapper {
    text-align: center;
    font-size: 2rem;
  }
  .items {
    margin-top: 1rem;
    display: flex;
    flex-flow: row wrap;
    .item {
      font-size: 2rem;
      padding: 0.25rem;
      transition: transform 0.1s;
      transition-timing-function: cubic-bezier(0.64, 0.57, 0.67, 1.53);
      cursor: pointer;
      &:hover {
        transform: scale(1.2);
      }
      &:active {
        transform: scale(0.8);
      }
      .fa-file-pdf {
        color: var(--red);
      }
      .fa-file-spreadsheet {
        color: var(--green);
      }
      .fa-file-csv {
        color: var(--green);
      }
      .fa-file-word {
        color: var(--blue);
      }
    }
  }
  .icon {
    font-size: 2.15rem;
    margin-right: 1rem;
    .far {
      color: var(--blue);
    }
    .fas {
      color: var(--red);
    }
  }
  .content {
    display: flex;
    flex-flow: column nowrap;
  }
}
</style>
