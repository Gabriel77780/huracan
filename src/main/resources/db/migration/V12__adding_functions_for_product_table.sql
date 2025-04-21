CREATE OR REPLACE FUNCTION is_product_available_fn(
    p_product_id IN NUMBER
) RETURN NUMBER IS
    v_stock NUMBER;
BEGIN
SELECT stock INTO v_stock FROM product WHERE id = p_product_id;

IF v_stock > 0 THEN
        RETURN 1;
ELSE
        RETURN 0;
END IF;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        RETURN 0;
END;
/