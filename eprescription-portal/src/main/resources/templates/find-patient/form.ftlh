<form
  method="post"
  action="/find-patient/search"
  class="bg-white shadow-sdk mt-10 max-w-4xl mx-auto border-b-8 border-b-red p-4 flex flex-col gap-2"
>
  <label for="country">Patientens hjemland</label>
  <select
    class="select-input"
    id="country"
    name="country"
    hx-get="/find-patient/fields"
    hx-include="this"
    hx-target="#search-fields"
  >
    <option value="">Vælg land…</option>
    <#list countries as c>
      <option value="${c.code}"<#if c.code == country> selected</#if>>${c.name}</option>
    </#list>
  </select>

  <#-- Every change inside the fields revalidates the whole form; the response only swaps the error
       slots (out of band), so the fields themselves are left alone. -->
  <div
    id="search-fields"
    class="flex flex-col gap-2"
    hx-post="/find-patient/validate"
    hx-trigger="change"
    hx-include="closest form"
    hx-swap="none"
  >
    <#include "fields.ftlh">
  </div>
</form>
