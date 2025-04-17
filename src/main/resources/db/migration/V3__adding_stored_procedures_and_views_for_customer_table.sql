CREATE OR REPLACE PROCEDURE create_customer_sp(
    p_name      IN VARCHAR2,
    p_email     IN VARCHAR2,
    p_phone     IN VARCHAR2
) AS
BEGIN
INSERT INTO customer (name, email, phone, created_at)
VALUES (p_name, p_email, p_phone, CURRENT_TIMESTAMP);

COMMIT;
EXCEPTION
WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20004, 'An unexpected error occurred: ' || SQLERRM);
END;
/

CREATE OR REPLACE VIEW customer_details_view AS
SELECT id, name, email, phone, created_at
FROM customer;

CREATE OR REPLACE PROCEDURE get_customer_by_id_sp(
    p_id            IN NUMBER,
    p_name          OUT VARCHAR2,
    p_email         OUT VARCHAR2,
    p_phone         OUT VARCHAR2,
    p_created_at    OUT TIMESTAMP
) AS
BEGIN
SELECT name, email, phone, created_at
INTO p_name, p_email, p_phone, p_created_at
FROM customer_details_view
WHERE id = p_id;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        p_name := NULL;
        p_description := NULL;
        p_price := NULL;
        p_stock := NULL;
        p_category := NULL;
        p_created_at := NULL;
WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20004, 'An unexpected error occurred: ' || SQLERRM);
END;
/

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
EXCEPTION
WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20004, 'An unexpected error occurred: ' || SQLERRM);
END;
/

CREATE OR REPLACE PROCEDURE delete_customer_sp(
    p_id IN NUMBER
) AS
BEGIN
DELETE FROM customer WHERE id = p_id;
COMMIT;
EXCEPTION
WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20004, 'An unexpected error occurred: ' || SQLERRM);
END;
/

CREATE OR REPLACE VIEW customer_view AS
SELECT id, name, email, phone
FROM customer;
/

CREATE OR REPLACE PROCEDURE get_all_customer_sp(
  p_customer_cursor OUT SYS_REFCURSOR
) AS
BEGIN
OPEN p_customer_cursor FOR
SELECT * FROM customer_view;
END;