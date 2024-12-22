package ch.zhaw.ads;

import java.awt.*;

public class TSPState implements AnnealingState,Cloneable {
    private static final int POINTS = 20;

    private class Point  {
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        double x, y;
    }

    Point[] points;
    int[] path;
    int swaps;

    private TSPState() {
        path = new int[POINTS];
    }

    public TSPState(int swaps) {
        this();
        this.swaps = swaps;
        points = new Point[POINTS];
        for (int i = 0; i < POINTS; i++) {
            path[i] = i;
            points[i] = new Point(Math.random(),Math.random());
        }
    }

    public void pertub() {
        for (int i =0; i <swaps; i++) {
            int p = (int)(Math.random()*POINTS);
            int q = (int)(Math.random()*POINTS);
            int t =  path[p]; path[p] = path[q]; path[q] = t;
        }
    }

    public TSPState clone() {
        TSPState copy = new TSPState();
        copy.swaps = swaps;
        copy.points = this.points;
        System.arraycopy(path,0, copy.path,0,POINTS);
        return copy;
    }

    public double getCost() {
        double pathLength = 0;
        for (int i = 0; i < path.length - 1; i++) {
            double dx = points[path[i]].x - points[path[i + 1]].x;
            double dy = points[path[i]].y - points[path[i + 1]].y;
            pathLength += Math.sqrt(dx * dx + dy * dy);
        }
        return pathLength;
    }

    public void draw(ServerGraphics g) {
        g.setColor(Color.BLACK);
        for (int i = 0; i< POINTS; i++) {
            g.fillOval(points[i].x-0.01,points[i].y-0.01,.02,.02);
        }
        g.setColor(Color.BLUE);
        for (int i = 0; i< POINTS-1; i++) {
            g.drawLine(points[path[i]].x,points[path[i]].y,points[path[i+1]].x,points[path[i+1]].y);
        }
    }
}