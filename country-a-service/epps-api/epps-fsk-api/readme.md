# Changes from automatic scripts
Somehow, sometimes, it will miss to download the XSD file here: https://test2-cnsp.ekstern-test.nspop.dk:8443/sfsk/dgws-wsdl/schema/wsu.xsd
Also, sometimes the tool downloads empty files, which need to be redownloaded. Easiest is to delete the empty files (AND THE WSDLs to trigger redownloads) and run it again...

After downloading, add the line
```
            <fault name="DGWSFault" message="ihe:DGWSFault"/>
```
To sfskiti18.wsdl at the bottom om `<operation name="DocumentRegistry_RegistryStoredQuery">`, to work with WSImport