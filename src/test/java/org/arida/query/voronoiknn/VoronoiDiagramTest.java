package org.arida.query.voronoiknn;

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

	// @Test
	// public void runVoronoiMonacoTest() {
	//
	// StopWatch voronoiPreprocessingSW = new StopWatch();
	// StopWatch voronoiExecutionSW = new StopWatch();
	// long source = 0;
	// int k = 4;
	//
	// // graphHopperExample4.reverseGraph();
	//
	// VoronoiDiagram voronoiDiagram = new VoronoiDiagram(graphHopperExample4);
	//
	// voronoiPreprocessingSW.start();
	// voronoiDiagram.createDiagram();
	// voronoiPreprocessingSW.stop();
	// // graphHopperExample4.reverseGraph();
	//
	// logger.info("Preprocessing time for the Voronoi Diagram: {}ns",
	// voronoiPreprocessingSW.getNanos());
	//
	// KNNVoronoi knn = new KNNVoronoi(graphHopperExample4, voronoiDiagram);
	//
	// voronoiExecutionSW.start();
	// Queue<DistanceEntry> finalResult = knn.executeKNN(source, k);
	// voronoiExecutionSW.stop();
	//
	// logger.info("Execution time for the Voronoi Diagram: {}ns",
	// voronoiExecutionSW.getNanos());
	// System.out.println(voronoiExecutionSW.getNanos());
	// int size = finalResult.size();
	//
	// for (int i = 1; i <= size; i++) {
	// DistanceEntry poi = finalResult.poll();
	// logger.info("k = {}", i);
	// logger.info("\tPoI: {}, Distance = {}", poi.getId(), poi.getDistance());
	// }
	//
	// }

	@Test
	public void runVoronoiMonacoTest() {

		// StdDraw.setCanvasSize(1280,720);
		// StdDraw.setXscale(43.720209, 43.755420);
		// StdDraw.setYscale(7.403727, 7.446110);
		//
		// StdDraw.setPenColor(StdDraw.BLACK);
		//
		// StdDraw.setPenRadius(0.005);

		StopWatch voronoiPreprocessingSW = new StopWatch();
		StopWatch voronoiExecutionSW = new StopWatch();
		Long source = graphMonaco.getNodeId(43.72842465479131, 7.414896579419745);
		int k = 128;

		graphMonaco.reverseGraph();

		VoronoiDiagram voronoiDiagram = new VoronoiDiagram(graphMonaco);

		voronoiPreprocessingSW.start();
		voronoiDiagram.createDiagram();
		voronoiPreprocessingSW.stop();
		graphMonaco.reverseGraph();

		// for (int i = 0; i < graphMonaco.getNumberOfNodes(); i++) {
		//
		// StdDraw.point(graphMonaco.getNode(i).getLatitude(),
		// graphMonaco.getNode(i).getLongitude());
		//
		// }
		//
		// StdDraw.setPenRadius(0.001);
		//
		// for (int i = 0; i < graphMonaco.getNumberOfEdges(); i++) {
		//
		// Node fromNode =
		// graphMonaco.getNode(graphMonaco.getEdge(i).getFromNode());
		// Node toNode =
		// graphMonaco.getNode(graphMonaco.getEdge(i).getToNode());
		//
		// StdDraw.line(fromNode.getLatitude(), fromNode.getLongitude(),
		// toNode.getLatitude(), toNode.getLongitude());
		//
		// }

		logger.info("Preprocessing time for the Voronoi Diagram: {}ns", voronoiPreprocessingSW.getNanos());

		KNNVoronoi knn = new KNNVoronoi(graphMonaco, voronoiDiagram);

		voronoiExecutionSW.start();
		Queue<DistanceEntry> finalResult = knn.executeKNN(source, k);
		voronoiExecutionSW.stop();

		logger.info("Execution time for the Voronoi Diagram: {}ns", voronoiExecutionSW.getNanos());
		System.out.println(voronoiExecutionSW.getNanos());
		int size = finalResult.size();

		for (int i = 1; i <= size; i++) {
			DistanceEntry poi = finalResult.poll();
			logger.info("k = {}", i);
			logger.info("\tPoI: {}, Distance = {}", poi.getId(), poi.getDistance());
		}

	}

	// @Test
	// public void experimentMonaco() {
	// List<Integer> numberOfNeighbors = new ArrayList<>(Arrays.asList(1, 2, 4,
	// 8, 16, 32, 64, 128, 256, 512, 777));
	// int numberOfRepetitions = 10;
	// Graph testGraph = graphMonaco;
	// StopWatch voronoiPreprocessingSW = new StopWatch();
	// // StopWatch voronoiExecutionSW = new StopWatch();
	// Long source = 0l;
	//
	// testGraph.reverseGraph();
	//
	// VoronoiDiagram voronoiDiagram = new VoronoiDiagram(testGraph);
	//
	// voronoiPreprocessingSW.start();
	// voronoiDiagram.createDiagram();
	// voronoiPreprocessingSW.stop();
	//
	// testGraph.reverseGraph();
	//
	// logger.info("Preprocessing time for the Voronoi Diagram: {}ns",
	// voronoiPreprocessingSW.getNanos());
	//
	// for (Integer k : numberOfNeighbors) {
	// System.out.println("Starting to run the Voronoi-based approach for k = "
	// + k);
	// double averageExecutionTime = 0;
	//
	// for (int i = 0; i < numberOfRepetitions; i++) {
	// StopWatch voronoiExecutionSW = new StopWatch();
	// KNNVoronoi knn = new KNNVoronoi(testGraph, voronoiDiagram);
	// voronoiExecutionSW.start();
	// knn.executeKNN(source, k);
	// voronoiExecutionSW.stop();
	//
	// averageExecutionTime += voronoiExecutionSW.getSeconds();
	// }
	//
	// averageExecutionTime = averageExecutionTime / numberOfRepetitions;
	// System.out.println("averageExecutionTime = " + averageExecutionTime);
	//
	// }
	//
	// }

//	@Test
//	public void shortestPathTest() {
//
//		Long source = 552l;
//		Long destination = 447l;
//
//		Dijkstra dj = new DijkstraConstantWeight(graphMonaco);
//		System.out.println(dj.shortestPath(graphMonaco.getNode(source), graphMonaco.getNode(destination)).getTotalDistance());
//
//	}

}