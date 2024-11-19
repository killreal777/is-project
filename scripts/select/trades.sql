-- Trade Policies
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

-- Trades
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

-- Trade Items
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

-- Sell Offers
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

-- Purchase Offers
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