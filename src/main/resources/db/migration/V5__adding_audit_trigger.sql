CREATE OR REPLACE TRIGGER trg_audit_product
AFTER INSERT OR UPDATE OR DELETE ON product
    FOR EACH ROW
DECLARE
v_old_data CLOB;
    v_new_data CLOB;
BEGIN

    IF DELETING OR UPDATING THEN
        v_old_data := '{"id":' || :OLD.id ||
                      ',"name":"' || :OLD.name ||
                      '","description":"' || :OLD.description ||
                      '","price":' || :OLD.price ||
                      ',"stock":' || :OLD.stock ||
                      ',"category":"' || :OLD.category || '"}';
END IF;

    IF INSERTING OR UPDATING THEN
        v_new_data := '{"id":' || :NEW.id ||
                      ',"name":"' || :NEW.name ||
                      '","description":"' || :NEW.description ||
                      '","price":' || :NEW.price ||
                      ',"stock":' || :NEW.stock ||
                      ',"category":"' || :NEW.category || '"}';
END IF;

INSERT INTO audit (
    table_name,
    operation,
    record_id,
    changed_by,
    old_data,
    new_data
) VALUES (
             'PRODUCT',
             CASE
                 WHEN INSERTING THEN 'INSERT'
                 WHEN UPDATING THEN 'UPDATE'
                 WHEN DELETING THEN 'DELETE'
                 END,
             NVL(:NEW.id, :OLD.id),
             USER,
             v_old_data,
             v_new_data
         );
END;
/
