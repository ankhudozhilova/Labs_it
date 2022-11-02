import java.util.HashMap;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    private final HashMap<Location, Waypoint> openWaypoints = new HashMap<>();
    private final HashMap<Location, Waypoint> closeWaypoints = new HashMap<>();

    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;


    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map)
    {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap()
    {
        return map;
    }

    /**
     * Проверяет все вершины из набора открытых вершин и после возвращает ссылку на вершину
     * с наименьшей общей стоимостью. Если в открытом наборе нет вершин то возвращает null.
     **/
    public Waypoint getMinOpenWaypoint()
    {
        if (numOpenWaypoints() == 0)
            return null;

        Waypoint minWaypoint = null;

        float mn = Float.MAX_VALUE;

        for(Waypoint waypoint : openWaypoints.values()) {
            float cost = waypoint.getTotalCost();
            if(cost < mn) {
                mn = cost;
                minWaypoint = waypoint;
            }
        }
        return minWaypoint;
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection.  If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection.  However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP)
    {
        Location loc = newWP.getLocation();
        if (openWaypoints.containsKey(loc) && newWP.getPreviousCost() > openWaypoints.get(loc).getPreviousCost())
            return false;
        else {
            openWaypoints.put(loc, newWP);
            return true;
        }
    }


    /** Возвращает количество точек в наборе открытых вершин **/
    public int numOpenWaypoints()
    {
        return openWaypoints.size();
    }


    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint new_close = openWaypoints.remove(loc);
        if (new_close != null)
            closeWaypoints.put(loc, new_close);
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closeWaypoints.containsKey(loc);
    }
}
