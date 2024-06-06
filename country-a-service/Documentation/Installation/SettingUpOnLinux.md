# How to set up Country A to run on linux (Ubuntu)
For people on windows, activate WSL-2, update it and download Ubuntu 22.04 from Microsoft Store.
### Installing WSL-2
- Go to Windows features and turn on ``Windows Subsystem for Linux``
- Restart once the installation is complete
- Go to powershell and run ``wsl.exe --update``
- Go to Microsoft Store and search for Ubuntu, install 22.04
- Open Ubuntu from the start menu
- Set username and password
## Installing packages
This is only required to develop on the machine, which might not be necessary for just running it (like on WSL-2). Consider skipping to [Running for the first time](#running-for-the-first-time), if you're not developing on linux.
### Docker
For docker we need to add their repository and trust their key
```shell
    sudo apt-get update
    sudo apt-get install ca-certificates curl
    sudo install -m 0755 -d /etc/apt/keyrings
    sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
    sudo chmod a+r /etc/apt/keyrings/docker.asc
    echo   "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" |   sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
```
Afterwards, we will install the docker tools that we need
```shell
 sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
```
After this, we can test the implementation using this to run a hello world docker container
```shell
sudo docker run hello-world
```
### Java
For java, we will be using the adoptium packages, and we will thus have to add their keys and endpoints as well
```shell
sudo apt install -y wget apt-transport-https gpg
sudo wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public | gpg --dearmor | tee /etc/apt/trusted.gpg.d/adoptium.gpg > /dev/nul
sudo echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/^UBUNTU_CODENAME/{print$2}' /etc/os-release) main" | tee /etc/apt/sources.list.d/adoptium.list
sudo apt update
sudo apt-get install temurin-21-jdk
```
We can verify the installation using
```shell
java -version
```

### Maven
```shell
sudo apt install maven
```
Verify with
```shell
mvn -v
```


## Running for the first time
Before we run the first time, there are some bookkeeping to do.
- Copy ``.env.defaults`` to ``.env`` [First time only]
- Go to a network that is not native to SDS (since they don't have access to the test services from inside)
- Navigate to the folder with the docker-compose file in it
- Run the following in bash
```bash
sudo docker compose -f docker-compose.local.yml up
```
It should now start up and run on http://localhost:8180
