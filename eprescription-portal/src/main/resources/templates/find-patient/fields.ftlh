<#-- The country-specific part of the search form: its id fields, errors and submit button.
     The htmx wiring that revalidates them lives on the #search-fields container in form.ftlh. -->
<#if fields?has_content>
    <#list fields as field>
        <div class="flex flex-col gap-1">
            <label for="id-${field.index}">${field.label}<#if field.mandatory> *</#if></label>
            <#if field.contextualDescription?has_content>
                <p class="text-sm">${field.contextualDescription}</p>
            </#if>
            <#-- Patient ids belong to someone other than the user, so no password manager or browser should
                 remember them. Labels like "Nationalt ID-kortnummer" also make 1Password guess "credit card
                 number" ("kortnummer"), so it needs the explicit opt-out; autocomplete="off" alone isn't enough. -->
            <input
                type="text"
                class="text-input"
                id="id-${field.index}"
                name="id.${field.index}"
                value="${field.value}"
                autocomplete="off" <#-- GDPR: the browser shouldn't store these data -->
                <#-- ask common browser plugins to ignore these fields -->
                <#-- (1password treats it as a credit card number otherwise) -->
                data-1p-ignore <#-- 1password -->
                data-lpignore="true" <#-- lastpass -->
                data-bwignore="true" <#-- bitwarden -->
                <#if field.mandatory>required</#if>
            />
            <div id="id-${field.index}-error"><#if field.error??><p class="text-red">${field.error}</p></#if></div>
        </div>
    </#list>

    <div id="form-error"><#if formError??><p class="text-red">${formError}</p></#if></div>

    <button type="submit" class="red-button">Søg</button>
</#if>
