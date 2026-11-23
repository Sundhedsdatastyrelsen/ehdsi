<#import "layout.ftlh" as layout>
<@layout.page title="Recept">
  <div class="mt-10 mx-auto">
    <#if patientRef??>
      <a href="/prescriptions?patient-ref=${patientRef?url}" class="underline">Tilbage til recepter</a>
    </#if>

    <div class="relative mt-4">
      <#-- The prescription is rendered by the CDA display tool, which is sandboxed: without allow-scripts nothing in
           the document can run, and allow-same-origin lets the script below read its height and listen to its events.
           box-content keeps the border out of the height the script sets. -->
      <iframe
        id="prescription"
        src="/prescription/${documentId?url}/display?patient-ref=${patientRef?url}&home-community-id=${homeCommunityId?url}&repository-unique-id=${repositoryUniqueId?url}"
        title="Recept"
        sandbox="allow-same-origin"
        class="block w-full border box-content"
      ></iframe>
      <#-- Shown on top of the empty iframe, after a delay so fast loads don't flash it, until the iframe has loaded
           (removed by the script below) -->
      <div
        id="prescription-spinner"
        class="absolute inset-0 flex items-center justify-center animate-[delayed-show_0s_200ms_both]"
        aria-hidden="true"
      >
        <span class="size-6 animate-spin rounded-full border-2 border-blue-300 border-t-blue-500"></span>
      </div>
    </div>
  </div>

  <script>
    // Fit the iframe to the prescription, so the page's scrollbar is the only one. The height changes after loading
    // when the display tool's collapsible sections are toggled (a checkbox change, then a max-height transition), and
    // when the window is resized. A ResizeObserver doesn't work here: it doesn't report changes in the iframe's document.
    const frame = document.getElementById("prescription");
    frame.addEventListener("load", () => {
      document.getElementById("prescription-spinner")?.remove();
      const doc = frame.contentDocument;
      // Without JavaScript the iframe keeps its own scrollbar, so the prescription can still be read
      doc.documentElement.style.overflow = "hidden";
      // The root's own height, not scrollHeight, which is at least the iframe's height and so would never shrink
      const fit = () => {
        frame.style.height = Math.ceil(doc.documentElement.getBoundingClientRect().height) + "px";
      };
      fit();
      doc.addEventListener("change", fit);
      doc.addEventListener("transitionend", fit);
      window.addEventListener("resize", fit);
    });
  </script>
</@layout.page>
