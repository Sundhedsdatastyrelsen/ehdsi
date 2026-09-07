<!DOCTYPE html>
<html lang="da">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="/static/style.css" rel="stylesheet" />
    <title>ePrescription Portal</title>
  </head>
  <body
    class="text-foreground bg-background flex flex-col min-h-dvh [&:has(dialog[open])]:overflow-hidden"
  >
    <header class="bg-white h-16 md:h-20 px-4 md:px-8">
      <nav class="max-w-4xl mx-auto flex h-full relative justify-between">
        <a
          href="/"
          title="Hjem"
          class="hidden md:flex justify-center items-center w-28 border-b-8 border-b-red bg-background text-lg z-10"
          >ePrescription portal</a
        >
        <a href="/form" title="Log på" class="self-center">Log på</a>
      </nav>
    </header>
    <main class="mt-10 px-4 md:px-8">
      <h1 class="text-center text-4xl">ePrescription portal</h1>
      <div class="max-w-prose mx-auto">
      </div>

      <#if error??>
        <div class="bg-white mt-10 max-w-4xl mx-auto border-b-8 border-b-yellow p-4">
          <#switch error>
            <#case "not-authorized">
              <p>Du skal logge på.</p>
              <#break>
            <#default>
              <p>Der er sket en fejl.</p>
          </#switch>
        </div>
      </#if>

      <div class="mt-10 text-center">
        <a href="/form" class="">
          Log på
        </a>
      </div>

      <section
        class="bg-white shadow-sdk mt-10 max-w-4xl mx-auto border-b-8 border-b-red p-4 flex flex-col gap-2"
      >
        <h2 class="text-lg">Dataansvarlig myndighed:</h2>
        <p>
          Sundhedsdatastyrelsen er dataansvarlig myndighed.
        </p>
        <p class="">
          Sundhedsdatastyrelsen<br />
          Ørestads Blvd. 5<br />
          2300 København S<br />
          Telefon: 7221 6800<br />
          E-mail:
          <a href="mailto:servicedesk@sundhedsdata.dk" class=""
            >servicedesk@sundhedsdata.dk</a
          >
        </p>
      </section>
    </main>
    <div class="flex-1"></div>
    <footer class="bg-white min-h-16 mt-10"></footer>
  </body>
</html>
