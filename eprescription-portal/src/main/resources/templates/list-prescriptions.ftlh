<#import "layout.ftlh" as layout>
<@layout.page title="ePrescription Portal - Prescriptions">
  <div class="mt-10 max-w-4xl mx-auto">
    <p>Patient-id: ${patientId}</p>

    <table class="mt-4 w-full text-left">
      <thead>
        <tr>
          <th>Lægemiddelnavn</th>
          <th>ATC-kode</th>
          <th>ATC-navn</th>
          <th>Lægemiddelform</th>
          <th>Styrke</th>
          <th>Udstedt</th>
          <th>Ordinerende læge</th>
          <th>Kan udleveres</th>
          <th>Recept</th>
        </tr>
      </thead>
      <tbody>
        <#list prescriptions as prescription>
          <tr>
            <td>${prescription.productName!"Unknown"}</td>
            <td>${prescription.atcCode}</td>
            <td>${prescription.atcName}</td>
            <td>${prescription.doseFormName}</td>
            <td>${prescription.strength}</td>
            <td>${prescription.effectiveTime}</td>
            <td>${prescription.authorName}</td>
            <td>
              <#if prescription.dispensable!false>Ja<#else>Nej</#if>
            </td>
            <td>
              <!-- Placeholder links -->
              <a href="/prescriptions/${prescription.l3DocumentId?url}">Se recept</a>
            </td>
          </tr>
        </#list>
      </tbody>
    </table>
  </div>
</@layout.page>
