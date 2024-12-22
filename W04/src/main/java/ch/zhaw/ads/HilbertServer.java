package ch.zhaw.ads;

public class HilbertServer implements CommandExecutor {
    @Override
    public String execute(String command) {
        int order = Integer.parseInt(command);
        Turtle turtle = Turtle.instance();

        turtle.reset(0.05, 0.05);

        hilbertCurve(turtle, order   + 1, 0.9);

        return turtle.getTrace();
    }

    public void hilbertCurve(Turtle turtle, int order, double wh) {
    hilbert(turtle, order, 90, wh / (Math.pow(2, order) - 1));
}

    private void hilbert(Turtle turtle, int order, int angle, double dist) {
        if (order == 0) {
            return;
        }

        turtle.turn(angle);
        hilbert(turtle, order - 1, -angle, dist);

        turtle.move(dist);
        turtle.turn(360 - angle);
        hilbert(turtle, order - 1, angle, dist);

        turtle.move(dist);
        hilbert(turtle, order - 1, angle, dist);

        turtle.turn(360 - angle);
        turtle.move(dist);
        hilbert(turtle, order - 1, -angle, dist);
        turtle.turn(angle);
    }
}
