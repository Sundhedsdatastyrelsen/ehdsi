<form hx-post="/find-patient/search" hx-target="#search-area" hx-swap="innerHTML" class="flex flex-col gap-2">
  <input type="hidden" name="country" value="${country}" />

  <#list fields as field>
    <div class="flex flex-col gap-1">
      <label for="id.${field.index}">${field.label}<#if field.mandatory> *</#if></label>
      <#if field.contextualDescription?has_content>
        <p class="text-sm">${field.contextualDescription}</p>
      </#if>
      <input
        type="text"
        id="id.${field.index}"
        name="id.${field.index}"
        value="${field.value}"
        <#if field.mandatory>required</#if>
      />
      <#if field.error??>
        <p class="text-red">${field.error}</p>
      </#if>
    </div>
  </#list>

  <#if formError??>
    <p class="text-red">${formError}</p>
  </#if>

  <button type="submit" class="red-button">Søg</button>
</form>
