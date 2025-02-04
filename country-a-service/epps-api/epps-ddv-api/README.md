NCPeH DK integration service - FMK API
===============================================================================

This contains the wsdl and api from "Fælles Medicin Kort" (FMK) services.

To avoid conflicts between FMK and DDV, go to the resources folder and run the following to give seperate schema names
to DDV

```bash
find ./schemas -type f -exec sed -i 's|http://www.sdsd.dk/|http://www.sdsd.dk/ddv/|g' {} +
find ./schemas-idws -type f -exec sed -i 's|http://www.sdsd.dk/|http://www.sdsd.dk/ddv/|g' {} +
sed -i 's|http://www.sdsd.dk/|http://www.sdsd.dk/ddv/|g' ./wsdl/vaccinationsServiceIDWS_2013-12-01-E1.wsdl
```
