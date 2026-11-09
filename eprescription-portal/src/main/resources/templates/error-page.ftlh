<#import "layout.ftlh" as layout>
<@layout.page title="Der er sket en fejl">
  <div class="mt-10 mx-auto bg-white p-4">
    <p class="text-red-500 font-bold">${error!"Der er sket en fejl."}</p>
  </div>
</@layout.page>
