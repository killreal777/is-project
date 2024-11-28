TRUNCATE TABLE
docking_spot,
dock_module,
production_module,
stored_resource,
storage_module,
trade_item,
trade,
spaceship,
account,
users
CASCADE;


-- USERSp

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

SELECT setval('users_id_seq', (SELECT MAX(id) FROM users));

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

SELECT setval('spaceship_id_seq', (SELECT MAX(id) FROM spaceship));



-- STORAGE MODULES

INSERT INTO storage_module (id, blueprint_id) VALUES
(1, 3), -- Storage L
(2, 2), -- Storage M
(3, 1); -- Storage S

SELECT setval('storage_module_id_seq', (SELECT MAX(id) FROM storage_module));

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

SELECT setval('dock_module_id_seq', (SELECT MAX(id) FROM dock_module));

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
(21, 'OFF', NULL);       -- Blueprint: Refined Metal Production, No Engineer



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

SELECT setval('trade_id_seq', (SELECT MAX(id) FROM trade));

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