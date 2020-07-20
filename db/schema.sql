CREATE TABLE IF NOT EXISTS acc_type(
    id SERIAL PRIMARY KEY,
    name VARCHAR(511)
);

CREATE TABLE IF NOT EXISTS acc_rule(
    id SERIAL PRIMARY KEY,
    name VARCHAR(511)
);

CREATE TABLE IF NOT EXISTS accident(
    id SERIAL PRIMARY KEY,
    name VARCHAR(511),
    description TEXT,
    address VARCHAR(511),
    type_id INT REFERENCES acc_type(id)
);

CREATE TABLE IF NOT EXISTS rule_acc(
    id SERIAL PRIMARY KEY,
    accident_id INT REFERENCES accident(id),
    rule_id INT REFERENCES acc_rule(id)
);