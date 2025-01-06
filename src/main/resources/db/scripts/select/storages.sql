-- Resources Total
SELECT
    r.name AS resource_name,
    SUM(sr.amount) AS total_amount
FROM stored_resource sr
JOIN resource r ON sr.resource_id = r.id
GROUP BY r.name
HAVING SUM(sr.amount) > 0
ORDER BY r.name;

-- Storages Space
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

-- Storage Resources
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