CREATE OR REPLACE PROCEDURE create_sale_sp (
    p_customer_id    IN  NUMBER,
    p_system_user_id IN  NUMBER,
    p_total          IN  NUMBER,
    p_sale_id        OUT NUMBER
) IS
BEGIN
INSERT INTO sale (customer_id, system_user_id, total)
VALUES (p_customer_id, p_system_user_id, p_total)
    RETURNING id INTO p_sale_id;
END;
/


CREATE OR REPLACE PROCEDURE create_sale_detail_sp (
    p_sale_id    IN NUMBER,
    p_product_id IN NUMBER,
    p_quantity   IN NUMBER,
    p_price      IN NUMBER,
    p_subtotal   IN NUMBER
) IS
BEGIN
INSERT INTO sale_detail (
    sale_id,
    product_id,
    quantity,
    price,
    subtotal
) VALUES (
             p_sale_id,
             p_product_id,
             p_quantity,
             p_price,
             p_subtotal
         );
END;
/