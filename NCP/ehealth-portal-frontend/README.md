# eHealth Portal

## Env

Modify the snowpack.config.js part

```

env: {
    DEV_API_URL: "https://openncp.ehdsi.eu:6453/ehealth-portal/api",
    PROD_API_URL: "https://openncp.ehdsi.eu:6453/ehealth-portal/api",
  },
```

## Project setup

`npm install`

## Development

`npm start`

## Build for production

`npm run release`

The output is the folder "build" located at root of ehealth-portal-frontend.
You can use it directly in the webapps folder of tomcat.

Warning: Do not execute the command "npm audit fix --force" as it might introduce breaking changes into the project.
