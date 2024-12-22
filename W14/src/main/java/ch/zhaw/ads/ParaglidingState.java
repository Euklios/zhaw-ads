package ch.zhaw.ads;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ParaglidingState implements AnnealingState {
    private double deltaPhi;
    private List<Circle> circles;
    private List<Point> path; // path

    private static class Circle {
        Circle(double cX, double cY, double r, double phi1, double phi2) {
            centerX = cX;
            centerY = cY;
            this.r = r;
            this.phi1 = phi1;
            this.phi2 = phi2;
        }
        double centerX, centerY;
        double r;
        double phi1, phi2;
    }

    private static class Point {
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        double x, y;
    }

    public double getCost() {
        double cost = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            Point p1 = path.get(i);
            Point p2 = path.get(i + 1);
            double dx = p2.x - p1.x;
            double dy = p2.y - p1.y;
            cost += Math.sqrt(dx * dx + dy * dy);
        }
        return cost;
    }

    private ParaglidingState() {}

    public ParaglidingState(double deltaPhi) {
        this();
        this.deltaPhi = deltaPhi;
        // initial State
        circles = new ArrayList<>();
        circles.add(new Circle(100, 100, 50, 0, Math.PI / 2));
        circles.add(new Circle(250, 70, 50, 0, Math.PI));
        circles.add(new Circle(380, 160, 30, 0, Math.PI * 3 / 2));
        circles.add(new Circle(380, 260, 30, 0, Math.PI * 3 / 2));
        circles.add(new Circle(150, 200, 50, 0, Math.PI * 3 / 2));
        buildPath();
    }

    public ParaglidingState clone() {
        ParaglidingState copy = new ParaglidingState();
        copy.circles = copyCircles(circles);
        copy.buildPath();
        copy.deltaPhi = deltaPhi;
        return copy;
    }

    private List<Circle> copyCircles(List<Circle> circles) {
        List<Circle> copy = new ArrayList<>(circles.size());
        for (Circle c : circles) {
            copy.add(copy.size(),
                    new Circle(c.centerX, c.centerY, c.r, c.phi1, c.phi2));
        }
        return copy;
    }

    public void pertub() {
        for (Circle c : circles) {
            double dp = deltaPhi / 2 - Math.random() * deltaPhi;
            c.phi1 += dp;
            dp = deltaPhi / 2 - Math.random() * deltaPhi;
            c.phi2 += dp;
        }
        buildPath();
    }

    private void buildPath() {
        path = new ArrayList<>();
        path.add(new Point(20, 100)); // start;
        for (Circle c : circles) {
            path.add(
                    new Point(c.centerX + c.r * Math.cos(c.phi1),
                            c.centerY + c.r * Math.sin(c.phi1)));
            path.add(
                    new Point(c.centerX + c.r * Math.cos(c.phi2),
                            c.centerY + c.r * Math.sin(c.phi2)));

        }
        path.add(new Point(20, 300)); // goal;
    }

    public void draw(ServerGraphics g) {
        final double SCALE = 1/420.0;
        g.setColor(Color.BLACK);
        for (Circle c : circles) {
            int r = (int) c.r;
            g.drawOval(SCALE*(c.centerX - r), SCALE*(c.centerY - r),
                    SCALE* ( 2 * r), SCALE*(2 * r));
        }
        g.setColor(Color.BLUE);
        for (int i = 0; i < path.size()-1; i++) {
            g.drawLine(SCALE*path.get(i).x,SCALE*path.get(i).y,SCALE*path.get(i+1).x,SCALE*path.get(i+1).y);
        }
        g.setColor(Color.RED);
        for (int i = 1; i < path.size()-1; i++) {
            int c = (i - 1)/2;
            g.drawLine(SCALE*circles.get(c).centerX,SCALE*circles.get(c).centerY,SCALE*path.get(i).x,SCALE*path.get(i).y);
        }


    }
}