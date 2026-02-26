# Test certificates

The test certificates here have been taken from the NSP test identity library.
Currently, this library resides here: https://git.nspop.dk/projects/NT/repos/nsp-test-identity-provider.
In this library, they link to the certificates we use in an EmployeeIdentities file, which is currently here:
https://git.nspop.dk/projects/NT/repos/nsp-test-identity-provider/browse/src/main/java/dk/nsp/test/idp/EmployeeIdentities.java.

The links used for the current certificates are https://www.nspop.dk/download/attachments/190481050/NSP_Test_Service_Consumer_sds.p12
and https://www.nspop.dk/download/attachments/190481050/NSP_Test_Identity_Provider_sds.p12.

The alias may have changed, use `keytool -keystore ... -storepass Test1234 -list -v` to determine the new alias,
and then `keytool -keystore ... -storepass Test1234 -changealias -alias "wrong alias" -destalias "1"` to change it to the
expected alias, which is "1".

The ones pulled today will expire in October 2028.
