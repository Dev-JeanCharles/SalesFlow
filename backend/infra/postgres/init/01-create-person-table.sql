CREATE TABLE IF NOT EXISTS person (
    person_id VARCHAR(8) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    tax_identifier VARCHAR(20) NOT NULL,
    person_status VARCHAR(20) NOT NULL,
    created TIMESTAMPTZ NOT NULL,
    birth_date DATE
    );

CREATE TABLE IF NOT EXISTS plans (
    plan_id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL,
    monthly_price NUMERIC(10,2) NOT NULL,
    created TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL,
    description TEXT
    );
