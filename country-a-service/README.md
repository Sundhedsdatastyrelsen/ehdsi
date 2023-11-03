ePrescriptionPoC
===============================================================================

## Introduction

TODO: Give a short introduction of your project. Let this section explain the objectives or the motivation behind this project.

## Building this project

### Pre-requisites

## Getting Started

* Maven - version 3.8 was used to develop this, it may or may not work with older versions.
* Java JDK 17 - [Eclipse Temurin 17](https://adoptium.net/?variant=openjdk17&jvmVariant=hotspot) from Adoptium was used to develop this project.
* Docker + Docker-Compose - Docker version 20.10.12 and docker-compose version 1.29.2 was used to develop this project.
* Bash - version 5+ - Most scripts in this project are Bash scripts - Linux and Mac should have that already installed, but if you are on windows you might need to install.



TODO: Guide users through getting your code up and running on their own system. In this section you can talk about:
1.	Installation process
2.	Software dependencies
3.	Latest releases
4.	API references

## Build and Test

The following command will build the code and run unit tests

```bash
    $ mvn -B clean package
```

## Running this project locally / runLocal.sh

```runLocal.sh``` is a script that runs commands to help speed up the implementation and testing of services by running services
in docker containers and allowing you to quickly build and redeploy.

Commands:

* build - builds the project
* start or up - start all services in docker
* run - runs a project outside of docker
* startdb - starts only the database
* debug - start all services in docker in debug mode
* restart - restarts the application, but not the database
* log or logs - tails the logs of the application
* stop or down - stops and removes all containers
* kill - stops and removes all containers forcefully

Options:

```runLocal.sh``` processes arguments in the order they are given.

Note: A simple web ui called pgweb is also started when using runLocal.sh to start the app. It can be accessed at [http://localhost:54321/](http://localhost:54321/) while the app is running.


### Examples

To build the project, start containers and follow the logs:
```bash
    $ ./runLocal.sh build start logs
```

To build and completely restart containers from scratch (including 3rd party) and follow logs:
```bash
    $ ./runLocal.sh stop build start logs
```

To shutdown the services:
```bash
    $ ./runLocal.sh stop
```

Check if a service is running:
```bash
    $ curl http://localhost:8080/actuator/health
```

Start only the database:
```bash
    $ ./runLocal.sh startdb
```


# Contribute
TODO: Explain how other users and developers can contribute to make your code better.

