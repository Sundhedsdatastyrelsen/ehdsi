<#import "layout.ftlh" as layout>
<@layout.page title="Recepter">
  <div class="mt-10 mx-auto bg-white p-4">
    <p class="font-bold">Patient-id: ${patientId}</p>

    <div class="overflow-x-auto">
      <div class="mt-4 table text-left border-collapse w-max min-w-full">
        <div class="table-header-group">
          <div class="table-row *:table-cell *:p-1 *:font-bold">
            <p>Lægemiddelnavn</p>
            <p>ATC-kode</p>
            <p>ATC-navn</p>
            <p>Lægemiddelform</p>
            <p>Styrke</p>
            <p>Udstedt</p>
            <p>Ordinerende læge</p>
            <p>Kan udleveres</p>
          </div>
        </div>
        <div class="table-row-group">
          <#list prescriptions as prescription>
            <a
              href="/prescriptions/${prescription.l3DocumentId?url}?opaque-id=${opaqueId?url}"
              class="table-row hover:bg-blue-200 *:table-cell *:p-1 *:px-2 *:max-w-64 *:[word-break:break-word]"
            >
              <p>${prescription.productName!"Unknown"}</p>
              <p>${prescription.atcCode}</p>
              <p>${prescription.atcName}</p>
              <p>${prescription.doseFormName}</p>
              <p>${prescription.strength}</p>
              <p>${prescription.effectiveTime}</p>
              <p>${prescription.authorName}</p>
              <p>
                <#if prescription.dispensable!false>Ja<#else>Nej</#if>
              </p>
            </a>
          </#list>
        </div>
      </div>
    </div>
  </div>
</@layout.page>
