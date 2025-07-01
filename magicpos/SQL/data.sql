USE magicpos;

-- 외래 키 제약 해제
SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE orders_details;
TRUNCATE TABLE carts;
TRUNCATE TABLE logs;
TRUNCATE TABLE seats_reservations;
TRUNCATE TABLE user_tickets;
TRUNCATE TABLE auths;

TRUNCATE TABLE orders;
TRUNCATE TABLE products;
TRUNCATE TABLE categories;
TRUNCATE TABLE tickets;
TRUNCATE TABLE seats;
TRUNCATE TABLE users;

-- 외래 키 제약 다시 켜기
SET FOREIGN_KEY_CHECKS = 1;




INSERT INTO users (id, username, password, birth, email, phone, memo) VALUES
('user1', '사용자1', 'pass1', '2000-01-01', 'user1@mail.com', '010-1234-1001', '메모1'),
('user2', '사용자2', 'pass2', '2000-01-02', 'user2@mail.com', '010-1234-1002', '메모2'),
('user3', '사용자3', 'pass3', '2000-01-03', 'user3@mail.com', '010-1234-1003', '메모3'),
('user4', '사용자4', 'pass4', '2000-01-04', 'user4@mail.com', '010-1234-1004', '메모4'),
('user5', '사용자5', 'pass5', '2000-01-05', 'user5@mail.com', '010-1234-1005', '메모5');


INSERT INTO tickets (ticket_name, time, price) VALUES
('이용권1', 60, 1000),
('이용권2', 120, 2000),
('이용권3', 180, 3000),
('이용권4', 240, 4000),
('이용권5', 300, 5000);


INSERT INTO categories (c_name) VALUES
('분류1'),
('분류2'),
('분류3'),
('분류4'),
('분류5');


INSERT INTO products (c_no, p_name, p_price, img_path, description, sell_status, stock) VALUES
(2, '상품1', 500, '/images/product1.jpg', '상품1 설명', 0, 10),
(3, '상품2', 1000, '/images/product2.jpg', '상품2 설명', 1, 20),
(4, '상품3', 1500, '/images/product3.jpg', '상품3 설명', 0, 30),
(5, '상품4', 2000, '/images/product4.jpg', '상품4 설명', 1, 40),
(1, '상품5', 2500, '/images/product5.jpg', '상품5 설명', 0, 50);


INSERT INTO seats (seat_id, seat_name, seat_status) VALUES
('S1', '좌석1', 1),
('S2', '좌석2', 2),
('S3', '좌석3', 3),
('S4', '좌석4', 0),
('S5', '좌석5', 1);


INSERT INTO orders (u_no, seat_id, total_price, payment, message, order_status, payment_status) VALUES
(1, 'S1', 2000, 'CASH', '요청메시지1', 1, 1),
(2, 'S2', 4000, 'CASH', '요청메시지2', 2, 0),
(3, 'S3', 6000, 'CASH', '요청메시지3', 0, 1),
(4, 'S4', 8000, 'CASH', '요청메시지4', 1, 0),
(5, 'S5', 10000, 'CASH', '요청메시지5', 2, 1);


INSERT INTO carts (p_no, u_no, quantity) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5);


INSERT INTO user_tickets (u_no, t_no, remain_time, pay_at) VALUES
(1, 1, 60, NOW()),
(2, 2, 120, NOW()),
(3, 3, 180, NOW()),
(4, 4, 240, NOW()),
(5, 5, 300, NOW());


INSERT INTO auths (u_no, auth) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_USER'),
(3, 'ROLE_USER'),
(4, 'ROLE_USER'),
(5, 'ROLE_USER');


INSERT INTO orders_details (o_no, p_no, quantity) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5);


INSERT INTO seats_reservations (seat_id, t_no, u_no, start_time, end_time) VALUES
('S1', 1, 1, '2025-07-01 03:18:23', '2025-07-01 04:18:23'),
('S2', 2, 2, '2025-07-01 04:18:23', '2025-07-01 05:18:23'),
('S3', 3, 3, '2025-07-01 05:18:23', '2025-07-01 06:18:23'),
('S4', 4, 4, '2025-07-01 06:18:23', '2025-07-01 07:18:23'),
('S5', 5, 5, '2025-07-01 07:18:23', '2025-07-01 08:18:23');


INSERT INTO logs (u_no, seat_id, action_type, description) VALUES
(1, 'S1', 'LOGIN', '사용자1님이 로그인하셨습니다.'),
(2, 'S2', 'LOGIN', '사용자2님이 로그인하셨습니다.'),
(3, 'S3', 'LOGIN', '사용자3님이 로그인하셨습니다.'),
(4, 'S4', 'LOGIN', '사용자4님이 로그인하셨습니다.'),
(5, 'S5', 'LOGIN', '사용자5님이 로그인하셨습니다.');
