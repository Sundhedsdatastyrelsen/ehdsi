<#-- Macro for filling out attributes for a coded CDA value -->
<#macro cdaCodeAttrs value><#rt>
code="${value.code}" <#rt>
codeSystem="${value.codeSystem}"<#rt>
<#if value.displayName??> displayName="${value.displayName}"</#if><#rt>
<#if value.codeSystemName??> codeSystemName="${value.codeSystemName}"</#if><#rt>
<#if value.codeSystemVersion??> codeSystemVersion="${value.codeSystemVersion}"</#if><#rt>
</#macro>
<#-- Macro for filling out attributes for a CDA identifier -->
<#macro cdaIdAttrs id><#rt>
root="${id.root}"<#rt>
<#if id.extension??> extension="${id.extension}"</#if><#rt>
</#macro>
<#--
 The following macro uses the string "__NULL__" as a replacement for a pure null value
 This is caused by the freemarker handling of null values as input to macros, and is chosen, since we do not expect
 any of the actual values to match this string. Treat it is a null value, only one that can be a default for the macro"
 -->
<#macro address value="__NULL__"><#rt>
<#if value=="__NULL__"><#rt>
<addr nullFlavor="NI" />
<#else>
<addr>
    <#list value.streetAddressLines as addressLine>
    <streetAddressLine>${ addressLine }</streetAddressLine>
    </#list>
    <#if value.city??><city>${ value.city }</city></#if><#rt>
    <#if value.postalCode??><postalCode>${ value.postalCode }</postalCode></#if><#rt>
    <#if value.countryCode??><country>${ value.countryCode }</country></#if><#rt>
</addr>
</#if><#rt>
</#macro><#rt>
<#macro valueOrNullField value fieldName>
  <#if value?has_content>
    <${fieldName} value="${value}" />
  <#else>
    <${fieldName} nullFlavor="UNK" />
  </#if>
</#macro>
