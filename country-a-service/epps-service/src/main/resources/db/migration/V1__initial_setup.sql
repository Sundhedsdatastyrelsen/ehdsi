CREATE TABLE dispensation_fmk_ids (
    cda_id_hash TEXT PRIMARY KEY,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    order_id TEXT NOT NULL,
    effectuation_id TEXT NOT NULL
);
