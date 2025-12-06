CREATE TABLE IF NOT EXISTS resource (
    id SERIAL PRIMARY KEY,
    name VARCHAR(32) UNIQUE NOT NULL
);


CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(32) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    role VARCHAR(16) CHECK (role IN ('ROLE_OWNER', 'ROLE_MANAGER', 'ROLE_ENGINEER', 'ROLE_PILOT')) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS account (
    user_id INT PRIMARY KEY REFERENCES users(id) ON DELETE RESTRICT,
    balance INT NOT NULL DEFAULT 0 CHECK (balance >= 0)
);

CREATE TABLE IF NOT EXISTS spaceship (
    id SERIAL PRIMARY KEY,
    pilot_id INT UNIQUE NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    size CHAR(1) CHECK (size IN ('S', 'M', 'L')) NOT NULL
);


CREATE TABLE IF NOT EXISTS trade_policy (
    resource_id INT PRIMARY KEY REFERENCES resource(id) ON DELETE RESTRICT,
    station_sells BOOLEAN NOT NULL DEFAULT FALSE,
    sell_price INT NOT NULL DEFAULT 0  CHECK (sell_price >= 0),
    sell_limit INT CHECK (sell_limit >= 0),
    station_buys BOOLEAN NOT NULL DEFAULT FALSE,
    purchase_price INT NOT NULL DEFAULT 0 CHECK (purchase_price >= 0),
    purchase_limit INT CHECK (purchase_limit >= 0)
);

CREATE TABLE IF NOT EXISTS trade (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
    time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS trade_item (
    trade_id INT NOT NULL,
    resource_id INT NOT NULL,
    operation VARCHAR(4) CHECK (operation IN ('BUY', 'SELL')) NOT NULL ,
    amount INT NOT NULL CHECK (amount > 0),
    price INT NOT NULL,
    PRIMARY KEY (trade_id, resource_id),
    FOREIGN KEY (trade_id) REFERENCES trade (id) ON DELETE CASCADE,
    FOREIGN KEY (resource_id) REFERENCES resource(id) ON DELETE RESTRICT
);


CREATE TABLE IF NOT EXISTS build_cost (
    id SERIAL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS build_cost_item (
    build_cost_id INT NOT NULL,
    resource_id INT NOT NULL,
    amount INT NOT NULL CHECK (amount > 0),
    PRIMARY KEY (build_cost_id, resource_id),
    FOREIGN KEY (build_cost_id) REFERENCES build_cost (id) ON DELETE CASCADE,
    FOREIGN KEY (resource_id) REFERENCES resource(id) ON DELETE RESTRICT
);


CREATE TABLE IF NOT EXISTS storage_module_blueprint (
    id SERIAL PRIMARY KEY,
    build_cost_id INT NOT NULL REFERENCES build_cost (id) ON DELETE RESTRICT,
    name VARCHAR(48) UNIQUE NOT NULL,
    capacity INT NOT NULL CHECK (capacity > 0)
);

CREATE TABLE IF NOT EXISTS production_module_blueprint (
    id SERIAL PRIMARY KEY,
    build_cost_id INT NOT NULL REFERENCES build_cost (id) ON DELETE RESTRICT,
    name VARCHAR(48) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS dock_module_blueprint (
    id SERIAL PRIMARY KEY,
    build_cost_id INT NOT NULL REFERENCES build_cost (id) ON DELETE RESTRICT,
    name VARCHAR(48) UNIQUE NOT NULL,
    s_docks_quantity INT NOT NULL CHECK (s_docks_quantity >= 0),
    m_docks_quantity INT NOT NULL CHECK (m_docks_quantity >= 0),
    l_docks_quantity INT NOT NULL CHECK (l_docks_quantity >= 0),
    CHECK (s_docks_quantity + m_docks_quantity + l_docks_quantity > 0)
);


CREATE TABLE IF NOT EXISTS storage_module (
    id SERIAL PRIMARY KEY,
    blueprint_id INT NOT NULL REFERENCES storage_module_blueprint(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS stored_resource (
    storage_id INT NOT NULL,
    resource_id INT NOT NULL,
    amount INT NOT NULL CHECK (amount >= 0),
    PRIMARY KEY (storage_id, resource_id),
    FOREIGN KEY (storage_id) REFERENCES storage_module(id) ON DELETE CASCADE,
    FOREIGN KEY (resource_id) REFERENCES resource(id) ON DELETE RESTRICT
);


CREATE TABLE IF NOT EXISTS production_module (
    id SERIAL PRIMARY KEY,
    blueprint_id INT NOT NULL REFERENCES production_module_blueprint(id) ON DELETE RESTRICT,
    state VARCHAR(16) CHECK (state IN ('OFF', 'READY', 'MANUFACTURING')) NOT NULL DEFAULT 'OFF',
    engineer_id INT REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS production (
    blueprint_id INT NOT NULL,
    resource_id INT NOT NULL,
    amount INT NOT NULL CHECK (amount > 0),
    PRIMARY KEY (blueprint_id, resource_id),
    FOREIGN KEY (blueprint_id) REFERENCES production_module_blueprint(id) ON DELETE CASCADE,
    FOREIGN KEY (resource_id) REFERENCES resource(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS consumption (
    blueprint_id INT NOT NULL,
    resource_id INT NOT NULL,
    amount INT NOT NULL CHECK (amount > 0),
    PRIMARY KEY (blueprint_id, resource_id),
    FOREIGN KEY (blueprint_id) REFERENCES production_module_blueprint(id) ON DELETE CASCADE,
    FOREIGN KEY (resource_id) REFERENCES resource(id) ON DELETE RESTRICT
);


CREATE TABLE IF NOT EXISTS dock_module (
    id SERIAL PRIMARY KEY,
    blueprint_id INT NOT NULL REFERENCES dock_module_blueprint(id) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS docking_spot (
    id SERIAL PRIMARY KEY,
    dock_module_id INT NOT NULL REFERENCES dock_module(id) ON DELETE CASCADE,
    size CHAR(1) CHECK (size IN ('S', 'M', 'L')) NOT NULL,
    is_occupied BOOLEAN NOT NULL DEFAULT FALSE,
    spaceship_id INT UNIQUE REFERENCES spaceship(id) ON DELETE RESTRICT
);