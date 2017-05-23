package org.arida.query.voronoiknn;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.graphast.config.Configuration;
import org.graphast.model.Edge;
import org.graphast.model.EdgeImpl;
import org.graphast.model.Graph;
import org.graphast.model.GraphImpl;
import org.graphast.model.Node;
import org.graphast.model.NodeImpl;
import org.graphast.query.route.shortestpath.dijkstra.Dijkstra;
import org.graphast.query.route.shortestpath.dijkstra.DijkstraConstantWeight;
import org.graphast.query.route.shortestpath.model.DistanceEntry;

public class KNNVoronoi {

	private Graph graph;
	private VoronoiDiagram voronoiDiagram;

	Map<Long, Integer> nearestNeighborsMap = new HashMap<>();
	
	Graph borderPointsGraph = new GraphImpl(Configuration.USER_HOME + "/graphast/test/borderPointsGraph");
	Queue<DistanceEntry> finalNearestNeighbors = new PriorityQueue<>();
	Queue<DistanceEntry> candidatePoIs = new PriorityQueue<>();
	long queryPoint = 0l;

	public KNNVoronoi(Graph graph, VoronoiDiagram voronoiDiagram) {
		this.graph = graph;
		this.voronoiDiagram = voronoiDiagram;
	}

	/**
	 * This method will return the k nearest neighbors of a source node.
	 * 
	 * @param queryPoint
	 *            the initial query point
	 * @param k
	 *            the number of neighbors that will be returned
	 */
	public Queue<DistanceEntry> executeKNN(long queryPoint, int k) {

		this.queryPoint = queryPoint;

		Queue<DistanceEntry> finalResult = new PriorityQueue<>();

		Set<Long> visitedNeighborsCandidates = new HashSet<>();
		Set<Long> nextNeighborCandidates = new HashSet<>();

		if (k == 0)
			return finalResult;

		Long firstNearestNeighborID = voronoiDiagram.getNodeToPoIMap().get(queryPoint);
		finalResult.add(new DistanceEntry(firstNearestNeighborID,
				voronoiDiagram.getNode2PoiDistance().get(queryPoint).get(firstNearestNeighborID), -1));

		nearestNeighborsMap.put(firstNearestNeighborID,
				voronoiDiagram.getNode2PoiDistance().get(queryPoint).get(firstNearestNeighborID));

		if (k == 1) {
			return finalResult;
		}

		Long firstNearestNeighbor = voronoiDiagram.getNodeToPoIMap().get(queryPoint);

		// The first iteration will consider all the borderPoints AND the query
		// point.
		Set<Long> newNodes = voronoiDiagram.getPolygonBorderPoints().get(firstNearestNeighbor);
		newNodes.add(firstNearestNeighbor);
//		newNodes.add(queryPoint);

		updateAuxiliarGraphWithNewBorderPoints(newNodes);
		updateAuxiliarGraphWithQueryPoint(queryPoint, firstNearestNeighbor);

		nextNeighborCandidates = voronoiDiagram.getAdjacentPolygons().get(firstNearestNeighbor);
		visitedNeighborsCandidates.add(firstNearestNeighbor);

		Queue<DistanceEntry> nearestNeighbors = new PriorityQueue<>();

		for (int i = 2; i <= k; i++) {

			for (Long nearestNeighborCandidatePoI : nextNeighborCandidates) {
				newNodes = voronoiDiagram.getPolygonBorderPoints().get(nearestNeighborCandidatePoI);
				newNodes.add(nearestNeighborCandidatePoI);
				updateAuxiliarGraphWithNewBorderPoints(newNodes);

				Dijkstra dj = new DijkstraConstantWeight(borderPointsGraph);
				long from = borderPointsGraph.getNodeId(graph.getNode(queryPoint).getLatitude(),
						graph.getNode(queryPoint).getLongitude());
				long to = borderPointsGraph.getNodeId(graph.getNode(nearestNeighborCandidatePoI).getLatitude(),
						graph.getNode(nearestNeighborCandidatePoI).getLongitude());

				int distance;

				try {
					distance = (int) dj.shortestPath(from, to).getTotalDistance();
				} catch (Exception e) {
					distance = Integer.MAX_VALUE;
				}

				if (nearestNeighborsMap.containsKey(nearestNeighborCandidatePoI)) {
					if (nearestNeighborsMap.get(nearestNeighborCandidatePoI) > distance) {
						nearestNeighbors.remove(new DistanceEntry(nearestNeighborCandidatePoI, distance, -1));
						nearestNeighborsMap.replace(nearestNeighborCandidatePoI, distance);
						nearestNeighbors.add(new DistanceEntry(nearestNeighborCandidatePoI, distance, -1));
					}
				} else {

					nearestNeighbors.add(new DistanceEntry(nearestNeighborCandidatePoI, distance, -1));
					nearestNeighborsMap.put(nearestNeighborCandidatePoI, distance);
				}

				nextNeighborCandidates.add(nearestNeighborCandidatePoI);
			}

			DistanceEntry nextPoI = nearestNeighbors.poll();
			if (nextPoI == null)
				continue;
			finalResult.add(nextPoI);

			nextNeighborCandidates.addAll(voronoiDiagram.getAdjacentPolygons().get(nextPoI.getId()));
			nextNeighborCandidates.remove(nextPoI.getId());
			// nextNeighborCandidates =
			// voronoiDiagram.getAdjacentPolygons().get(nextPoI.getId());
			nextNeighborCandidates.removeAll(visitedNeighborsCandidates);
			visitedNeighborsCandidates.add(nextPoI.getId());

		}

		return finalResult;

	}
	
	private void updateAuxiliarGraphWithQueryPoint(long queryPoint, long firstNearestNeighbor) {
		
		Node fromNode;
		Long fromNodeId = borderPointsGraph.getNodeId(graph.getNode(queryPoint).getLatitude(),
				graph.getNode(queryPoint).getLongitude());

		// Checking if the node already exists
		if (fromNodeId == null) {
			fromNode = new NodeImpl(queryPoint, graph.getNode(queryPoint).getLatitude(),
					graph.getNode(queryPoint).getLongitude());
			borderPointsGraph.addNode(fromNode);
		} else {
			fromNode = borderPointsGraph.getNode(fromNodeId);
		}
		
		
		for(Long borderPointTo : voronoiDiagram.getPolygonBorderPoints().get(firstNearestNeighbor)) {
			
			Edge newEdge;
			//TODO Change this.
			Long destination = borderPointsGraph.getNodeId(graph.getNode(borderPointTo).getLatitude(),
					graph.getNode(borderPointTo).getLongitude());
			Dijkstra dj = new DijkstraConstantWeight(graph);
			
			
			
			newEdge = new EdgeImpl(fromNode.getId(), destination, 
					(int) dj.shortestPath(graph.getNode(queryPoint), graph.getNode(borderPointTo)).getTotalCost());
				

			borderPointsGraph.addEdge(newEdge);
			
		}
		
	}

	/**
	 * This method will receive a set of border points and will add them to an
	 * auxiliary graph that contains only the border points of the nearest
	 * neighbors for that iteration.
	 * 
	 * @param points
	 *            points that will be added.
	 */
	private void updateAuxiliarGraphWithNewBorderPoints(Set<Long> points) {

		for (Long borderPointFrom : points) {

			Node fromNode;
			Long fromNodeId = borderPointsGraph.getNodeId(graph.getNode(borderPointFrom).getLatitude(),
					graph.getNode(borderPointFrom).getLongitude());

			// Checking if the node already exists
			if (fromNodeId == null) {
				fromNode = new NodeImpl(borderPointFrom, graph.getNode(borderPointFrom).getLatitude(),
						graph.getNode(borderPointFrom).getLongitude());
				borderPointsGraph.addNode(fromNode);
				// System.out.println("Adicionando nó " + fromNode.getId() + ".
				// ID original: " + fromNode.getExternalId());
			} else {
				fromNode = borderPointsGraph.getNode(fromNodeId);
			}

			for (Long borderPointTo : points) {

				Node toNode;
				Long toNodeId = borderPointsGraph.getNodeId(graph.getNode(borderPointTo).getLatitude(),
						graph.getNode(borderPointTo).getLongitude());

				// Checking if the node already exists
				if (toNodeId == null) {
					toNode = new NodeImpl(borderPointTo, graph.getNode(borderPointTo).getLatitude(),
							graph.getNode(borderPointTo).getLongitude());
					borderPointsGraph.addNode(toNode);
					// System.out.println("Adicionando nó " + toNode.getId() +
					// ". ID original: " + toNode.getExternalId());
				} else {
					toNode = borderPointsGraph.getNode(toNodeId);
				}

				if (fromNode.getId().equals(toNode.getId()))
					continue;

				Edge newEdge;

				if (voronoiDiagram.getBorder2BorderDistance().get(borderPointTo) == null
						|| voronoiDiagram.getBorder2BorderDistance().get(borderPointFrom) == null) {
					if (voronoiDiagram.getBorder2BorderDistance().get(borderPointTo) == null) {
						Dijkstra dj = new DijkstraConstantWeight(graph);
						
						newEdge = new EdgeImpl(fromNode.getId(), toNode.getId(), (int) dj.shortestPath(fromNode.getExternalId(), toNode.getExternalId()).getTotalDistance());
					} else {
						newEdge = new EdgeImpl(fromNode.getId(), toNode.getId(), voronoiDiagram.getBorder2NodeDistance()
								.get(borderPointTo).get(borderPointFrom).intValue());
					}
				} else {
					if (voronoiDiagram.getBorder2BorderDistance().get(borderPointTo).get(borderPointFrom)
							.intValue() == -1) {
						newEdge = new EdgeImpl(fromNode.getId(), toNode.getId(), Integer.MAX_VALUE);
					} else {
						newEdge = new EdgeImpl(fromNode.getId(), toNode.getId(), voronoiDiagram
								.getBorder2BorderDistance().get(borderPointFrom).get(borderPointTo).intValue());
					}

				}

				borderPointsGraph.addEdge(newEdge);
				// System.out.println("\tAdicionando aresta de " +
				// fromNode.getId() + " para " + toNode.getId());

			}

			if (voronoiDiagram.getBorderNeighbor().get(borderPointFrom) == null)
				continue;

			for (DistanceEntry crossingPolygonEntry : voronoiDiagram.getBorderNeighbor().get(borderPointFrom)) {

				if (crossingPolygonEntry == null)
					continue;

				Long crossingPolygonNodeId = borderPointsGraph.getNodeId(
						graph.getNode(crossingPolygonEntry.getId()).getLatitude(),
						graph.getNode(crossingPolygonEntry.getId()).getLongitude());

				if (crossingPolygonNodeId == null) {
					Node nextPolygonNode = new NodeImpl(crossingPolygonEntry.getId(),
							graph.getNode(crossingPolygonEntry.getId()).getLatitude(),
							graph.getNode(crossingPolygonEntry.getId()).getLongitude());
					borderPointsGraph.addNode(nextPolygonNode);
					// System.out.println("Adicionando nó do próximo poligono:"
					// + nextPolygonNode.getId()
					// + ". ID original: " + nextPolygonNode.getExternalId());
					crossingPolygonNodeId = nextPolygonNode.getId();
				}

				Long crossingPolygonParentNodeId = borderPointsGraph.getNodeId(
						graph.getNode(crossingPolygonEntry.getParent()).getLatitude(),
						graph.getNode(crossingPolygonEntry.getParent()).getLongitude());

				if (crossingPolygonParentNodeId == null) {
					Node nextPolygonNode = new NodeImpl(crossingPolygonEntry.getParent(),
							graph.getNode(crossingPolygonEntry.getParent()).getLatitude(),
							graph.getNode(crossingPolygonEntry.getParent()).getLongitude());
					borderPointsGraph.addNode(nextPolygonNode);
					// System.out.println("Adicionando nó do próximo poligono:"
					// + nextPolygonNode.getId()
					// + ". ID original: " + nextPolygonNode.getExternalId());
					crossingPolygonParentNodeId = nextPolygonNode.getId();
				}

				Edge crossingEdgeForward = new EdgeImpl(crossingPolygonParentNodeId, crossingPolygonNodeId,
						crossingPolygonEntry.getDistance());
				borderPointsGraph.addEdge(crossingEdgeForward);
				// System.out.println("\tAdicionando aresta de TRAVESSIA de " +
				// crossingPolygonParentNodeId + " para "
				// + crossingPolygonNodeId);

			}
		}
	}
}
