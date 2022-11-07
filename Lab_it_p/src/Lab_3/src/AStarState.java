import java.util.HashMap;

/**
 * Данный класс поддерживает наборы открытых и закрытых вершин, поэтому он обеспечивает
 * основную функциональность для реализации алгоритма А*
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map.  This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints."  In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState
{
    //поля для открытых и закрытых вершин и их инициализация
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
     Добавляет указанную вершину только в том случае, если существующая вершина хуже новой.
     - Если в наборе открытых вершин в настоящее время нет вершины для данного местоположения,
     то необходимо просто добавить новую вершину
     - Если в наборе открытых вершин уже есть вершина для этой локации,
     то добавляем новую вершину только в том случае если стоимость пути до новой вершины меньше стоимости пути до текущей
     Если путь через новую вершину короче, то добавляем новую вершину

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
     * Перемещает вершину из набора открытых в закрытые.
     **/
    public void closeWaypoint(Location loc)
    {
        Waypoint new_close = openWaypoints.remove(loc);
        if (new_close != null)
            closeWaypoints.put(loc, new_close);
    }

    /**
     * Возвращает значение true если указанное местоположение встречается
     * в наборе закрытых вершин и false в противном случае.
     **/
    public boolean isLocationClosed(Location loc)
    {
        return closeWaypoints.containsKey(loc);
    }
}