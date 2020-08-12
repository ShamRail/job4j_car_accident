CREATE TABLE IF NOT EXISTS authorities (
  id SERIAL PRIMARY KEY,
  authority VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(100) NOT NULL,
  enabled boolean default true,
  authority_id int not null references authorities(id)
);

