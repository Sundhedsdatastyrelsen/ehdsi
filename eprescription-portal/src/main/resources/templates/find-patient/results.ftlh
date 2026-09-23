<#import "../layout.ftlh" as layout>
<@layout.page title="Find patient">
  <div class="mt-10 max-w-4xl mx-auto flex flex-col gap-4">
    <#if error??>
      <p class="text-red">${error}</p>
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

    <a href="/find-patient" class="red-link">Ny søgning</a>
  </div>
</@layout.page>
