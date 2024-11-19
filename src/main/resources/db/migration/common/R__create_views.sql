CREATE OR REPLACE VIEW engineers_details AS
SELECT
    u.id AS engineer_id,
    u.username AS engineer_name,
    u.enabled AS engineer_enabled,
    pm.id AS module_id,
    pmb.name AS module_name,
    pm.state AS module_state
FROM users u
LEFT JOIN production_module pm ON pm.engineer_id = u.id
LEFT JOIN production_module_blueprint pmb ON pmb.id = pm.blueprint_id
WHERE u.role = 'ROLE_ENGINEER'
ORDER BY
    CASE
        WHEN pm.state = 'MANUFACTURING' THEN 0
        WHEN pm.state = 'READY' THEN 1
        WHEN pm.state = 'OFF' THEN 2
        WHEN pm.state IS NULL THEN 3
        WHEN u.enabled IS FALSE then 4
    END,
    pmb.name,
    u.username;


CREATE OR REPLACE VIEW pilots_details AS
SELECT
    u.id AS pilot_id,
    u.username AS pilot_name,
    u.enabled AS pilot_enabled,
    a.balance AS balance,
    s.id AS spaceship_id,
    s.size AS spaceship_size,
    ds.id AS docking_spot_id,
    dm.id AS dock_module_id,
    dmb.name AS dock_module_name
FROM users u
LEFT JOIN spaceship s ON u.id = s.pilot_id
LEFT JOIN account a ON u.id = a.user_id
LEFT JOIN docking_spot ds ON s.id = ds.spaceship_id
LEFT JOIN dock_module dm ON ds.dock_module_id = dm.id
LEFT JOIN dock_module_blueprint dmb ON dmb.id = dm.blueprint_id
WHERE u.role = 'ROLE_PILOT';


CREATE OR REPLACE VIEW built_modules_view AS
    SELECT
        dmb.name AS module_name,
        'Dock' AS module_type,
        COUNT(1) AS count
    FROM dock_module_blueprint dmb
    JOIN dock_module dm ON dmb.id = dm.blueprint_id
    GROUP BY dmb.name

    UNION ALL

    SELECT
        smb.name AS module_name,
        'Storage' AS module_type,
        COUNT(1) AS count
    FROM storage_module_blueprint smb
    JOIN storage_module sm ON smb.build_cost_id = sm.blueprint_id
    GROUP BY smb.name

    UNION ALL

    SELECT
        pmb.name AS module_name,
        'Production' AS module_type,
        COUNT(1) AS count
    FROM production_module_blueprint pmb
    JOIN production_module pm ON pmb.id = pm.blueprint_id
    GROUP BY pmb.name

ORDER BY
    module_type,
    module_name;


CREATE OR REPLACE VIEW production_modules_view AS
SELECT
    pm.id AS module_id,
    pmb.name AS module_name,
    pm.state AS module_state,
    u.username AS engineer_name
FROM production_module pm
JOIN production_module_blueprint pmb ON pm.blueprint_id = pmb.id
LEFT JOIN users u ON pm.engineer_id = u.id
ORDER BY
    CASE
        WHEN pm.state = 'MANUFACTURING' THEN 0
        WHEN pm.state = 'READY' THEN 1
        WHEN pm.state = 'OFF' THEN 2
        WHEN u.username IS NULL THEN 3
    END,
    pmb.name;


CREATE OR REPLACE VIEW consumption_view AS
SELECT
    pm.id AS module_id,
    pmb.name AS module_name,
    r.name AS resource_name,
    c.amount AS amount
FROM production_module pm
JOIN production_module_blueprint pmb ON pm.blueprint_id = pmb.id
JOIN consumption c ON c.blueprint_id = pmb.id
JOIN resource r ON c.resource_id = r.id;


CREATE OR REPLACE VIEW production_view AS
SELECT
    pm.id AS module_id,
    pmb.name AS module_name,
    r.name AS resource_name,
    p.amount AS amount
FROM production_module pm
JOIN production_module_blueprint pmb ON pm.blueprint_id = pmb.id
JOIN production p ON p.blueprint_id = pmb.id
JOIN resource r ON p.resource_id = r.id;


CREATE OR REPLACE VIEW dock_modules_view AS
SELECT
    dm.id AS module_id,
    dmb.name AS module_name,
    COUNT(ds.id) FILTER (WHERE ds.size = 'S') AS s_quantity,
    COUNT(ds.id) FILTER (WHERE ds.size = 'S' AND NOT ds.is_occupied) AS s_free,
    COUNT(ds.id) FILTER (WHERE ds.size = 'M') AS m_quantity,
    COUNT(ds.id) FILTER (WHERE ds.size = 'M' AND NOT ds.is_occupied) AS m_free,
    COUNT(ds.id) FILTER (WHERE ds.size = 'L') AS l_quantity,
    COUNT(ds.id) FILTER (WHERE ds.size = 'L' AND NOT ds.is_occupied) AS l_free
FROM dock_module dm
JOIN dock_module_blueprint dmb ON dmb.id = dm.blueprint_id
JOIN docking_spot ds ON ds.dock_module_id = dm.id
GROUP BY dm.id, dmb.name
ORDER BY module_name, module_id;


CREATE OR REPLACE VIEW docking_spots_view AS
SELECT
    ds.id AS docking_spot_id,
    dm.id AS module_id,
    dmb.name AS module_name,
    ds.size AS size,
    ds.is_occupied AS is_occupied,
    sp.id AS spaceship_id,
    u.id AS pilot_id,
    u.username AS pilot_name
FROM docking_spot ds
JOIN dock_module dm ON ds.dock_module_id = dm.id
JOIN dock_module_blueprint dmb ON dm.blueprint_id = dmb.id
LEFT JOIN spaceship sp ON ds.spaceship_id = sp.id
LEFT JOIN users u ON sp.pilot_id = u.id
ORDER BY
    module_name,
    module_id,
    size,
    is_occupied;


CREATE OR REPLACE VIEW resources_view AS
SELECT
    r.name AS resource_name,
    SUM(sr.amount) AS total_amount
FROM stored_resource sr
JOIN resource r ON sr.resource_id = r.id
GROUP BY r.name
HAVING SUM(sr.amount) > 0
ORDER BY r.name;


CREATE OR REPLACE VIEW storage_space_view AS
SELECT
    sm.id AS storage_id,
    smb.name AS module_name,
    smb.capacity AS capacity,
    COALESCE(SUM(sr.amount), 0) AS used_space,
    smb.capacity - COALESCE(SUM(sr.amount), 0) AS free_space
FROM storage_module sm
JOIN storage_module_blueprint smb ON sm.blueprint_id = smb.build_cost_id
LEFT JOIN stored_resource sr ON sm.id = sr.storage_id
GROUP BY sm.id, smb.name, smb.capacity
ORDER BY
    module_name,
    storage_id,
    used_space;


CREATE OR REPLACE VIEW storage_resources_view AS
SELECT
    sm.id AS storage_id,
    smb.name AS module_name,
    r.name AS resource_name,
    sr.amount AS resource_amount
FROM storage_module sm
JOIN storage_module_blueprint smb ON sm.blueprint_id = smb.id
JOIN stored_resource sr ON sm.id = sr.storage_id
JOIN resource r ON sr.resource_id = r.id
WHERE sr.amount > 0
ORDER BY
    module_name,
    storage_id,
    resource_name;


CREATE OR REPLACE VIEW trade_policy_view AS
SELECT
    r.id AS resource_id,
    r.name AS resource_name,
    tp.station_sells,
    tp.sell_price,
    tp.sell_limit,
    tp.station_buys,
    tp.purchase_price,
    tp.purchase_limit
FROM trade_policy tp
JOIN resource r ON tp.resource_id = r.id
ORDER BY
    tp.station_sells DESC,
    tp.station_buys DESC,
    r.name;


CREATE OR REPLACE VIEW trades_view AS
SELECT
    t.id AS trade_id,
    t.user_id,
    u.username AS pilot_name,
    SUM(CASE
        WHEN ti.operation = 'BUY' THEN ti.amount * ti.price
        ELSE 0
    END) - SUM(CASE
        WHEN ti.operation = 'SELL' THEN ti.amount * ti.price
        ELSE 0
    END) AS income,
    t.time
FROM trade t
JOIN trade_item ti ON t.id = ti.trade_id
JOIN users u ON t.user_id = u.id
GROUP BY t.id, t.user_id, t.time, u.username
ORDER BY t.time DESC;


CREATE OR REPLACE VIEW trade_items_view AS
SELECT
    ti.trade_id,
    r.name AS resource_name,
    ti.operation,
    ti.amount,
    ti.price,
    CASE
        WHEN ti.operation = 'SELL' THEN - (ti.amount * ti.price)
        WHEN ti.operation = 'BUY' THEN ti.amount * ti.price
    END AS income,
    t.time AS trade_time
FROM trade_item ti
JOIN trade t ON ti.trade_id = t.id
JOIN resource r ON r.id = ti.resource_id
ORDER BY t.time DESC;


CREATE OR REPLACE VIEW sell_offers_view AS
SELECT
    r.name AS resource_name,
    tp.sell_price,
    GREATEST(COALESCE(SUM(sr.amount), 0) - tp.sell_limit, 0) AS sell_amount
FROM trade_policy tp
JOIN resource r ON tp.resource_id = r.id
JOIN stored_resource sr ON sr.resource_id = r.id
WHERE tp.station_sells = TRUE
GROUP BY r.name, tp.sell_price, tp.sell_limit
HAVING GREATEST(COALESCE(SUM(sr.amount), 0) - tp.sell_limit, 0) > 0;


CREATE OR REPLACE VIEW purchase_offers_view AS
SELECT
    r.name AS resource_name,
    tp.purchase_price,
    GREATEST(COALESCE(SUM(sr.amount), 0) - tp.purchase_limit, 0) AS purchase_amount
FROM trade_policy tp
JOIN resource r ON tp.resource_id = r.id
JOIN stored_resource sr ON sr.resource_id = r.id
WHERE tp.station_sells = TRUE
GROUP BY r.name, tp.purchase_price, tp.purchase_limit
HAVING GREATEST(COALESCE(SUM(sr.amount), 0) - tp.purchase_limit, 0) > 0;


CREATE OR REPLACE VIEW trade_offers_view AS
SELECT *
FROM (
    SELECT
        sov.resource_name AS resource_name,
        'FOR SALE' AS trade_type,
        sov.sell_price AS price,
        sov.sell_amount AS amount
    FROM sell_offers_view sov
    UNION ALL
    SELECT
        pov.resource_name AS resource_name,
        'FOR PURCHASE' AS trade_type,
        pov.purchase_price AS price,
        pov.purchase_amount AS amount
    FROM purchase_offers_view pov
) AS o
ORDER BY
    trade_type DESC,
    price * amount DESC;