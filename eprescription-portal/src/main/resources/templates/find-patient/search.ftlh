<#-- Response to a search, swapped into #search-results: the results (nothing if the search was invalid,
     which also clears any earlier results), and the id fields with their errors morphed back into
     #search-fields out of band. -->
<#if searchError??>
  <p class="text-red-500">${searchError}</p>
<#elseif patients?? && patients?size == 0>
  <p>Ingen patienter fundet.</p>
<#elseif patients??>
  <div class="table bg-white section-border p-4 border-collapse w-max min-w-full">
    <div class="table-header-group">
      <div class="table-row *:table-cell *:p-2 *:font-bold">
        <div>Navn</div>
        <div>Fødselsdato</div>
        <div>Køn</div>
        <div>Adresse</div>
        <div>Telefon</div>
        <div>E-mail</div>
        <div>Patient-ID'er</div>
      </div>
    </div>
    <div class="table-row-group">
      <#list patients as patient>
        <a
          href="/prescriptions?opaque-id=${patient.opaqueId}"
          class="table-row hover:bg-blue-200 *:table-cell *:p-1 *:px-2 *:max-w-64 *:[word-break:break-word]"
        >
          <p>${patient.name}</p>
          <p>${patient.birthDate}</p>
          <p>${patient.gender}</p>
          <div>
            <#if patient.addressStreet??><p>${patient.addressStreet}</p></#if>
            <p>
              <#if patient.addressPostalCode??>${patient.addressPostalCode} </#if>
              <#if patient.addressCity??>${patient.addressCity}</#if>
            </p>
            <#if patient.addressCountry??><p>${patient.addressCountry}</p></#if>
          </div>
          <p>${patient.phone}</p>
          <p>${patient.email}</p>
          <div>
            <#list patient.ids as id>
              <p>${id}</p>
            </#list>
          </div>
         </a>
      </#list>
    </div>
  </table>
</#if>
<div id="search-fields" hx-swap-oob="innerMorph">
  <#include "fields.ftlh">
</div>
