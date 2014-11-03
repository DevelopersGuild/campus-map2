package net.developersguild.campus_map.pathfinding;

import java.util.ArrayList;
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

    public ArrayList<DestinationNode> dumbSearch(String query){
        ArrayList<DestinationNode> newList=new ArrayList<DestinationNode>();
        synchronized(nodes){
            //todo search for matches to query
        }
        return newList;
    }

}
