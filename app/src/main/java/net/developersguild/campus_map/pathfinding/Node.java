package net.developersguild.campus_map.pathfinding;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;


public class Node {
	
	private final HashMap<Node, PathSegment> paths=new HashMap<Node, PathSegment>();
	
	private final double x, y;
	
	public Node(double x, double y){
		this.x=x;
		this.y=y;
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
		return distanceSquaredTo(n1.getX(), n1.getY());
	}

	public double distanceSquaredTo(double latitude, double longitude) {
		return (getX()-latitude)*(getX()-latitude)+(getY()-longitude)*(getY()-longitude);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	
	public String toString(){
		return "N@("+x+","+y+")";
	}
	
}
