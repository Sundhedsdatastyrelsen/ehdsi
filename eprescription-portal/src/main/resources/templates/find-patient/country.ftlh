<#-- Response to picking a country, swapped into #search-fields: that country's empty id fields (nothing if no
     country is selected), and #search-results cleared out of band so results for the previous country go away. -->
<#include "fields.ftlh">
<div id="search-results" hx-swap-oob="innerHTML"></div>
