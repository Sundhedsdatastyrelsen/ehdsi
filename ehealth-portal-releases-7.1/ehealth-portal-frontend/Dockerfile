FROM node:12.22.12-alpine

RUN apk add --update python3 make g++ && rm -rf /var/cache/apk/*

RUN npm install -g http-server

WORKDIR /app

COPY package*.json ./

RUN npm install

COPY . .

RUN npm run local

EXPOSE 8080
CMD [ "http-server", "build" ]
