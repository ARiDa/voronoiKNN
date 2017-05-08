package org.arida.experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.arida.graphgenerator.GraphGenerator;
import org.arida.query.voronoiknn.KNNVoronoi;
import org.arida.query.voronoiknn.VoronoiDiagram;
import org.graphast.model.Graph;
import org.graphast.model.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphhopper.util.StopWatch;

public class VoronoiKNNExperiment {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(VoronoiKNNExperiment.class);

		Graph graphMonaco = new GraphGenerator().generateMonaco();
		
		generateRandomPoIs(graphMonaco, 75);
		
		for(int j = 0; j<graphMonaco.getNumberOfNodes(); j++) {
			System.out.println("ID: " + graphMonaco.getNode(j).getId() + ", isPoi? " + graphMonaco.getNode(j).getCategory());
		}
		
		List<Integer> numberOfNeighbors = new ArrayList<>(Arrays.asList(1, 2, 4, 8, 16, 32, 64, 128, 256, 512));
		int numberOfRepetitions = 10;

		StopWatch voronoiPreprocessingSW = new StopWatch();

		Long source = graphMonaco.getNodeId(43.72842465479131, 7.414896579419745);

		graphMonaco.reverseGraph();

		VoronoiDiagram voronoiDiagram = new VoronoiDiagram(graphMonaco);

		voronoiPreprocessingSW.start();
		voronoiDiagram.createDiagram();
		voronoiPreprocessingSW.stop();

		graphMonaco.reverseGraph();

		logger.info("Preprocessing time for the Voronoi Diagram: {}ns", voronoiPreprocessingSW.getNanos());

		for (Integer k : numberOfNeighbors) {
			logger.info("Starting to run the Voronoi-based approach for k = {}", k);
			int averageExecutionTime = 0;

			for (int i = 0; i < numberOfRepetitions; i++) {
				StopWatch voronoiExecutionSW = new StopWatch();
				KNNVoronoi knn = new KNNVoronoi(graphMonaco, voronoiDiagram);
				voronoiExecutionSW.start();
				knn.executeKNN(source, k);
				voronoiExecutionSW.stop();

				averageExecutionTime += voronoiExecutionSW.getNanos();
			}

			averageExecutionTime = averageExecutionTime / numberOfRepetitions;
			logger.info("averageExecutionTime = {}", averageExecutionTime);

		}

	}
	
	private static void generateRandomPoIs(Graph graph, double density) {
		
		int numberOfPoIsGenerated = (int) ((int) graph.getNumberOfNodes()*(density/100));
		
		if (graph.getNumberOfNodes() < numberOfPoIsGenerated)
		{
		    throw new IllegalArgumentException("Can't ask for more numbers than are available");
		}
		Random rng = new Random(); // Ideally just create one instance globally
		// Note: use LinkedHashSet to maintain insertion order
		Set<Integer> generated = new LinkedHashSet<Integer>();
		while (generated.size() < numberOfPoIsGenerated)
		{
		    Integer next = rng.nextInt((int)graph.getNumberOfNodes()-1) + 1;
		    // As we're adding to a set, this will automatically do a containment check
		    generated.add(next);
		}
		
		for(Integer nodeID : generated) {
			Node n = graph.getNode(nodeID);
			n.setCategory(4);
			graph.updateNodeInfo(n);
		}
		
	}

}
