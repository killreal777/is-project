-- Built Modules
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

-- Module build cost (by module name)
SELECT
    r.name AS resource_name,
    bci.amount AS resource_amount
FROM production_module_blueprint pmb
    JOIN build_cost bc ON pmb.name = 'Quantum Tube Production' AND bc.id = pmb.build_cost_id
    JOiN build_cost_item bci ON bci.build_cost_id = bc.id
    JOIN resource r ON r.id = bci.resource_id
GROUP BY r.id, r.name, bci.amount;