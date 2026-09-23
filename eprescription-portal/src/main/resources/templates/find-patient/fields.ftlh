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
      />
      <div id="id-${field.index}-error"><#if field.error??><p class="text-red">${field.error}</p></#if></div>
    </div>
  </#list>

  <div id="form-error"><#if formError??><p class="text-red">${formError}</p></#if></div>

  <#-- The spinner is an htmx indicator: it shows while the form has a request in flight -->
  <button type="submit" class="red-button relative">
    Søg
    <span
      class="htmx-indicator absolute right-3 top-1/2 -mt-2 size-4 animate-spin rounded-full border-2 border-white/40 border-t-white"
      aria-hidden="true"
    ></span>
  </button>
</#if>
