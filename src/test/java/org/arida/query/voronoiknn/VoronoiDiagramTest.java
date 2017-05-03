package org.arida.query.voronoiknn;

import java.util.Queue;

import org.arida.graphgenerator.GraphGenerator;
import org.graphast.model.Graph;
import org.graphast.query.route.shortestpath.model.DistanceEntry;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphhopper.util.StopWatch;

public class VoronoiDiagramTest {

	private Graph graph;
//	private Graph graphMonaco;
	private Graph graphHopperExample4;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setup() {

		graph = new GraphGenerator().generateExamplePoIPaper();
//		graphMonaco = new GraphGenerator().generateMonaco();
		graphHopperExample4 = new GraphGenerator().generateGraphHopperExample4();
	}

	@Test
	public void runVoronoiPoIPaperTest() {

		StopWatch voronoiPreprocessingSW = new StopWatch();
		StopWatch voronoiExecutionSW = new StopWatch();
		long source = 0l;
		int k = 3;

		VoronoiDiagram voronoiDiagram = new VoronoiDiagram(graph);

		voronoiPreprocessingSW.start();
		voronoiDiagram.createDiagram();
		voronoiPreprocessingSW.stop();

		logger.info("Preprocessing time for the Voronoi Diagram: {}ns", voronoiPreprocessingSW.getNanos());

		KNNVoronoi knn = new KNNVoronoi(graph, voronoiDiagram);

		voronoiExecutionSW.start();
		Queue<DistanceEntry> finalResult = knn.executeKNN(source, k);
		voronoiExecutionSW.stop();
		
		logger.info("Execution time for the Voronoi Diagram: {}ns", voronoiExecutionSW.getNanos());
		
		int size = finalResult.size();
		
		for(int i = 1; i<= size; i++) {
			DistanceEntry poi = finalResult.poll();
			logger.info("k = {}", i);
			logger.info("\tPoI: {}, Distance = {}", poi.getId(), poi.getDistance());
		}
		
	}
	
	@Test
	public void runVoronoiMonacoTest() {

		StopWatch voronoiPreprocessingSW = new StopWatch();
		StopWatch voronoiExecutionSW = new StopWatch();
		long source = 0;
		int k = 4;
		
		graphHopperExample4.reverseGraph();
		
		VoronoiDiagram voronoiDiagram = new VoronoiDiagram(graphHopperExample4);

		voronoiPreprocessingSW.start();
		voronoiDiagram.createDiagram();
		voronoiPreprocessingSW.stop();
		graphHopperExample4.reverseGraph();
//		voronoiDiagram.getPolygonBorderPoints()
		logger.info("Preprocessing time for the Voronoi Diagram: {}ns", voronoiPreprocessingSW.getNanos());
		KNNVoronoi knn = new KNNVoronoi(graphHopperExample4, voronoiDiagram);

		voronoiExecutionSW.start();
		Queue<DistanceEntry> finalResult = knn.executeKNN(source, k);
		voronoiExecutionSW.stop();
		
		logger.info("Execution time for the Voronoi Diagram: {}ns", voronoiExecutionSW.getNanos());
		
		int size = finalResult.size();
		
		for(int i = 1; i<= size; i++) {
			DistanceEntry poi = finalResult.poll();
			logger.info("k = {}", i);
			logger.info("\tPoI: {}, Distance = {}", poi.getId(), poi.getDistance());
		}
		
	}

}