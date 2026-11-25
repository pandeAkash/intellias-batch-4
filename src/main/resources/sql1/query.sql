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


SELECT * FROM SHIPMENTS;

/*SELECT island_name, total_teu, port_count, avg_teu_per_port FROM PORTS;
WHERE total_teu = NOT NULL;
*/



SELECT
    p.island_name,
    SUM(s.teu) AS total_teu,
    COUNT(DISTINCT p.port_id) AS port_count,
    SUM(s.teu) / COUNT(DISTINCT p.port_id) AS avg_teu_per_port
FROM PORTS p
JOIN SHIPMENTS s
    ON p.port_id = s.port_id
GROUP BY p.island_name
HAVING COUNT(DISTINCT p.port_id) > 5;