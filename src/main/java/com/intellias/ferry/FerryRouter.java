package com.intellias.ferry;

import java.util.List;
import java.util.Optional;

/**
 * Finds the nearest port a ferry could sail to from a given coordinate.
 *
 * <p>A ferry only runs between different islands, so the port returned must lie on an
 * island other than the one the passenger is standing on.
 */
public class FerryRouter implements findNearestConnectingPort {

    /**
     *   list of ports. A port that does not lie on any island is not
     *                reachable by ferry.
     */
    private  List (list<Ports> ports);
    private  List (list<Island> island);

    public optional <ports> findNearestConnectingPort (list<ports>ports , list<island> islands){
        if(coordinate = null){
            throw new IlligalExceptionHandaling(" coordinate not null");
        }

    }

    // public FerryRouter(List<Island> islands, List<Port> ports) {
    //     throw new NotImplementedException()

     //find nearest port      
    Selectedport  port:ports;
    for(port ports : ports){
        polygon polygon=new polygon (island.boundries){
            ifselected port=port
             break;
        }  
    }
    // not find port
     if (selectedport==null){
        return Optional.empty();
     }

   //create polygon
     polygon polygon = new polygon(selectedport.boundries())

     // nearest port
     port= nearestport;
     double mindistance =MAX.VALUE(){
        continue;
     }
     if(!polygon.insidecoordinate().getposition){
        continue;
     }


     //cout distance
     double dx= coordinate.getX() - port.getposition().getX();
     double dY= coordinate.getY() - port.getposition().getY();
     
     distance = dx * dx + dY * dY ;
     
     if(distance < mindistance){
        mindistance=distance;
        Nearestport=port;
     }
   return optional.nearest{

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
    // public Optional<Port> findNearestConnectingPort(Coordinate from) {
    //     throw new NotImplementedException();
    // }
}
