# DK national infrastructure integration for NCPeH

This service exposes a REST API which can be called by the OpenNCP national connector.

The national connector used to be called "country a service", some traces of that might still linger.

## Setup

- Remember to let the user ID (10001:10001) own the /data directory, so it can create the SQLite databases

## Certificates
The certificates for calling the opt-out service needs to match the DNS name of the opt out server, otherwise Java
won't recognize it. SANs don't work.

See [../docs/certificates.md](../docs/certificates.md) for how to generate and update certificates.


## Overview of National-connector

The National-connector directory consists of the following modules:

```
/National-connector
├── authentication/     # [Authentication logic (IDWS, DGWS, Certificates, etc)]
├── base-utils/         # [XML components (Namespace, utils, XPathWrapper, etc)]
├── base-utils-test/    # [Test of XML components]
├── cda-generator/      # [Core components for CDA generation, hereunder business logic and models]
├── epps-api/           # [Holds the schemas and generated data structures for data objects for the different sources]
├── epps-application/   # [Beans and controllers]
├── epps-service/       # [Our outgoing services e.g PatientSummary and incoming from e.g DDV or FMK]
├── integration-tests/  # [Tests toward outside services, e.g FMK, DVV, etc]
├── job-queue/          # [TODO: Add description]
├── local-lms-db/       # [TODO: Add description]
├── nsp-client/         # [Clients toward NSP]
├── opt-out/            # [FSEU opt-out service]
├── testing-shared/     # [Shared testing modules, e.g retrieval of test data which gets stored locally, to pretend tests breaking if updates happen]
└── testing-tools/      # [Tests for EPrescription]
```



## Tests
How is tests run?

/src
├── main/        # [...]
└── test/      # [Run tests in the files or via maven]

Some tests require Key vault secrets to run

## Developer guidelines

- Write in a mostly functional style. Prefer static methods, use filter and map, avoid unnecessary state, prefer
  immutable data to mutable state (eg records over JavaBeans).
- Don't let Spring spread further than epps-application. It becomes impossible to test simple things.
- Prefer runtime exceptions. They work better with lambda functions. Do catch them. And test that you do so.
- Be pragmatic. A caching class should probably be stateful.
