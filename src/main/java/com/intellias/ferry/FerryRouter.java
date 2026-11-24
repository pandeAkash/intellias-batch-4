package com.intellias.ferry;

import java.util.List;
import java.util.Optional;

/**
 * Finds the nearest port a ferry could sail to from a given coordinate.
 *
 * <p>A ferry only runs between different islands, so the port returned must lie on an
 * island other than the one the passenger is standing on.
 */
public class FerryRouter {

    /**
     * @param islands list of islands with their polygon borders. Islands may touch, so a
     *                coordinate on a shared border lies on more than one island.
     * @param ports   list of ports. A port that does not lie on any island is not
     *                reachable by ferry.
     */
    public FerryRouter(List<Island> islands, List<Port> ports) {
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }
}
