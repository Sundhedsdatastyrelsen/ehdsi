<#import "layout.ftlh" as layout>
<@layout.page title="ePrescription Portal - Recept">
  <div class="mt-10 max-w-6xl mx-auto">
    <a href="/prescriptions?opaque-id=${opaqueId?url}" class="underline">Tilbage til recepter</a>

    <#-- The prescription is rendered by the CDA display tool, which is sandboxed: without allow-scripts nothing in the
         document can run, and allow-same-origin lets the script below read its height and listen to its events.
         box-content keeps the border out of the height the script sets. -->
    <iframe
      id="prescription"
      src="/prescriptions/${documentId?url}/display"
      title="Recept"
      sandbox="allow-same-origin"
      class="mt-4 w-full border box-content"
    ></iframe>
  </div>

  <script>
    // Fit the iframe to the prescription, so the page's scrollbar is the only one. The height changes after loading
    // when the display tool's collapsible sections are toggled (a checkbox change, then a max-height transition), and
    // when the window is resized. A ResizeObserver doesn't work here: it doesn't report changes in the iframe's document.
    const frame = document.getElementById("prescription");
    frame.addEventListener("load", () => {
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
