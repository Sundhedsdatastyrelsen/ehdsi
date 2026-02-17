> [!IMPORTANT]
> All the docker commands need to be executed in from the `openncp-docker` folder

From the root of this project execute:
```shell
cd openncp-docker
```

> [!WARNING]
> From now on every following command assumes you are in the `openncp-docker` folder: `ehealth/openncp-docker`
If not, execute the command above from the root folder.

To delete all previous docker containers (eg. to start with a clean slate, for example after a release)
```shell
docker compose down --rmi local
```

To start the whole openncp within the docker environment (as containers)
```shell
docker compose up -d
```

To stop all of the openncp containers use 
```shell
docker compose down
```