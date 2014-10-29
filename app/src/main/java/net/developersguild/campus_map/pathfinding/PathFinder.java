package net.developersguild.campus_map.pathfinding;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Contains the logic for A* pathfinding, allowing either single or multiple goal nodes.
 */
public class PathFinder {

    /**
     * Configures how many paths to evaluate before giving up.
     */
    public static volatile int timeout=1000000;

    /**
     * Finds a path between the given nodes, following their designated PathSegments.
     * @param start the node to start at
     * @param end the node to find a path to
     * @return the path segments to follow
     */
	public static List<PathSegment> pathBetween(Node start, Node end){
		PriorityQueue<SummedSingleGoalPath> queue=new PriorityQueue<SummedSingleGoalPath>();
		for(PathSegment p:start.getPaths()){
			queue.add(new SummedSingleGoalPath(p, end));
		}
        int i=0;
		while(!queue.isEmpty() && i < timeout){
            i++;
			SummedSingleGoalPath p=queue.remove();
			if(p.frontNode().equals(end)){
				//done
				return p.paths;
			}else{
				for(PathSegment ps:p.frontNode().getPaths()){
					if(!ps.destination.equals(p.frontNode())) //exclude backtracking
						queue.add(new SummedSingleGoalPath(p, ps));
				}
			}
		}
		return null; //if no path found
	}

    /**
     * Finds a path from the start node to the nearest possible end node in possibleEnds.
     * @param start the node to start finding a path from
     * @param possibleEnds the list of acceptable goal nodes
     * @return the path segments to follow
     */
    public static List<PathSegment> pathBetween(Node start, List<Node> possibleEnds){
        PriorityQueue<SummedMultiGoalPath> queue=new PriorityQueue<SummedMultiGoalPath>();
        for(PathSegment p:start.getPaths()){
            queue.add(new SummedMultiGoalPath(p, possibleEnds));
        }
        int i=0;
        while(!queue.isEmpty() && i < timeout){
            i++;
            SummedMultiGoalPath p=queue.remove();
            for(Node goal:possibleEnds)
                if(p.frontNode().equals(goal))
                    return p.paths;

            for(PathSegment ps:p.frontNode().getPaths()){
                if(!ps.destination.equals(p.frontNode())) //exclude backtracking
                    queue.add(new SummedMultiGoalPath(p, ps));
            }
        }
        return null; //if no path found
    }

    /**
     * An abstract summed path. A summed path is the set of path segments to take to get to a goal.
     */
    private abstract static class AbstractSummedPath implements Comparable<AbstractSummedPath>{

        public final double lengthTotal;

        public final List<PathSegment> paths;

        public AbstractSummedPath(PathSegment path){
            super();
            paths=new ArrayList<PathSegment>();
            paths.add(path);
            lengthTotal=path.distance;
        }

        public AbstractSummedPath(AbstractSummedPath path, PathSegment next){
            paths=new ArrayList<PathSegment>(path.paths);

            paths.add(next);
            lengthTotal=next.distance+path.lengthTotal;
        }

        /**
         * @return the node reached by following this object's paths
         */
        public Node frontNode(){
            return paths.get(paths.size()-1).destination;
        }

        @Override
        public int compareTo(AbstractSummedPath arg0) {
            return Double.compare(
                    //Square both and compare. Estimated future length must not > actual future
                    //length, or else you are not guaranteed the best solution.
                    this.distanceToGoalSquared()+this.lengthTotal*this.lengthTotal,
                    arg0.distanceToGoalSquared()+arg0.lengthTotal*arg0.lengthTotal);
        }

        /**
         * @return the square of the distance to the nearest allowable goal node
         */
        public abstract double distanceToGoalSquared();

    }

    /**
     * A summed path with one single goal node.
     */
	private static class SummedSingleGoalPath extends AbstractSummedPath {
		
		public final Node goal;

		public SummedSingleGoalPath(PathSegment path, Node goal){
            super(path);
			this.goal=goal;
		}

        public SummedSingleGoalPath(SummedSingleGoalPath past, PathSegment next){
            super(past, next);
            this.goal=past.goal;
        }

		public double distanceToGoalSquared(){
			return frontNode().distanceSquaredTo(goal);
		}
		
	}

    /**
     * A summed path with multiple goal nodes.
     */
    private static class SummedMultiGoalPath extends AbstractSummedPath {

        public final List<Node> goal;

        public SummedMultiGoalPath(PathSegment path, List<Node> goal){
            super(path);
            this.goal=goal;
        }

        public SummedMultiGoalPath(SummedMultiGoalPath past, PathSegment next){
            super(past, next);
            this.goal=past.goal;
        }

        public double distanceToGoalSquared(){
            double distance=Double.MAX_VALUE;
            for(Node n:goal){
                distance=Math.min(distance, frontNode().distanceSquaredTo(n));
            }
            return distance;
        }

    }

}
