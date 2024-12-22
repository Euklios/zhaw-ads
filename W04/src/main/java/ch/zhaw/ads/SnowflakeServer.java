package ch.zhaw.ads;

public class SnowflakeServer implements CommandExecutor {
    @Override
    public String execute(String command) {
        int order = Integer.parseInt(command);
        double distance = 0.5;
        Turtle turtle = Turtle.instance();
        turtle.reset(0.25, 0.75);

        drawSnowflake(turtle, order, distance);

        return turtle.getTrace();
    }

    public void drawSnowflake(Turtle turtle, int order, double distance) {
        snowflakeSide(turtle, order, distance);
        turtle.turn(-120);
        snowflakeSide(turtle, order, distance);
        turtle.turn(-120);
        snowflakeSide(turtle, order, distance);
        turtle.turn(-120);
    }

    public void snowflakeSide(Turtle turtle, int order, double distance) {
        if (order == 0) {
            turtle.move(distance);
        } else {
            order--;
            distance = distance/3;
            snowflakeSide(turtle, order, distance);
            turtle.turn(60);
            snowflakeSide(turtle, order, distance);
            turtle.turn(-120);
            snowflakeSide(turtle, order, distance);
            turtle.turn(60);
            snowflakeSide(turtle, order, distance);
        }
    }
}
