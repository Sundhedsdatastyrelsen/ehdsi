<#-- The results of a search, shown below the form in #search-results. -->
<#if searchError??>
  <p class="text-red">${searchError}</p>
<#elseif patients?size == 0>
  <p>Ingen patienter fundet.</p>
<#else>
  <#list patients as patient>
    <div class="bg-white shadow-sdk border-b-8 border-b-red p-4 flex flex-col gap-1">
      <#if patient.name??>
        <h2 class="text-lg">${patient.name}</h2>
      </#if>
      <#if patient.birthDate??>
        <p>Fødselsdato: ${patient.birthDate}</p>
      </#if>
      <#if patient.gender??>
        <p>Køn: ${patient.gender}</p>
      </#if>
      <#if (patient.addressStreet?? || patient.addressPostalCode?? || patient.addressCity?? || patient.addressCountry??)>
        <p>
          Adresse:
          <#if patient.addressStreet??>${patient.addressStreet}, </#if>
          <#if patient.addressPostalCode??>${patient.addressPostalCode} </#if>
          <#if patient.addressCity??>${patient.addressCity}, </#if>
          <#if patient.addressCountry??>${patient.addressCountry}</#if>
        </p>
      </#if>
      <#if patient.phone??>
        <p>Telefon: ${patient.phone}</p>
      </#if>
      <#if patient.email??>
        <p>E-mail: ${patient.email}</p>
      </#if>
      <#if patient.ids?size gt 0>
        <p>Patient-ID'er:</p>
        <#list patient.ids as id>
          <p>${id}</p>
        </#list>
      </#if>
    </div>
  </#list>
</#if>
