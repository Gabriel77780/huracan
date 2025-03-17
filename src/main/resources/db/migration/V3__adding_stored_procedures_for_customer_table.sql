CREATE OR REPLACE PROCEDURE create_customer(
    p_name      IN VARCHAR2,
    p_email     IN VARCHAR2,
    p_phone     IN VARCHAR2
) AS
BEGIN
INSERT INTO customers (name, email, phone, created_at)
VALUES (p_name, p_email, p_phone, CURRENT_TIMESTAMP);

COMMIT;
END;

CREATE OR REPLACE PROCEDURE get_customer_by_id(
    p_id        IN NUMBER,
    p_name      OUT VARCHAR2,
    p_email     OUT VARCHAR2,
    p_phone     OUT VARCHAR2,
    p_created_at OUT TIMESTAMP
) AS
BEGIN
SELECT name, email, phone, created_at
INTO p_name, p_email, p_phone, p_created_at
FROM customers
WHERE id = p_id;
END;

CREATE OR REPLACE PROCEDURE update_customer(
    p_id        IN NUMBER,
    p_name      IN VARCHAR2,
    p_email     IN VARCHAR2,
    p_phone     IN VARCHAR2
) AS
BEGIN
UPDATE customers
SET name = p_name,
    email = p_email,
    phone = p_phone
WHERE id = p_id;

COMMIT;
END;

CREATE OR REPLACE PROCEDURE delete_customer(
    p_id IN NUMBER
) AS
BEGIN
DELETE FROM customers WHERE id = p_id;
COMMIT;
END;