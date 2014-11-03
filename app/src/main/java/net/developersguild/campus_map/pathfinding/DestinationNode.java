package net.developersguild.campus_map.pathfinding;

/**
 * Created by planetguy on 11/3/2014.
 */
public class DestinationNode extends Node{

    public final String name;

    public DestinationNode(NavMap nm, String name, double x, double y) {
        super(nm,x, y);
        this.name=name;
        nm.addDestination(this);
    }
}
