package ch.zhaw.ads;

public interface AnnealingState {
    public double getCost();
    public AnnealingState clone();
    public void pertub();
    public void draw(ServerGraphics g);
}