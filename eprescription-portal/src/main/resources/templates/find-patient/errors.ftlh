<#-- The validation errors for the whole form, addressed to the error slots rendered by fields.ftlh.
     They are swapped out of band so the fields keep their DOM: a caret, a half-typed neighbouring field
     and a click on the submit button all survive a validation round-trip. -->
<#list fields as field>
    <div id="id-${field.index}-error" hx-swap-oob="true"><#if field.error??><p class="text-red">${field.error}</p></#if></div>
</#list>
<div id="form-error" hx-swap-oob="true"><#if formError??><p class="text-red">${formError}</p></#if></div>
