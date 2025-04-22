CREATE OR REPLACE FUNCTION is_customer_used_in_sale_fn (
  p_customer_id IN NUMBER
) RETURN NUMBER IS
  v_count NUMBER;
  v_sql   VARCHAR2(1000);
BEGIN
  v_sql := 'SELECT COUNT(*) FROM sale WHERE customer_id = :1';

EXECUTE IMMEDIATE v_sql INTO v_count USING p_customer_id;

IF v_count > 0 THEN
    RETURN 1;
ELSE
    RETURN 0;
END IF;
END;
/

CREATE OR REPLACE FUNCTION is_product_used_in_sale_detail_fn (
  p_product_id IN NUMBER
) RETURN NUMBER IS
  v_count NUMBER;
  v_sql   VARCHAR2(1000);
BEGIN
  v_sql := 'SELECT COUNT(*) FROM sale_detail WHERE product_id = :1';

EXECUTE IMMEDIATE v_sql INTO v_count USING p_product_id;

IF v_count > 0 THEN
    RETURN 1;
ELSE
    RETURN 0;
END IF;
END;
/
