<#-- The search is posted with htmx and its results swapped in below the form, rather than as a normal
     form post, so that reloading the page neither asks to resubmit the form nor sends a new XCPD query.
     method="post" is only a fallback that keeps the patient ids out of the URL if htmx isn't loaded. -->
<form
  method="post"
  action="/find-patient/search"
  hx-post="/find-patient/search"
  hx-target="#search-results"
  hx-disable="find button[type=submit]"
  class="bg-white shadow-sdk mt-10 max-w-4xl mx-auto border-b-8 border-b-red p-4 flex flex-col gap-2"
>
  <label for="country">Patientens hjemland</label>
  <#-- autocomplete="off" stops the browser from restoring the selection on reload, which would leave a
       country selected with no id fields loaded for it -->
  <select
    class="select-input"
    id="country"
    name="country"
    autocomplete="off" <#-- stops the browser from restoring the selection on reload -->
    hx-get="/find-patient/fields"
    hx-include="this"
    hx-target="#search-fields"
  >
    <option value="">Vælg land…</option>
    <#list countries as c>
      <option value="${c.code}"<#if c.code == country> selected</#if>>${c.name}</option>
    </#list>
  </select>

  <div id="search-fields" class="flex flex-col gap-2">
    <#include "fields.ftlh">
  </div>
</form>
