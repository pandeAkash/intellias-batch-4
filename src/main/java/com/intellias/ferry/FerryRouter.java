package com.intellias.ferry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Finds the nearest port a ferry could sail to from a given coordinate.
 *
 * <p>A ferry only runs between different islands, so the port returned must lie on an
 * island other than the one the passenger is standing on.
 */
public class FerryRouter {

    private static final double MAX_FERRY_DISTANCE = 20.0;

    private final List<Polygon> islandPolygons;
    private final List<Port> ports;

    /**
     * @param islands list of islands with their polygon borders. Islands may touch, so a
     *                coordinate on a shared border lies on more than one island.
     * @param ports   list of ports. A port that does not lie on any island is not
     *                reachable by ferry.
     */
    public FerryRouter(List<Island> islands, List<Port> ports) {
        throw new NotImplementedException();
        this.islandPolygons = new ArrayList<>(islands.size());
        for (Island island : islands) {
            islandPolygons.add(new Polygon(island.getBorders()));
        }
        this.ports = new ArrayList<>(ports);
    }

    /**
     * Should find the nearest port that lies on a different island than {@code from},
     * if it is within 20 distance units.
     *
     * @param from location the passenger departs from
     * @return the nearest reachable port, or empty if {@code from} is not on any island,
     *         if no port lies on a different island, or if the nearest such port is more
     *         than 20 distance units away
     */
    public Optional<Port> findNearestConnectingPort(Coordinate from) {
        boolean[] departureIslands = new boolean[islandPolygons.size()];
        boolean isOnIsland = false;

        for (int i = 0; i < islandPolygons.size(); i++) {
            if (islandPolygons.get(i).isCoordinateInside(from)) {
                departureIslands[i] = true;
                isOnIsland = true;
            }
        }

        if (!isOnIsland) {
            return Optional.empty();
        }

        Port nearestPort = null;
        double nearestDistance = Double.POSITIVE_INFINITY;

        for (Port port : ports) {
            boolean isOnIslandPort = false;
            boolean isOnDepartureIsland = false;

            for (int i = 0; i < islandPolygons.size(); i++) {
                if (islandPolygons.get(i).isCoordinateInside(port.getPosition())) {
                    isOnIslandPort = true;
                    if (departureIslands[i]) {
                        isOnDepartureIsland = true;
                        break;
                    }
                }
            }

            if (!isOnIslandPort || isOnDepartureIsland) {
                continue;
            }

            double distance = Math.hypot(
                    from.getX() - port.getPosition().getX(),
                    from.getY() - port.getPosition().getY());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPort = port;
            }
        }

        return nearestPort != null && nearestDistance <= MAX_FERRY_DISTANCE
                ? Optional.of(nearestPort)
                : Optional.empty();
    }
}
