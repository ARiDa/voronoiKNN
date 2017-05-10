package org.arida.query.voronoiknn;

import static org.junit.Assert.assertEquals;

import java.util.Queue;

import org.arida.graphgenerator.GraphGenerator;
import org.graphast.importer.POIImporter;
import org.graphast.model.Graph;
import org.graphast.query.route.shortestpath.dijkstra.Dijkstra;
import org.graphast.query.route.shortestpath.dijkstra.DijkstraConstantWeight;
import org.graphast.query.route.shortestpath.model.DistanceEntry;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphhopper.util.StopWatch;

public class VoronoiDiagramTest {

	private Graph graph;
	private Graph graphMonaco;
	private Graph graphHopperExample4;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setup() {

		graph = new GraphGenerator().generateExamplePoIPaper();

		StopWatch preprocessingSW = new StopWatch();
		preprocessingSW.start();
		graphMonaco = new GraphGenerator().generateMonaco();

		logger.info("preprocessingTime = {} seconds", preprocessingSW.getNanos());

		logger.info("Starting to generate PoI'S");
		POIImporter.generateRandomPoIs(graphMonaco, 100);
		logger.info("Finishing PoI's generation.");

		graphHopperExample4 = new GraphGenerator().generateGraphHopperExample4();
		POIImporter.generateRandomPoIs(graphHopperExample4, 100);
	}

	// @Test
	// public void runVoronoiPoIPaperTest() {
	//
	// StopWatch voronoiPreprocessingSW = new StopWatch();
	// StopWatch voronoiExecutionSW = new StopWatch();
	// long source = 0l;
	// int k = 3;
	//
	// VoronoiDiagram voronoiDiagram = new VoronoiDiagram(graph);
	//
	// voronoiPreprocessingSW.start();
	// voronoiDiagram.createDiagram();
	// voronoiPreprocessingSW.stop();
	//
	// logger.info("Preprocessing time for the Voronoi Diagram: {}ns",
	// voronoiPreprocessingSW.getNanos());
	//
	// KNNVoronoi knn = new KNNVoronoi(graph, voronoiDiagram);
	//
	// voronoiExecutionSW.start();
	// Queue<DistanceEntry> finalResult = knn.executeKNN(source, k);
	// voronoiExecutionSW.stop();
	//
	// logger.info("Execution time for the Voronoi Diagram: {}ns",
	// voronoiExecutionSW.getNanos());
	//
	// int size = finalResult.size();
	//
	// for(int i = 1; i<= size; i++) {
	// DistanceEntry poi = finalResult.poll();
	// logger.info("k = {}", i);
	// logger.info("\tPoI: {}, Distance = {}", poi.getId(), poi.getDistance());
	// }
	//
	// }

	@Test
	public void runGraphHopperExample4Test() {

		Graph testGraph = graphHopperExample4;
		long source = 0;
		int k = 34;

		testGraph.reverseGraph();
		VoronoiDiagram voronoiDiagram = new VoronoiDiagram(testGraph);
		voronoiDiagram.createDiagram();
		testGraph.reverseGraph();

		KNNVoronoi knn = new KNNVoronoi(testGraph, voronoiDiagram);

		Queue<DistanceEntry> finalResult = knn.executeKNN(source, k);

		int size = finalResult.size();

		for (int i = 1; i <= size; i++) {
			DistanceEntry poi = finalResult.poll();
			logger.info("k = {}", i);
			logger.info("\tPoI: {}, Distance = {}", poi.getId(), poi.getDistance());

			Long destination = poi.getId();

			Dijkstra dj = new DijkstraConstantWeight(testGraph);

			assertEquals(dj.shortestPath(testGraph.getNode(source), testGraph.getNode(destination)).getTotalDistance(),
					poi.getDistance());
		}

	}

	

//	@Test
//	public void experimentMonaco() {
//		List<Integer> numberOfNeighbors = new ArrayList<>(Arrays.asList(1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 777));
//		int numberOfRepetitions = 100;
//		Graph testGraph = graphMonaco;
//		StopWatch voronoiPreprocessingSW = new StopWatch();
//		// StopWatch voronoiExecutionSW = new StopWatch();
//		Long source = 0l;
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
//		logger.info("Preprocessing time for the Voronoi Diagram: {}ns", voronoiPreprocessingSW.getNanos());
//
//		for (Integer k : numberOfNeighbors) {
//			System.out.println("Starting to run the Voronoi-based approach for k = " + k);
//			double averageExecutionTime = 0;
//
//			for (int i = 0; i < numberOfRepetitions; i++) {
//				StopWatch voronoiExecutionSW = new StopWatch();
//				KNNVoronoi knn = new KNNVoronoi(testGraph, voronoiDiagram);
//				voronoiExecutionSW.start();
//				knn.executeKNN(source, k);
//				voronoiExecutionSW.stop();
//
//				averageExecutionTime += voronoiExecutionSW.getSeconds();
//			}
//
//			averageExecutionTime = averageExecutionTime / numberOfRepetitions;
//			System.out.println("averageExecutionTime = " + averageExecutionTime);
//
//		}
//
//	}

	// @Test
	// public void shortestPathTest() {
	//
	// Long source = 552l;
	// Long destination = 726l;
	//
	// Dijkstra dj = new DijkstraConstantWeight(graphMonaco);
	// System.out
	// .println(dj.shortestPath(graphMonaco.getNode(source),
	// graphMonaco.getNode(destination)).getTotalCost());
	//
	// }

}