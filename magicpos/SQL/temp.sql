-- Active: 1750388003489@@127.0.0.1@3306@magicpos
        SELECT 
            p.no AS productNo,
            IFNULL(SUM(CASE WHEN DATE(o.order_time) = CURDATE() THEN od.quantity ELSE 0 END), 0) AS todaySales
        FROM 
            products p
        LEFT JOIN orders_details od ON p.no = od.p_no
        LEFT JOIN orders o ON od.o_no = o.no
        GROUP BY p.no;


SELECT *
FROM orders
WHERE DATE(order_time) = '2025-07-09'
;

UPDATE orders
SET order_time = now()
WHERE DATE(order_time) = '2025-07-09';