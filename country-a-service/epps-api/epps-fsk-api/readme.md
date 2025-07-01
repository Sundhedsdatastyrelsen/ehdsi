# Changes from automatic scripts

After downloading the WSDL files (only needed if they update, as they are checked in), they might miss some information
to be used with the `wsimport` plugin in maven.

For the current version, we are missing the folllowing
## sfskiti18.wsdl
After downloading, add the line
```
            <fault name="DGWSFault" message="ihe:DGWSFault"/>
```
To sfskiti18.wsdl at the bottom of the `<operation name="DocumentRegistry_RegistryStoredQuery">` tag, inside the
`<portType>` tag.

## sfskiti43.wsdl
This file has the correct tag, but is missing the "message" attribute in the tag, in the same location as before,
at the bottom of the `<operation name="DocumentRegistry_RegistryStoredQuery">` tag, inside the `<portType>` tag. Add the
message attribute to the tag
```
message="ihe:DGWSFault"
```
