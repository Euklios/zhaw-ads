package ch.zhaw.ads;

public class Town {
    int hashCode;
    String name;
    String nb;
    Town(int hashCode, String name, String nb) {
        this.name = name; this.hashCode = hashCode; this.nb = nb;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof Town)) return false;
        return ((Town)o).name.equals(this.name);
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return name+" "+hashCode;
    }
}
