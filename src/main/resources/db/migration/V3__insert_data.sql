-- RESOURCES

INSERT INTO resource (id, name) VALUES
(1, 'Advanced Composites'),
(2, 'Advanced Electronics'),
(3, 'Antimatter Cells'),
(4, 'Antimatter Converter'),
(5, 'Antimatter Converters'),
(6, 'Bio-Optic Wiring'),
(7, 'Carbon Filters'),
(8, 'Claytronics'),
(9, 'Computing Substrate'),
(10, 'Coolant Fluid'),
(11, 'Drone Components'),
(12, 'Drones'),
(13, 'Electromagnetic Converters'),
(14, 'Energy Cells'),
(15, 'Engine Parts'),
(16, 'Explosives'),
(17, 'Field Coils'),
(18, 'Food Rations'),
(19, 'Fusion Reactors'),
(20, 'Graphene'),
(21, 'Helium'),
(22, 'Hull Parts'),
(23, 'Hydrogen'),
(24, 'Ice'),
(25, 'Insulating Membranes'),
(26, 'Laser Towers'),
(27, 'Luxury Goods'),
(28, 'Luxury Rations'),
(29, 'Maja Dust'),
(30, 'Maja Snails'),
(31, 'Matrix Elements'),
(32, 'Meat'),
(33, 'Medical Supplies'),
(34, 'Metallic Microlattice'),
(35, 'Methane'),
(36, 'Microchips'),
(37, 'Microorganisms'),
(38, 'Missile Components'),
(39, 'Missiles'),
(40, 'Nanotubes'),
(41, 'Neon'),
(42, 'Nividium'),
(43, 'Nostrop Oil'),
(44, 'Ore'),
(45, 'Plankton'),
(46, 'Plasma Conductors'),
(47, 'Plasma Screens'),
(48, 'Plastic Waste'),
(49, 'Protein Paste'),
(50, 'Proton Launchers'),
(51, 'Quantum Tubes'),
(52, 'Refined Metals'),
(53, 'Scanning Arrays'),
(54, 'Scrap Metal'),
(55, 'Shield Components'),
(56, 'Silicon Carbide'),
(57, 'Silicon Wafers'),
(58, 'Smart Chips'),
(59, 'Soja Beans'),
(60, 'Soja Husks'),
(61, 'Space Fuel'),
(62, 'Space Weed'),
(63, 'Spices'),
(64, 'Sunrise Flowers'),
(65, 'Superfluid Coolant'),
(66, 'Swamp Plant'),
(67, 'Swamp Plants'),
(68, 'Teladianium'),
(69, 'Turret Components'),
(70, 'Turrets'),
(71, 'Water'),
(72, 'Weapon Components'),
(73, 'Wheat');



-- STORAGE MODULES BLUEPRINTS

INSERT INTO build_cost (id) VALUES
(1),
(2),
(3);

INSERT INTO storage_module_blueprint (id, build_cost_id, name, capacity) VALUES
(1, 1, 'Storage S', 25000),
(2, 2, 'Storage M', 100000),
(3, 3, 'Storage L', 1000000);

-- Storage S
INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(1, 8, 52),   -- Claytronics
(1, 14, 103), -- Energy Cells
(1, 22, 189); -- Hull Parts

-- Storage M
INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(2, 8, 82),   -- Claytronics
(2, 14, 163), -- Energy Cells
(2, 22, 299); -- Hull Parts

-- Storage L
INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(3, 8, 115),  -- Claytronics
(3, 14, 230), -- Energy Cells
(3, 22, 421); -- Hull Parts



-- DOCK MODULES BLUEPRINTS

INSERT INTO build_cost (id) VALUES
(4),
(5),
(6),
(7),
(8),
(9);

INSERT INTO dock_module_blueprint (id, build_cost_id, name, s_docks_quantity, m_docks_quantity, l_docks_quantity) VALUES
(1, 4, 'Dock Area 1M6S', 6, 1, 0),
(2, 5, 'Dock Area 3M6S', 6, 3, 0),
(3, 6, 'Dock Area 8M', 0, 8, 0),
(4, 7, 'Pier 1L', 0, 0, 1),
(5, 8, 'Pier 3L-E', 0, 0, 3),
(6, 9, 'Pier 3L-T', 0, 0, 3);

-- Dock Area 1M6S
INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(4, 8, 54),   -- Claytronics
(4, 14, 108), -- Energy Cells
(4, 22, 198); -- Hull Parts

-- Dock Area 3M6S
INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(5, 8, 78),   -- Claytronics
(5, 14, 155), -- Energy Cells
(5, 22, 284); -- Hull Parts

-- Dock Area 8M
INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(6, 8, 116),  -- Claytronics
(6, 14, 232), -- Energy Cells
(6, 22, 424); -- Hull Parts

-- Pier 1L
INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(7, 8, 313),   -- Claytronics
(7, 14, 625),  -- Energy Cells
(7, 22, 1143); -- Hull Parts

-- Pier 3L-E
INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(8, 8, 625),   -- Claytronics
(8, 14, 1250), -- Energy Cells
(8, 22, 2287); -- Hull Parts

-- Pier 3L-T
INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(9, 8, 542),   -- Claytronics
(9, 14, 1083), -- Energy Cells
(9, 22, 1980); -- Hull Parts



-- PRODUCTION MODULES BLUEPRINTS

-- Advanced Composite Production
INSERT INTO build_cost (id) VALUES (10);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(10, 8, 648),   -- Claytronics (resource_id 8)
(10, 14, 1296), -- Energy Cells (resource_id 14)
(10, 22, 2373); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(1, 10, 'Advanced Composite Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(1, 1, 720); -- Advanced Composites (resource_id 1)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(1, 14, 600), -- Energy Cells (resource_id 14)
(1, 20, 960), -- Graphene (resource_id 20)
(1, 52, 960); -- Refined Metals (resource_id 52)


-- Advanced Electronics Production
INSERT INTO build_cost (id) VALUES (11);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(11, 14, 482),  -- Energy Cells (resource_id 14)
(11, 8, 965),   -- Claytronics (resource_id 8)
(11, 22, 1767); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(2, 11, 'Advanced Electronics Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(2, 2, 300);  -- Advanced Electronics (resource_id 2)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(2, 14, 300),  -- Energy Cells (resource_id 14)
(2, 36, 220),  -- Microchips (resource_id 36)
(2, 51, 100);  -- Quantum Tubes (resource_id 51)


-- Antimatter Cell Production
INSERT INTO build_cost (id) VALUES (12);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(12, 14, 69),    -- Energy Cells (resource_id 14)
(12, 8, 138),    -- Claytronics (resource_id 8)
(12, 22, 253);   -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(3, 12, 'Antimatter Cell Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(3, 3, 3300); -- Antimatter Cells (resource_id 3)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(3, 14, 3000),  -- Energy Cells (resource_id 14)
(3, 23, 9600);  -- Hydrogen (resource_id 23)


-- Antimatter Converter Production
INSERT INTO build_cost (id) VALUES (13);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(13, 14, 150),   -- Energy Cells (resource_id 14)
(13, 8, 300),    -- Claytronics (resource_id 8)
(13, 22, 780);   -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(4, 13, 'Antimatter Converter Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(4, 5, 1800); -- Antimatter Converters (resource_id 5)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(4, 1, 240),   -- Advanced Composites (resource_id 1)
(4, 14, 960),  -- Energy Cells (resource_id 14)
(4, 36, 360);  -- Microchips (resource_id 36)


-- Claytronics Production
INSERT INTO build_cost (id) VALUES (14);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(14, 8, 2822),   -- Claytronics (resource_id 8)
(14, 14, 5642),  -- Energy Cells (resource_id 14)
(14, 22, 10327); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(5, 14, 'Claytronics Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(5, 8, 480); -- Claytronics (resource_id 8)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(5, 14, 560),  -- Energy Cells (resource_id 14)
(5, 3, 400),   -- Antimatter Cells (resource_id 3)
(5, 51, 400),  -- Quantum Tubes (resource_id 51)
(5, 36, 640);  -- Microchips (resource_id 36)


-- Drone Component Production
INSERT INTO build_cost (id) VALUES (15);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(15, 8, 1225),   -- Claytronics (resource_id 8)
(15, 14, 2449),  -- Energy Cells (resource_id 14)
(15, 22, 4483);  -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(6, 15, 'Drone Component Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(6, 11, 360); -- Drone Components (resource_id 11)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(6, 14, 180),   -- Energy Cells (resource_id 14)
(6, 15, 60),    -- Engine Parts (resource_id 15)
(6, 22, 60),    -- Hull Parts (resource_id 22)
(6, 36, 60),    -- Microchips (resource_id 36)
(6, 53, 120);   -- Scanning Arrays (resource_id 53)


-- Energy Cell Production
INSERT INTO build_cost (id) VALUES (16);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(16, 8, 260),    -- Claytronics (resource_id 8)
(16, 14, 520),   -- Energy Cells (resource_id 14)
(16, 22, 951);   -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(7, 16, 'Energy Cell Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(7, 14, 12000); -- Energy Cells (resource_id 14)


-- Engine Part Production
INSERT INTO build_cost (id) VALUES (17);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(17, 8, 389),   -- Claytronics (resource_id 8)
(17, 14, 779),  -- Energy Cells (resource_id 14)
(17, 22, 1426); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(8, 17, 'Engine Part Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(8, 15, 480); -- Engine Parts (resource_id 15)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(8, 3, 320),   -- Antimatter Cells (resource_id 3)
(8, 14, 240),  -- Energy Cells (resource_id 14)
(8, 52, 384);  -- Refined Metals (resource_id 52)


-- Field Coil Production
INSERT INTO build_cost (id) VALUES (18);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(18, 8, 1735),  -- Claytronics (resource_id 8)
(18, 14, 3470), -- Energy Cells (resource_id 14)
(18, 22, 6351); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(9, 18, 'Field Coil Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(9, 17, 1200); -- Field Coils (resource_id 17)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(9, 14, 360),  -- Energy Cells (resource_id 14)
(9, 46, 240),  -- Plasma Conductors (resource_id 46)
(9, 51, 258);  -- Quantum Tubes (resource_id 51)


-- Food Ration Production
INSERT INTO build_cost (id) VALUES (19);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(19, 8, 262),    -- Claytronics (resource_id 8)
(19, 14, 525),   -- Energy Cells (resource_id 14)
(19, 22, 961);   -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(10, 19, 'Food Ration Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(10, 18, 4920); -- Food Rations (resource_id 18)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(10, 14, 600),  -- Energy Cells (resource_id 14)
(10, 32, 600),  -- Meat (resource_id 32)
(10, 63, 300),  -- Spices (resource_id 63)
(10, 73, 600);  -- Wheat (resource_id 73)


-- Graphene Production
INSERT INTO build_cost (id) VALUES (20);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(20, 8, 28),     -- Claytronics (resource_id 8)
(20, 14, 57),    -- Energy Cells (resource_id 14)
(20, 22, 104);   -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(11, 20, 'Graphene Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(11, 20, 1650); -- Graphene (resource_id 20)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(11, 14, 1200),  -- Energy Cells (resource_id 14)
(11, 35, 4800);  -- Methane (resource_id 35)


-- Hull Part Production
INSERT INTO build_cost (id) VALUES (21);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(21, 8, 614),    -- Claytronics (resource_id 8)
(21, 14, 1229),  -- Energy Cells (resource_id 14)
(21, 22, 2249);  -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(12, 21, 'Hull Part Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(12, 22, 1320);  -- Hull Parts (resource_id 22)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(12, 14, 320),   -- Energy Cells (resource_id 14)
(12, 20, 160),   -- Graphene (resource_id 20)
(12, 52, 1120);  -- Refined Metals (resource_id 52)


-- Maja Dust Production
INSERT INTO build_cost (id) VALUES (22);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(22, 8, 373),    -- Claytronics (resource_id 8)
(22, 14, 746),   -- Energy Cells (resource_id 14)
(22, 22, 1366);  -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(13, 22, 'Maja Dust Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(13, 29, 480);  -- Maja Dust (resource_id 29)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(13, 14, 240),   -- Energy Cells (resource_id 14)
(13, 30, 720),   -- Maja Snails (resource_id 30)
(13, 63, 360);   -- Spices (resource_id 63)


-- Maja Snail Production
INSERT INTO build_cost (id) VALUES (23);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(23, 8, 248),    -- Claytronics (resource_id 8)
(23, 14, 497),   -- Energy Cells (resource_id 14)
(23, 22, 910);   -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(14, 23, 'Maja Snail Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(14, 30, 1392);  -- Maja Snails (resource_id 30)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(14, 14, 320),   -- Energy Cells (resource_id 14)
(14, 71, 800);   -- Water (resource_id 71)


-- Meat Production
INSERT INTO build_cost (id) VALUES (24);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(24, 8, 248),    -- Claytronics (resource_id 8)
(24, 14, 497),   -- Energy Cells (resource_id 14)
(24, 22, 910);   -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(15, 24, 'Meat Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(15, 32, 1760);  -- Meat (resource_id 32)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(15, 14, 320),   -- Energy Cells (resource_id 14)
(15, 71, 800);   -- Water (resource_id 71)


-- Microchip Production
INSERT INTO build_cost (id) VALUES (25);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(25, 8, 758),    -- Claytronics (resource_id 8)
(25, 14, 1516),  -- Energy Cells (resource_id 14)
(25, 22, 2774);  -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(16, 25, 'Microchip Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(16, 36, 480);  -- Microchips (resource_id 36)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(16, 14, 300),   -- Energy Cells (resource_id 14)
(16, 57, 1200);  -- Silicon Wafers (resource_id 57)


-- Missile Component Production
INSERT INTO build_cost (id) VALUES (26);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(26, 8, 35),     -- Claytronics (resource_id 8)
(26, 14, 71),    -- Energy Cells (resource_id 14)
(26, 22, 131);   -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(17, 26, 'Missile Component Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(17, 38, 1320);  -- Missile Components (resource_id 38)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(17, 1, 8),      -- Advanced Composites (resource_id 1)
(17, 14, 80),    -- Energy Cells (resource_id 14)
(17, 22, 8);     -- Hull Parts (resource_id 22)


-- Nostrop Oil Production
INSERT INTO build_cost (id) VALUES (27);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(27, 8, 289),    -- Claytronics (resource_id 8)
(27, 14, 579),   -- Energy Cells (resource_id 14)
(27, 22, 1063);  -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(18, 27, 'Nostrop Oil Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(18, 43, 3840);  -- Nostrop Oil (resource_id 43)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(18, 14, 480),   -- Energy Cells (resource_id 14)
(18, 63, 140),   -- Spices (resource_id 63)
(18, 64, 480),   -- Sunrise Flowers (resource_id 64)
(18, 71, 480);   -- Water (resource_id 71)


-- Plasma Conductor Production
INSERT INTO build_cost (id) VALUES (28);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(28, 8, 473),   -- Claytronics (resource_id 8)
(28, 14, 946),  -- Energy Cells (resource_id 14)
(28, 22, 1732); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(19, 28, 'Plasma Conductor Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(19, 46, 200);  -- Plasma Conductors (resource_id 46)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(19, 14, 240),  -- Energy Cells (resource_id 14)
(19, 20, 384),  -- Graphene (resource_id 20)
(19, 65, 560);  -- Superfluid Coolant (resource_id 65)


-- Quantum Tube Production
INSERT INTO build_cost (id) VALUES (29);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(29, 8, 380),   -- Claytronics (resource_id 8)
(29, 14, 761),  -- Energy Cells (resource_id 14)
(29, 22, 1394); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(24, 29, 'Quantum Tube Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(24, 51, 550);  -- Quantum Tubes (resource_id 51)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(24, 14, 200),  -- Energy Cells (resource_id 14)
(24, 20, 580),  -- Graphene (resource_id 20)
(24, 65, 150);  -- Superfluid Coolant (resource_id 65)


-- Refined Metal Production
INSERT INTO build_cost (id) VALUES (30);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(30, 8, 36),     -- Claytronics (resource_id 8)
(30, 14, 73),    -- Energy Cells (resource_id 14)
(30, 22, 135);   -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(25, 30, 'Refined Metal Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(25, 52, 2400);  -- Refined Metals (resource_id 52)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(25, 14, 2160),  -- Energy Cells (resource_id 14)
(25, 44, 5760);  -- Ore (resource_id 44)


-- Scanning Array Production
INSERT INTO build_cost (id) VALUES (31);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(31, 8, 629),    -- Claytronics (resource_id 8)
(31, 14, 1259),  -- Energy Cells (resource_id 14)
(31, 22, 2305);  -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(26, 31, 'Scanning Array Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(26, 53, 240);  -- Scanning Arrays (resource_id 53)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(26, 14, 360),   -- Energy Cells (resource_id 14)
(26, 52, 600),   -- Refined Metals (resource_id 52)
(26, 57, 360);   -- Silicon Wafers (resource_id 57)


-- Shield Component Production
INSERT INTO build_cost (id) VALUES (32);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(32, 8, 432),   -- Claytronics (resource_id 8)
(32, 14, 865),  -- Energy Cells (resource_id 14)
(32, 22, 1583); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(27, 32, 'Shield Component Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(27, 55, 660);  -- Shield Components (resource_id 55)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(27, 14, 210),  -- Energy Cells (resource_id 14)
(27, 46, 60),   -- Plasma Conductors (resource_id 46)
(27, 51, 60);   -- Quantum Tubes (resource_id 51)


-- Smart Chip Production
INSERT INTO build_cost (id) VALUES (33);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(33, 8, 126),   -- Claytronics (resource_id 8)
(33, 14, 253),  -- Energy Cells (resource_id 14)
(33, 22, 464);  -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(28, 33, 'Smart Chip Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(28, 58, 480);  -- Smart Chips (resource_id 58)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(28, 14, 300),  -- Energy Cells (resource_id 14)
(28, 57, 120);  -- Silicon Wafers (resource_id 57)


-- Soja Bean Production
INSERT INTO build_cost (id) VALUES (34);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(34, 8, 296),   -- Claytronics (resource_id 8)
(34, 14, 592),  -- Energy Cells (resource_id 14)
(34, 22, 1084); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(29, 34, 'Soja Bean Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(29, 59, 1440); -- Soja Beans (resource_id 59)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(29, 14, 360),  -- Energy Cells (resource_id 14)
(29, 71, 960);  -- Water (resource_id 71)


-- Soja Husk Production
INSERT INTO build_cost (id) VALUES (35);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(35, 8, 316),   -- Claytronics (resource_id 8)
(35, 14, 663),  -- Energy Cells (resource_id 14)
(35, 22, 1159); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(30, 35, 'Soja Husk Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(30, 60, 3840); -- Soja Husks (resource_id 60)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(30, 14, 480),  -- Energy Cells (resource_id 14)
(30, 30, 480),  -- Maja Snails (resource_id 30)
(30, 59, 480),  -- Soja Beans (resource_id 59)
(30, 63, 240);  -- Spices (resource_id 63)


-- Spacefuel Production
INSERT INTO build_cost (id) VALUES (36);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(36, 8, 319),   -- Claytronics (resource_id 8)
(36, 14, 638),  -- Energy Cells (resource_id 14)
(36, 22, 1168); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(31, 36, 'Spacefuel Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(31, 61, 900);  -- Space Fuel (resource_id 61)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(31, 14, 300),  -- Energy Cells (resource_id 14)
(31, 71, 750),  -- Water (resource_id 71)
(31, 73, 600);  -- Wheat (resource_id 73)


-- Spaceweed Production
INSERT INTO build_cost (id) VALUES (37);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(37, 8, 639),   -- Claytronics (resource_id 8)
(37, 14, 1277), -- Energy Cells (resource_id 14)
(37, 22, 2336); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(32, 37, 'Spaceweed Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(32, 62, 1350);  -- Space Weed (resource_id 62)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(32, 14, 840),   -- Energy Cells (resource_id 14)
(32, 63, 240),   -- Spices (resource_id 63)
(32, 67, 720);   -- Swamp Plants (resource_id 67)


-- Spice Production
INSERT INTO build_cost (id) VALUES (38);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(38, 8, 139),   -- Claytronics (resource_id 8)
(38, 14, 278),  -- Energy Cells (resource_id 14)
(38, 22, 510);  -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(33, 38, 'Spice Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(33, 63, 2880);  -- Spices (resource_id 63)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(33, 14, 240),   -- Energy Cells (resource_id 14)
(33, 71, 480);   -- Water (resource_id 71)


-- Sunrise Flower Production
INSERT INTO build_cost (id) VALUES (39);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(39, 8, 296),   -- Claytronics (resource_id 8)
(39, 14, 592),  -- Energy Cells (resource_id 14)
(39, 22, 1087); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(34, 39, 'Sunrise Flower Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(34, 64, 1200);  -- Sunrise Flowers (resource_id 64)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(34, 14, 360),   -- Energy Cells (resource_id 14)
(34, 71, 960);   -- Water (resource_id 71)


-- Superfluid Coolant Production
INSERT INTO build_cost (id) VALUES (40);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(40, 8, 25),    -- Claytronics (resource_id 8)
(40, 14, 51),   -- Energy Cells (resource_id 14)
(40, 22, 94);   -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(35, 40, 'Superfluid Coolant Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(35, 65, 1650);  -- Superfluid Coolant (resource_id 65)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(35, 14, 900),   -- Energy Cells (resource_id 14)
(35, 21, 4800);  -- Helium (resource_id 21)


-- Swamp Plant Production
INSERT INTO build_cost (id) VALUES (41);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(41, 8, 639),   -- Claytronics (resource_id 8)
(41, 14, 1277), -- Energy Cells (resource_id 14)
(41, 22, 2338); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(36, 41, 'Swamp Plant Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(36, 67, 960);  -- Swamp Plants (resource_id 67)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(36, 14, 320),   -- Energy Cells (resource_id 14)
(36, 71, 800);   -- Water (resource_id 71);


-- Teladianium Production
INSERT INTO build_cost (id) VALUES (42);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(42, 8, 50),    -- Claytronics (resource_id 8)
(42, 14, 101),  -- Energy Cells (resource_id 14)
(42, 22, 185);  -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(37, 42, 'Teladianium Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(37, 68, 2400);  -- Teladianium (resource_id 68)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(37, 14, 1350),  -- Energy Cells (resource_id 14)
(37, 44, 8400);  -- Ore (resource_id 44)


-- Turret Component Production
INSERT INTO build_cost (id) VALUES (43);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(43, 8, 475),   -- Claytronics (resource_id 8)
(43, 14, 951),  -- Energy Cells (resource_id 14)
(43, 22, 1741); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(38, 43, 'Turret Component Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(38, 69, 400);  -- Turret Components (resource_id 69)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(38, 14, 120),   -- Energy Cells (resource_id 14)
(38, 36, 40),    -- Microchips (resource_id 36)
(38, 51, 40),    -- Quantum Tubes (resource_id 51)
(38, 53, 20);    -- Scanning Arrays (resource_id 53)


-- Water Production
INSERT INTO build_cost (id) VALUES (44);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(44, 8, 36),    -- Claytronics (resource_id 8)
(44, 14, 72),   -- Energy Cells (resource_id 14)
(44, 22, 132);  -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(39, 44, 'Water Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(39, 71, 6600);  -- Water (resource_id 71)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(39, 14, 1800),   -- Energy Cells (resource_id 14)
(39, 24, 9600);   -- Ice (resource_id 24)


-- Weapon Component Production
INSERT INTO build_cost (id) VALUES (45);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(45, 8, 396),   -- Claytronics (resource_id 8)
(45, 14, 793),  -- Energy Cells (resource_id 14)
(45, 22, 1452); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(40, 45, 'Weapon Component Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(40, 72, 400);  -- Weapon Components (resource_id 72)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(40, 14, 120),   -- Energy Cells (resource_id 14)
(40, 22, 40),    -- Hull Parts (resource_id 22)
(40, 46, 60);    -- Plasma Conductors (resource_id 46)


-- Wheat Production
INSERT INTO build_cost (id) VALUES (46);

INSERT INTO build_cost_item (build_cost_id, resource_id, amount) VALUES
(46, 8, 296),   -- Claytronics (resource_id 8)
(46, 14, 592),  -- Energy Cells (resource_id 14)
(46, 22, 1084); -- Hull Parts (resource_id 22)

INSERT INTO production_module_blueprint (id, build_cost_id, name) VALUES
(41, 46, 'Wheat Production');

INSERT INTO production (blueprint_id, resource_id, amount) VALUES
(41, 73, 3240);  -- Wheat (resource_id 73)

INSERT INTO consumption (blueprint_id, resource_id, amount) VALUES
(41, 14, 360),   -- Energy Cells (resource_id 14)
(41, 71, 960);   -- Water (resource_id 71);



-- USERS

INSERT INTO users (id, username, password, role, enabled) VALUES -- BCrypt password encoding
(1, 'AliceSmith', '$2a$10$mxUxwT6BU1L6hY0.UFVoQekPDAuckjho0sOVoY6uW2e2GdMTUy/SS', 'ROLE_OWNER', TRUE), -- 1$;{ghd@`8
(2, 'BobJohnson', '$2a$10$y9HFM.VQzXGWdteoLBaq1ORWlI7K/XPutx9VMpbuIl1FKmY52EVl2', 'ROLE_MANAGER', TRUE), -- 2&v#q8P*3n
(3, 'CarolWilliams', '$2a$10$LEIzsPvV6ne8FcQ799uXEO3thyCPMHmlUePJDGxNuFzTUGmZFohB2', 'ROLE_MANAGER', TRUE), -- 5bG@x9{C%g
(4, 'DavidJones', '$2a$10$E5fdJzlIB713toDBeyLQV.CBvLvkBh2ekKfJTfBA/Ys0NnE8P2KnK', 'ROLE_MANAGER', TRUE), -- 9#Bv2k6%L!p
(5, 'EvaBrown', '$2a$10$DSIrGjKw4eLol/JMHg1Ud.aCiA3nXgFIzGy66QsveoBSk5gLnCUJ6', 'ROLE_MANAGER', FALSE), -- kR8!#j5&9P
(6, 'FrankGarcia', '$2a$10$bKLT.f/WP/3CVGMOtTaI9OL9lkbpiQZb59XPD09Q7WA4v8qrkojNm', 'ROLE_ENGINEER', TRUE), -- w$5Q8fP!9r
(7, 'GraceMartinez', '$2a$10$SVvj5hBQQYZdOtMkE/DtwOMUGI7AYROAGB2Zt4caOi8fU46LUTC1u', 'ROLE_ENGINEER', TRUE), -- 3S%p@6T*1J
(8, 'HenryRobinson', '$2a$10$ToR6C0/eS4HKSZJthu.8M.60FDJN5wa45XPh5f.lR6RjYpFUqIzMG', 'ROLE_ENGINEER', TRUE), -- t5^Lk@9r%2
(9, 'IvyClark', '$2a$10$mvnf22lfgcAH./5Q8D0q2u7fX924VaBn3z9N0mxbZXzSgKNEkyy2m', 'ROLE_ENGINEER', FALSE), -- Z5&n8#P1$r
(10, 'JackLewis', '$2a$10$2Kp9xGx8ZmfTUILSyNgmbu02MB01f2vjmvFEo.zp3D81uIvMIILTS', 'ROLE_ENGINEER', FALSE), -- gQ8!t3@L%6
(11, 'KarenLee', '$2a$10$Gz2xhFGWzi57MM8DFJRboOlepBN1GukPWJA4o55fKt7CwRPJCVkJO', 'ROLE_PILOT', TRUE), -- jR8#3g$W!s
(12, 'LeoHall', '$2a$10$bH/oqQwdvQ5ISEIqR5NfcOEUjYav7Vk/B.WRM/vMB3Ktr7lot1Buy', 'ROLE_PILOT', TRUE), -- P3@r8&5J!L
(13, 'MiaAllen', '$2a$10$Mptp4a7bTKo6397oUOms1.q/wq/Y7uCYYGnlVZlyw/ugE6sorFLMy', 'ROLE_PILOT', TRUE), -- y5%k2@T!8g
(14, 'NinaSanchez', '$2a$10$GmIfrh7hKc/V4jmTF847l.rGV33/VwWPEHCPFoeMMO6oiSLyMLi..', 'ROLE_PILOT', TRUE), -- K1&r7#N3$s
(15, 'OscarWright', '$2a$10$zthupLbFtI1EV2iVqvqV/e.f9dSM9wmyWQJprK8TqFbgBoiY3kxLi', 'ROLE_PILOT', TRUE), -- xP2%v8#Y6@
(16, 'PaulYoung', '$2a$10$VMiyWhqDcCxQ4o8UNrXeQO2E.dAyz8FlHyrE7I4SCcTq2g2OrYOKK', 'ROLE_PILOT', TRUE), -- q$5Y9g#T1*3
(17, 'QuinnHernandez', '$2a$10$AT0oerx0O/L5Mb5GrohIsuaf457oGMLph.Zr1dg3dLMD2MHPFTGiy', 'ROLE_PILOT', TRUE), -- nR2@6V5%9L
(18, 'RitaKing', '$2a$10$M1zqvt3ao/AuU2dOl2Z6GOfr/wMwQahUqGmqLpEfRydOyqXPYRdPK', 'ROLE_PILOT', TRUE), -- 8B9#t1@K2Z
(19, 'SamAdams', '$2a$10$mNCelQXPiZVXpALNNLX14eFNETYSJsV/wF6o7GUm8e6mci1bJ54tO', 'ROLE_PILOT', TRUE), -- 1tR%8&nK4#9
(20, 'TinaNelson', '$2a$10$XESym7UqCBFiRtupMywLmeXNCpVsTESg7Tb34UziKOG82c0RncNpm', 'ROLE_PILOT', TRUE), -- L1$z3R@5&8
(21, 'UmaCarter', '$2a$10$WEMZGqasi.x63w9LhcM18Oj/lZiSuDMft3HkN3oszIALBUa7flL7q', 'ROLE_PILOT', TRUE), -- v8&X3%p1$5
(22, 'VeraMitchell', '$2a$10$v36PNgt1stq.DEolISaLEeXq.QrrJK7/ffZu2mMdkvbsWNefg4UoW', 'ROLE_PILOT', TRUE), -- K7#b5@P6%9
(23, 'WadePerez', '$2a$10$QXjEu.1nVL/P9uglIn6c../SngVvWDm90xI70wMJ614fOhSiu.S7y', 'ROLE_PILOT', TRUE), -- N2@9V#1L3$8
(24, 'XenaRoberts', '$2a$10$U8aOTJnLGzw/Yhz4FepnoeO559RELVLWiZYpyj8SVLt.9MYNS8IA6', 'ROLE_PILOT', TRUE), -- g$6t7@Y1&2
(25, 'YaraTorres', '$2a$10$6HFVkt7BFcj88TQjYvhR4uzL97xJwXzyXOmp4IEQk49.wPYpH5tCK', 'ROLE_PILOT', TRUE), -- 8C9!%L7@1#k
(26, 'ZacharyPhillips', '$2a$10$XEyuvDlQc3D49dGcHSC0wOE4TrD0rOHyy1VfV07vwqugyo/IOI0Oy', 'ROLE_PILOT', TRUE); -- R1%k@8G4$2


INSERT INTO account (user_id, balance) VALUES
(1, 15000000),      -- Owner balance
(11, 75000),        -- Pilot KarenLee
(12, 120000),       -- Pilot LeoHall
(13, 135000),       -- Pilot MiaAllen
(14, 98000),        -- Pilot NinaSanchez
(15, 105000),       -- Pilot OscarWright
(16, 87000),        -- Pilot PaulYoung
(17, 95000),        -- Pilot QuinnHernandez
(18, 110000),       -- Pilot RitaKing
(19, 62000),        -- Pilot SamAdams
(20, 92000),        -- Pilot TinaNelson
(21, 78000),        -- Pilot UmaCarter
(22, 67000),        -- Pilot VeraMitchell
(23, 89000),        -- Pilot WadePerez
(24, 93000),        -- Pilot XenaRoberts
(25, 88000),        -- Pilot YaraTorres
(26, 71000);        -- Pilot ZacharyPhillips

INSERT INTO spaceship (id, pilot_id, size) VALUES
(1, 11, 'S'),
(2, 12, 'S'),
(3, 13, 'S'),
(4, 14, 'S'),
(5, 15, 'S'),
(6, 16, 'S'),
(7, 17, 'S'),
(8, 18, 'M'),
(9, 19, 'M'),
(10, 20, 'M'),
(11, 21, 'M'),
(12, 22, 'M'),
(13, 23, 'L'),
(14, 24, 'L'),
(15, 25, 'L'),
(16, 26, 'L');



-- STORAGE MODULES

INSERT INTO storage_module (id, blueprint_id) VALUES
(1, 3), -- Storage L
(2, 2), -- Storage M
(3, 1); -- Storage S

-- Storage L
INSERT INTO stored_resource (storage_id, resource_id, amount) VALUES
(1, 3, 60000),  -- Antimatter Cells (resource_id 3)
(1, 19, 70000), -- Fusion Reactors (resource_id 19)
(1, 20, 75000), -- Graphene (resource_id 20)
(1, 57, 55000), -- Silicon Wafers (resource_id 57)
(1, 58, 65000), -- Smart Chips (resource_id 58)
(1, 2, 80000),  -- Advanced Electronics (resource_id 2)
(1, 22, 50000), -- Hull Parts (resource_id 22)
(1, 14, 95000), -- Energy Cells (resource_id 14)
(1, 8, 70000),  -- Claytronics (resource_id 8)
(1, 62, 60000); -- Space Weed (resource_id 62)

-- Storage M
INSERT INTO stored_resource (storage_id, resource_id, amount) VALUES
(2, 1, 12000),  -- Advanced Composites (resource_id 1)
(2, 2, 18000),  -- Advanced Electronics (resource_id 2)
(2, 19, 21000), -- Fusion Reactors (resource_id 19)
(2, 18, 15000); -- Food Rations (resource_id 18)

-- Storage S
INSERT INTO stored_resource (storage_id, resource_id, amount) VALUES
(3, 14, 7000),  -- Energy Cells (resource_id 14)
(3, 22, 5500),  -- Hull Parts (resource_id 22)
(3, 8, 6000);   -- Claytronics (resource_id 8)



-- DOCK MODULES

-- DOCK MODULES
INSERT INTO dock_module (id, blueprint_id) VALUES
(1, 1),  -- Dock Area 1M6S
(2, 2),  -- Dock Area 3M6S
(3, 3),  -- Dock Area 8M
(4, 5),  -- Pier 3L-E
(5, 6);  -- Pier 3L-T

UPDATE docking_spot
SET is_occupied  = TRUE,
    spaceship_id = 1
WHERE dock_module_id = 1
  AND size = 'S'
  AND id = (SELECT MIN(id) FROM docking_spot WHERE dock_module_id = 1 AND size = 'S' AND is_occupied = FALSE);

UPDATE docking_spot
SET is_occupied  = TRUE,
    spaceship_id = 2
WHERE dock_module_id = 1
  AND size = 'S'
  AND id = (SELECT MIN(id) FROM docking_spot WHERE dock_module_id = 1 AND size = 'S' AND is_occupied = FALSE);

UPDATE docking_spot
SET is_occupied  = TRUE,
    spaceship_id = 3
WHERE dock_module_id = 2
  AND size = 'S'
  AND id = (SELECT MIN(id) FROM docking_spot WHERE dock_module_id = 2 AND size = 'S' AND is_occupied = FALSE);

UPDATE docking_spot
SET is_occupied  = TRUE,
    spaceship_id = 4
WHERE dock_module_id = 2
  AND size = 'S'
  AND id = (SELECT MIN(id) FROM docking_spot WHERE dock_module_id = 2 AND size = 'S' AND is_occupied = FALSE);

UPDATE docking_spot
SET is_occupied  = TRUE,
    spaceship_id = 8
WHERE dock_module_id = 1
  AND size = 'M'
  AND id = (SELECT MIN(id) FROM docking_spot WHERE dock_module_id = 1 AND size = 'M' AND is_occupied = FALSE);

UPDATE docking_spot
SET is_occupied = TRUE,
    spaceship_id = 9
WHERE dock_module_id = 2
  AND size = 'M'
  AND id = (SELECT MIN(id) FROM docking_spot WHERE dock_module_id = 2 AND size = 'M' AND is_occupied = FALSE);

UPDATE docking_spot
SET is_occupied = TRUE,
    spaceship_id = 10
WHERE dock_module_id = 3
  AND size = 'M'
  AND id = (SELECT MIN(id) FROM docking_spot WHERE dock_module_id = 3 AND size = 'M' AND is_occupied = FALSE);

UPDATE docking_spot
SET is_occupied = TRUE,
    spaceship_id = 13
WHERE dock_module_id = 4
  AND size = 'L'
  AND id = (SELECT MIN(id) FROM docking_spot WHERE dock_module_id = 4 AND size = 'L' AND is_occupied = FALSE);

UPDATE docking_spot
SET is_occupied = TRUE,
    spaceship_id = 14
WHERE dock_module_id = 5
  AND size = 'L'
  AND id = (SELECT MIN(id) FROM docking_spot WHERE dock_module_id = 5 AND size = 'L' AND is_occupied = FALSE);


-- PRODUCTION MODULES

INSERT INTO production_module (blueprint_id, state, engineer_id) VALUES
(12, 'READY', 6),        -- Blueprint: Hull Part Production, Engineer: FrankGarcia (ID 6)
(11, 'READY', 8),        -- Blueprint: Graphene Production, Engineer: HenryRobinson (ID 8)
(7, 'MANUFACTURING', 7), -- Blueprint: Energy Cell Production, Engineer: GraceMartinez (ID 7)
(1, 'OFF', NULL),        -- Blueprint: Advanced Composite Production, No Engineer
(25, 'OFF', NULL);       -- Blueprint: Refined Metal Production, No Engineer



-- TRADE POLICY

UPDATE trade_policy
SET
    station_sells = TRUE,
    sell_price = 200,
    sell_limit = 20000
WHERE resource_id = 14;  -- Resource: Energy Cells (ID 14)

UPDATE trade_policy
SET
    station_sells = TRUE,
    sell_price = 1500,
    sell_limit = 0
WHERE resource_id = 1;  -- Resource: Advanced Composites (ID 1)

UPDATE trade_policy
SET
    station_sells = TRUE,
    sell_price = 700,
    sell_limit = 15000
WHERE resource_id = 20;  -- Resource: Graphene (ID 20)

UPDATE trade_policy
SET
    station_buys = TRUE,
    purchase_price = 500,
    purchase_limit = 100000,
    station_sells = TRUE,
    sell_price = 2000,
    sell_limit = 20000
WHERE resource_id = 22;  -- Resource: Hull Parts (ID 22)

UPDATE trade_policy
SET
    station_buys = TRUE,
    purchase_price = 1000,
    purchase_limit = 70000,
    station_sells = TRUE,
    sell_price = 5000,
    sell_limit = 10000
WHERE resource_id = 8;  -- Resource: Claytronics (ID 8)



-- TRADES

INSERT INTO trade (id, user_id, time) VALUES
(1, 11, '2024-11-01 10:41:59'),
(2, 12, '2024-11-02 21:17:29'),
(3, 13, '2024-11-02 12:03:43'),
(4, 11, '2024-11-03 17:41:46'),
(5, 15, '2024-11-04 11:23:02');

INSERT INTO trade_item (trade_id, resource_id, operation, amount, price) VALUES
(1, 14, 'BUY', 1200, 190);  -- Trade ID 1: Energy Cells (ID 14) - BUY 1200 at price 190

INSERT INTO trade_item (trade_id, resource_id, operation, amount, price) VALUES
(2, 1, 'BUY', 1000, 1600),  -- Trade ID 2: Advanced Composites (ID 1) - BUY 1000 at price 1600
(2, 20, 'BUY', 1500, 600),  -- Trade ID 2: Graphene (ID 20) - BUY 1500 at price 600
(2, 22, 'BUY', 1300, 1700); -- Trade ID 2: Hull Parts (ID 22) - BUY 1300 at price 1700

INSERT INTO trade_item (trade_id, resource_id, operation, amount, price) VALUES
(3, 19, 'BUY', 200, 1500),   -- Trade ID 3: Fusion Reactors (ID 19) - BUY 200 at price 1500
(3, 58, 'BUY', 480, 1800),   -- Trade ID 3: Smart Chips (ID 58) - BUY 480 at price 1800
(3, 14, 'SELL', 8000, 80);   -- Trade ID 3: Energy Cells (ID 14) - SELL 8000 at price 80

INSERT INTO trade_item (trade_id, resource_id, operation, amount, price) VALUES
(4, 8, 'SELL', 240, 2100);   -- Trade ID 4: Claytronics (ID 8) - SELL 240 at price 2100

INSERT INTO trade_item (trade_id, resource_id, operation, amount, price) VALUES
(5, 40, 'BUY', 200, 800),    -- Trade ID 5: Nanotubes (ID 40) - BUY 200 at price 800
(5, 20, 'SELL', 3800, 300),  -- Trade ID 5: Graphene (ID 20) - SELL 3800 at price 300
(5, 2, 'SELL', 400, 1100);   -- Trade ID 5: Advanced Electronics (ID 2) - SELL 400 at price 1100