package org.arida.query.voronoiknn;

import java.util.HashSet;
import java.util.Set;

import org.graphast.model.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KNNVoronoi {

	private Graph graph;
	private VoronoiDiagram voronoiDiagram;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public KNNVoronoi(Graph graph, VoronoiDiagram voronoiDiagram) {
		this.graph = graph;
		this.voronoiDiagram = voronoiDiagram;
	}

	/**
	 * This method will return the k nearest neighbors of a source node.
	 * 
	 * @param source
	 *            the initial query point
	 * @param k
	 *            the number of neighbors that will be returned
	 */
	public void executeKNN(long source, int k) {

		Long firstNeighbor = voronoiDiagram.getNodeToPoIMap().get(source);
		Set<Long> listOfKnownCandidates;
		Set<Long> foundNeighbors = new HashSet<>();
		
		foundNeighbors.add(firstNeighbor);

		for(int i = 2; i <= k; i++) {
			// Filter step
			listOfKnownCandidates = generateCandidateSet(foundNeighbors);  
		}

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

}
