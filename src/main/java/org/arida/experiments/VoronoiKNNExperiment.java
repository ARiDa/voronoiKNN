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

public class VoronoiKNNExperiment {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(VoronoiKNNExperiment.class);

		Graph graphMonaco = new GraphGenerator().generateMonaco();
//		Graph graphHopperExample4 = new GraphGenerator().generateGraphHopperExample4();
		POIImporter.generateRandomPoIs(graphMonaco, 100);
		
		List<Integer> numberOfNeighbors = new ArrayList<>(Arrays.asList(1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 777));
		int numberOfRepetitions = 100;

		StopWatch voronoiPreprocessingSW = new StopWatch();

		Long source = graphMonaco.getNodeId(43.72842465479131, 7.414896579419745);
//		Long source = 0l;

		graphMonaco.reverseGraph();

		VoronoiDiagram voronoiDiagram = new VoronoiDiagram(graphMonaco);

		voronoiPreprocessingSW.start();
		voronoiDiagram.createDiagram();
		voronoiPreprocessingSW.stop();

		graphMonaco.reverseGraph();

		logger.info("Preprocessing time for the Voronoi Diagram: {}ns", voronoiPreprocessingSW.getSeconds());

		
		for (Integer k : numberOfNeighbors) {
			logger.info("Starting to run the Voronoi-based approach for k = {}", k);
			double averageExecutionTime = 0;

			for (int i = 0; i < numberOfRepetitions; i++) {
				KNNVoronoi knn = new KNNVoronoi(graphMonaco, voronoiDiagram);
				StopWatch voronoiExecutionSW = new StopWatch();
				voronoiExecutionSW.start();
				knn.executeKNN(source, k);
				voronoiExecutionSW.stop();
				System.out.println(voronoiExecutionSW.getSeconds());
				averageExecutionTime += voronoiExecutionSW.getSeconds();
			}

			averageExecutionTime = averageExecutionTime / numberOfRepetitions;
			logger.info("averageExecutionTime = {}", averageExecutionTime);

		}

	}
	
}
