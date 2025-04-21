

CREATE OR REPLACE TRIGGER trg_update_product_stock_after_purchase_detail
AFTER INSERT ON purchase_detail
FOR EACH ROW
BEGIN
UPDATE product
SET stock = stock + :NEW.quantity
WHERE id = :NEW.product_id;
END;
/