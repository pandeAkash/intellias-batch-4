SELECT p.island_name, SUM(s.teu) AS total_teu, COUNT(DISTINCT p.port_id) AS port_count, SUM(s.teu) / COUNT(DISTINCT p.port_id) AS avg_teu_per_port
FROM PORTS p
JOIN SHIPMENTS s ON p.port_id = s.port_id
GROUP BY p.island_name
HAVING COUNT(DISTINCT p.port_id) > 5;