CREATE SEQUENCE sale.order_id_seq;

CREATE TABLE sale.orders
(
    id       BIGINT DEFAULT nextval('sale.order_id_seq') PRIMARY KEY,
    user_id  BIGINT         NOT NULL,
    product  VARCHAR(255)   NOT NULL,
    quantity INT            NOT NULL,
    price    DECIMAL(10, 2) NOT NULL,
    status   VARCHAR(50)    NOT NULL
);
