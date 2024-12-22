/**
 * @(#)ShortPathFrame.java
 *
 * Calculates shortest path for TSP and paraglider competitions
 *
 * @author K. Rege
 * @version 2.00 2021/12/12
 */
package ch.zhaw.ads;

public class AnnealingServer implements CommandExecutor {
    static final double DISTURBANCE = 0.5;
    static final double COOLING = 0.9995;

    Annealing annealing;

    @Override
    public String execute(String command) {
        String[] args = command.split("[, ]+");
        if (args.length < 2) {
            return "usage: (TSP|PARA) 1000 (TEXT|GRAPHIC)\n";
        }
        String state = args[0].toUpperCase();
        if (annealing == null) {
            if (state.equals("TSP")) annealing = new Annealing(COOLING,new TSPState(2));
            else  if (state.equals("PARA")) annealing = new Annealing(COOLING,new ParaglidingState(DISTURBANCE));
        }
        int steps = Integer.parseInt(args[1]);
        for (int i = 0; i < steps; i++) {
            assert annealing != null;
            annealing.step();
        }
        String mode = "TEXT";
        if (args.length > 2) mode = args[2].toUpperCase();
        if (mode.equals("GRAPHIC")) {
            ServerGraphics g = new ServerGraphics();
            annealing.draw(g);
            return g.getTrace();
        } else {
            assert annealing != null;
            return state+" "+annealing.getInfo()+"\n";
        }
    }

    public static void main(String[] args) {
        AnnealingServer server = new AnnealingServer();
        for (int i = 0; i < 25; i++) {
            System.out.print(server.execute("TSP 1000 TEXT"));
        }
        server = new AnnealingServer();
        for (int i = 0; i < 25; i++) {
            System.out.print(server.execute("PARA 1000 TEXT"));
        }
    }
}

