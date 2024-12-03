# OptOut Service

A simple Java service to manage opt-out requests and check eligibility for personal ID numbers. This project uses SQLite as the database and requires Java 21.

---

## Features

- **OptOut**: Save a personal ID number as opted out.
- **IsEligible**: Check if a personal ID number is eligible (i.e., not opted out).
- **Logging**: Logs eligibility queries with timestamps.

---

## Requirements

- Java 21+
- Maven
- SQLite3 (for database management)

---

## Project Structure

```bash
├── README.md
├── pom.xml # Maven config
├── src
   ├── main
   │   ├── java
   │   │   └── # Java Source Files
   │   └── resources
   │       └── # Resources (Sqlite db located here)
   └── test
       └── # Unit tests
```

## Setup and Compilation

### 1. Clone the Repository

```bash
git clone <repository-url>
cd optout-service
```

### 2. Ensure SQLite3 is isntalled for testing

`sudo apt-get install sqlite3`

### 3. Build the Project

`mvn clean install`

### 4. Run the Application to start the service

`mvn exec:java -Dexec.mainClass="com.sds.ehdsi.optoutservice.App"`

The server will run at [localhost:8080](http://localhost:8080).

## API Endpoints

### 1. OptOut

POST `/optout`
Saves a personal ID number as opted out.

- Body: Raw text containing the personal ID number (e.g., "123-456-789").
- Response: 200 OK if successful.

Example:

`curl -X POST -d "123-456-789" http://localhost:8080/optout`

### 2. IsEligible

GET `/iseligible`
Checks if a personal ID number is eligible.

- Query Parameter: id (e.g., ?id=123-456-789).
- Response: true (eligible) or false (not eligible).

Example:

`curl -X GET "http://localhost:8080/iseligible?id=123-456-789"`

## SQLite Database

The SQLite database is stored at src/main/resources/db/optout.db. You can inspect or manually query the database using the SQLite CLI:

### 1. Open the Database

`sqlite3 src/main/resources/db/optout.db`

### 2. View Tables

`.tables`

### 3. Check Opt-Out Records

`SELECT * FROM opt_out;`

### 4. Check Eligibility Queries

`SELECT * FROM eligibility_queries;`

## Future Enhancements

- Add authentication for endpoints.
