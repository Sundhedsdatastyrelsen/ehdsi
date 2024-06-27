## Frequently encountered problems with docker compose

### backend doesn't start up because of `tomcat-config-entrypoint.sh: no such file or directory`
On windows make sure git doesnt do automatic line ending conversions:

* `git config --global core.autocrlf input`
* `git rm --cached -r .`
* `git reset --hard`
* Retry docker compose
* 
### backend doesn't start up because `Caused by: java.net.UnknownHostException: mysql`
* This docker compose project requires you to first setup the openNCP docker compose.

