"""select p.islands,s.teu 
from PORTS p 
JOIN SHIPMENTS s 
on
where islands > 5""";

"""select islands,count(*)port_name from Ports
""";

"""select count(port_name)
where islands > 5"""

"""select SUM(teu) as total_teu
from SHIPMENTS""";

"""select AVG(teu)from SHIPMENTS""";