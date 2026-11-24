CREATE TABLE IF NOT EXISTS PORTS
(
    port_id     int         NOT NULL,
    island_name varchar(50) NOT NULL,
    port_name   varchar(50) NOT NULL,
    berth_count int         not null,
    PRIMARY KEY (port_id)
);

CREATE TABLE IF NOT EXISTS SHIPMENTS
(
    shipment_id int  NOT NULL,
    port_id     int  not null,
    teu         int  NOT NULL,
    shipped_at  date not null,
    PRIMARY KEY (shipment_id)
);
