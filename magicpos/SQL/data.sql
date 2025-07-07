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


INSERT INTO products (c_no, p_name, p_price, img_path, description, sell_status, stock)
VALUES 
(1, '고기짬뽕밥', 8000, '/images/고기짬뽕밥.png', '얼큰한 국물과 고기가 어우러진 짬뽕밥입니다.', true, 10),
(1, '떡만두국', 8000, '/images/떡만두국.jpg', '쫄깃한 만두와 떡이 듬뿍 담긴 든든한 국물요리.', true, 10),
(1, '삼겹비빔밥', 8000, '/images/삼겹비빔밥.jpg', '불맛 가득한 삼겹살과 신선한 야채가 어우러진 비빔밥.', true, 10),
(1, '쭈삼덮밥', 8000, '/images/쭈삼.jpg', '쭈꾸미와 삼겹살의 환상적인 조합!', true, 10),
(1, '직화불고기 덮밥', 8000, '/images/직화불고기.jpg', '불맛 가득한 직화불고기 덮밥입니다.', true, 10),
(1, '안동찜닭 덮밥', 8000, '/images/안동찜닭.jpg', '간장 베이스의 달달한 안동찜닭을 덮밥으로 즐겨보세요.', true, 10),
(1, '제육 덮밥', 8000, '/images/제육덮밥.jpg', '매콤달콤한 제육볶음이 올라간 든든한 한끼.', true, 10),
(1, '춘천닭갈비 덮밥', 8000, '/images/춘천닭갈비덮밥.jpg', '춘천식 닭갈비 소스를 곁들인 덮밥!', true, 10),
(1, '치즈간장계란X2밥', 8000, '/images/치즈간계밥.jpg', '치즈와 계란이 두 배! 고소하고 부드러운 맛.', true, 10),
(1, '카레 덮밥', 8000, '/images/카레덮밥.jpg', '한국식 매콤한 카레로 즐기는 덮밥 한 그릇.', true, 10),
(1, '치킨마요 덮밥', 8000, '/images/치킨마요.jpg', '고소한 치킨과 마요네즈가 어우러진 덮밥입니다.', true, 10),
(1, '빅치킨불닭마요 덮밥', 8000, '/images/빅치킨불닭마요.jpg', '맵단맛이 중독성 있는 불닭소스와 치킨마요.', true, 10),
(1, '돈까스마요 덮밥', 8000, '/images/돈가스마요.jpg', '바삭한 돈까스와 마요 소스의 환상 조합.', true, 10),
(1, '돈까스불닭마요 덮밥', 8000, '/images/돈가스마요.jpg', '불닭 소스의 매콤함이 어우러진 돈까스마요.', true, 10),
(1, '치킨카레덮밥', 8000, '/images/치킨카레.jpg', '치킨과 카레가 만난 든든한 메뉴.', true, 10),
(1, '돈까스 김치나베', 8000, '/images/돈가스김치나베.jpg', '김치찌개 느낌의 따끈한 나베와 돈까스.', true, 10),
(1, '참치마요 덮밥', 8000, '/images/참치마요.jpg', '담백한 참치와 고소한 마요네즈가 가득.', true, 10),
(1, '소불고기 덮밥', 8000, '/images/소불고기덮밥.jpg', '달달한 소불고기와 밥의 완벽한 조합.', true, 10),
(1, '오삼 덮밥', 8000, '/images/오삼불고기덮밥.jpg', '오징어와 삼겹살이 매콤하게 볶아진 덮밥.', true, 10),
(1, '해물 짬뽕밥', 8000, '/images/해물짬뽕밥.jpg', '해물의 풍미가 살아있는 국물 덮밥.', true, 10);


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
(4, 4, 4)


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
(4, 4, 4)


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

