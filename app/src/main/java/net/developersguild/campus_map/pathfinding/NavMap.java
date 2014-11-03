package net.developersguild.campus_map.pathfinding;
import java.util.ArrayList;
import java.util.List;

/**
 * A NavMap includes all available nodes in a region, and services for accessing them.
 */
public class NavMap {

    /**
     * Specifies the square root of the number of spaces. More spaces will exclude more nodes from
     * consideration, making the nearest-finding algorithm run faster, however if no node exists
     * within a 3x3 square of spaces, you will not be able to find directions to or from inside
     * that space.
     */
	private static final int spaceSize=10;
	
	//limits on the size of area
	private final double minX, maxX, minY, maxY; 
	
	//assume a node exists in each 3x3 group of regions...
	public List<Node>[][] regions=new List[spaceSize][spaceSize];

    private static final DestinationRegistry destinations=new DestinationRegistry();

	public NavMap(double minX, double maxX, double minY, double maxY){
		this.minX=minX;
		this.minY=minY;
		this.maxX=maxX;
		this.maxY=maxY;
		for(int i=0; i<spaceSize; i++)
            for (int j = 0; j < spaceSize; j++)
                regions[i][j] = new ArrayList();
	}
	
	public Node nearest(double latitude, double longitude) throws IllegalArgumentException{
		List<List<Node>> regions=new ArrayList<List<Node>>();
        //collect 3x3 square of regions in which an adjacent node could lie
		int regionX=(int)((latitude-minX)/(maxX-minX) * spaceSize); // (0-1) * spaceSize
		int regionY=(int)((latitude-minY)/(maxY-minY) * spaceSize); // (0-1) * spaceSize
		for(int x=regionX-1; x<=regionX+1; x++){
			for(int y=regionY-1; y<=regionY+1; y++){
                //bounds check
				if(x>=0 && x < spaceSize && y >= 0 && y < spaceSize){
					regions.add(this.regions[x][y]);
				}
			}
		}
        //Pick the closest of those nodes
		double distance=Double.MAX_VALUE;
		Node bestContender = null;
		for(List<Node> nodes:regions){
			for(Node n:nodes){
				double thisDistance=n.distanceSquaredTo(latitude, longitude);
				if(thisDistance<distance){
					distance=thisDistance;
					bestContender=n;
				}
			}
		}
		//complain if we can't find a nearby node
        if(bestContender==null)
			throw new IllegalArgumentException("Could not find a node near "+latitude+", "+longitude);
        //otherwise return that node
        return bestContender;
	}

    public void addDestination(DestinationNode destinationNode) {
        addNode(destinationNode);
        destinations.add(destinationNode);
    }

    /**
     * Adds a node to this NavMap.
     *
     * @param n the node to add. Its X and Y coordinates must be within this map's bounds.
     */

	public void addNode(Node n){
		int regionX=(int)((n.getLatitude()-minX)/(maxX-minX) * spaceSize); // (0-1) * spaceSize
		int regionY=(int)((n.getLongitude()-minY)/(maxY-minY) * spaceSize); // (0-1) * spaceSize
        try {
            regions[regionX][regionY].add(n);
        }catch(ArrayIndexOutOfBoundsException e){
            throw new IllegalArgumentException("Node "+n+" is not within "+this);
        }
	}

    public String toString(){
        return "("+minX+" - "+maxX+", "+minY + " - "+maxY+")";
    }

}
