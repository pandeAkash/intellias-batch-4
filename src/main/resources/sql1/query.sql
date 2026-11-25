
    SELECT 
         p.port_id,s.shipment_name
    SUM(s.shipments)AS total_teu
    COUNT(DISTINCT(p.ports))As island
    AVG(p.ports)As avg_teu_per_port
    FROM ports
    JOIN shipments
    ON p.port_id = s.port_name
    HAVING COUNT (DISTINCT port_count > 5);