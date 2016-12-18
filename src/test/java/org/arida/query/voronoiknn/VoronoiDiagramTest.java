package org.arida.query.voronoiknn;

import org.arida.graphgenerator.GraphGenerator;
import org.graphast.model.Graph;
import org.junit.Before;
import org.junit.Test;

public class VoronoiDiagramTest {

	private Graph graph;
	
	@Before
	public void setup() {
		
		graph = new GraphGenerator().generateExamplePoI2();
		
	}

	@Test
	public void runVoronoiTest() {

		VoronoiDiagram voronoiDiagram = new VoronoiDiagram(graph);
		
		voronoiDiagram.createDiagram();
		
		KNNVoronoi knn = new KNNVoronoi();
		
	}
	

}