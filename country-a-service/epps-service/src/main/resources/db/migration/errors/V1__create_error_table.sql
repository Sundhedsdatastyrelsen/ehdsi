CREATE TABLE errors (
    cda_id_hash TEXT PRIMARY KEY,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    request_body TEXT,
    error_message TEXT
);
