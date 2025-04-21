CREATE OR REPLACE PACKAGE system_user_pkg AS
  PROCEDURE create_system_user_sp(
    p_name     IN VARCHAR2,
    p_email    IN VARCHAR2,
    p_password IN VARCHAR2,
    p_role     IN VARCHAR2
  );

  PROCEDURE get_system_user_by_id_sp(
    p_id         IN  NUMBER,
    p_name       OUT VARCHAR2,
    p_email      OUT VARCHAR2,
    p_password   OUT VARCHAR2,
    p_role       OUT VARCHAR2,
    p_created_at OUT TIMESTAMP
  );

  PROCEDURE update_system_user_sp(
    p_id       IN NUMBER,
    p_name     IN VARCHAR2,
    p_email    IN VARCHAR2,
    p_password IN VARCHAR2,
    p_role     IN VARCHAR2
  );

  PROCEDURE delete_system_user_sp(
    p_id IN NUMBER
  );

       PROCEDURE get_all_system_user_sp(
        p_system_user_cursor OUT SYS_REFCURSOR
    );

END system_user_pkg;
/


CREATE OR REPLACE PACKAGE BODY system_user_pkg AS

  PROCEDURE create_system_user_sp(
    p_name     IN VARCHAR2,
    p_email    IN VARCHAR2,
    p_password IN VARCHAR2,
    p_role     IN VARCHAR2
  ) IS
BEGIN
INSERT INTO system_user (
    name, email, password, role, created_at
) VALUES (
             p_name,
             p_email,
             p_password,
             p_role,
             SYSTIMESTAMP
         );
END create_system_user_sp;

  PROCEDURE get_system_user_by_id_sp(
    p_id         IN  NUMBER,
    p_name       OUT VARCHAR2,
    p_email      OUT VARCHAR2,
    p_password   OUT VARCHAR2,
    p_role       OUT VARCHAR2,
    p_created_at OUT TIMESTAMP
  ) IS
BEGIN
SELECT name, email, password, role, created_at
INTO   p_name, p_email, p_password, p_role, p_created_at
FROM   system_user
WHERE  id = p_id;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
      p_name := NULL;
      p_email := NULL;
      p_password := NULL;
      p_role := NULL;
      p_created_at := NULL;
END get_system_user_by_id_sp;

  PROCEDURE update_system_user_sp(
    p_id       IN NUMBER,
    p_name     IN VARCHAR2,
    p_email    IN VARCHAR2,
    p_password IN VARCHAR2,
    p_role     IN VARCHAR2
  ) IS
BEGIN
UPDATE system_user
SET name     = p_name,
    email    = p_email,
    password = p_password,
    role     = p_role
WHERE id = p_id;
END update_system_user_sp;

  PROCEDURE delete_system_user_sp(
    p_id IN NUMBER
  ) IS
BEGIN
DELETE FROM system_user WHERE id = p_id;
END delete_system_user_sp;

    PROCEDURE get_all_system_user_sp(
        p_system_user_cursor OUT SYS_REFCURSOR
    ) AS
BEGIN
OPEN p_system_user_cursor FOR
SELECT * FROM system_user_view;
END get_all_system_user_sp;


END system_user_pkg;
/
