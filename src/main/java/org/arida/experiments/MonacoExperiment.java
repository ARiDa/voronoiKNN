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

public class MonacoExperiment {

	private MonacoExperiment() {
		
		throw new IllegalAccessError("Utility class");
	
	}
	
	public static void main(String[] args) {
		
		Logger logger = LoggerFactory.getLogger(MonacoExperiment.class);
		
		Graph testGraph = new GraphGenerator().generateMonaco();
		POIImporter.generateRandomPoIs(testGraph, 25);
		Long source = testGraph.getNodeId(43.72842465479131, 7.414896579419745);
		
		List<Integer> numberOfNeighbors = new ArrayList<>(Arrays.asList(1, 2, 4, 8, 16, 32, 64, 128, 194));
		int numberOfRepetitions = 1;
//
//		StopWatch voronoiPreprocessingSW = new StopWatch();
//
//		testGraph.reverseGraph();
//
//		VoronoiDiagram voronoiDiagram = new VoronoiDiagram(testGraph);
//
//		voronoiPreprocessingSW.start();
//		voronoiDiagram.createDiagram();
//		voronoiPreprocessingSW.stop();
//
//		testGraph.reverseGraph();
//
//		logger.info("Preprocessing time for the Voronoi Diagram: {}s", voronoiPreprocessingSW.getSeconds());

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
