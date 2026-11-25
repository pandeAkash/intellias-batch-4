# Exercise 4

## Java: Ferry Connection Finder

### Context and Problem Statement

You are given a list of islands with their names and border information in the form of
coordinate lists, plus a list of ports with their coordinates.

A ferry only ever runs **between different islands**. Given the coordinate a passenger
departs from, find the nearest port they could sail to — that is, the nearest port lying
on an island **other than** the one they are standing on, and only if it is within
**20 distance units**.

Two things about the geography matter:

- **Islands can touch.** A coordinate sitting on a border shared by two islands lies on
  *both* of them. In that case neither island's ports are a valid destination.
- **Not every port is on an island.** A port outside every island cannot be reached by
  ferry and must be ignored.

A passenger whose departure coordinate is not on any island is in open water and cannot
board a ferry at all.

There is already an implemented [Polygon] class that can check whether a point is inside
an island.

### Implementation Requirements

- Read the existing codebase before starting
- Implement the constructor and `findNearestConnectingPort` in [FerryRouter]
- In the constructor: store the islands and ports in whatever form you need
- In `findNearestConnectingPort`:
  - Find every island the departure coordinate belongs to (there may be more than one if
    it sits on a shared border)
  - If the departure coordinate belongs to **no** island, return empty
  - Discard any port that does not lie on any island
  - Discard any port that lies on one of the passenger's islands (same island = invalid
    destination); if the passenger is on a shared border, ports on *all* of those islands
    are discarded
  - From the remaining ports, find the nearest one; if its distance is **≤ 20**, return
    it, otherwise return empty

### Restrictions

You should not change the signature of existing methods, but you may introduce your own.

[Polygon]: src/main/java/com/intellias/ferry/Polygon.java
[FerryRouter]: src/main/java/com/intellias/ferry/FerryRouter.java

---

## SQL: Island Port Throughput Report

You are given two tables: `PORTS` and `SHIPMENTS`.

Write a SQL query that returns, for each island, the total TEU shipped, the number of
ports serving that island, and the average TEU per port — only for islands served by
**more than 5** ports.

Write your solution in: `src/main/resources/sql1/query.sql`

### Schema structure

```sql
CREATE TABLE IF NOT EXISTS PORTS
(
    port_id     int         NOT NULL,
    island_name varchar(50) NOT NULL,
    port_name   varchar(50) NOT NULL,
    berth_count int         NOT NULL,
    PRIMARY KEY (port_id)
);

CREATE TABLE IF NOT EXISTS SHIPMENTS
(
    shipment_id int  NOT NULL,
    port_id     int  NOT NULL,
    teu         int  NOT NULL,
    shipped_at  date NOT NULL,
    PRIMARY KEY (shipment_id)
);
```

### Expected query structure

```shell
select island_name, total_teu, port_count, avg_teu_per_port
...
```

Where:
- `total_teu` = sum of `teu` across all shipments from that island's ports
- `port_count` = the number of **distinct ports** on that island
- `avg_teu_per_port` = `total_teu / port_count` — per port, not per shipment
- Only include islands where `port_count > 5`

Each port has many shipments — make sure `port_count` counts ports, not shipment rows.

### How to run

```bash
./gradlew test
```

