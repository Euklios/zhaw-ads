package ch.zhaw.ads;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class RouteServer implements CommandExecutor {
    /*
    build the graph given a text file with the topology
    */
    public Graph<DijkstraNode, Edge> createGraph(String topo) {
        Graph<DijkstraNode, Edge> graph = new AdjListGraph<>(DijkstraNode.class, Edge.class);
        for (String line : topo.split("\n")) {
            String[] split = line.split(" ");

            assert split.length == 3;

            String city1 = split[0];
            String city2 = split[1];
            int distance = Integer.parseInt(split[2]);

            try {
                graph.addEdge(city1, city2, distance);
                graph.addEdge(city2, city1, distance);
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }

        return graph;
    }


    /*
    apply the dijkstra algorithm
    */
    public void dijkstraRoute(Graph<DijkstraNode, Edge> graph, String from, String to) {
        PriorityQueue<DijkstraNode> q = new PriorityQueue<>(Comparator.comparing(dijkstraNode -> dijkstraNode.dist));
        DijkstraNode start = graph.findNode(from);
        DijkstraNode end = graph.findNode(to);

        start.dist = 0;
        q.add(start);
        while (!q.isEmpty()) {
            DijkstraNode current = q.poll();
            current.mark = true;
            if (current.equals(end)) {
                return;
            }

            for (Object e : current.getEdges()) {
                Edge edge = (Edge) e;
                DijkstraNode n = (DijkstraNode) edge.getDest();

                if (!(n.mark)) {
                    double dist = ((Edge<?>) e).weight + current.dist;
                    if ((n.prev == null) || (dist < n.dist)) {
                        q.remove(n);
                        n.dist = dist;
                        n.prev = current;
                        q.offer(n);
                    }
                }
            }
        }
    }

    /*
    find the route in the graph after applied dijkstra
    the route should be returned with the start town first
    */
    public List<DijkstraNode<Edge>> getRoute(Graph<DijkstraNode, Edge> graph, String to) {
        List<DijkstraNode<Edge>> route = new LinkedList<>();
        DijkstraNode<Edge> town = graph.findNode(to);
        do {
            route.add(0,town);
            town = town.getPrev();
        } while (town != null);
        return route;
    }

    public String execute(String topo) throws Exception {
        Graph<DijkstraNode, Edge> graph = createGraph(topo);
        dijkstraRoute(graph, "Winterthur", "Lugano");
        List<DijkstraNode<Edge>> route = getRoute(graph, "Lugano");
        // generate result string
        StringBuffer buf = new StringBuffer();
        for (DijkstraNode<Edge> rt : route) buf.append(rt+"\n");
        return buf.toString();
    }

    public static void main(String[] args)throws Exception {
        String swiss =
                "Winterthur Zürich 25\n"+
                        "Zürich Bern 126\n"+
                        "Zürich Genf 277\n"+
                        "Zürich Luzern 54\n"+
                        "Zürich Chur 121\n"+
                        "Zürich Berikon 16\n"+
                        "Bern Genf 155\n"+
                        "Genf Lugano 363\n"+
                        "Lugano Luzern 206\n"+
                        "Lugano Chur 152\n"+
                        "Chur Luzern 146\n"+
                        "Luzern Bern 97\n"+
                        "Bern Berikon 102\n"+
                        "Luzern Berikon 41\n";
        RouteServer server = new RouteServer();
        System.out.println(server.execute(swiss));
    }
}
