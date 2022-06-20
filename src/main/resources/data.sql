INSERT INTO USERS(USERNAME, PASSWORD, ENABLED)
VALUES ('admin', '{bcrypt}$2a$10$et7t/56FULw.WohTI9fIpuu2G78xPUxhWGNAnrymZ6fchFRX0Nd1q', true);
INSERT INTO USERS(USERNAME, PASSWORD, ENABLED)
VALUES ('gl', '{bcrypt}$2a$10$VT/n8x/VMzrBeoS6wW02g.a34ldNbb.HR2ZpS9oVff6lEyH33m9i6', true);
INSERT INTO USERS(USERNAME, PASSWORD, ENABLED)
VALUES ('admin123', '{bcrypt}$2a$10$buSi3X959EyyshxFXT/hHeWqzSbK7Q5B/MpdhCUU3BUNuiIYFAHzC', true);


INSERT INTO AUTHORITIES(USERNAME, AUTHORITY)
VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO AUTHORITIES(USERNAME, AUTHORITY)
VALUES ('gl', 'ROLE_USER');
INSERT INTO AUTHORITIES(USERNAME, AUTHORITY)
VALUES ('admin123', 'ROLE_ADMIN');

INSERT INTO MENU_ITEM(NAME, PRICE)
VALUES ('Panner Tikka Dry', 250);
INSERT INTO MENU_ITEM(NAME, PRICE)
VALUES ('Panner Tikka Curry', 300);
INSERT INTO MENU_ITEM(NAME, PRICE)
VALUES ('Panner Biryani', 350);
INSERT INTO MENU_ITEM(NAME, PRICE)
VALUES ('Peas Pulav', 275);
INSERT INTO MENU_ITEM(NAME, PRICE)
VALUES ('Naan', 100);
INSERT INTO MENU_ITEM(NAME, PRICE)
VALUES ('Lime Soda', 150);

-- Views
CREATE VIEW SALES_PER_CITY
as
SELECT address as city, sum(amount) as total_sale
FROM CUSTOMER_DETAILS c
         inner join orders o
where c.user_name = o.user_name
group by city;

CREATE VIEW MAX_SALE_PER_MONTH
as
SELECT EXTRACT(MONTH FROM ORDER_DATE) as month,
       max(amount) as max_sale
from orders
GROUP BY month;

CREATE VIEW TOTAL_LAST_YEAR_SALES_PER_MONTH
as
SELECT EXTRACT(MONTH FROM ORDER_DATE) as month,
       sum(amount) as max_sale
from orders
WHERE EXTRACT(YEAR FROM ORDER_DATE) = YEAR(CURRENT_TIMESTAMP) - 1
GROUP BY month;
