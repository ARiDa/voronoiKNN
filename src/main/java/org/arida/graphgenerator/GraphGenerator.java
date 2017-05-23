package org.arida.graphgenerator;

import org.graphast.config.Configuration;
import org.graphast.importer.CostGenerator;
import org.graphast.importer.OSMImporterImpl;
import org.graphast.model.Edge;
import org.graphast.model.EdgeImpl;
import org.graphast.model.Graph;
import org.graphast.model.GraphBounds;
import org.graphast.model.GraphImpl;
import org.graphast.model.Node;
import org.graphast.model.NodeImpl;
import org.graphast.model.contraction.CHEdge;
import org.graphast.model.contraction.CHEdgeImpl;
import org.graphast.model.contraction.CHGraph;
import org.graphast.model.contraction.CHGraphImpl;
import org.graphast.model.contraction.CHNode;
import org.graphast.model.contraction.CHNodeImpl;

public class GraphGenerator {

	public Graph generateExamplePoI() {

		Graph graph = new GraphImpl(Configuration.USER_HOME + "/graphast/test/examplePoI");

		Node node;
		Edge edge;

		// Creating Nodes

		node = new NodeImpl(0l, 0.0d, 1.0d);
		graph.addNode(node);

		node = new NodeImpl(1l, 0.0d, 10.0d);
		node.setCategory(1);
		node.setLabel("Bradesco");
		graph.addNode(node);

		node = new NodeImpl(2l, 0.0d, 20.0d);
		graph.addNode(node);

		node = new NodeImpl(3l, 0.0d, 30.0d);
		graph.addNode(node);

		node = new NodeImpl(4l, 0.0d, 40.0d);
		node.setCategory(2);
		node.setLabel("Padaria Costa Mendes");
		graph.addNode(node);

		node = new NodeImpl(5l, 10.0d, 0.0d);
		graph.addNode(node);

		node = new NodeImpl(6l, 10.0d, 10.0d);
		graph.addNode(node);

		node = new NodeImpl(7l, 10.0d, 20.0d);
		graph.addNode(node);

		node = new NodeImpl(8l, 10.0d, 30.0d);
		graph.addNode(node);

		node = new NodeImpl(9l, 10.0d, 40.0d);
		node.setCategory(3);
		node.setLabel("Escola Vila");
		graph.addNode(node);

		// Creating Edges

		edge = new EdgeImpl(1l, 0l, 1l, 10);
		graph.addEdge(edge);

		edge = new EdgeImpl(2l, 1l, 2l, 20);
		graph.addEdge(edge);

		edge = new EdgeImpl(3l, 1l, 7l, 30);
		graph.addEdge(edge);

		edge = new EdgeImpl(4l, 2l, 3l, 40);
		graph.addEdge(edge);

		edge = new EdgeImpl(5l, 3l, 4l, 50);
		graph.addEdge(edge);

		edge = new EdgeImpl(6l, 4l, 8l, 60);
		graph.addEdge(edge);

		edge = new EdgeImpl(7l, 4l, 9l, 70);
		graph.addEdge(edge);

		edge = new EdgeImpl(8l, 5l, 0l, 80);
		graph.addEdge(edge);

		edge = new EdgeImpl(9l, 6l, 5l, 90);
		graph.addEdge(edge);

		edge = new EdgeImpl(10l, 7l, 2l, 100);
		graph.addEdge(edge);

		edge = new EdgeImpl(11l, 7l, 6l, 110);
		graph.addEdge(edge);

		edge = new EdgeImpl(12l, 8l, 7l, 120);
		graph.addEdge(edge);

		edge = new EdgeImpl(13l, 9l, 8l, 130);
		graph.addEdge(edge);

		graph.save();
		return graph;

	}
	
	public Graph generateExamplePoI2() {

		Graph graph = new GraphImpl(Configuration.USER_HOME + "/graphast/test/examplePoI2");

		Node node;
		Edge edge;

		// Creating Nodes

		node = new NodeImpl(0l, 10.0d, 40.0d);
		node.setCategory(1);
		node.setLabel("Category1");
		graph.addNode(node);

		node = new NodeImpl(1l, 30.0d, 40.0d);
		node.setCategory(2);
		node.setLabel("Category2");
		graph.addNode(node);

		node = new NodeImpl(2l, 25.0d, 10.0d);
		node.setCategory(3);
		node.setLabel("Category3");
		graph.addNode(node);

		node = new NodeImpl(3l, 18.0d, 40.0d);
		graph.addNode(node);

		node = new NodeImpl(4l, 25.0d, 40.0d);
		graph.addNode(node);

		node = new NodeImpl(5l, 20.0d, 30.0d);
		graph.addNode(node);

		node = new NodeImpl(6l, 25.0d, 30.0d);
		graph.addNode(node);

		node = new NodeImpl(7l, 10.0d, 20.0d);
		graph.addNode(node);

		node = new NodeImpl(8l, 18.0d, 20.0d);
		graph.addNode(node);

		node = new NodeImpl(9l, 25.0d, 20.0d);
		graph.addNode(node);
		
		node = new NodeImpl(10l, 30.0d, 20.0d);
		graph.addNode(node);
		
		node = new NodeImpl(11l, 20.0d, 25.0d);
		graph.addNode(node);
		
		node = new NodeImpl(12l, 25.0d, 15.0d);
		graph.addNode(node);
		
		node = new NodeImpl(13l, 10.0d, 10.0d);
		graph.addNode(node);
		
		node = new NodeImpl(14l, 15.0d, 10.0d);
		graph.addNode(node);
		
		node = new NodeImpl(15l, 30.0d, 10.0d);
		graph.addNode(node);

		// Creating Edges

		edge = new EdgeImpl(0l, 0l, 3l, 10);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(1l, 0l, 7l, 9);
		graph.addEdge(edge);

		edge = new EdgeImpl(2l, 1l, 4l, 6);
		graph.addEdge(edge);

		edge = new EdgeImpl(3l, 1l, 10l, 9);
		graph.addEdge(edge);

		edge = new EdgeImpl(4l, 2l, 12l, 4);
		graph.addEdge(edge);

		edge = new EdgeImpl(5l, 2l, 14l, 15);
		graph.addEdge(edge);

		edge = new EdgeImpl(6l, 2l, 15l, 6);
		graph.addEdge(edge);

		edge = new EdgeImpl(7l, 3l, 4l, 11);
		graph.addEdge(edge);

		edge = new EdgeImpl(8l, 3l, 5l, 6);
		graph.addEdge(edge);

		edge = new EdgeImpl(9l, 4l, 6l, 5);
		graph.addEdge(edge);

		edge = new EdgeImpl(10l, 5l, 6l, 7);
		graph.addEdge(edge);

		edge = new EdgeImpl(11l, 5l, 8l, 6);
		graph.addEdge(edge);

		edge = new EdgeImpl(12l, 6l, 9l, 5);
		graph.addEdge(edge);

		edge = new EdgeImpl(13l, 7l, 8l, 10);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(14l, 7l, 13l, 9);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(15l, 8l, 11l, 6);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(16l, 9l, 10l, 6);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(17l, 9l, 12l, 4);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(18l, 10l, 15l, 9);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(19l, 11l, 12l, 7);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(20l, 11l, 14l, 10);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(21l, 13l, 14l, 7);
		graph.addEdge(edge);
		
		
		
		
		edge = new EdgeImpl(22l, 3l, 0l, 10);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(23l, 7l, 0l, 9);
		graph.addEdge(edge);

		edge = new EdgeImpl(24l, 4l, 1l, 6);
		graph.addEdge(edge);

		edge = new EdgeImpl(25l, 10l, 1l, 9);
		graph.addEdge(edge);

		edge = new EdgeImpl(26l, 12l, 2l, 4);
		graph.addEdge(edge);

		edge = new EdgeImpl(27l, 14l, 2l, 15);
		graph.addEdge(edge);

		edge = new EdgeImpl(28l, 15l, 2l, 6);
		graph.addEdge(edge);

		edge = new EdgeImpl(29l, 4l, 3l, 11);
		graph.addEdge(edge);

		edge = new EdgeImpl(30l, 5l, 3l, 6);
		graph.addEdge(edge);

		edge = new EdgeImpl(31l, 6l, 4l, 5);
		graph.addEdge(edge);

		edge = new EdgeImpl(32l, 6l, 5l, 7);
		graph.addEdge(edge);

		edge = new EdgeImpl(33l, 8l, 5l, 6);
		graph.addEdge(edge);

		edge = new EdgeImpl(34l, 9l, 6l, 5);
		graph.addEdge(edge);

		edge = new EdgeImpl(35l, 8l, 7l, 10);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(36l, 13l, 7l, 9);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(37l, 11l, 8l, 6);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(38l, 10l, 9l, 6);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(39l, 12l, 9l, 4);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(40l, 15l, 10l, 9);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(41l, 12l, 11l, 7);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(42l, 14l, 11l, 10);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(43l, 14l, 13l, 7);
		graph.addEdge(edge);

		graph.save();
		return graph;

	}
	
	public Graph generateExamplePoIPaper() {

		Graph graph = new GraphImpl(Configuration.USER_HOME + "/graphast/test/examplePoIPaper");

		Node node;
		Edge edge;

		// Creating Nodes

		node = new NodeImpl(1l, 5.0d, 15.0d);
		node.setCategory(1);
		node.setLabel("PoI 1");
		graph.addNode(node);

		node = new NodeImpl(2l, 24.0d, 15.0d);
		node.setCategory(1);
		node.setLabel("PoI 2");
		graph.addNode(node);
		
		node = new NodeImpl(3l, 20.0d, 5.0d);
		node.setCategory(1);
		node.setLabel("PoI 3");
		graph.addNode(node);
		
		node = new NodeImpl(4l, 10.0d, 15.0d);
		graph.addNode(node);
		
		node = new NodeImpl(5l, 20.0d, 15.0d);
		graph.addNode(node);
		
		node = new NodeImpl(6l, 12.0d, 13.0d);
		graph.addNode(node);
		
		node = new NodeImpl(7l, 20.0d, 13.0d);
		graph.addNode(node);
		
		node = new NodeImpl(8l, 5.0d, 10.0d);
		graph.addNode(node);
		
		node = new NodeImpl(9l, 10.0d, 10.0d);
		graph.addNode(node);
		
		node = new NodeImpl(10l, 20.0d, 10.0d);
		graph.addNode(node);
		
		node = new NodeImpl(11l, 24.0d, 10.0d);
		graph.addNode(node);
		
		node = new NodeImpl(12l, 12.0d, 8.0d);
		graph.addNode(node);
		
		node = new NodeImpl(13l, 20.0d, 8.0d);
		graph.addNode(node);
		
		node = new NodeImpl(14l, 5.0d, 5.0d);
		graph.addNode(node);
		
		node = new NodeImpl(15l, 8.0d, 5.0d);
		graph.addNode(node);
		
		node = new NodeImpl(16l, 24.0d, 5.0d);
		graph.addNode(node);

		
		// Creating Edges

		edge = new EdgeImpl(1l, 0l, 3l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(2l, 3l, 0l, 5);
		graph.addEdge(edge);

		edge = new EdgeImpl(3l, 0l, 7l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(4l, 7l, 0l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(5l, 1l, 4l, 4);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(6l, 4l, 1l, 4);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(7l, 1l, 10l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(8l, 10l, 1l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(9l, 2l, 12l, 3);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(10l, 12l, 2l, 3);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(11l, 2l, 14l, 12);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(12l, 14l, 2l, 12);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(13l, 2l, 15l, 4);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(14l, 15l, 2l, 4);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(15l, 3l, 4l, 10);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(16l, 4l, 3l, 10);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(17l, 3l, 5l, 6);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(18l, 5l, 3l, 6);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(19l, 4l, 6l, 2);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(20l, 6l, 4l, 2);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(21l, 5l, 6l, 8);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(22l, 6l, 5l, 8);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(23l, 5l, 8l, 4);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(24l, 8l, 5l, 4);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(25l, 6l, 9l, 3);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(26l, 9l, 6l, 3);
		graph.addEdge(edge);

		edge = new EdgeImpl(27l, 7l, 8l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(28l, 8l, 7l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(29l, 7l, 13l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(30l, 13l, 7l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(31l, 8l, 11l, 3);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(32l, 11l, 8l, 3);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(33l, 9l, 10l, 4);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(34l, 10l, 9l, 4);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(35l, 9l, 12l, 2);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(36l, 12l, 9l, 2);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(37l, 10l, 15l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(38l, 15l, 10l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(39l, 11l, 12l, 8);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(40l, 12l, 11l, 8);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(41l, 11l, 14l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(42l, 14l, 11l, 5);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(43l, 13l, 14l, 3);
		graph.addEdge(edge);
		
		edge = new EdgeImpl(44l, 14l, 13l, 3);
		graph.addEdge(edge);
		
		graph.save();
		return graph;

	}
	
	public GraphBounds generateMonaco() {

		String osmFile = this.getClass().getResource("/monaco-latest.osm.pbf").getPath();
		String graphHopperMonacoDir = Configuration.USER_HOME + "/graphhopper/test/monaco";
		String graphastMonacoDir = Configuration.USER_HOME + "/graphast/test/monaco";

		GraphBounds graph = new OSMImporterImpl(osmFile, graphHopperMonacoDir, graphastMonacoDir).execute();

//		POIImporter.importPoIList(graph, "src/test/resources/monaco-latest.csv");

		CostGenerator.generateAllSyntheticEdgesCosts(graph);

		return graph;

	}
	
	/*
	 * Based on the initRoundaboutGraph() graph from GraphHopper
	 * PrepareContractionHierarchiesTest.java class.
	 */
	public CHGraph generateGraphHopperExample4() {

		CHGraph graph = new CHGraphImpl(Configuration.USER_HOME + "/voronoiKNN/test/graphHopperExample4WithPoIs");

		CHEdge edge;
		CHNode node;

		node = new CHNodeImpl(0l, 10, 30, 1);
		graph.addNode(node);

		node = new CHNodeImpl(1l, 10, 10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(2l, 20, 10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(3l, 30, 10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(4l, 40, 10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(5l, 50, 0, 1);
		graph.addNode(node);

		node = new CHNodeImpl(6l, 60, 0, 1);
		graph.addNode(node);

		node = new CHNodeImpl(7l, 70, 10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(8l, 80, 10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(9l, 20, 30, 1);
		graph.addNode(node);

		node = new CHNodeImpl(10l, 30, 30, 1);
		graph.addNode(node);

		node = new CHNodeImpl(11l, 40, 30, 1);
		graph.addNode(node);

		node = new CHNodeImpl(12l, 50, 20, 1);
		graph.addNode(node);

		node = new CHNodeImpl(13l, 60, 20, 1);
		graph.addNode(node);

		node = new CHNodeImpl(14l, 10, 0, 1);
		graph.addNode(node);

		node = new CHNodeImpl(15l, -10, 10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(16l, -10, 30, 1);
		graph.addNode(node);

		node = new CHNodeImpl(17l, 10, 20, 1);
		graph.addNode(node);

		node = new CHNodeImpl(18l, 10, -10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(19l, 10, -20, 1);
		graph.addNode(node);

		node = new CHNodeImpl(20l, 0, -10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(21l, 0, -20, 1);
		graph.addNode(node);

		node = new CHNodeImpl(22l, 80, 0, 1);
		graph.addNode(node);

		node = new CHNodeImpl(23l, 80, -10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(24l, 70, -10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(25l, 50, -10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(26l, 40, -30, 1);
		graph.addNode(node);

		node = new CHNodeImpl(27l, 40, -10, 1);
		graph.addNode(node);

		node = new CHNodeImpl(28l, 40, 50, 1);
		graph.addNode(node);

		node = new CHNodeImpl(29l, 50, 50, 1);
		graph.addNode(node);

		node = new CHNodeImpl(30l, 50, 40, 1);
		graph.addNode(node);

		node = new CHNodeImpl(31l, 50, 30, 1);
		graph.addNode(node);
		
		node = new CHNodeImpl(32l, 70, 30);
		graph.addNode(node);
		
		node = new CHNodeImpl(33l, 10, 50);
		graph.addNode(node);

		// TODO Create a constructor without the originalEdgeCounter
		edge = new CHEdgeImpl(0l, 16l, 20, 1, "Edge 0");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(16l, 0l, 20, 1, "Edge 1");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(0l, 9l, 10, 1, "Edge 2");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(9l, 0l, 10, 1, "Edge 3");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(0l, 17l, 10, 1, "Edge 4");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(17l, 0l, 10, 1, "Edge 5");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(9l, 10l, 10, 1, "Edge 6");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(10l, 9l, 10, 1, "Edge 7");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(10l, 11l, 10, 1, "Edge 8");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(11l, 10l, 10, 1, "Edge 9");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(11l, 28l, 20, 1, "Edge 10");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(28l, 11l, 20, 1, "Edge 11");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(28l, 29l, 10, 1, "Edge 12");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(29l, 28l, 10, 1, "Edge 13");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(29l, 30l, 10, 1, "Edge 14");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(30l, 29l, 10, 1, "Edge 15");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(30l, 31l, 10, 1, "Edge 16");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(31l, 30l, 10, 1, "Edge 17");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(31l, 4l, 22, 1, "Edge 18");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(4l, 31l, 22, 1, "Edge 19");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(17l, 1l, 10, 1, "Edge 20");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(1l, 17l, 10, 1, "Edge 21");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(15l, 1l, 20, 1, "Edge 22");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(1l, 15l, 20, 1, "Edge 23");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(14l, 1l, 10, 1, "Edge 24");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(1l, 14l, 10, 1, "Edge 25");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(14l, 18l, 10, 1, "Edge 26");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(18l, 14l, 10, 1, "Edge 27");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(18l, 19l, 10, 1, "Edge 28");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(19l, 18l, 10, 1, "Edge 29");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(19l, 20l, 14, 1, "Edge 30");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(20l, 19l, 14, 1, "Edge 31");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(19l, 21l, 10, 1, "Edge 32");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(21l, 19l, 10, 1, "Edge 33");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(21l, 16l, 51, 1, "Edge 34");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(16l, 21l, 51, 1, "Edge 35");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(1l, 2l, 10, 1, "Edge 36");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(2l, 1l, 10, 1, "Edge 37");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(2l, 3l, 10, 1, "Edge 38");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(3l, 2l, 10, 1, "Edge 39");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(3l, 4l, 10, 1, "Edge 40");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(4l, 3l, 10, 1, "Edge 41");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(4l, 5l, 14, 1, "Edge 42");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(5l, 6l, 10, 1, "Edge 43");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(6l, 7l, 14, 1, "Edge 44");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(7l, 13l, 14, 1, "Edge 45");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(13l, 12l, 10, 1, "Edge 46");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(12l, 4l, 14, 1, "Edge 47");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(7l, 8l, 10, 1, "Edge 48");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(8l, 7l, 10, 1, "Edge 49");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(8l, 22l, 10, 1, "Edge 50");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(22l, 8l, 10, 1, "Edge 51");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(22l, 23l, 10, 1, "Edge 52");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(23l, 22l, 10, 1, "Edge 53");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(23l, 24l, 10, 1, "Edge 54");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(24l, 23l, 10, 1, "Edge 55");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(24l, 25l, 20, 1, "Edge 56");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(25l, 24l, 20, 1, "Edge 57");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(25l, 27l, 10, 1, "Edge 58");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(27l, 25l, 10, 1, "Edge 59");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(27l, 5l, 14, 1, "Edge 60");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(5l, 27l, 14, 1, "Edge 61");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(25l, 26l, 22, 1, "Edge 62");
		graph.addEdge(edge);

		edge = new CHEdgeImpl(26l, 25l, 22, 1, "Edge 63");
		graph.addEdge(edge);
		
		edge = new CHEdgeImpl(13l, 32l, 14, 1, "Edge 64");
		graph.addEdge(edge);
		
		edge = new CHEdgeImpl(0l, 33l, 20, 1, "Edge 65");
		graph.addEdge(edge);
		
		edge = new CHEdgeImpl(0l, 9l, 100, 1, "Edge 66");
		graph.addEdge(edge);

		// graph.createHyperPOIS();

		graph.setMaximumEdgeCount((int) graph.getNumberOfEdges());

		graph.save();

		return graph;

	}

}
