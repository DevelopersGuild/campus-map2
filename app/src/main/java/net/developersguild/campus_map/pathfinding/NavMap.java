package net.developersguild.maps.pathfinding;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import android.location.Location;

//API for the map system
public class NavMap {
	
	private static final int spaceSize=10;
	
	//limits on the size of area
	private final double minX, maxX, minY, maxY; 
	
	//assume a node exists in each region...
	public List<Node>[][] regions=new List[spaceSize][spaceSize];
	
	public NavMap(double minX, double maxX, double minY, double maxY){
		this.minX=minX;
		this.minY=minY;
		this.maxX=maxX;
		this.maxY=maxY;
		for(int i=0; i<spaceSize; i++){
			for(int j=0; j<spaceSize; j++)
				regions[i][j]=new ArrayList();
		}
	}
	
	public Node nearest(double latitude, double longitude) throws NoSuchNodeException{
		List<List<Node>> regions=new ArrayList<List<Node>>();
		int regionX=(int)((latitude-minX)/(maxX-minX) * spaceSize); // (0-1) * spaceSize
		int regionY=(int)((latitude-minY)/(maxY-minY) * spaceSize); // (0-1) * spaceSize
		for(int x=regionX-1; x<=regionX+1; x++){
			for(int y=regionY-1; y<=regionY+1; y++){
				if(x>=0 && x < spaceSize && y >= 0 && y < spaceSize){
					regions.add(this.regions[x][y]);
				}
			}
		}
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
		if(bestContender==null)
			throw new NoSuchNodeException();
		return bestContender;
	}
	
	public void addNode(Node n){
		int regionX=(int)((n.getX()-minX)/(maxX-minX) * spaceSize); // (0-1) * spaceSize
		int regionY=(int)((n.getY()-minY)/(maxY-minY) * spaceSize); // (0-1) * spaceSize
		regions[regionX][regionY].add(n);
	}
	
}
