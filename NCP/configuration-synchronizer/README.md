# Configuration synchronizer for OpenNCP

OpenNCP reads its configuration from a database table, but we manage the configuration in a file.
This service watches the configuration file for changes and updates the configuration table.

We restrict the tool to updating the keys that are specified in the configuration file.
I.e., if we delete an entry in the file it will not be deleted in the database.
This is because other parts of the system can add properties – at the moment we are only aware of `AVAILABLE_TRANSLATION_LANGUAGES` from `openncp-tsam-sync`.

## Why make our own?

The bundled `openncp-configuration-utility` is made for populating the database table with the configuration values from the configuration file.
However, this tool is designed to work only when the database table is empty, i.e., as an initialisation of the configuration table.
This leaves us with two options for changing the configuration of a running OpenNCP instance:  Update the value in the database table manually (and risk drift from the configuration file), or truncating the configuration table and rerunning the configuration initialisation script.
Both options require getting access to the database with a SQL client, and give an annoyingly long feedback loop when tuning the configuration values.
