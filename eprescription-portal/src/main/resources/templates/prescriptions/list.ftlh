<#-- Response to loading the list on index.ftlh -->
<#if !prescriptions??>
  <#-- if the prescription list isn't there, it's an error. -->
  <p class="mt-4 text-red-500 font-bold">${error!"Der er sket en fejl."}</p>
<#elseif !prescriptions?has_content>
  <#-- if the prescription list is empty, there are no prescriptions -->
  <p class="mt-4">Patienten har ingen tilgængelige recepter.</p>
<#else>
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
          <p>Substitution</p>
          <p>Kan udleveres</p>
        </div>
      </div>
      <div class="table-row-group">
        <#list prescriptions as prescription>
          <a
            <#-- l3DocumentId should never be null. If it ever is, it's a big error, but we don't
                 want to crash the full list. -->
            href="/prescription/${prescription.l3DocumentId?url}?patient-ref=${patientRef?url}&home-community-id=${prescription.homeCommunityId?url}&repository-unique-id=${prescription.repositoryUniqueId?url}"
            class="table-row hover:bg-blue-200 *:table-cell *:p-1 *:px-2 *:max-w-64 *:[word-break:break-word]"
          >
            <p>${prescription.productName!"Unknown"}</p>
            <p>${prescription.atcCode!""}</p>
            <p>${prescription.atcName!""}</p>
            <p>${prescription.doseFormName!""}</p>
            <p>${prescription.strength!""}</p>
            <p title="${prescription.effectiveTimeUtc!""}">${prescription.prettyDate!""}</p>
            <p>${prescription.authorName!""}</p>
            <p>${prescription.substitution!""}</p>
            <p>
              <#if prescription.dispensable!false>Ja<#else>Nej</#if>
            </p>
          </a>
        </#list>
      </div>
    </div>
  </div>
</#if>
