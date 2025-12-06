-- Production Modules
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

-- Consumption
SELECT
    pm.id AS module_id,
    pmb.name AS module_name,
    r.name AS resource_name,
    c.amount AS amount
FROM production_module pm
JOIN production_module_blueprint pmb ON pm.blueprint_id = pmb.id
JOIN consumption c ON c.blueprint_id = pmb.id
JOIN resource r ON c.resource_id = r.id;

-- Production
SELECT
    pm.id AS module_id,
    pmb.name AS module_name,
    r.name AS resource_name,
    p.amount AS amount
FROM production_module pm
JOIN production_module_blueprint pmb ON pm.blueprint_id = pmb.id
JOIN production p ON p.blueprint_id = pmb.id
JOIN resource r ON p.resource_id = r.id;

-- Consumption (by production module name)
SELECT
    r.name AS resource_name,
    c.amount AS resource_amount
FROM production_module_blueprint pmb
JOIN consumption c ON pmb.name = 'Quantum Tube Production' AND c.blueprint_id = pmb.id
JOIN resource r ON r.id = c.resource_id
GROUP BY r.id, r.name, c.amount;

-- Production (by production module name)
SELECT
    r.name AS resource_name,
    p.amount AS resource_amount
FROM production_module_blueprint pmb
JOIN production p ON pmb.name = 'Quantum Tube Production' AND p.blueprint_id = pmb.id
JOIN resource r ON r.id = p.resource_id;

-- Consumption and Production (by production module name)
SELECT *
FROM (
    SELECT
        'Consumption' AS type,
        r.name AS resource_name,
        c.amount AS resource_amount
    FROM production_module_blueprint pmb
    JOIN consumption c ON pmb.name = 'Quantum Tube Production' AND c.blueprint_id = pmb.id
    JOIN resource r ON r.id = c.resource_id
    GROUP BY r.id, r.name, c.amount
) as c
UNION ALL
SELECT * FROM (
    SELECT
        'Production' AS type,
        r.name AS resource_name,
        p.amount AS resource_amount
    FROM production_module_blueprint pmb
    JOIN production p ON pmb.name = 'Quantum Tube Production' AND p.blueprint_id = pmb.id
    JOIN resource r ON r.id = p.resource_id
) as p
ORDER BY type DESC;