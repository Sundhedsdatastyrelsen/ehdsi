
# In case this needs to be upgraded at some point.


Note that the wsdl found in this directory is not the original fetched from
[here](https://wiki.fmk-teknik.dk/doku.php?id=fmk:1.4.6:wsdl_og_xml_skemaer)
as that one can not be parsed.

To make it usable several definitions have been moved to be much earlier in
the wsdl as the original references definitions before defining them and also
for the types ```SignatureType``` and ```AssertionType``` double definition
of ```id``` field with different casing have been removed (marked with "TODO"
in the wsdl).
