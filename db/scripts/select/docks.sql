-- Dock Modules
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

-- Docking Spots
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