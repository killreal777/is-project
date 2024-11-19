-- Users Details
SELECT
    id AS user_id,
    username,
    role,
    enabled
FROM users
ORDER BY
    CASE
        WHEN role = 'ROLE_OWNER' THEN 0
        WHEN role = 'ROLE_MANAGER' THEN 1
        WHEN role = 'ROLE_ENGINEER' THEN 2
        WHEN role = 'ROLE_PILOT' THEN 3
    END,
    username;

-- Engineers Details
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

-- Pilots Details
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