#!/bin/bash

#Create user for running the system
#
# To recreate a broken user, run `sudo userdel sds-test-admin`
sudo adduser --disabled-password --gecos "" sds-test-admin
sudo usermod -aG sudo sds-test-admin
sudo passwd -d sds-test-admin
sudo su - sds-test-admin

sudo apt-get update

# Install docker, by adding their repository to apt-get
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

read -sp 'API Token for Github login: ' passvar
export GITHUB_PAT=${passvar}
sudo git -C /var clone https://${GITHUB_PAT}@github.com/Sundhedsdatastyrelsen/ehdsi.git 

sudo chown -R sds-test-admin /var/ehdsi

read -sp 'CTS Password: ' ctspass
echo ${ctspass} >> /var/ehdsi/NCP/cts_password.txt

chmod +x /var/ehdsi/NCP/run.sh
/var/ehdsi/NCP/run.sh init
sudo /var/ehdsi/NCP/run.sh up

cp /var/ehdsi/country-a-service/.env.defaults /var/ehdsi/country-a-service/.env
export $(grep -v '^#' /var/ehdsi/country-a-service/.env | xargs -d '\n')
sudo docker compose -f /var/ehdsi/country-a-service/docker-compose.local.yml up -d