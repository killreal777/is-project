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
    size VARCHAR(1) CHECK (size IN ('S', 'M', 'L')) NOT NULL
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
    build_cost_id INT REFERENCES build_cost (id) ON DELETE RESTRICT,
    name VARCHAR(48) UNIQUE NOT NULL,
    capacity INT NOT NULL CHECK (capacity > 0)
);

CREATE TABLE IF NOT EXISTS production_module_blueprint (
    id SERIAL PRIMARY KEY,
    build_cost_id INT REFERENCES build_cost (id) ON DELETE RESTRICT,
    name VARCHAR(48) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS dock_module_blueprint (
    id SERIAL PRIMARY KEY,
    build_cost_id INT REFERENCES build_cost (id) ON DELETE RESTRICT,
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
    size VARCHAR(1) CHECK (size IN ('S', 'M', 'L')) NOT NULL,
    is_occupied BOOLEAN NOT NULL DEFAULT FALSE,
    spaceship_id INT UNIQUE REFERENCES spaceship(id) ON DELETE RESTRICT
);


CREATE INDEX idx_docking_spot_spaceship_id ON docking_spot USING HASH (spaceship_id);

CREATE INDEX idx_spaceship_pilot_id ON spaceship USING HASH (pilot_id);


CREATE OR REPLACE FUNCTION prevent_updates()
RETURNS TRIGGER AS $$
BEGIN
    RAISE EXCEPTION 'Updates are not allowed on this table: %', TG_TABLE_NAME;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER no_updates_trade
BEFORE UPDATE ON trade
FOR EACH ROW
EXECUTE FUNCTION prevent_updates();

CREATE TRIGGER no_updates_trade_item
BEFORE UPDATE ON trade_item
FOR EACH ROW
EXECUTE FUNCTION prevent_updates();

CREATE TRIGGER no_updates_build_cost
BEFORE UPDATE ON build_cost
FOR EACH ROW
EXECUTE FUNCTION prevent_updates();


CREATE TRIGGER no_updates_build_cost_item
BEFORE UPDATE ON build_cost_item
FOR EACH ROW
EXECUTE FUNCTION prevent_updates();

CREATE TRIGGER no_updates_storage_module_blueprint
BEFORE UPDATE ON storage_module_blueprint
FOR EACH ROW
EXECUTE FUNCTION prevent_updates();

CREATE TRIGGER no_updates_production_module_blueprint
BEFORE UPDATE ON production_module_blueprint
FOR EACH ROW
EXECUTE FUNCTION prevent_updates();

CREATE TRIGGER no_updates_dock_module_blueprint
BEFORE UPDATE ON dock_module_blueprint
FOR EACH ROW
EXECUTE FUNCTION prevent_updates();

CREATE TRIGGER no_updates_production
BEFORE UPDATE ON production
FOR EACH ROW
EXECUTE FUNCTION prevent_updates();

CREATE TRIGGER no_updates_consumption
BEFORE UPDATE ON consumption
FOR EACH ROW
EXECUTE FUNCTION prevent_updates();


CREATE OR REPLACE FUNCTION create_trade_policy()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO trade_policy (resource_id)
    VALUES (NEW.id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trade_policy_auto_create
AFTER INSERT ON resource
FOR EACH ROW
EXECUTE FUNCTION create_trade_policy();


CREATE OR REPLACE FUNCTION create_docking_spots()
RETURNS TRIGGER AS $$
DECLARE
    s_count INT;
    m_count INT;
    l_count INT;
BEGIN
    SELECT s_docks_quantity, m_docks_quantity, l_docks_quantity
    INTO s_count, m_count, l_count
    FROM dock_module_blueprint
    WHERE id = NEW.blueprint_id;

    FOR i IN 1..s_count LOOP
        INSERT INTO docking_spot (dock_module_id, size)
        VALUES (NEW.id, 'S');
    END LOOP;

    FOR i IN 1..m_count LOOP
        INSERT INTO docking_spot (dock_module_id, size)
        VALUES (NEW.id, 'M');
    END LOOP;

    FOR i IN 1..l_count LOOP
        INSERT INTO docking_spot (dock_module_id, size)
        VALUES (NEW.id, 'L');
    END LOOP;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER docking_spots_auto_create
AFTER INSERT ON dock_module
FOR EACH ROW
EXECUTE FUNCTION create_docking_spots();


CREATE OR REPLACE FUNCTION get_total_free_space_in_storages()
RETURNS INT AS $$
DECLARE
    total_free_space INT;
BEGIN
    SELECT SUM(smb.capacity) - COALESCE(SUM(sr.amount), 0) INTO total_free_space
    FROM storage_module sm
             LEFT JOIN stored_resource sr ON sm.id = sr.storage_id
             JOIN storage_module_blueprint smb ON sm.blueprint_id = smb.build_cost_id;
    RETURN total_free_space;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_free_space_in_storage(storage_module_id INT)
RETURNS INT AS $$
DECLARE
    free_space INT;
BEGIN
    SELECT smb.capacity - COALESCE(SUM(sr.amount), 0)INTO free_space
    FROM storage_module sm
    JOIN storage_module_blueprint smb ON sm.id = storage_module_id AND sm.blueprint_id = smb.build_cost_id
    JOIN stored_resource sr ON sr.storage_id = storage_module_id
    GROUP BY smb.capacity;
    RETURN free_space;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION prevent_storage_overflow()
RETURNS TRIGGER AS $$
DECLARE
    current_free_space INT;
    current_amount INT;
BEGIN
    SELECT amount INTO current_amount
    FROM stored_resource sr
    WHERE sr.storage_id = NEW.storage_id AND sr.resource_id = NEW.resource_id;

    SELECT get_free_space_in_storage(NEW.storage_id) INTO current_free_space;

    IF NEW.amount > COALESCE(current_amount, 0) THEN
        IF current_free_space < (NEW.amount - COALESCE(current_amount, 0)) THEN
            RAISE EXCEPTION 'Not enough space in the storage (ID: %). Free space: %, required: %',
                NEW.storage_id, current_free_space, NEW.amount - COALESCE(current_amount, 0);
        END IF;
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER check_storage_capacity
BEFORE INSERT OR UPDATE ON stored_resource
FOR EACH ROW
EXECUTE FUNCTION prevent_storage_overflow();


CREATE OR REPLACE FUNCTION check_build_cost_unique_constraint()
RETURNS TRIGGER AS $$
BEGIN
    IF EXISTS (
        SELECT 1
        FROM storage_module_blueprint
        WHERE build_cost_id = NEW.build_cost_id
    ) OR EXISTS (
        SELECT 1
        FROM production_module_blueprint
        WHERE build_cost_id = NEW.build_cost_id
    ) OR EXISTS (
        SELECT 1
        FROM dock_module_blueprint
        WHERE build_cost_id = NEW.build_cost_id
    ) THEN
        RAISE EXCEPTION 'build_cost_id % is already used in another module blueprint', NEW.build_cost_id;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER storage_module_build_cost_check
BEFORE INSERT OR UPDATE ON storage_module_blueprint
FOR EACH ROW EXECUTE FUNCTION check_build_cost_unique_constraint();

CREATE TRIGGER production_module_build_cost_check
BEFORE INSERT OR UPDATE ON production_module_blueprint
FOR EACH ROW EXECUTE FUNCTION check_build_cost_unique_constraint();

CREATE TRIGGER dock_module_build_cost_check
BEFORE INSERT OR UPDATE ON dock_module_blueprint
FOR EACH ROW EXECUTE FUNCTION check_build_cost_unique_constraint();
