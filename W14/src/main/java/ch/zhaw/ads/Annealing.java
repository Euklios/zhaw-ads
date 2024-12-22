/**
 * @(#)Annealing.java
 * *
 * @author K. Rege
 * @version 2.00 2021/12/12
 */

package ch.zhaw.ads;

import java.awt.*;

public class Annealing {
    private static final double ACCEPTCOUNT = 1000;
    private int count;
    private double coolingRate;
    private double normFactor;

    // public readonly (for unittests)
    public AnnealingState state;
    public double temperature;
    public int acceptBoltzCount;
    public int acceptCount;

    public void draw(ServerGraphics g) {
        state.draw(g);
        g.setColor(Color.BLACK);
        String s = getInfoBlock();
        double y = 0.95;
        String[] lines = s.split("\n");
        for (String line : lines) {
            g.drawString(line, 0.02, y);
            y -= 0.05;
        }
    }

    private String getInfoBlock() {
        String info = "";
        info += "Count:" + Integer.toString(count) + "\n";
        info += "Cost:" + Double.toString((int) (state.getCost() * 100) / 100.0) + "\n";
        info += "Temp:" + Double.toString((int) (temperature * 1000) / 1000.0) + "\n";
        info += "Boltz:" + acceptBoltzCount + "\n";
        info += "Accept:" + acceptCount + "\n";
        info += accept() ? "ACCEPT" : "" + "\n";
        return info;
    }

    public String getInfo() {
        return getInfoBlock().replace("\n", " ");
    }

    Annealing(double coolingRate, AnnealingState state) {
        this.state = state;
        temperature = 1;
        this.coolingRate = coolingRate;
        // estimate normfactor so that initial boltz is ~0.5
        AnnealingState nextState = state.clone();
        nextState.pertub();
        double d = Math.abs(nextState.getCost() - state.getCost());
        normFactor = Math.log(2) / d / 100;
    }

    void step() {
        AnnealingState nextState = state.clone();
        nextState.pertub();
        // scale delta cost so that initial boltz is ~0.5
        double deltaCost = (nextState.getCost() - state.getCost()) * normFactor;

        if(deltaCost < 0.0) {
            state = nextState;
            acceptCount++;
        } else {
            double boltzProb = Math.exp(-deltaCost / temperature);
            if (Math.random() < boltzProb) {
                state = nextState;
                acceptBoltzCount++;
            }
        }

        count++;
        temperature *= coolingRate;
        if ((count - 1) % ACCEPTCOUNT == 0) {
            acceptBoltzCount = 0;
            acceptCount = 0;
        }
    }

    boolean accept() {
        return count > 100 && count % ACCEPTCOUNT == 0 && acceptCount == 0;
    }
}
