package org.arida.experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.arida.graphgenerator.GraphGenerator;
import org.arida.query.voronoiknn.KNNVoronoi;
import org.arida.query.voronoiknn.VoronoiDiagram;
import org.graphast.importer.POIImporter;
import org.graphast.model.Graph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphhopper.util.StopWatch;

public class GraphhopperExample4Experiment {

	private GraphhopperExample4Experiment() {

		throw new IllegalAccessError("Utility class");

	}

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(GraphhopperExample4Experiment.class);

		Graph testGraph = new GraphGenerator().generateGraphHopperExample4();
		POIImporter.generateRandomPoIs(testGraph, 100);
		Long source = 0l;

		List<Integer> numberOfNeighbors = new ArrayList<>(Arrays.asList(1, 2, 4, 8, 16, 32, 34));
		int numberOfRepetitions = 100;

		double averagePreprocessingTime = 0;
		
		for (Integer k : numberOfNeighbors) {

			double averageExecutionTime = 0;
			logger.info("Starting to run the Voronoi-based approach for k = {}", k);

			for (int i = 0; i < numberOfRepetitions; i++) {
				
				StopWatch voronoiPreprocessingSW = new StopWatch();

				testGraph.reverseGraph();

				VoronoiDiagram voronoiDiagram = new VoronoiDiagram(testGraph);

				voronoiPreprocessingSW.start();
				voronoiDiagram.createDiagram();
				voronoiPreprocessingSW.stop();

				testGraph.reverseGraph();

				KNNVoronoi knn = new KNNVoronoi(testGraph, voronoiDiagram);
				StopWatch voronoiExecutionSW = new StopWatch();

				voronoiExecutionSW.start();
				knn.executeKNN(source, k);
				voronoiExecutionSW.stop();

				averageExecutionTime += voronoiExecutionSW.getSeconds();
				averagePreprocessingTime += voronoiPreprocessingSW.getSeconds();

			}

			averageExecutionTime = averageExecutionTime / numberOfRepetitions;
			logger.info("averageExecutionTime = {}", averageExecutionTime);

		}
		
		averagePreprocessingTime = averagePreprocessingTime / (numberOfRepetitions*numberOfNeighbors.size());
		logger.info("averagePreprocessingTime = {}", averagePreprocessingTime);

	}

}
