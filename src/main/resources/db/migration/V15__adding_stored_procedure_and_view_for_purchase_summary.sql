CREATE OR REPLACE VIEW purchase_summary_view AS
SELECT
    s.id AS purchase_id,
    c.name AS supplier_name,
    s.total AS total_amount,
    TO_CHAR(s.created_at, 'YYYY-MM-DD HH24:MI:SS') AS sale_date
FROM purchase s
         JOIN supplier c ON s.supplier_id = c.id
    /

CREATE OR REPLACE PROCEDURE get_purchase_summary_by_id_sp(
  p_purchase_id       IN  NUMBER,
  p_supplier_name OUT VARCHAR2,
  p_total_amount  OUT NUMBER,
  p_sale_date     OUT VARCHAR2
) AS
BEGIN
SELECT
    supplier_name,
    total_amount,
    sale_date
INTO
    p_supplier_name,
    p_total_amount,
    p_sale_date
FROM purchase_summary_view
WHERE purchase_id = p_purchase_id;
END;
