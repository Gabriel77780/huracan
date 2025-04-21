
CREATE OR REPLACE PACKAGE supplier_pkg AS

    PROCEDURE create_supplier_sp(
        p_name      IN VARCHAR2,
        p_email     IN VARCHAR2,
        p_phone     IN VARCHAR2
    );

    PROCEDURE get_supplier_by_id_sp(
        p_id            IN NUMBER,
        p_name          OUT VARCHAR2,
        p_email         OUT VARCHAR2,
        p_phone         OUT VARCHAR2,
        p_created_at    OUT TIMESTAMP
    );

    PROCEDURE update_supplier_sp(
        p_id        IN NUMBER,
        p_name      IN VARCHAR2,
        p_email     IN VARCHAR2,
        p_phone     IN VARCHAR2
    );

    PROCEDURE delete_supplier_sp(
        p_id IN NUMBER
    );

    PROCEDURE get_all_supplier_sp(
        p_supplier_cursor OUT SYS_REFCURSOR
    );
END supplier_pkg;
/

CREATE OR REPLACE PACKAGE BODY supplier_pkg AS

    PROCEDURE create_supplier_sp(
        p_name      IN VARCHAR2,
        p_email     IN VARCHAR2,
        p_phone     IN VARCHAR2
    ) AS
BEGIN
INSERT INTO supplier (name, email, phone, created_at)
VALUES (p_name, p_email, p_phone, CURRENT_TIMESTAMP);
COMMIT;
EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20004, 'An unexpected error occurred: ' || SQLERRM);
END create_supplier_sp;

    PROCEDURE get_supplier_by_id_sp(
        p_id            IN NUMBER,
        p_name          OUT VARCHAR2,
        p_email         OUT VARCHAR2,
        p_phone         OUT VARCHAR2,
        p_created_at    OUT TIMESTAMP
    ) AS
BEGIN
SELECT name, email, phone, created_at
INTO p_name, p_email, p_phone, p_created_at
FROM supplier_details_view
WHERE id = p_id;

EXCEPTION
        WHEN NO_DATA_FOUND THEN
            p_name := NULL;
            p_email := NULL;
            p_phone := NULL;
            p_created_at := NULL;
WHEN OTHERS THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20004, 'An unexpected error occurred: ' || SQLERRM);
END get_supplier_by_id_sp;

    PROCEDURE update_supplier_sp(
        p_id        IN NUMBER,
        p_name      IN VARCHAR2,
        p_email     IN VARCHAR2,
        p_phone     IN VARCHAR2
    ) AS
BEGIN
UPDATE supplier
SET name = p_name,
    email = p_email,
    phone = p_phone
WHERE id = p_id;
COMMIT;
EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20004, 'An unexpected error occurred: ' || SQLERRM);
END update_supplier_sp;
    PROCEDURE delete_supplier_sp(
        p_id IN NUMBER
    ) AS
BEGIN
DELETE FROM supplier WHERE id = p_id;
COMMIT;
EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            RAISE_APPLICATION_ERROR(-20004, 'An unexpected error occurred: ' || SQLERRM);
END delete_supplier_sp;

    PROCEDURE get_all_supplier_sp(
        p_supplier_cursor OUT SYS_REFCURSOR
    ) AS
BEGIN
OPEN p_supplier_cursor FOR
SELECT * FROM supplier_view;
END get_all_supplier_sp;

END supplier_pkg;
/
