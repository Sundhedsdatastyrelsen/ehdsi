<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper">
    <transition name="fade">
      <DocumentSearch tab="ML" @showContent="showContent" v-if="!content" />
    </transition>
    <transition name="fade">
      <table v-if="content">
        <thead>
          <tr>
            <th>Date</th>
            <th>Dispensed ID</th>
            <th>Dispense name</th>
            <th>&nbsp;</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in documents">
            <td>{{ parseDate(item.effectiveTime) }}</td>
            <td>{{ item.dispensedId }}</td>
            <td>{{ item.document }}</td>
            <td>
              <Button label="Discard" primary @click="discard(item)" />
            </td>
          </tr>
        </tbody>
      </table>
    </transition>
  </div>
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import { loadMedicationDispensedList, discardDispense } from "./../../api";
import Button from "./../ui/Button";
import DocumentSearch from "./DocumentSearch";
export default {
  components: { Button, DocumentSearch },

  beforeMount() {},

  data() {
    return {
      content: false,
      discarded: [],
    };
  },

  computed: {
    documents() {
      let docs = this.$store.getters.documents;
      return docs.filter((el) => {
        return this.discarded.indexOf(el.dispensedId) == -1;
      });
    },
  },

  methods: {
    showContent() {
      this.content = true;
    },
    parseDate(value) {
      return new Date(value).toString();
    },
    discard(item) {
      discardDispense(item).then((data) => {
        if (data.errorCode) {
          this.$store.dispatch("error", data);
        } else {
          if (
              data.status ===
              "urn:oasis:names:tc:ebxml-regrep:ResponseStatusType:Success"
          ) {
            this.$store.dispatch(
                "info",
                `Dispense with ID '${item.dispensedId}' has been successfully discarded`
            );
          } else {
            this.$store.dispatch("error", {
              errors: [`An error occured on the server`],
            });
          }
          this.discarded.push(item.dispensedId);
          this.mdl = this.documents.filter((el) => {
            return item.dispensedId !== el.dispensedId;
          });
        }
      });
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
.wrapper {
  table {
    border: 1px solid #ccc;
    background-color: #fff;
    thead {
      font-size: 0.8rem;
    }
    tbody tr {
      cursor: pointer;
      font-size: 0.8rem;
      &:hover {
        th,
        td {
          background-color: #d7deff;
        }
      }
      &:nth-child(odd) {
        background-color: #f5f5f5;
      }
    }
    th,
    td {
      padding: 0.5rem;
      vertical-align: top;
    }
    th {
      font-weight: 500;
    }
  }
}
</style>
