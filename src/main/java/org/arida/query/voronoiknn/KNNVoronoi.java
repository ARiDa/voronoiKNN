package org.arida.query.voronoiknn;

import java.util.HashSet;
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
	long queryPoint = 0l;

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

		this.queryPoint = queryPoint;

		Queue<DistanceEntry> finalResult = new PriorityQueue<>();

		Set<Long> visitedNeighborsCandidates = new HashSet<>();
		Set<Long> nextNeighborCandidates = new HashSet<>();

		if (k == 0)
			return finalResult;

		Long firstNearestNeighborID = voronoiDiagram.getNodeToPoIMap().get(queryPoint);
		finalResult.add(new DistanceEntry(firstNearestNeighborID,
				voronoiDiagram.getNode2PoiDistance().get(queryPoint).get(firstNearestNeighborID), -1));

		if (k == 1) {
			return finalResult;
		}

		Long firstNearestNeighbor = voronoiDiagram.getNodeToPoIMap().get(queryPoint);

		// The first iteration will consider all the borderPoints AND the query
		// point.
		Set<Long> newNodes = voronoiDiagram.getPolygonBorderPoints().get(firstNearestNeighbor);
		newNodes.add(queryPoint);

		updateAuxiliarGraphWithNewBorderPoints(newNodes);

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
				long distance = dj.shortestPath(from, to).getTotalDistance();

				nearestNeighbors.add(new DistanceEntry(nearestNeighborCandidatePoI, (int) distance, -1));
			}

			DistanceEntry nextPoI = nearestNeighbors.poll();
			finalResult.add(nextPoI);

			nextNeighborCandidates = voronoiDiagram.getAdjacentPolygons().get(nextPoI.getId());
			nextNeighborCandidates.removeAll(visitedNeighborsCandidates);
			visitedNeighborsCandidates.add(nextPoI.getId());

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

				if (fromNode.getId().equals(toNode.getId()))
					continue;

				Edge newEdge;

				if (voronoiDiagram.getBorder2BorderDistance().get(borderPointTo) == null
						|| voronoiDiagram.getBorder2BorderDistance().get(borderPointFrom) == null) {
					if (voronoiDiagram.getBorder2BorderDistance().get(borderPointTo) == null) {
						newEdge = new EdgeImpl(fromNode.getId(), toNode.getId(), voronoiDiagram.getBorder2NodeDistance()
								.get(borderPointFrom).get(borderPointTo).intValue());
					} else {
						newEdge = new EdgeImpl(fromNode.getId(), toNode.getId(), voronoiDiagram.getBorder2NodeDistance()
								.get(borderPointTo).get(borderPointFrom).intValue());
					}
				} else {
					newEdge = new EdgeImpl(fromNode.getId(), toNode.getId(), voronoiDiagram.getBorder2BorderDistance()
							.get(borderPointTo).get(borderPointFrom).intValue());
				}

				borderPointsGraph.addEdge(newEdge);

			}

			DistanceEntry crossingPolygonEntry = voronoiDiagram.getBorderNeighbor().get(borderPointFrom);
			if (crossingPolygonEntry == null)
				continue;

			Long crossingPolygonNodeId = borderPointsGraph.getNodeId(
					graph.getNode(crossingPolygonEntry.getId()).getLatitude(),
					graph.getNode(crossingPolygonEntry.getId()).getLongitude());

			if (crossingPolygonNodeId != null) {

				Long crossingPolygonParentNodeId = borderPointsGraph.getNodeId(
						graph.getNode(crossingPolygonEntry.getParent()).getLatitude(),
						graph.getNode(crossingPolygonEntry.getParent()).getLongitude());

				Edge crossingEdgeForward = new EdgeImpl(crossingPolygonParentNodeId, crossingPolygonNodeId,
						crossingPolygonEntry.getDistance());
				borderPointsGraph.addEdge(crossingEdgeForward);
			}

			DistanceEntry crossingPolygonEntryBackwards = voronoiDiagram.getBorderNeighbor()
					.get(crossingPolygonEntry.getId());
			if (crossingPolygonEntryBackwards == null)
				continue;

			Long crossingPolygonBackwardsNodeId = borderPointsGraph.getNodeId(
					graph.getNode(crossingPolygonEntryBackwards.getId()).getLatitude(),
					graph.getNode(crossingPolygonEntryBackwards.getId()).getLongitude());

			if (crossingPolygonBackwardsNodeId != null) {

				Long crossingPolygonBackwardsParentNodeId = borderPointsGraph.getNodeId(
						graph.getNode(crossingPolygonEntryBackwards.getParent()).getLatitude(),
						graph.getNode(crossingPolygonEntryBackwards.getParent()).getLongitude());
				
				if(crossingPolygonBackwardsParentNodeId == null)
					continue;

				if (borderPointsGraph.getEdge(crossingPolygonBackwardsParentNodeId,
						crossingPolygonBackwardsNodeId) == null) {

					Edge crossingEdgeForward = new EdgeImpl(crossingPolygonBackwardsParentNodeId,
							crossingPolygonBackwardsNodeId, crossingPolygonEntryBackwards.getDistance());
					borderPointsGraph.addEdge(crossingEdgeForward);
				}
			}
		}
	}
}
