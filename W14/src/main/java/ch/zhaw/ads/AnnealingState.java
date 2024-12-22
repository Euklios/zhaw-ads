package ch.zhaw.ads;

public interface AnnealingState {
    double getCost();
    AnnealingState clone();
    void pertub();
    void draw(ServerGraphics g);
}