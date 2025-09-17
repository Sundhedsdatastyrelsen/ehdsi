# DK national infrastructure integration for NCPeH

This service exposes a REST API which can be called by the OpenNCP national connector.

## Setup

- Remember to let the usert ID (10001:10001) own the /data directory, so it can create the SQLite databases

## Developer guidelines

- Write in a mostly functional style. Prefer static methods, use filter and map, avoid unnecessary state, prefer
  immutable data to mutable state (eg records over JavaBeans).
- Don't let Spring spread further than epps-application. It becomes impossible to test simple things.
- Prefer runtime exceptions. They work better with lambda functions. Do catch them. And test that you do so.
- Be pragmatic. A caching class should probably be stateful.
