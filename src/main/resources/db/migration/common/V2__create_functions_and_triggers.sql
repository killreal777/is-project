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
    total_space INT;
    total_resources INT;
BEGIN
    SELECT COALESCE(SUM(smb.capacity), 0) INTO total_space FROM storage_module sm
    JOIN storage_module_blueprint smb ON sm.blueprint_id = smb.id;
    SELECT COALESCE(SUM(sr.amount), 0) INTO total_resources FROM stored_resource sr;
    RETURN total_space - total_resources;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION get_free_space_in_storage(storage_module_id INT)
RETURNS INT AS $$
DECLARE
    free_space INT;
BEGIN
    SELECT smb.capacity - COALESCE(SUM(sr.amount), 0) INTO free_space
    FROM storage_module sm
    JOIN storage_module_blueprint smb ON sm.id = storage_module_id AND sm.blueprint_id = smb.build_cost_id
    LEFT JOIN stored_resource sr ON sr.storage_id = storage_module_id
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