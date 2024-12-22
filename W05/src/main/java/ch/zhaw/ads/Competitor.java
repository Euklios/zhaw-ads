package ch.zhaw.ads;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Competitor implements Comparable<Competitor> {
    private String name;
    private String time;
    private int rank;

    public Competitor(int rank, String name, String time)  {
        this.rank = rank;
        this.name = name;
        this.time = time;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    private static long parseTime(String s)  {
        try {
            DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date = sdf.parse(s);
            return date.getTime();
        } catch (Exception e) {System.err.println(e);}
        return 0;
    }

    private static String timeToString(int time) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(new Date(time));
    }

    public String toString() {
        return rank + " "+name+" "+time;
    }

    @Override
    public int compareTo(Competitor o) {
        return Long.compare(parseTime(this.time), parseTime(o.time));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Competitor)) return false;

        Competitor comp = (Competitor) obj;

        return Objects.equals(this.time, comp.time) && Objects.equals(this.name, comp.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, time);
    }
}