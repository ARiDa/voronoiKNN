package org.arida.query.voronoiknn;

import org.arida.graphgenerator.GraphGenerator;
import org.graphast.model.Graph;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.graphhopper.util.StopWatch;

public class VoronoiDiagramTest {

	private Graph graph;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before
	public void setup() {

		graph = new GraphGenerator().generateExamplePoIPaper();

	}

	@Test
	public void runVoronoiTest() {

		StopWatch voronoiPreprocessingSW = new StopWatch();
		long source = 0l;
		int k = 3;

		VoronoiDiagram voronoiDiagram = new VoronoiDiagram(graph);

		voronoiPreprocessingSW.start();
		voronoiDiagram.createDiagram();
		voronoiPreprocessingSW.stop();

		logger.info("Preprocessing time for the Voronoi Diagram: {}ns", voronoiPreprocessingSW.getNanos());

		KNNVoronoi knn = new KNNVoronoi(graph, voronoiDiagram);

		knn.executeKNN(source, k);

	}

}