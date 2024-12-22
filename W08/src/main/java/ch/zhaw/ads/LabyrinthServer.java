package ch.zhaw.ads;


public class LabyrinthServer implements CommandExecutor {
    ServerGraphics g = new ServerGraphics();

    public Graph<DijkstraNode, Edge> createGraph(String s) {
        Graph<DijkstraNode, Edge> graph = new AdjListGraph<>(DijkstraNode.class, Edge.class);

        for (String line : s.split("\n")) {
            String[] split = line.split(" ");
            assert split.length == 2;

            try {
                graph.addEdge(split[0], split[1], 1);
                graph.addEdge(split[1], split[0], 1);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        return graph;
    }

    public void drawLabyrinth(Graph<DijkstraNode, Edge> graph) {
        for (DijkstraNode node : graph.getNodes()) {
            for (Object o : node.getEdges()) {
                Edge edge = (Edge) o;
                g.drawPath(node.getName(), ((Node)edge.getDest()).getName(), false);
            }
        }
    }

    private boolean search(DijkstraNode<Edge> current, DijkstraNode<Edge> ziel) {
        if (current == ziel) return true;

        if (current.getMark()) {
            return false;
        }
        current.setMark(true);

        for (Edge edge : current.getEdges()) {
            DijkstraNode dest = (DijkstraNode) edge.getDest();

            if (search(dest, ziel)) {
                g.drawPath(current.getName(), dest.getName(), true);
                return true;
            }
        }

        return false;
    }

    // search and draw result
    public void drawRoute(Graph<DijkstraNode, Edge> graph, String startNode, String zielNode) {
        search(graph.findNode(startNode), graph.findNode(zielNode));
    }

    public String execute(String s) throws Exception {
        Graph<DijkstraNode, Edge> graph;
        graph = createGraph(s);
        drawLabyrinth(graph);
        drawRoute(graph, "0-6", "3-0");
        return g.getTrace();
    }
}
