<#import "layout.ftlh" as layout>
<@layout.page title="ePrescription Portal">
  <#if error??>
    <div class="bg-white mt-10 max-w-4xl mx-auto section-border p-4">
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
    <p>Logget ind som: <span>${principal.name!"Intet navn"} (${principal.cpr[0..5]}-${principal.cpr[6..]})</span></p>
    <p class="mt-4"><a href="/find-patient" class="blue-button">Find patient</a></p>
    <#else>
    <a href="/login" class="blue-button">
      Log på
    </a>
    </#if>
  </div>
</@layout.page>
