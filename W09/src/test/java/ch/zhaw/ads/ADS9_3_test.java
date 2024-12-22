package ch.zhaw.ads;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ADS9_3_test {
    Map<Town,Town> hashmap;
    List<Town> towns;

    @Before
    public void setUp() throws Exception {
        towns = new LinkedList<>();
        towns.add(new Town(5,"Bari","BA"));
        towns.add(new Town(8,"Bologna","BO"));
        towns.add(new Town(3,"Catania","CA"));
        towns.add(new Town(9,"Firenze","FI"));
        towns.add(new Town(0,"Genova","GV"));
        towns.add(new Town(12,"Milano","MI"));
        towns.add(new Town(7,"Napoli","NA"));
        towns.add(new Town(7,"Palermo","PA"));
        towns.add(new Town(7,"Roma","RM"));
        towns.add(new Town(5,"Torino","TO"));

        hashmap = new MyHashtable<>(100);
    }

    @Test
    public void testAdd() {
        hashmap.clear();
        Town t0 = towns.get(0);
        hashmap.put(t0,t0);
        Town t1 = hashmap.get(t0);
        assertEquals(t0,t1);
    }

    @Test
    public void testAdd2() {
        hashmap.clear();
        Town t0 = towns.get(0);
        Town t1 = towns.get(1);
        hashmap.put(t0,t0);
        hashmap.put(t1,t1);
        Town t2 = hashmap.get(t0);
        assertEquals(t0,t2);
        t2 = hashmap.get(t1);
        assertEquals(t1,t2);
    }

    @Test
    public void testAdd3() {
        hashmap.clear();
        Town t0 = towns.get(0);
        hashmap.remove(t0);
        hashmap.put(t0,t0);
        hashmap.put(t0,t0);
        assertEquals(1, hashmap.size());
        Town t1 = hashmap.get(t0);
        assertEquals(t0,t1);
    }

    @Test
    public void testAdd4() {
        hashmap.clear();
        Town t0 = towns.get(0);
        hashmap.put(t0,t0);
        hashmap.put(t0,t0);
        assertEquals(1, hashmap.size());
    }


    @Test
    public void testSize() {
        hashmap.clear();
        assertEquals(0, hashmap.size());
        testAdd2();
        assertEquals(2, hashmap.size());
    }

    @Test
    public void testRemove() {
        hashmap.clear();
        Town t0 = towns.get(0);
        Town t1 = towns.get(1);
        hashmap.put(t0,t0);
        hashmap.remove(t0);
        assertEquals(0, hashmap.size());
        hashmap.put(t0,t0);
        hashmap.remove(t1);
        assertEquals(1, hashmap.size());
        hashmap.remove(t0);
        assertEquals(0, hashmap.size());
    }

    @Test
    public void testMixed() {
        hashmap.clear();
        Map<Town,Town> hashmap2 = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            Town c = towns.get((int)(Math.random()*towns.size()));
            int op = (int)(Math.random()*2);
            System.out.println("op: " + op + ", town: " + c);
            switch (op) {
                case 0 : hashmap.put(c,c); hashmap2.put(c,c); break;
                case 1 : hashmap.remove(c); hashmap2.remove(c); break;
            }
            assertEquals(hashmap2.size(), hashmap.size());
        }
        assertEquals(hashmap2.size(), hashmap.size());
        for (Town t : towns) {
            Town c1 = hashmap.get(t);
            Town c2 = hashmap2.get(t);
            assertEquals(c1,c2);
        }
    }

    @Test
    public void testMixed2() {
        hashmap.clear();

        hashmap.put(towns.get(7), towns.get(7));
        hashmap.put(towns.get(3), towns.get(3));
        hashmap.remove(towns.get(0));
        hashmap.put(towns.get(3), towns.get(3));
        hashmap.remove(towns.get(0));
        hashmap.remove(towns.get(6));
        hashmap.remove(towns.get(3));
        hashmap.put(towns.get(9), towns.get(9));
        hashmap.remove(towns.get(4));
        hashmap.put(towns.get(9), towns.get(9));
        hashmap.remove(towns.get(2));
        hashmap.put(towns.get(5), towns.get(5));
        hashmap.put(towns.get(4), towns.get(4));
        hashmap.remove(towns.get(1));
        hashmap.remove(towns.get(2));
        hashmap.put(towns.get(8), towns.get(8));
        hashmap.put(towns.get(5), towns.get(5));
        hashmap.put(towns.get(7), towns.get(7));
        hashmap.remove(towns.get(8));
    }

    @Test
    public void testMixed3() {
        String definition = "op: 0, town: Napoli 7\n" +
                "op: 0, town: Palermo 7\n" +
                "op: 0, town: Napoli 7\n" +
                "op: 0, town: Torino 5\n" +
                "op: 1, town: Milano 12\n" +
                "op: 1, town: Roma 7\n" +
                "op: 1, town: Bari 5\n" +
                "op: 1, town: Torino 5\n" +
                "op: 0, town: Palermo 7\n" +
                "op: 0, town: Bari 5\n" +
                "op: 1, town: Roma 7\n" +
                "op: 1, town: Roma 7\n" +
                "op: 0, town: Genova 0\n" +
                "op: 0, town: Milano 12\n" +
                "op: 1, town: Firenze 9\n" +
                "op: 1, town: Catania 3\n" +
                "op: 1, town: Firenze 9\n" +
                "op: 1, town: Napoli 7\n" +
                "op: 0, town: Bari 5\n" +
                "op: 0, town: Bari 5\n" +
                "op: 1, town: Roma 7\n" +
                "op: 0, town: Genova 0\n" +
                "op: 1, town: Napoli 7\n" +
                "op: 0, town: Bologna 8\n" +
                "op: 0, town: Napoli 7\n" +
                "op: 1, town: Torino 5\n" +
                "op: 0, town: Bologna 8\n" +
                "op: 1, town: Bologna 8\n" +
                "op: 0, town: Torino 5\n" +
                "op: 0, town: Bari 5\n" +
                "op: 1, town: Palermo 7\n" +
                "op: 0, town: Catania 3\n" +
                "op: 1, town: Torino 5\n" +
                "op: 1, town: Catania 3\n" +
                "op: 0, town: Genova 0\n" +
                "op: 0, town: Catania 3\n" +
                "op: 1, town: Firenze 9\n" +
                "op: 1, town: Bologna 8\n" +
                "op: 1, town: Palermo 7\n" +
                "op: 1, town: Palermo 7\n" +
                "op: 1, town: Firenze 9\n" +
                "op: 0, town: Torino 5\n" +
                "op: 0, town: Genova 0\n" +
                "op: 0, town: Firenze 9\n" +
                "op: 0, town: Roma 7\n" +
                "op: 1, town: Catania 3\n" +
                "op: 1, town: Torino 5\n" +
                "op: 1, town: Napoli 7\n" +
                "op: 0, town: Milano 12\n" +
                "op: 1, town: Palermo 7\n" +
                "op: 1, town: Palermo 7\n" +
                "op: 0, town: Milano 12\n" +
                "op: 1, town: Bari 5\n" +
                "op: 1, town: Genova 0\n" +
                "op: 0, town: Milano 12\n" +
                "op: 1, town: Genova 0\n" +
                "op: 0, town: Firenze 9\n" +
                "op: 0, town: Milano 12\n" +
                "op: 0, town: Firenze 9\n" +
                "op: 1, town: Napoli 7\n" +
                "op: 0, town: Bologna 8\n" +
                "op: 1, town: Milano 12\n" +
                "op: 1, town: Firenze 9\n" +
                "op: 0, town: Bologna 8";

        runMixedTest(definition);
    }

    @Test
    public void testMixed4() {
    String definition = "op: 1, town: Milano 12\n" +
            "op: 0, town: Bologna 8\n" +
            "op: 1, town: Torino 5\n" +
            "op: 0, town: Napoli 7\n" +
            "op: 0, town: Bologna 8\n" +
            "op: 1, town: Genova 0\n" +
            "op: 0, town: Catania 3\n" +
            "op: 0, town: Napoli 7\n" +
            "op: 0, town: Torino 5\n" +
            "op: 1, town: Catania 3\n" +
            "op: 1, town: Firenze 9\n" +
            "op: 0, town: Palermo 7\n" +
            "op: 1, town: Milano 12\n" +
            "op: 0, town: Firenze 9\n" +
            "op: 0, town: Napoli 7\n" +
            "op: 1, town: Catania 3\n" +
            "op: 0, town: Milano 12\n" +
            "op: 1, town: Bologna 8\n" +
            "op: 1, town: Bologna 8\n" +
            "op: 0, town: Bologna 8\n" +
            "op: 0, town: Genova 0\n" +
            "op: 1, town: Torino 5\n" +
            "op: 1, town: Firenze 9\n" +
            "op: 1, town: Milano 12\n" +
            "op: 1, town: Roma 7\n" +
            "op: 1, town: Palermo 7\n" +
            "op: 0, town: Milano 12\n" +
            "op: 1, town: Bari 5\n" +
            "op: 0, town: Genova 0\n" +
            "op: 0, town: Bari 5\n" +
            "op: 0, town: Catania 3\n" +
            "op: 1, town: Bari 5\n" +
            "op: 0, town: Catania 3\n" +
            "op: 0, town: Napoli 7\n" +
            "op: 1, town: Genova 0\n" +
            "op: 1, town: Napoli 7\n" +
            "op: 1, town: Palermo 7\n" +
            "op: 0, town: Bologna 8\n" +
            "op: 0, town: Milano 12\n" +
            "op: 0, town: Palermo 7\n" +
            "op: 1, town: Catania 3\n" +
            "op: 0, town: Milano 12\n" +
            "op: 1, town: Firenze 9\n" +
            "op: 1, town: Torino 5\n" +
            "op: 0, town: Napoli 7\n" +
            "op: 1, town: Catania 3\n" +
            "op: 1, town: Bologna 8\n" +
            "op: 1, town: Torino 5\n" +
            "op: 0, town: Palermo 7\n" +
            "op: 0, town: Bari 5\n" +
            "op: 0, town: Bologna 8\n" +
            "op: 0, town: Palermo 7\n" +
            "op: 0, town: Palermo 7\n" +
            "op: 0, town: Catania 3\n" +
            "op: 1, town: Bologna 8\n" +
            "op: 1, town: Bologna 8\n" +
            "op: 1, town: Firenze 9\n" +
            "op: 1, town: Milano 12\n" +
            "op: 1, town: Bologna 8\n" +
            "op: 1, town: Torino 5\n" +
            "op: 1, town: Palermo 7\n" +
            "op: 0, town: Genova 0\n" +
            "op: 0, town: Genova 0\n" +
            "op: 0, town: Bari 5\n" +
            "op: 1, town: Palermo 7\n" +
            "op: 0, town: Palermo 7\n" +
            "op: 1, town: Milano 12\n" +
            "op: 1, town: Bari 5\n" +
            "op: 0, town: Genova 0\n" +
            "op: 1, town: Napoli 7\n" +
            "op: 1, town: Firenze 9\n" +
            "op: 0, town: Bari 5\n" +
            "op: 0, town: Milano 12\n" +
            "op: 0, town: Bari 5\n" +
            "op: 1, town: Torino 5\n" +
            "op: 0, town: Torino 5\n" +
            "op: 1, town: Firenze 9\n" +
            "op: 0, town: Milano 12\n" +
            "op: 1, town: Bari 5\n" +
            "op: 1, town: Napoli 7\n";

    Map<Town, Town> ref = runMixedTest(definition);

    ref.put(new Town(5, "Torino", "Torino"), new Town(5, "Torino", "Torino"));
    hashmap.put(new Town(5, "Torino", "Torino"), new Town(5, "Torino", "Torino"));

    assertEquals(ref.size(), hashmap.size());
    }

    private Map<Town, Town> runMixedTest(String definition) {
        hashmap.clear();
        Map<Town, Town> ref = new HashMap<>();

        for (String line : definition.split("\n")) {
            String[] split = line.split(",");
            assert split.length == 2;

            int op = Integer.parseInt(split[0].split(":")[1].trim());
            String town = split[1].split(":")[1].trim().split(" ")[0].trim();
            int hashCode = Integer.parseInt(split[1].split(":")[1].trim().split(" ")[1].trim());

            if (op == 0) {
                hashmap.put(new Town(hashCode, town, town), new Town(hashCode, town, town));
                ref.put(new Town(hashCode, town, town), new Town(hashCode, town, town));
            }

            if (op == 1) {
                hashmap.remove(new Town(hashCode, town, town));
                ref.remove(new Town(hashCode, town, town));
            }
        }

        return ref;
    }
}
