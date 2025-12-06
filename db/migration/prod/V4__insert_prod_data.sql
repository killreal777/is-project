-- STORAGE MODULE
INSERT INTO storage_module (id, blueprint_id) VALUES
(1, 1); -- Storage S

SELECT setval('storage_module_id_seq', (SELECT MAX(id) FROM storage_module));


-- DOCK MODULE
INSERT INTO dock_module (id, blueprint_id) VALUES
(1, 1);  -- Dock Area 1M6S

SELECT setval('dock_module_id_seq', (SELECT MAX(id) FROM dock_module));