<#import "../layout.ftlh" as layout>
<@layout.page title="Find patient">
  <#-- The search is posted with htmx and its results swapped in below the form, rather than as a normal
       form post, so that reloading the page neither asks to resubmit the form nor sends a new XCPD query. -->
  <form
    method="post"
    action="/find-patient/search"
    hx-post="/find-patient/search"
    hx-target="#search-results"
    hx-disable="find button[type=submit]"
    hx-sync="drop" <#-- do nothing if a country change request is in-flight -->
    hx-indicator="closest form, #search-results-spinner" <#-- the form itself for the spinner in the button -->
    <#-- remove the old results while the new ones load (the event also bubbles up from the fields and select) -->
    hx-on::before:request="if (event.target === this) document.getElementById('search-results').replaceChildren()"
    class="bg-white shadow-sdk mt-10 max-w-4xl mx-auto border-b-8 border-b-red p-4 flex flex-col gap-2"
  >
    <label for="country">Patientens hjemland</label>
    <select
      class="select-input"
      id="country"
      name="country"
      autocomplete="off" <#-- stops the browser from restoring the selection on reload -->
      hx-get="/find-patient/fields"
      hx-include="this"
      hx-target="#search-fields"
      hx-indicator="#search-fields-spinner"
      hx-sync="closest form:replace" <#-- cancel any search request in-flight on change -->
      <#-- remove the old country's fields and any results from a search in it while the new fields load -->
      hx-on::before:request="document.getElementById('search-fields').replaceChildren(); document.getElementById('search-results').replaceChildren()"
    >
      <option value="">Vælg land…</option>
      <#list countries as c>
        <option value="${c.code}"<#if c.code == country> selected</#if>>${c.name}</option>
      </#list>
    </select>

    <div id="search-fields" class="flex flex-col gap-2">
      <#include "fields.ftlh">
    </div>
    <#-- Shown while the fields for a newly selected country are loading -->
    <div id="search-fields-spinner" class="delayed-indicator justify-center py-4" aria-hidden="true">
      <span class="size-6 animate-spin rounded-full border-2 border-red/30 border-t-red"></span>
    </div>
  </form>
  <div id="search-results" class="mt-10 max-w-4xl mx-auto flex flex-col gap-4"></div>
  <#-- Shown while a search is running -->
  <div id="search-results-spinner" class="delayed-indicator justify-center py-4" aria-hidden="true">
    <span class="size-6 animate-spin rounded-full border-2 border-red/30 border-t-red"></span>
  </div>
</@layout.page>
