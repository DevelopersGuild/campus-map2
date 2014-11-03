package net.developersguild.campus_map.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by planetguy on 11/3/2014.
 */
public class DestinationRegistry {

    static ExecutorService threads= Executors.newCachedThreadPool();

    private ArrayList<DestinationNode> nodes= new ArrayList<DestinationNode>();

    public void add(DestinationNode node){
        synchronized(nodes){
            nodes.add(node);
        }
    }

    /**
     * Naively searches the destination nodes for those that contain the query, and returns all that
     * match sorted by how close they are to the coords.
     * @param query the text to search for
     * @param latitude the
     * @param longitude
     * @return
     */
    public ArrayList<DestinationNode> dumbSearch(String query, final double latitude, final double longitude){
        ArrayList<DestinationNode> newList=new ArrayList<DestinationNode>();
        synchronized(nodes) {
            for (DestinationNode n : nodes) {
                if (n.name.contains(query))
                    newList.add(n);
            }
        }
        //sort
        Collections.sort(newList, new Comparator<DestinationNode>() {

            @Override
            public int compare(DestinationNode n1, DestinationNode n2) {
                //reversed - sort by largest-to-smallest.
                return Double.compare(
                        n2.distanceSquaredTo(latitude,longitude),
                        n1.distanceSquaredTo(latitude,longitude)
                );
            }
        });
        return newList;
    }

}
