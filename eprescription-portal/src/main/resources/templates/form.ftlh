<#import "layout.ftlh" as layout>
<@layout.page title="ePrescription portal">
  <section
    class="bg-white shadow-sdk mt-10 max-w-4xl mx-auto border-b-8 border-b-red p-4 flex flex-col gap-2"
  >
    <p>Logget ind som: <span>${userCpr!?string}</span>.</p>
    <a href="/find-patient" class="red-button">Find patient</a>
  </section>
  <#if error??>
    <div class="bg-white shadow-sdk mt-10 max-w-4xl mx-auto border-b-8 border-b-yellow p-4">
      <#switch error>
        <#case "form-update-failed">
          <p>Der skete en fejl.</p>
          <#break>
        <#default>
          <p>Der er sket en fejl.</p>
      </#switch>
    </div>
  </#if>
</@layout.page>
