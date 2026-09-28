<#-- Response to validating a single id field, swapped into its error slot. A valid, filled-in field also
     clears the form-level "fill in at least one field" error out of band. -->
<#if error??><p class="text-red">${error}</p></#if>
<#if clearFormError><div id="form-error" hx-swap-oob="innerHTML"></div></#if>
