CREATE
OR REPLACE TRIGGER trg_system_audit_supplier
AFTER INSERT OR
UPDATE OR
DELETE
ON trg_system_audit_supplier
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