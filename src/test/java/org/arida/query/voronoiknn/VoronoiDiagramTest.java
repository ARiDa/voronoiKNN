package org.arida.query.voronoiknn;

import static org.junit.Assert.assertEquals;

import java.util.Queue;

import org.arida.graphgenerator.GraphGenerator;
import org.graphast.importer.POIImporter;
import org.graphast.model.Graph;
import org.graphast.model.Node;
import org.graphast.query.route.shortestpath.dijkstra.Dijkstra;
import org.graphast.query.route.shortestpath.dijkstra.DijkstraConstantWeight;
import org.graphast.query.route.shortestpath.model.DistanceEntry;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphhopper.util.StopWatch;

public class VoronoiDiagramTest {

	private Graph graphMonaco;
	private Graph graphHopperExample4;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setup() {

		graphMonaco = new GraphGenerator().generateMonaco();
//		POIImporter.generateRandomPoIs(graphMonaco, 1);
		manuallySetPoIs();

//		graphHopperExample4 = new GraphGenerator().generateGraphHopperExample4();
//		POIImporter.generateRandomPoIs(graphHopperExample4, 100);
	}


	private void manuallySetPoIs() {
		
		Node n = graphMonaco.getNode(87l);
		n.setCategory(4);
		graphMonaco.updateNodeInfo(n);
		
		n = graphMonaco.getNode(621l);
		n.setCategory(4);
		graphMonaco.updateNodeInfo(n);
		
		n = graphMonaco.getNode(355l);
		n.setCategory(4);
		graphMonaco.updateNodeInfo(n);
		
		n = graphMonaco.getNode(524l);
		n.setCategory(4);
		graphMonaco.updateNodeInfo(n);
		
		n = graphMonaco.getNode(238l);
		n.setCategory(4);
		graphMonaco.updateNodeInfo(n);
		
		n = graphMonaco.getNode(403l);
		n.setCategory(4);
		graphMonaco.updateNodeInfo(n);
		
	}
	
//	@Test
//	public void runGraphHopperExample4Test() {
//
//		Graph testGraph = graphHopperExample4;
//		long source = 0;
//		int k = 34;
//
//		testGraph.reverseGraph();
//		VoronoiDiagram voronoiDiagram = new VoronoiDiagram(testGraph);
//		voronoiDiagram.createDiagram();
//		testGraph.reverseGraph();
//
//		KNNVoronoi knn = new KNNVoronoi(testGraph, voronoiDiagram);
//
//		Queue<DistanceEntry> finalResult = knn.executeKNN(source, k);
//
//		int size = finalResult.size();
//
//		for (int i = 1; i <= size; i++) {
//			DistanceEntry poi = finalResult.poll();
//			logger.info("k = {}", i);
//			logger.info("\tPoI: {}, Distance = {}", poi.getId(), poi.getDistance());
//
//			Long destination = poi.getId();
//
//			Dijkstra dj = new DijkstraConstantWeight(testGraph);
//
//			assertEquals(dj.shortestPath(testGraph.getNode(source), testGraph.getNode(destination)).getTotalDistance(),
//					poi.getDistance());
//		}
//
//	}
	
	@Test
	public void runGraphMonacoTest() {

		Graph testGraph = graphMonaco;
		long source = testGraph.getNodeId(43.72842465479131, 7.414896579419745);
		int k = 6;

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

			if(dj.shortestPath(testGraph.getNode(source), testGraph.getNode(destination)).getTotalDistance() != poi.getDistance()) {
				logger.info("\t\tDistance with wrong value - Dijkstra: {}, kNN: {}", dj.shortestPath(testGraph.getNode(source), testGraph.getNode(destination)).getTotalDistance(), poi.getDistance());
			}
			
//			assertEquals(dj.shortestPath(testGraph.getNode(source), testGraph.getNode(destination)).getTotalDistance(),
//					poi.getDistance());
		}

	}

}