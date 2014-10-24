package net.developersguild.maps.pathfinding;

public class PathSegment {
	
	public final Node source, destination;
	
	public final String direction;
	
	public final double distance;
	
	public static PathSegment create(Node n1, Node n2, double length, String desc){
		return new PathSegment(n1, n2, length, desc);
	}
	
	private PathSegment(Node node, Node n, double lengthMeters, String description) {
		source=node;
		destination=n;
		distance=lengthMeters;
		direction=description;
	}
	
	public String toString(){
		return source+"  -->  "+destination +" ("+distance+"): "+direction;
	}
	
}
