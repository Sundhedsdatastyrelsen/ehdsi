<#-- The country-specific part of the search form: its id fields, errors and submit button. -->
<#if fields?has_content>
  <#list fields as field>
    <div class="flex flex-col gap-1">
      <label for="id-${field.index}">${field.label}<#if field.mandatory> *</#if></label>
      <#if field.contextualDescription?has_content>
        <p class="text-sm">${field.contextualDescription}</p>
      </#if>
      <input
        type="text"
        class="text-input"
        id="id-${field.index}"
        name="id.${field.index}"
        value="${field.value}"
        autocomplete="off" <#-- GDPR: the browser shouldn't store these data -->
        <#-- ask common browser plugins to ignore these fields -->
        <#-- (1password treats it as a credit card number, and they shouldn't save the data anyway) -->
        data-1p-ignore <#-- 1password -->
        data-lpignore="true" <#-- lastpass -->
        data-bwignore="true" <#-- bitwarden -->
        <#if field.mandatory>required</#if>
        <#-- A change triggers a validation -->
        hx-post="/find-patient/validate"
        hx-vals='{"field": "${field.index}"}'
        hx-target="#id-${field.index}-error"
      />
      <div id="id-${field.index}-error"><#if field.error??><p class="text-red-500">${field.error}</p></#if></div>
    </div>
  </#list>

  <div id="form-error"><#if formError??><p class="text-red-500">${formError}</p></#if></div>

  <div class="text-right">
    <button type="submit" class="blue-button relative">
      Søg
    </button>
  </div>
</#if>
