let form = document.querySelector("form");

function submit(e) {
	e.preventDefault();
	let prescriptionId = document.querySelector("input#prescriptionID").value;
	let countryCode = document.querySelector("input#country").value;
	let substitution = document.querySelector("input[id^=substituted_]")
		.checked;

	let numberOfPackage = document.querySelector(
		"input[id^=dispensedNumberOfPackages_]"
	).value;
	let productName = document.querySelector(
		"input[id^=dispensedProductValue_]"
	).value;
	let packageSize = {
		quantity: document.querySelector("input#quantity") !== null ? document.querySelector("input#quantity").value : null,
		packageSizeL1: document.querySelector("input#packageSizeL1") != null ? document.querySelector("input#packageSizeL1").value : null,
		packageSizeL2: document.querySelector("input#packageSizeL2") != null ? document.querySelector("input#packageSizeL2").value : null,
		packageSizeL3: document.querySelector("input#packageSizeL3") != null ? document.querySelector("input#packageSizeL3").value : null
	}

	let obj = {
		prescriptionId: prescriptionId,
		numberOfPackage: numberOfPackage,
		productName: productName,
		countryCode: countryCode,
		substitution: substitution,
		packageSize: packageSize,
		partPackageSizes: []
	};
	document.querySelectorAll("[data-dispense-field]").forEach((field) => {
		let id = field.dataset.dispenseField.split("_");
		if (!obj.partPackageSizes[Number(id[1])])
			obj.partPackageSizes[Number(id[1])] = {};
		obj.partPackageSizes[Number(id[1])][id[0]] = field.value;
	});

	callback(obj);
}

form.addEventListener("submit", submit);