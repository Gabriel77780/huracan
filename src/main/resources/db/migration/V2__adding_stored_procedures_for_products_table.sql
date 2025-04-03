CREATE OR REPLACE PROCEDURE create_product_sp(
    p_name       IN VARCHAR2,
    p_description IN VARCHAR2,
    p_price      IN NUMBER,
    p_stock      IN NUMBER,
    p_category   IN VARCHAR2
) AS
BEGIN
INSERT INTO product (name, description, price, stock, category, created_at)
VALUES (p_name, p_description, p_price, p_stock, p_category, CURRENT_TIMESTAMP);

COMMIT;

EXCEPTION
WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20004, 'An unexpected error occurred: ' || SQLERRM);
END;
/

CREATE OR REPLACE VIEW product_details_view AS
SELECT id, name, description, price, stock, category, created_at
FROM product
END;
/

CREATE OR REPLACE PROCEDURE get_product_by_id(
    p_id            IN NUMBER,
    p_name          OUT VARCHAR2,
    p_description   OUT VARCHAR2,
    p_price         OUT NUMBER,
    p_stock         OUT NUMBER,
    p_category      OUT VARCHAR2,
    p_created_at    OUT TIMESTAMP
) AS
BEGIN
SELECT name, description, price, stock, category, created_at
INTO p_name, p_description, p_price, p_stock, p_category, p_created_at
FROM product_details_view
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
        RAISE;
END;
/

CREATE OR REPLACE PROCEDURE update_product_sp(
    p_id         IN NUMBER,
    p_name       IN VARCHAR2,
    p_description IN VARCHAR2,
    p_price      IN NUMBER,
    p_stock      IN NUMBER,
    p_category   IN VARCHAR2
) AS
BEGIN
UPDATE product
SET name = p_name,
    description = p_description,
    price = p_price,
    stock = p_stock,
    category = p_category
WHERE id = p_id;

COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20004, 'An unexpected error occurred: ' || SQLERRM);
END;
/

CREATE OR REPLACE PROCEDURE delete_product_sp(
    p_id IN NUMBER
) AS
BEGIN
DELETE FROM product WHERE id = p_id;
COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        RAISE_APPLICATION_ERROR(-20002, 'An unexpected error occurred: ' || SQLERRM);
END;
/

CREATE OR REPLACE VIEW product_view AS
SELECT id, name, description, price, stock, category
FROM product;
/

CREATE OR REPLACE PROCEDURE get_all_product_sp(
  p_product_cursor OUT SYS_REFCURSOR
) AS
BEGIN
OPEN p_product_cursor FOR
SELECT * FROM product_view;
END;