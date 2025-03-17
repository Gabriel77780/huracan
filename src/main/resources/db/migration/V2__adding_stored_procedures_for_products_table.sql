CREATE OR REPLACE PROCEDURE create_product(
    p_name       IN VARCHAR2,
    p_description IN VARCHAR2,
    p_price      IN NUMBER,
    p_stock      IN NUMBER,
    p_category   IN VARCHAR2
) AS
BEGIN
INSERT INTO products (name, description, price, stock, category, created_at)
VALUES (p_name, p_description, p_price, p_stock, p_category, CURRENT_TIMESTAMP);

COMMIT;
END;

CREATE OR REPLACE PROCEDURE get_product_by_id(
    p_id         IN NUMBER,
    p_name       OUT VARCHAR2,
    p_description OUT VARCHAR2,
    p_price      OUT NUMBER,
    p_stock      OUT NUMBER,
    p_category   OUT VARCHAR2,
    p_created_at OUT TIMESTAMP
) AS
BEGIN
SELECT name, description, price, stock, category, created_at
INTO p_name, p_description, p_price, p_stock, p_category, p_created_at
FROM products
WHERE id = p_id;
END;

CREATE OR REPLACE PROCEDURE update_product(
    p_id         IN NUMBER,
    p_name       IN VARCHAR2,
    p_description IN VARCHAR2,
    p_price      IN NUMBER,
    p_stock      IN NUMBER,
    p_category   IN VARCHAR2
) AS
BEGIN
UPDATE products
SET name = p_name,
    description = p_description,
    price = p_price,
    stock = p_stock,
    category = p_category
WHERE id = p_id;

COMMIT;
END;

CREATE OR REPLACE PROCEDURE delete_product(
    p_id IN NUMBER
) AS
BEGIN
DELETE FROM products WHERE id = p_id;
COMMIT;
END;