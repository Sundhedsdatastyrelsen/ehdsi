<!DOCTYPE html>
<html lang="da">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="/static/style.css" rel="stylesheet" />
    <title>ePrescription Portal - Prescriptions</title>
  </head>
  <body
    class="text-foreground bg-background flex flex-col min-h-dvh"
  >
    <header class="bg-white h-16 md:h-20 shadow-sdk px-4 md:px-8">
      <nav class="max-w-4xl mx-auto flex h-full relative justify-between">
        <a
          href="/"
          title="Hjem"
          class="hidden md:flex justify-center items-center w-28 border-b-8 border-b-red bg-background text-lg z-10"
          >ePrescription Portal</a
        >
        <a href="/logout" title="Log af" class="red-button self-center">Log af</a>
      </nav>
    </header>
    <main class="mt-10 px-4 md:px-8">
      <h1 class="text-center text-4xl">Recepter</h1>

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
                  <a href="/prescriptions/document/${prescription.l3DocumentId}">Se recept (kodet)</a>
                  <a href="/prescriptions/document/${prescription.l1DocumentId}">Se recept (PDF)</a>
                </td>
              </tr>
            </#list>
          </tbody>
        </table>
      </div>
    </main>
  </body>
</html>
