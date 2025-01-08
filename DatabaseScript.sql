CREATE DATABASE sharaf_dg;

USE sharaf_dg;

CREATE TABLE charge_rates (
    charge_code VARCHAR(10) PRIMARY KEY,
    charge_rate DECIMAL(5,2) NOT NULL
);

INSERT INTO charge_rates (charge_code, charge_rate) VALUES
    ('RATE1', 10.00),
    ('RATE2', 5.00),
    ('RATE3', 0.00),
    ('RATE4', 7.50),
    ('RATE5', 12.50);