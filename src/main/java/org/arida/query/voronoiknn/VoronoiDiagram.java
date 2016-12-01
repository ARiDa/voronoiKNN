package org.arida.query.voronoiknn;

import java.util.HashMap;
import java.util.Set;

public class VoronoiDiagram {

	private HashMap<Long, HashMap<Long, Integer>> border2BorderDistance;
	private HashMap<Long, HashMap<Long, Integer>> border2PoIDistance;
	
	private HashMap<Long, Set<Long>> polygonBorderPoints;
	
	private HashMap<Long, Set<Long>> adjacentPolygons;
	
	private HashMap<Long, Long> voronoiCell;
	
	public void createDiagram() {
		
	}
	
	public void createCells() {
		
	}
	
	private void calculateB2BDistance() {
		
	}
	
	private void calculateP2BDistance() {
		
	}
}
