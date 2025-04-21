CREATE
OR REPLACE TRIGGER trg_system_audit_product
AFTER INSERT OR
UPDATE OR
DELETE
ON product
    FOR EACH ROW
DECLARE
v_old_data   CLOB;
    v_new_data
CLOB;
    v_operation
VARCHAR2(10);
BEGIN

    IF
INSERTING THEN
        v_operation := 'INSERT';
    ELSIF
UPDATING THEN
        v_operation := 'UPDATE';
    ELSIF
DELETING THEN
        v_operation := 'DELETE';
END IF;

    IF
DELETING OR UPDATING THEN
        v_old_data := '{"id":' || :OLD.id ||
                      ',"name":"' || :OLD.name ||
                      '","description":"' || :OLD.description ||
                      ',"price":' || :OLD.price ||
                      ',"stock":' || :OLD.stock ||
                      ',"category":"' || :OLD.category || '"}';
END IF;

    IF
INSERTING OR UPDATING THEN
        v_new_data := '{"id":' || :NEW.id ||
                      ',"name":"' || :NEW.name ||
                      ',"description":"' || :NEW.description ||
                      ',"price":' || :NEW.price ||
                      ',"stock":' || :NEW.stock ||
                      ',"category":"' || :NEW.category || '"}';
END IF;

INSERT INTO system_audit (table_name,
                          operation,
                          record_id,
                          changed_by,
                          old_data,
                          new_data)
VALUES ('PRODUCT',
        v_operation,
        NVL(:NEW.id, :OLD.id),
        USER,
        v_old_data,
        v_new_data);
END;
/


CREATE
OR REPLACE TRIGGER trg_system_audit_customer
AFTER INSERT OR
UPDATE OR
DELETE
ON customer
    FOR EACH ROW
DECLARE
v_old_data CLOB;
v_new_data
CLOB;
v_operation
VARCHAR2(10);
BEGIN

    IF
INSERTING THEN
        v_operation := 'INSERT';
    ELSIF
UPDATING THEN
        v_operation := 'UPDATE';
    ELSIF
DELETING THEN
        v_operation := 'DELETE';
END IF;

    IF
DELETING OR UPDATING THEN
        v_old_data := '{"id":' || :NEW.id ||
                      ',"name":"' || :NEW.name ||
                      ',"email":"' || :NEW.email ||
                      ',"phone":' || :NEW.phone || '"}';
END IF;

    IF
INSERTING OR UPDATING THEN
        v_new_data := '{"id":' || :NEW.id ||
                      ',"name":"' || :NEW.name ||
                      ',"email":"' || :NEW.email ||
                      ',"phone":' || :NEW.phone || '"}';
END IF;

INSERT INTO system_audit (table_name,
                          operation,
                          record_id,
                          changed_by,
                          old_data,
                          new_data)
VALUES ('CUSTOMER',
        v_operation,
        NVL(:NEW.id, :OLD.id),
        USER,
        v_old_data,
        v_new_data);
END;
/

CREATE OR REPLACE TRIGGER trg_system_audit_sale
AFTER INSERT OR UPDATE OR DELETE ON sale
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
                  ',"customer_id":' || :OLD.customer_id ||
                  ',"system_user_id":' || :OLD.system_user_id ||
                  ',"total":' || :OLD.total ||
                  ',"created_at":"' || TO_CHAR(:OLD.created_at, 'YYYY-MM-DD"T"HH24:MI:SS') || '"}';
END IF;

  IF INSERTING OR UPDATING THEN
    v_new_data := '{"id":' || :NEW.id ||
                  ',"customer_id":' || :NEW.customer_id ||
                  ',"system_user_id":' || :NEW.system_user_id ||
                  ',"total":' || :NEW.total ||
                  ',"created_at":"' || TO_CHAR(:NEW.created_at, 'YYYY-MM-DD"T"HH24:MI:SS') || '"}';
END IF;

INSERT INTO system_audit (table_name, operation, record_id, changed_by, old_data, new_data)
VALUES ('SALE', v_operation, NVL(:NEW.id, :OLD.id), USER, v_old_data, v_new_data);
END;
/

CREATE OR REPLACE TRIGGER trg_system_audit_sale_detail
AFTER INSERT OR UPDATE OR DELETE ON sale_detail
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
                  ',"sale_id":' || :OLD.sale_id ||
                  ',"product_id":' || :OLD.product_id ||
                  ',"quantity":' || :OLD.quantity ||
                  ',"price":' || :OLD.price ||
                  ',"subtotal":' || :OLD.subtotal || '}';
END IF;

  IF INSERTING OR UPDATING THEN
    v_new_data := '{"id":' || :NEW.id ||
                  ',"sale_id":' || :NEW.sale_id ||
                  ',"product_id":' || :NEW.product_id ||
                  ',"quantity":' || :NEW.quantity ||
                  ',"price":' || :NEW.price ||
                  ',"subtotal":' || :NEW.subtotal || '}';
END IF;

INSERT INTO system_audit (table_name, operation, record_id, changed_by, old_data, new_data)
VALUES ('SALE_DETAIL', v_operation, NVL(:NEW.id, :OLD.id), USER, v_old_data, v_new_data);
END;
/


CREATE
OR REPLACE TRIGGER trg_system_audit_supplier
AFTER INSERT OR
UPDATE OR
DELETE
ON supplier
    FOR EACH ROW
DECLARE
v_old_data CLOB;
v_new_data
CLOB;
v_operation
VARCHAR2(10);
BEGIN

    IF
INSERTING THEN
        v_operation := 'INSERT';
    ELSIF
UPDATING THEN
        v_operation := 'UPDATE';
    ELSIF
DELETING THEN
        v_operation := 'DELETE';
END IF;

    IF
DELETING OR UPDATING THEN
        v_old_data := '{"id":' || :NEW.id ||
                      ',"name":"' || :NEW.name ||
                      ',"email":"' || :NEW.email ||
                      ',"phone":' || :NEW.phone || '"}';
END IF;

    IF
INSERTING OR UPDATING THEN
        v_new_data := '{"id":' || :NEW.id ||
                      ',"name":"' || :NEW.name ||
                      ',"email":"' || :NEW.email ||
                      ',"phone":' || :NEW.phone || '"}';
END IF;

INSERT INTO system_audit (table_name,
                          operation,
                          record_id,
                          changed_by,
                          old_data,
                          new_data)
VALUES ('SUPPLIER',
        v_operation,
        NVL(:NEW.id, :OLD.id),
        USER,
        v_old_data,
        v_new_data);
END;
/