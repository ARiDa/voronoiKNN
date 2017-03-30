package org.arida.graphgenerator;

import org.graphast.config.Configuration;
import org.graphast.model.Edge;
import org.graphast.model.EdgeImpl;
import org.graphast.model.Graph;
import org.graphast.model.GraphImpl;
import org.graphast.model.Node;
import org.graphast.model.NodeImpl;

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

}
