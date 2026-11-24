package com.intellias.ferry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FerryRouterTest {

    // Four islands meeting at the origin. Alba shares a whole edge with Corin (the
    // y-axis from 0 to 100) and another with Dunia (the x-axis from 0 to 100).
    Island alba = new Island("Alba", List.of(
            new Coordinate(0, 0),
            new Coordinate(100, 0),
            new Coordinate(100, 100),
            new Coordinate(0, 100),
            new Coordinate(0, 0)
    ));

    Island brava = new Island("Brava", List.of(
            new Coordinate(0, 0),
            new Coordinate(-100, 0),
            new Coordinate(-100, -100),
            new Coordinate(0, -100),
            new Coordinate(0, 0)
    ));

    Island corin = new Island("Corin", List.of(
            new Coordinate(0, 0),
            new Coordinate(-100, 0),
            new Coordinate(-100, 100),
            new Coordinate(0, 100),
            new Coordinate(0, 0)
    ));

    Island dunia = new Island("Dunia", List.of(
            new Coordinate(0, 0),
            new Coordinate(100, 0),
            new Coordinate(100, -100),
            new Coordinate(0, -100),
            new Coordinate(0, 0)
    ));

    Port portAlba = new Port("Port Alba", new Coordinate(10, 50));            // on Alba
    Port portCorin = new Port("Port Corin", new Coordinate(-10, 50));         // on Corin
    Port portCorinSouth = new Port("Port Corin South", new Coordinate(-5, 5)); // on Corin
    Port portDunia = new Port("Port Dunia", new Coordinate(50, -10));         // on Dunia
    Port portDuniaEast = new Port("Port Dunia East", new Coordinate(90, -5));  // on Dunia
    Port portBrava = new Port("Port Brava", new Coordinate(-50, -50));        // on Brava
    Port portAlbaEast = new Port("Port Alba East", new Coordinate(95, 50));    // on Alba
    Port portMirage = new Port("Port Mirage", new Coordinate(10, 105));       // on NO island

    List<Island> allIslands;
    List<Port> allPorts;
    FerryRouter ferryRouter;

    @BeforeEach
    void setUp() {
        allIslands = List.of(alba, brava, corin, dunia);
        allPorts = List.of(portAlba, portCorin, portCorinSouth, portDunia, portDuniaEast,
                portBrava, portAlbaEast, portMirage);

        ferryRouter = new FerryRouter(allIslands, allPorts);
    }

    @Test
    void testSkipsPortOnTheSameIsland() {
        // From (5, 50), strictly inside Alba. Port Alba is only 5 units away but sits on
        // the same island, so the answer is Port Corin at 15 units.
        Optional<Port> result = ferryRouter.findNearestConnectingPort(new Coordinate(5, 50));

        assertTrue(result.isPresent(), "Expected to find a port on another island");
        assertEquals("Port Corin", result.get().getName(),
                "Port Alba is nearer but lies on the same island, so it must be skipped");
    }

    @Test
    void testFindsPortAcrossTheSouthernBorder() {
        // From (90, 5), inside Alba. Port Dunia East is 10 units away on Dunia.
        Optional<Port> result = ferryRouter.findNearestConnectingPort(new Coordinate(90, 5));

        assertTrue(result.isPresent(), "Expected to find an port on another island");
        assertEquals("Port Dunia East", result.get().getName());
    }

    @Test
    void testFindsPortFromBrava() {
        // From (-5, -5), inside Brava. Port Corin South is 10 units away on Corin.
        Optional<Port> result = ferryRouter.findNearestConnectingPort(new Coordinate(-5, -5));

        assertTrue(result.isPresent(), "Expected to find a port on another island");
        assertEquals("Port Corin South", result.get().getName());
    }

    @Test
    void testCoordinateOnSharedBorderBelongsToBothIslands() {
        // (0, 50) lies on the border Alba and Corin share, so the passenger is on BOTH
        // islands. Every port on Alba or Corin is therefore the "same island", and the
        // nearest remaining port (Port Dunia, ~78 units) is beyond the 20-unit limit.
        //
        // Picking just the first matching island would treat the other one as a valid
        // destination and wrongly return a port roughly 10 units away.
        Optional<Port> result = ferryRouter.findNearestConnectingPort(new Coordinate(0, 50));

        assertTrue(result.isEmpty(),
                "A coordinate on a shared border lies on both islands, so neither "
                        + "island's ports are a valid destination");
    }

    @Test
    void testCoordinateOnCornerSharedByAllIslands() {
        // The origin is covered by all four islands, so there is no "different island".
        Optional<Port> result = ferryRouter.findNearestConnectingPort(new Coordinate(0, 0));

        assertTrue(result.isEmpty(), "Every island contains the origin, so no port qualifies");
    }

    @Test
    void testNearestPortTooFarReturnsEmpty() {
        // (50, 50) is inside Alba. The nearest port on another island is 60 units away.
        Optional<Port> result = ferryRouter.findNearestConnectingPort(new Coordinate(50, 50));

        assertTrue(result.isEmpty(), "Expected empty because the nearest port exceeds 20 units");
    }

    @Test
    void testPortNotOnAnyIslandIsIgnored() {
        // From (10, 95), inside Alba. Port Mirage at (10, 105) is only 10 units away but
        // lies outside every island, so it is not on a "different island" and cannot be
        // reached by ferry. The nearest qualifying port is ~49 units away, so the result
        // is empty.
        Optional<Port> result = ferryRouter.findNearestConnectingPort(new Coordinate(10, 95));

        assertTrue(result.isEmpty(),
                "A port that lies on no island at all must not be returned");
    }

    @Test
    void testDepartureInOpenWaterNearAPortReturnsEmpty() {
        // (110, 50) is just off Alba's east coast, on no island at all. Port Alba East
        // is only 15 units away, but a passenger standing in open water cannot board a
        // ferry, so the result is empty rather than that port.
        Optional<Port> result = ferryRouter.findNearestConnectingPort(new Coordinate(110, 50));

        assertTrue(result.isEmpty(),
                "A departure point on no island must return empty even when a port is "
                        + "well within 20 units");
    }

    @Test
    void testDepartureOutsideAllIslandsReturnsEmpty() {
        Optional<Port> result = ferryRouter.findNearestConnectingPort(new Coordinate(1000, 1000));

        assertTrue(result.isEmpty(), "A passenger in open water cannot board a ferry");
    }
}
