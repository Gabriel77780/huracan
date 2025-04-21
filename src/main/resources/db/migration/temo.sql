CREATE OR REPLACE TRIGGER trg_system_audit_purchase
AFTER INSERT OR UPDATE OR DELETE ON purchase
    FOR EACH ROW
DECLARE
v_old_data CLOB;
  v_new_data CLOB;
  v_operation VARCHAR2(10);
BEGIN
  IF INSERTING THEN
    v_operation := 'INSERT';
  ELSIF UPDATING THEN
    v_operation := 'UPDATE';
  ELSIF DELETING THEN
    v_operation := 'DELETE';
END IF;

  IF DELETING OR UPDATING THEN
    v_old_data := '{"id":' || :OLD.id ||
                  ',"supplier_id":' || :OLD.supplier_id ||
                  ',"system_user_id":' || :OLD.system_user_id ||
                  ',"total":' || :OLD.total ||
                  ',"created_at":"' || TO_CHAR(:OLD.created_at, 'YYYY-MM-DD"T"HH24:MI:SS') || '"}';
END IF;

  IF INSERTING OR UPDATING THEN
    v_new_data := '{"id":' || :NEW.id ||
                  ',"supplier_id":' || :NEW.supplier_id ||
                  ',"system_user_id":' || :NEW.system_user_id ||
                  ',"total":' || :NEW.total ||
                  ',"created_at":"' || TO_CHAR(:NEW.created_at, 'YYYY-MM-DD"T"HH24:MI:SS') || '"}';
END IF;

INSERT INTO system_audit (table_name, operation, record_id, changed_by, old_data, new_data)
VALUES ('SALE', v_operation, NVL(:NEW.id, :OLD.id), USER, v_old_data, v_new_data);
END;
/

CREATE OR REPLACE TRIGGER trg_system_audit_purchase_detail
AFTER INSERT OR UPDATE OR DELETE ON purchase_detail
    FOR EACH ROW
DECLARE
v_old_data CLOB;
  v_new_data CLOB;
  v_operation VARCHAR2(10);
BEGIN
  IF INSERTING THEN
    v_operation := 'INSERT';
  ELSIF UPDATING THEN
    v_operation := 'UPDATE';
  ELSIF DELETING THEN
    v_operation := 'DELETE';
END IF;

  IF DELETING OR UPDATING THEN
    v_old_data := '{"id":' || :OLD.id ||
                  ',"purchase_id":' || :OLD.purchase_id ||
                  ',"product_id":' || :OLD.product_id ||
                  ',"quantity":' || :OLD.quantity ||
                  ',"price":' || :OLD.price ||
                  ',"subtotal":' || :OLD.subtotal || '}';
END IF;

  IF INSERTING OR UPDATING THEN
    v_new_data := '{"id":' || :NEW.id ||
                  ',"purchase_id":' || :NEW.purchase_id ||
                  ',"product_id":' || :NEW.product_id ||
                  ',"quantity":' || :NEW.quantity ||
                  ',"price":' || :NEW.price ||
                  ',"subtotal":' || :NEW.subtotal || '}';
END IF;

INSERT INTO system_audit (table_name, operation, record_id, changed_by, old_data, new_data)
VALUES ('SALE_DETAIL', v_operation, NVL(:NEW.id, :OLD.id),
        USER, v_old_data, v_new_data);
END;
/