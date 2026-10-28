<#import "layout.ftlh" as layout>
<@layout.page title="ePrescription Portal - Prescriptions">
  <#if error??>
    <div class="bg-white mt-10 max-w-4xl mx-auto border-b-8 border-b-yellow p-4">
      <#switch error>
        <#case "not-authorized">
          <p>Du skal logge på.</p>
          <#break>
        <#case "login-not-enabled">
          <p>Det er ikke muligt at logge på.</p>
          <#break>
        <#default>
          <p>Der er sket en fejl.</p>
      </#switch>
    </div>
  </#if>

  <div class="mt-10 text-center">
    <#if principal??>
    <p>Logget ind som: <span>${principal.cpr[0..5]}-${principal.cpr[6..]} - ${principal.name!"Intet navn"}</span></p>
    <a href="/find-patient" class="red-button">Find patient</a>
    <#else>
    <a href="/login" class="">
      Log på
    </a>
    </#if>
  </div>

  <section
    class="bg-white shadow-sdk mt-10 max-w-4xl mx-auto border-b-8 border-b-red p-4 flex flex-col gap-2"
  >
    <h2 class="text-lg">Dataansvarlig myndighed:</h2>
    <p>
      Sundhedsdatastyrelsen er dataansvarlig myndighed.
    </p>
    <p class="">
      Sundhedsdatastyrelsen<br />
      Ørestads Blvd. 5<br />
      2300 København S<br />
      Telefon: 7221 6800<br />
      E-mail:
      <a href="mailto:servicedesk@sundhedsdata.dk" class=""
        >servicedesk@sundhedsdata.dk</a
      >
    </p>
  </section>
</@layout.page>
