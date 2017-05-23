package org.arida.query.voronoiknn;

import org.arida.graphgenerator.GraphGenerator;
import org.graphast.model.Graph;
import org.graphast.query.route.shortestpath.dijkstra.Dijkstra;
import org.graphast.query.route.shortestpath.dijkstra.DijkstraConstantWeight;
import org.junit.Before;
import org.junit.Test;

public class ShortestPathTest {

	private Graph graphMonaco;

	@Before
	public void setup() {

		graphMonaco = new GraphGenerator().generateMonaco();

	}

	@Test
	public void shortestPathTest() {

		Long source = 552l;
		Long destination = 403l;
//		graphMonaco.reverseGraph();
		Dijkstra dj = new DijkstraConstantWeight(graphMonaco);
		System.out.println(dj.shortestPath(graphMonaco.getNode(source), graphMonaco.getNode(destination)).getTotalDistance());
//		graphMonaco.reverseGraph();

	}

}
