CREATE OR REPLACE PROCEDURE create_purchase_sp (
    p_supplier_id    IN  NUMBER,
    p_system_user_id IN  NUMBER,
    p_total          IN  NUMBER,
    p_purchase_id        OUT NUMBER
) IS
BEGIN
INSERT INTO purchase (supplier_id, system_user_id, total)
VALUES (p_supplier_id, p_system_user_id, p_total)
    RETURNING id INTO p_purchase_id;
END;
/


CREATE OR REPLACE PROCEDURE create_purchase_detail_sp (
    p_purchase_id    IN NUMBER,
    p_product_id IN NUMBER,
    p_quantity   IN NUMBER,
    p_price      IN NUMBER,
    p_subtotal   IN NUMBER
) IS
BEGIN
INSERT INTO purchase_detail (
    purchase_id,
    product_id,
    quantity,
    price,
    subtotal
) VALUES (
             p_purchase_id,
             p_product_id,
             p_quantity,
             p_price,
             p_subtotal
         );
END;
/