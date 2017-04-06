package org.arida.query.voronoiknn;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.graphast.model.Edge;
import org.graphast.model.EdgeImpl;
import org.graphast.model.Graph;
import org.graphast.model.GraphImpl;
import org.graphast.model.Node;
import org.graphast.model.NodeImpl;
import org.graphast.query.route.shortestpath.model.DistanceEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KNNVoronoi {

	private Graph graph;
	private VoronoiDiagram voronoiDiagram;
	
	Graph borderPointsGraph = new GraphImpl();
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
		
		if(k == 0)
			return finalResult;
		
		if(k == 1) {
			finalResult.add(new DistanceEntry(voronoiDiagram.getNodeToPoIMap().get(queryPoint), 0, -1));
			return finalResult;
		}
		
		
		Long firstNearestNeighbor = voronoiDiagram.getNodeToPoIMap().get(queryPoint);
		
		Set<Long> newNodes = voronoiDiagram.getPolygonBorderPoints().get(firstNearestNeighbor);
		newNodes.add(queryPoint);
		
		updateGraphNewPolygon(newNodes);
		
		Set<Long> nextNeighborCandidates = voronoiDiagram.getAdjacentPolygons().get(firstNearestNeighbor);
		
		for(int i = 2; i < k; i++) {
			
		}
		
		return finalResult;
		
	}

	/**
	 * THis method will update the list of known candidates of nearest nodes
	 * based on the adjacent polygons of a Voronoi Diagram.
	 * 
	 * @param foundNeighbors
	 *            list that will be updated
	 * @return the updated list
	 */
	private Set<Long> generateCandidateSet(Set<Long> foundNeighbors) {

		Set<Long> newListOfKnownCandidates = new HashSet<>();

		for (Long knownCandidate : foundNeighbors) {
			newListOfKnownCandidates.add(knownCandidate);
			newListOfKnownCandidates.addAll(voronoiDiagram.getAdjacentPolygons().get(knownCandidate));
		}

		return newListOfKnownCandidates;

	}
	
	//TODO Refactor this method
	private void updateGraphNewPolygon(Set<Long> points) {
		
//		Set<Long> borderPoints = voronoiDiagram.getPolygonBorderPoints().get(generatorPoI);
		
		for(Long borderPointFrom : points) {
			Node fromNode;
			//Checking if the node already exists
			if(borderPointsGraph.getNodeId(graph.getNode(borderPointFrom).getLatitude(), graph.getNode(borderPointFrom).getLongitude()) == null) {
				fromNode = new NodeImpl(graph.getNode(borderPointFrom).getLatitude(), graph.getNode(borderPointFrom).getLongitude());
			} else {
				fromNode = borderPointsGraph.getNearestNode(graph.getNode(borderPointFrom).getLatitude(), graph.getNode(borderPointFrom).getLongitude());
			}
			borderPointsGraph.addNode(fromNode);
			
			for(Long borderPointTo : points) {
				
				Node toNode;
				//Checking if the node already exists
				if(borderPointsGraph.getNodeId(graph.getNode(borderPointTo).getLatitude(), graph.getNode(borderPointTo).getLongitude()) == null) {
					toNode = new NodeImpl(graph.getNode(borderPointTo).getLatitude(), graph.getNode(borderPointTo).getLongitude());
				} else {
					toNode = borderPointsGraph.getNearestNode(graph.getNode(borderPointTo).getLatitude(), graph.getNode(borderPointTo).getLongitude());
				}
				borderPointsGraph.addNode(toNode);
				
				Edge newEdge = new EdgeImpl(fromNode.getId(), toNode.getId(), voronoiDiagram.getBorder2BorderDistance().get(borderPointFrom).get(borderPointTo).intValue());
				
				borderPointsGraph.addEdge(newEdge);
				
			}
			
			
			if(borderPointsGraph.getNodeId(graph.getNode(voronoiDiagram.getBorderNeighbor().get(borderPointFrom).getId()).getLatitude(), graph.getNode(voronoiDiagram.getBorderNeighbor().get(borderPointFrom).getId()).getLongitude()) != null) {
				Edge crossingEdgeForward = new EdgeImpl(voronoiDiagram.getBorderNeighbor().get(borderPointFrom).getId(), voronoiDiagram.getBorderNeighbor().get(borderPointFrom).getParent(), voronoiDiagram.getBorderNeighbor().get(borderPointFrom).getDistance());
				Edge crossingEdgeBackward = new EdgeImpl(voronoiDiagram.getBorderNeighbor().get(borderPointFrom).getParent(), voronoiDiagram.getBorderNeighbor().get(borderPointFrom).getId(), voronoiDiagram.getBorderNeighbor().get(borderPointFrom).getDistance());
				borderPointsGraph.addEdge(crossingEdgeForward);
				borderPointsGraph.addEdge(crossingEdgeBackward);
			}
			
		}
	}

}
