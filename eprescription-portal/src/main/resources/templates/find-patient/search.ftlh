<#-- Response to a search, swapped into #search-results: the results (nothing if the search was invalid,
     which also clears any earlier results), and the id fields with their errors morphed back into
     #search-fields out of band. -->
<#if patients?? || searchError??>
  <#include "results.ftlh">
</#if>
<div id="search-fields" hx-swap-oob="innerMorph">
  <#include "fields.ftlh">
</div>
