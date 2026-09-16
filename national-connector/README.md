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
├── cda-generator/      # [Core functionality for generating CDA documents. Contains the business logic, models and supporting components used to transform source data into the CDA structures required for exchange.]
├── epps-api/           # [API-related data structures and schemas used when integrating with external data sources. Contains schemas as well as generated classes/models representing data received from or sent to the different APIs]
├── epps-application/   # [Main entry point when getting familiar with the application. Contains application configuration, beans and controllers, and is a useful place to start when tracing how requests enter the application.]
├── epps-service/       # [Service integrations and the logic connecting the application to external systems. Contains outgoing eHealth services such as Patient Summary as well as integrations used to retrieve data from Danish services such as DDV and FMK.]
└── testing-shared/     # [Shared testing modules, e.g retrieval of test data which gets stored locally, to pretend tests breaking if updates happen]
```

## Tests
* Some tests require Key vault secrets to run.
  
/src
├── main/        # [...]
└── test/      # [Run tests in the files or via maven]


## Developer guidelines

- Write in a mostly functional style. Prefer static methods, use filter and map, avoid unnecessary state, prefer
  immutable data to mutable state (eg records over JavaBeans).
- Don't let Spring spread further than epps-application. It becomes impossible to test simple things.
- Prefer runtime exceptions. They work better with lambda functions. Do catch them. And test that you do so.
- Be pragmatic. A caching class should probably be stateful.
