package net.developersguild.maps.pathfinding;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PathFinder {
	
	public static List<PathSegment> pathBetweenFast(Node n1, Node n2){
		PriorityQueue<SummedPath> queue=new PriorityQueue<SummedPath>();
		for(PathSegment p:n1.getPaths()){
			queue.add(new SummedPath(p, n2));
		}
		while(!queue.isEmpty()){
			SummedPath p=queue.remove();
			if(p.frontNode().equals(n2)){
				//done
				return p.paths;
			}else{
				for(PathSegment ps:p.frontNode().getPaths()){
					if(!ps.destination.equals(p.frontNode())) //exclude backtracking
						queue.add(new SummedPath(p, ps));
				}
			}
		}
		return null; //if no path found
	}
	
	private static class SummedPath implements Comparable<SummedPath>{
		
		public final List<PathSegment> paths;
		
		public final double lengthTotal;
		
		public final Node goal;

		@Override
		public int compareTo(SummedPath arg0) {
			return Double.compare(
					this.distanceToGoalSquared(),
					arg0.distanceToGoalSquared());
		}
		
		public SummedPath(PathSegment path, Node goal){
			this.goal=goal;
			
			paths=new ArrayList<PathSegment>();
			paths.add(path);
			lengthTotal=path.distance;
		}
		
		public SummedPath(SummedPath path, PathSegment next){
			this.goal=path.goal;
			
			paths=new ArrayList<PathSegment>(path.paths);
			
			paths.add(next);
			lengthTotal=next.distance+path.lengthTotal;
		}
		
		public double distanceToGoalSquared(){
			return frontNode().distanceSquaredTo(goal);
		}
		
		public Node frontNode(){
			return paths.get(paths.size()-1).destination;
		}
	}

}
