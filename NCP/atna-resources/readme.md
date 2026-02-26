# Openatna configuration

`ArrConnections.template.xml` is used in the `entrypoint.sh` in the docker containers.
We replace the environment variables there, and write the resulting file to `ArrConnections.xml`, which is the file actually used by the containers.
This is done because the openatna configuration reader doesn't support environment variables.

We moved the openatna configuration out of the openncp configuration folder for two reasons.
First, openatna is a third-party product, and it doesn't fit in with the rest of the configuration in openncp-configuration.
Second, we can mount this atna configuration as a readonly volume (configuration should be readonly), and then produce the actual file from the template and write that to where the openncp openatna docker container looks for it. We're not doing that yet.
