package net.developersguild.campus_map.pathfinding;

/**
 * Created by planetguy on 10/31/2014.
 */
public class DeAnzaMap {

    public static NavMap init(){ //test
        NavMap map=new NavMap(37.322973, -122.049368, 37.315627, -122.041518);

        Node n=new Node(37.322238, -122.046353);

        Node n2=new Node(37.322208, -122.045463);
        n.addPathToNode(n2, "Walk east behind the art buildings.");
        n2.addPathToNode(n, "Walk west behind the art buildings.");

        Node n3=new Node(37.321867, -122.046294);
        n3.addPathToNode(n, "Walk north between the Flint Center and music building.");
        n.addPathToNode(n3, "Walk north between the Flint Center and music building.");

        Node n4=new Node(37.321858, -122.045993);
        n4.addPathToNode(n3, "Walk west towards the Flint Center auditorium.");
        n3.addPathToNode(n4, "Walk east towards the quad.");

        Node n5=new Node(37.321884, -122.045446);
        n5.addPathToNode(n4, "Walk west towards the quad.");
        n4.addPathToNode(n5, "Walk east towards the access road.");
        n2.addPathToNode(n5, "Walk south beside the classrooms.");
        n4.addPathToNode(n2, "Walk north beside the classrooms.");

        return map;
    }
}
