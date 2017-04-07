package org.arida.query.voronoiknn;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KNNVoronoi {

	private Graph graph;
	private VoronoiDiagram voronoiDiagram;

	Graph borderPointsGraph = new GraphImpl(Configuration.USER_HOME + "/graphast/test/borderPointsGraph");
	Queue<DistanceEntry> finalNearestNeighbors = new PriorityQueue<>();
	Queue<DistanceEntry> candidatePoIs = new PriorityQueue<>();

	private Logger logger = LoggerFactory.getLogger(this.getClass());

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

		Queue<DistanceEntry> finalResult = new PriorityQueue<>();
		
		Set<Long> visitedNeighborsCandidates;
		Set<Long> nextNeighborCandidates;

		if (k == 0)
			return finalResult;

		if (k == 1) {
			finalResult.add(new DistanceEntry(voronoiDiagram.getNodeToPoIMap().get(queryPoint), 0, -1));
			return finalResult;
		}

		Long firstNearestNeighbor = voronoiDiagram.getNodeToPoIMap().get(queryPoint);

		// The first iteration will consider all the borderPoints AND the query
		// point.
		Set<Long> newNodes = voronoiDiagram.getPolygonBorderPoints().get(firstNearestNeighbor);
		newNodes.add(queryPoint);
		
		updateGraphWithNewBorderPoints(newNodes);
		
		nextNeighborCandidates = voronoiDiagram.getAdjacentPolygons().get(firstNearestNeighbor);
		visitedNeighborsCandidates = voronoiDiagram.getAdjacentPolygons().get(firstNearestNeighbor);
		
		
		Queue<DistanceEntry> nearestNeighbors = new PriorityQueue<>();
		
		for (int i = 2; i <= k; i++) {

			for(Long nearestNeighborCandidatePoI : nextNeighborCandidates) {
				newNodes = voronoiDiagram.getPolygonBorderPoints().get(nearestNeighborCandidatePoI);
				updateGraphWithNewBorderPoints(newNodes);
				Dijkstra dj = new DijkstraConstantWeight(borderPointsGraph);
				long distance = dj.shortestPath(queryPoint, nearestNeighborCandidatePoI).getTotalDistance();
				
				nearestNeighbors.add(new DistanceEntry(nearestNeighborCandidatePoI, (int) distance, -1));
			}
			
			nextNeighborCandidates = voronoiDiagram.getAdjacentPolygons().get(nearestNeighbors.poll().getId());
			nextNeighborCandidates.removeAll(visitedNeighborsCandidates);
			visitedNeighborsCandidates.addAll(nextNeighborCandidates);
			
		}

		return finalResult;

	}

	/**
	 * This method will receive a set of border points and will add them to an
	 * auxiliary graph that contains only the border points of the nearest
	 * neighbors for that iteration.
	 * 
	 * @param points
	 *            points that will be added.
	 */
	private void updateGraphWithNewBorderPoints(Set<Long> points) {

		for (Long borderPointFrom : points) {

			Node fromNode;
			Long fromNodeId = borderPointsGraph.getNodeId(graph.getNode(borderPointFrom).getLatitude(),
					graph.getNode(borderPointFrom).getLongitude());
			
			// Checking if the node already exists
			if (fromNodeId == null) {
				fromNode = new NodeImpl(borderPointFrom, graph.getNode(borderPointFrom).getLatitude(),
						graph.getNode(borderPointFrom).getLongitude());
				borderPointsGraph.addNode(fromNode);
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
				} else {
					toNode = borderPointsGraph.getNode(toNodeId);
				}

				Edge newEdge = new EdgeImpl(fromNode.getId(), toNode.getId(),
						voronoiDiagram.getBorder2BorderDistance().get(borderPointFrom).get(borderPointTo).intValue());

				borderPointsGraph.addEdge(newEdge);

			}

			DistanceEntry crossingPolygonEntry = voronoiDiagram.getBorderNeighbor().get(borderPointFrom);
			Long crossingPolygonNodeId = borderPointsGraph.getNodeId(
					graph.getNode(crossingPolygonEntry.getId()).getLatitude(),
					graph.getNode(crossingPolygonEntry.getId()).getLongitude());

			if (crossingPolygonNodeId != null) {
				Edge crossingEdgeForward = new EdgeImpl(crossingPolygonEntry.getId(), crossingPolygonEntry.getParent(),
						crossingPolygonEntry.getDistance());
				Edge crossingEdgeBackward = new EdgeImpl(crossingPolygonEntry.getParent(), crossingPolygonEntry.getId(),
						crossingPolygonEntry.getDistance());
				borderPointsGraph.addEdge(crossingEdgeForward);
				borderPointsGraph.addEdge(crossingEdgeBackward);
			}
		}
	}
}
