package org.arida.query.voronoiknn;

import static org.graphast.util.NumberUtils.convertToInt;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.graphast.model.Edge;
import org.graphast.model.Graph;
import org.graphast.model.Node;
import org.graphast.model.contraction.CHEdge;
import org.graphast.query.route.shortestpath.model.DistanceEntry;
import org.graphast.query.route.shortestpath.model.RouteEntry;

import it.unimi.dsi.fastutil.longs.Long2IntMap;

public class DijkstraVD {

	//External references
	private Graph graph;
	private Set<Long> globalSettleNodes = new HashSet<Long>();
	private Queue<DistanceEntry> globalPriorityQueue = new PriorityQueue<DistanceEntry>();
	private Node source;
	
	private int wasRemoved = -1;

	//Internal attributes
	private Queue<DistanceEntry> queue = new PriorityQueue<DistanceEntry>();
	private HashMap<Long, Integer> wasTraversed = new HashMap<Long, Integer>();
	private HashMap<Long, Integer> wasTraversedPoI = new HashMap<Long, Integer>();
	private HashMap<Long, RouteEntry> parents = new HashMap<Long, RouteEntry>();
	private DistanceEntry removed = null;

	public DijkstraVD(Graph graph, Node source, Set<Long> globalSettleNodes, Queue<DistanceEntry> globalPriorityQueue) {
		
		this.graph = graph;
		this.source = source;
		this.globalSettleNodes = globalSettleNodes;
		this.globalPriorityQueue = globalPriorityQueue;
		
		init(source, queue);

	}

	public void init(Node source, Queue<DistanceEntry> queue) {
		
		int sid = convertToInt(source.getId());
		queue.offer(new DistanceEntry(sid, 0, -1));
	
	}

	public void iterate() {

		removed = queue.poll();
		
		System.out.println("Node being analyzed: " + removed.getId());
		
		
		if(globalSettleNodes.contains(removed.getId())) {
			
			//TODO Verificar se ja existe esse nó no globalSettleNodes. Se sim, colocar a distancia como border distance
			return;
		} else {
			
			wasTraversed.put(removed.getId(), wasRemoved);
			globalSettleNodes.add(removed.getId());
			
		}
		
		expandVertex(removed, queue, parents, wasTraversed, wasTraversedPoI);

	}
	
	public void expandVertex(DistanceEntry removed, Queue<DistanceEntry> queue,
			HashMap<Long, RouteEntry> parents, HashMap<Long, Integer> wasTraversed,
			HashMap<Long, Integer> wasTraversedPoI) {

		Long2IntMap neighbors = graph.accessNeighborhood(graph.getNode(removed.getId()));

		for (long vid : neighbors.keySet()) {

			DistanceEntry newEntry = new DistanceEntry(vid, removed.getDistance() + neighbors.get(vid), removed.getId());

			Edge edge = null;
			int distance = -1;

			if (!wasTraversed.containsKey(vid)) {

				queue.offer(newEntry);
				//colocar esse nó na queue global
				
				
				//Verificar se ja existe esse nó no globalSettleNodes. Se sim, colocar a distancia
				//como border distance
				//colocar esse nó na globalPriorityQueue
				
				
				wasTraversed.put(newEntry.getId(), newEntry.getDistance());

				distance = neighbors.get(vid);
				edge = getEdge(removed.getId(), vid, distance);

				parents.put(vid, new RouteEntry(removed.getId(), distance, edge.getId(), edge.getLabel()));
				
				//TODO Create a similar class, just like the DistanceEntry.
				//Esse é uma entrada especial. Está apenas mapeando a atual distancia para o PoI correpondente
				globalPriorityQueue.add(new DistanceEntry(source.getId(), newEntry.getDistance(), vid));

			} else {

				int cost = wasTraversed.get(vid);
				distance = newEntry.getDistance();

				if (cost != wasRemoved) {
					if (cost > distance) {
						queue.remove(newEntry);
						queue.offer(newEntry);
						wasTraversed.remove(newEntry.getId());
						
						//colocar esse nó na globalPriorityQueue
						//TODO
//						globalPriorityQueue.add(new DistanceEntry(source.getId(), distance, -1l));
						
						wasTraversed.put(newEntry.getId(), newEntry.getDistance());

						//TODO Double check if this is working
						newEntry.setId(source.getId());
						newEntry.setParent(vid);
						newEntry.setDistance(distance);
						globalPriorityQueue.remove(newEntry);
						globalPriorityQueue.add(new DistanceEntry(source.getId(), distance, vid));
						
						parents.remove(vid);
						distance = neighbors.get(vid);
						edge = getEdge(removed.getId(), vid, distance);
						parents.put(vid, new RouteEntry(removed.getId(), distance, edge.getId(), edge.getLabel()));
						
						

					}
				}
			}
		}
	}
	
	private Edge getEdge(long fromNodeId, long toNodeId, int distance) {
		Edge edge = null;
		for (Long outEdge : graph.getOutEdges(fromNodeId)) {
			edge = graph.getEdge(outEdge);
			if ((int) edge.getToNode() == toNodeId && edge.getDistance() == distance) {
				break;
			}
		}
		return edge;
	}

}
