# How to upgrade curl on linux
Linux on Ubuntu 22.04 at the time of writing has problems using ssl certificates created by the newest openssl version.

To upgrade manually, run these commands in sequence after entering sudo shell (``sudo -i``)

Likely not needed going forward, documented to preserve history

```bash
apt remove curl
apt purge curl
apt-get update
apt-get install -y libssl-dev autoconf libtool make
cd /usr/local/src
rm -rf curl*
wget https://curl.se/download/curl-8.8.0.zip
apt install unzip
unzip curl-8.8.0.zip
cd curl-8.8.0/
./buildconf
./configure --with-ssl
make
make install
mv /usr/bin/curl /usr/bin/curl.bak
cp /usr/local/bin/curl /usr/bin/curl
ldconfig
curl -V
```