package org.arida.query.voronoiknn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.graphast.model.Graph;
import org.graphast.model.Node;
import org.graphast.query.route.shortestpath.model.DistanceEntry;

public class VoronoiDiagram {

	private Graph g;
	private Map<Long, Node> PoIs = new HashMap<Long, Node>();
	private Map<Long, DijkstraVD> DijkstraHash = new HashMap<Long, DijkstraVD>();
	
	private HashMap<Long, HashMap<Long, Integer>> border2BorderDistance;
	private HashMap<Long, HashMap<Long, Integer>> border2PoIDistance;
	
	private HashMap<Long, Set<Long>> polygonBorderPoints;
	
	private HashMap<Long, Set<Long>> adjacentPolygons;
	
	private HashMap<Long, Long> voronoiCell;
	
	public Set<Long> globalSettleNodes = new HashSet<Long>();
	
	//TODO criar um SET com nós que já são settle
	
	private Queue<DistanceEntry> globalPriorityQueue = new PriorityQueue<DistanceEntry>();
	
	public VoronoiDiagram(Graph g) {
		
		this.g = g;
		
		for(Node poi : g.getPOIsNodes()) {
			DistanceEntry distancePoI = new DistanceEntry(poi.getId(), 0, -1);
			globalPriorityQueue.add(distancePoI);
			
			PoIs.put(poi.getId(), poi);
			
//			globalSettleNodes.add(poi.getId());
		}
		
	}
	
	/*
	 * Run Parallel Dijkstra starting from each PoI
	 */
	public void createDiagram() {
		
		//Initialize a Hash with an instance of Dijkstra Algorithm
		//for each Point of Interest
		for(Node poi : g.getPOIsNodes()) {
			DijkstraVD dj = new DijkstraVD(g, poi, globalSettleNodes, globalPriorityQueue);
			DijkstraHash.put(poi.getId(), dj);
			
		}
		
		//Corresponds to one iteration of vertex expansion, picking
		//always the PoI with lowest node in the unsettle queue
		while(globalSettleNodes.size() != g.getNumberOfNodes()) {
			
			long currentPoIID = PoIs.get(globalPriorityQueue.poll().getId()).getId();
			
			//Passar a variável globalSettleNodes aqui?
			DijkstraHash.get(currentPoIID).iterate();
			
			
			
		}
	
	}
	
	public void createCells() {
		
	}
	
	private void calculateB2BDistance() {
		
	}
	
	private void calculateP2BDistance() {
		
	}
	
}
