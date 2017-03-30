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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VoronoiDiagram {

	private Graph g;
	private Map<Long, Node> pois = new HashMap<Long, Node>();

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Map that will store an instance of a modified Dijkstra Algorithm to each
	 * Point of Interest (PoI).
	 */
	private Map<Long, DijkstraVD> dijkstraHash = new HashMap<>();

	private Map<Long, Long> nodeToPoIMap = new HashMap<>();

	/**
	 * border2BorderDistance will store the distance between all pairs of border
	 * points in the same cell. A HashMap that maps the first border point
	 * (NodeID-Long) to a HashMap that maps the second border point
	 * (NodeID-Long) to the distance (distance - Integer) between them.
	 */
	private Map<Long, HashMap<Long, Integer>> border2BorderDistance;

	/**
	 * border2PoIDistance will store the distance between all border points and
	 * their respective Point of Interest (PoI). A HashMap that maps the border
	 * point (NodeID-Long) to a HashMap that maps the Point of Interest
	 * (NodeID-Long) to the distance (distance - Integer) between them.
	 */
	private Map<Long, HashMap<Long, Integer>> border2PoIDistance;

	/**
	 * polygonBorderPoints will store all borderPoints that belongs to a certain
	 * Voronoi Cell (polygon). A HashMap that maps the Point of Interest
	 * (NodeID-Long) of the cell to a set of all border points (Set of NodeID's)
	 */
	private Map<Long, HashSet<Long>> polygonBorderPoints = new HashMap<>();

	/**
	 * adjacentPolygons will store all adjacent polygons of a certain Voronoi
	 * Cell (polygon). A HashMap that maps the Point of Interest (NodeID-Long)
	 * of the cell to a set of all adjacent polygons Points of Interest (Set of
	 * NodeID's)
	 */
	private Map<Long, Set<Long>> adjacentPolygons = new HashMap<>();

	private Set<Long> globalSettleNodes = new HashSet<Long>();
	private Queue<DistanceEntry> globalUnsettleNodes = new PriorityQueue<>();

	public VoronoiDiagram(Graph g) {

		this.g = g;

		for (Node poi : g.getPOIsNodes()) {
			DistanceEntry distancePoI = new DistanceEntry(poi.getId(), 0, -1);
			globalUnsettleNodes.add(distancePoI);

			pois.put(poi.getId(), poi);

		}

	}

	/**
	 * This method will run a slightly modified Dijkstra algorithm for each
	 * Point of Interest. There's a simple scheduler in this method: the next
	 * PoI that will have it's respective Dijkstra Algorithm runned is the one
	 * with the smaller unsettleNode distance.
	 */
	public void createDiagram() {

		// Initialize a Hash with an instance of Dijkstra Algorithm
		// for each Point of Interest
		for (Node poi : g.getPOIsNodes()) {
			DijkstraVD dj = new DijkstraVD(g, poi, globalSettleNodes, globalUnsettleNodes, polygonBorderPoints,
					nodeToPoIMap, adjacentPolygons);
			dijkstraHash.put(poi.getId(), dj);
			polygonBorderPoints.put(poi.getId(), new HashSet<Long>());
			adjacentPolygons.put(poi.getId(), new HashSet<Long>());

		}

		// Corresponds to one iteration of vertex expansion, picking
		// always the PoI with lowest node in the unsettle queue
		while (!globalUnsettleNodes.isEmpty()) {

			Long currentPoIID = pois.get(globalUnsettleNodes.poll().getId()).getId();

			logger.debug("PoI being iterated: {}", currentPoIID);
			dijkstraHash.get(currentPoIID).iterate();

		}

		System.out.println("Test");

	}

	public void createCells() {

	}

	private void calculateB2BDistance() {

	}

	private void calculateP2BDistance() {

	}

}
