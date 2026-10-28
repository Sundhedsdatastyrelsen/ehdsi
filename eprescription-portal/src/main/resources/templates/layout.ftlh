<#-- Page chrome for all pages: <@layout.page title="…">content</@layout.page> -->
<#macro page title>
<!DOCTYPE html>
<html lang="da">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="/static/style.css" rel="stylesheet" />
    <title>${title}</title>
    <script src="/static/htmx-4.0.0.min.js"></script>
  </head>
  <body
    class="text-foreground bg-background flex flex-col min-h-dvh"
  >
    <header class="bg-white h-16 md:h-20 px-4 md:px-8 section-border">
      <nav class="flex max-w-4xl mx-auto h-full relative">
        <a
          href="/"
          title="Hjem"
          class="flex-grow self-center text-lg text-center"
          >ePrescription Portal</a
        >
        <#if principal??>
        <a href="/logout" title="Log af" class="absolute top-0 bottom-0 right-0 blue-button self-center">Log af</a>
        <#else>
        <a href="/login" title="Log på" class="absolute top-0 bottom-0 right-0 blue-button self-center">Log på</a>
        </#if>
      </nav>
    </header>
    <main class="mt-10 px-4 md:px-8">
      <h1 class="text-center text-2xl">${title}</h1>

      <#nested>
    </main>
  </body>
</html>
</#macro>
