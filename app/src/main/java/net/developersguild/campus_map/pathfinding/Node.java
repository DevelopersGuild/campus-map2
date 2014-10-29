package net.developersguild.campus_map.pathfinding;
import java.util.Collection;
import java.util.HashMap;

/**
 * A Node is a place you can go to. It has coordinates in 2D space and connections to other nodes.
 */
public class Node {
	
	private final HashMap<Node, PathSegment> paths=new HashMap<Node, PathSegment>();

	private final double latitude, longitude;
	
	public Node(double x, double y){
		this.latitude =x;
		this.longitude =y;
	}
	
	public void addPathToNode(Node n, double d, String description){
		paths.put(n, PathSegment.create(this, n, d, description));
	}
	
	public PathSegment getPathToNeighbouringNode(Node n){
		return paths.get(n);
	}
	
	public Collection<PathSegment> getPaths(){
		return paths.values();
	}

	public double distanceSquaredTo(Node n1) {
		return distanceSquaredTo(n1.getLatitude(), n1.getLongitude());
	}

	public double distanceSquaredTo(double latitude, double longitude) {
		return (getLatitude()-latitude)*(getLatitude()-latitude)
                +(getLongitude()-longitude)*(getLongitude()-longitude);
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
	
	public String toString(){
		return "N@("+ latitude +","+ longitude +")";
	}
	
}
