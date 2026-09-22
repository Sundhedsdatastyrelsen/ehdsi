<!DOCTYPE html>
<html lang="da">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="/static/style.css" rel="stylesheet" />
    <title>ePrescription Portal</title>
    <!-- htmx 4.0.0, vendored (not loaded from a CDN); see src/main/resources/public/static/htmx.min.js -->
    <script src="/static/htmx.min.js"></script>
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
      <h1 class="text-center text-4xl">Find patient</h1>

      <section
          class="bg-white shadow-sdk mt-10 max-w-4xl mx-auto border-b-8 border-b-red p-4 flex flex-col gap-2"
      >
        <label for="country">Patientens hjemland</label>
        <select
          id="country"
          name="country"
          hx-get="/find-patient/form"
          hx-include="this"
          hx-target="#search-area"
          hx-swap="innerHTML"
          hx-trigger="change"
        >
          <option value="">Vælg land&hellip;</option>
          <#list countries as country>
            <option value="${country.code}">${country.name}</option>
          </#list>
        </select>

        <div id="search-area"></div>
      </section>
    </main>
  </body>
</html>
