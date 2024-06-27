<!-- ---------------------------------------  HTML -->

<template>
  <div class="wrapper">
    <div class="empty" v-if="filteredDocuments.length == 0">
      No documents found
    </div>
    <table class="orcd-list" v-if="filteredDocuments.length > 0">
      <!--{{
        documents
      }}-->
      <thead>
        <tr>
          <th>Name</th>
          <th>Class code</th>
          <th>Document</th>
          <th>Dates</th>
          <th>Authors</th>
          <th>Reasons of Hospitalisation</th>
        </tr>
      </thead>
      <tbody>
        <tr
          class="orcd-list-item"
          v-for="(item, index) in filteredDocuments"
          @click="download(item)"
        >
          <th class="name">{{ item.name }}</th>
          <td class="classCode">{{ parseClassCode(item.classCode) }}</td>
          <td class="doc-infos">
            <div class="type">
              <i class="far" :class="docTypes[item.formatCode].icon"></i>
            </div>
            <div class="size">{{ parseSize(item.size) }}</div>
          </td>
          <td class="dates">
            <div class="creation">
              <div class="label">Creation:</div>
              <div class="value">{{ parseDate(item.creationDate) }}</div>
            </div>
            <div class="event">
              <div class="label">Event:</div>
              <div class="value">{{ parseDate(item.eventDate) }}</div>
            </div>
          </td>
          <td class="authors" v-for="(author, index) in item.authors">
            <div class="author">
              <div class="person">
                {{ author.person }}
              </div>
              <div class="specialities">
                {{ parseSpecialities(author.specialities) }}
              </div>
            </div>
          </td>
          <td class="reasons">
            <div v-if="item.reasonOfHospitalisation">
              {{ item.reasonOfHospitalisation.text }}
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
  <!-- {
      "identifier": "1.2.3^eHDSI_Test_File_Hospital_Discharge_Report.1",
      "repositoryId": "2.16.17.710.818.1000.990.1",
      "homeCommunityId": "urn:oid:2.16.17.710.818.1000.990.1",
      "name": "OrCD Hospital Discharge Summary",
      "mimeType": "text/xml",
      "formatCode": "urn:eHDSI:orcd:pdf:2021",
      "classCode": "34105-7",
      "size": 179410,
      "creationDate": "2018-09-18 06:00:00.000+0000",
      "eventDate": "2018-09-16 22:00:00.000+0000",
      "authors": [
        {
          "person": "AuthorPerson OrCD Test",
          "specialities": [
            "Speciality 1",
            "Speciality 2",
            "Speciality 3"
          ]
        }
      ],
      "reasonOfHospitalisation": {
        "code": "K56.2",
        "text": "Volvulus"
      }
    } -->
</template>

<!-- ---------------------------------------  SCRIPT -->

<script>
import numeral from "numeral";
import { loadDocumentTypes, loadOrcdDoc } from "./../../api";
import parser from "fast-xml-parser";
function b64_to_utf8(str) {
  return decodeURIComponent(escape(window.atob(str)));
}
export default {
  beforeMount() {
    loadDocumentTypes().then((data) => {
      this.classCodes = data;
    });
  },
  components: {},

  props: { documents: [] },

  data() {
    return {
      classCodes: [],
      searchTxt: "",
      docTypes: {
        "urn:eHDSI:orcd:pdf:2021": {
          icon: "fa-file-pdf",
          mimeType: "application/pdf",
        },
        "urn:eHDSI:orcd:jpeg:2021": {
          icon: "fa-file-image",
          mimeType: " image/jpeg",
        },
        "urn:eHDSI:orcd:png:2021": {
          icon: "fa-file-image",
          mimeType: " image/png",
        },
      },
    };
  },

  computed: {
    filteredDocuments() {
      return this.documents.filter((item) => {
        return item.name.toLowerCase().indexOf(this.searchTxt) > -1;
      });
    },
  },

  methods: {
    search(event) {
      this.searchTxt = event.target.value.toLowerCase();
    },
    reset() {
      this.searchTxt = "";
    },
    parseSize(v) {
      return numeral(v).format("0.0a");
    },
    parseDate(v) {
      return new Date(v).toLocaleDateString();
    },
    parseClassCode(v) {
      return this.classCodes.find((e) => e.classCode === v)?.value || v;
    },
    parseSpecialities(v) {
      return v.join(", ");
    },
    download(item) {
      let mimeType = this.docTypes[item.formatCode].mimeType;
      loadOrcdDoc(item).then((data) => {
        if (data.errorCode) {
          this.$store.dispatch("error", data);
        } else {
          let xml = b64_to_utf8(data.content);
          let doc = parser.parse(xml, {
            parseAttributeValue: false,
            parseTrueNumberOnly: true,
            parseNodeValue: false,
            ignoreNameSpace: true,
            ignoreAttributes: false,
            arrayMode: false,
          });
          const b64 = doc.ClinicalDocument.component.nonXMLBody.text["#text"];
          let mt = doc.ClinicalDocument.component.nonXMLBody.text["@_mediaType"];
          switch (mt) {
            case "application/png":
              mt = "image/png";
              break;
          }
          let displayWindow = window.open("");
          displayWindow.document.write(
              `<iframe width='100%' height='100%' src='data:${mt};base64,${b64}'
          </iframe>`
          );
          displayWindow.document.title = "Document";
        }
      });
    },
  },
};
</script>

<!-- ---------------------------------------  STYLES -->

<style lang="scss" scoped>
table {
  border: 1px solid #ccc;
  background-color: #fff;
  thead {
    font-size: 0.8rem;
  }
  tbody tr {
    cursor: pointer;
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
.orcd-list-item {
  width: 100%;
  font-size: 0.8rem;

  .label {
    font-size: 0.7rem;
    color: #999;
  }
  .specialities {
    font-size: 0.7rem;
    font-style: italic;
  }
}
</style>
