# NCPeH implementation for Denmark

- [NCP](./NCP) contains the OpenNCP setup
- [national-connector](./national-connector) contains the national infrastructure integration service for NCP-A.

The NCP and the national connector communicate with a REST interface and use mutual tls for authentication. See [./docs/certificates.md](./docs/certificates.md) for more.