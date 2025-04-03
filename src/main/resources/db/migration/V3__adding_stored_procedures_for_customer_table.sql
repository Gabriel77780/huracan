CREATE OR REPLACE PROCEDURE create_customer_sp(
    p_name      IN VARCHAR2,
    p_email     IN VARCHAR2,
    p_phone     IN VARCHAR2
) AS
BEGIN
INSERT INTO customer (name, email, phone, created_at)
VALUES (p_name, p_email, p_phone, CURRENT_TIMESTAMP);

COMMIT;
END;
/

CREATE OR REPLACE VIEW customer_details_view AS
SELECT id, name, email, phone, created_at
FROM customer;

CREATE OR REPLACE PROCEDURE update_customer_sp(
    p_id        IN NUMBER,
    p_name      IN VARCHAR2,
    p_email     IN VARCHAR2,
    p_phone     IN VARCHAR2
) AS
BEGIN
UPDATE customer
SET name = p_name,
    email = p_email,
    phone = p_phone
WHERE id = p_id;

COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE delete_customer_sp(
    p_id IN NUMBER
) AS
BEGIN
DELETE FROM customer WHERE id = p_id;
COMMIT;
END;
/