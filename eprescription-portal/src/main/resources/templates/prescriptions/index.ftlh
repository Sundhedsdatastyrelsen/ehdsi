<#import "../layout.ftlh" as layout>
<@layout.page title="Recepter">
  <div class="mt-10 mx-auto bg-white p-4">
    <p class="font-bold">Patient-id: ${patientId}</p>

    <#-- Show a spinner while we wait for the prescriptions to arrive. -->
    <div
      hx-get="/prescriptions/list?patient-ref=${patientRef?url}"
      hx-trigger="load"
      hx-swap="outerHTML"
      hx-indicator="#prescriptions-spinner"
    >
      <div id="prescriptions-spinner" class="delayed-indicator justify-center py-4" aria-hidden="true">
        <span class="size-6 animate-spin rounded-full border-2 border-blue-300 border-t-blue-500"></span>
      </div>
    </div>
  </div>
</@layout.page>
