CREATE TABLE undo_dispensation (
    cda_id_hash TEXT PRIMARY KEY,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    order_id INTEGER NOT NULL,
    effectuation_id INTEGER NOT NULL
);
