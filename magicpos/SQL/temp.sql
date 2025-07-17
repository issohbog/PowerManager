-- Active: 1750388007092@@127.0.0.1@3306@magicpos
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



SELECT 
    s.seat_id,
    s.seat_name,
    s.seat_status,
    u.no AS user_no,
    u.username,
    ut.remain_time,
    sr.start_time,
    sr.end_time
FROM users u
LEFT JOIN seats_reservations sr ON u.no = sr.u_no
LEFT JOIN seats s ON s.seat_id = sr.seat_id
LEFT JOIN user_tickets ut ON u.no = ut.u_no
WHERE u.no = 2
ORDER BY sr.end_time DESC
LIMIT 1
;

SELECT *
FROM users u
WHERE u.no = 2
;

SELECT *
FROM seats s
WHERE u.no = 2
;

SELECT *
FROM seats_reservations s

;
