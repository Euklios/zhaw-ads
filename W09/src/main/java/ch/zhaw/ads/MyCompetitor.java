package ch.zhaw.ads;

import java.util.Objects;

public class MyCompetitor implements Comparable<MyCompetitor> {
    private final String name;
    private final String time;
    private final int rank;

    public MyCompetitor(int rank, String name, String time)  {
        this.rank = rank;
        this.name = name;
        this.time = time;
    }

    public String toString() {
        return rank + " "+name+" "+time;
    }

    @Override
    public int compareTo(MyCompetitor o) {
        return this.rank - o.rank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyCompetitor that = (MyCompetitor) o;
        return rank == that.rank && Objects.equals(name, that.name) && Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, time, rank);
    }
}
