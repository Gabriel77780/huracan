CREATE OR REPLACE VIEW sale_summary_view AS
SELECT
    s.id AS sale_id,
    c.name AS customer_name,
    s.total AS total_amount,
    TO_CHAR(s.created_at, 'YYYY-MM-DD HH24:MI:SS') AS sale_date
FROM sale s
         JOIN customer c ON s.customer_id = c.id
    /

CREATE OR REPLACE PROCEDURE get_sale_summary_by_id_sp(
  p_sale_id       IN  NUMBER,
  p_customer_name OUT VARCHAR2,
  p_total_amount  OUT NUMBER,
  p_sale_date     OUT VARCHAR2
) AS
BEGIN
SELECT
    customer_name,
    total_amount,
    sale_date
INTO
    p_customer_name,
    p_total_amount,
    p_sale_date
FROM sale_summary_view
WHERE sale_id = p_sale_id;
END;
