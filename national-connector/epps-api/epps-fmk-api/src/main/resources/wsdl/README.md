# In case this needs to be upgraded at some point.

## FMK

Note that the wsdl file ```MedicineCard-inline_2015_06_01_E6.wsdl``` found in
this directory is not the original fetched from
[here](https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:wsdl_og_xml_skemaer)
as that one can not be parsed.

To make it usable, these changes have been made:

- several definitions have been moved to be much earlier in
  the wsdl as the original references definitions before defining them
- for the types ```SignatureType``` and ```AssertionType``` double definition
  of ```id``` field with different casing have been commented out.
- some complex types have been simplified to make the generated classes easier
  to use.

Some of these are commented with `NCP:`.

For comparison the original file is kept here as
```MedicineCard-inline_2015_06_01_E6.orig.wsdl```.
