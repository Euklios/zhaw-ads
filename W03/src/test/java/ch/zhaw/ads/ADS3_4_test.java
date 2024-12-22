package ch.zhaw.ads;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class ADS3_4_test {
    Stream<Competitor> rankGood;
    Stream<Competitor> rankTest;
    String textGood;
    String textTest;

    public Stream<Competitor> createStream(String rankingText) {
        Function<String,Competitor> construct = s -> new Competitor(0, s.split(";")[0],s.split(";")[1]);

        return Arrays.stream(rankingText.split("\n"))
                .map(s -> construct.apply(s));
    }

    public String createSortedText(Stream<Competitor> competitorStream) {
        AtomicInteger rank = new AtomicInteger();
        rank.set(1);
        Function<Competitor,Competitor> ranked = c -> new Competitor(rank.getAndIncrement(),c.getName(),c.getTime());

        return competitorStream
                .sorted()
                .map(c -> ranked.apply(c))
                .map(c -> c.toString())
                .reduce ("",(s1,s2) -> s1 + s2 +"\n");
    }


    @Before
    public void setUp() throws Exception {
        String rangliste =
                "Mueller Stefan;02:31:14\n"+
                        "Marti Adrian;02:30:09\n"+
                        "Kiptum Daniel;02:11:31\n"+
                        "Ancay Tarcis;02:20:02\n"+
                        "Kreibuhl Christian;02:21:47\n"+
                        "Ott Michael;02:33:48\n"+
                        "Menzi Christoph;02:27:26\n"+
                        "Oliver Ruben;02:32:12\n"+
                        "Elmer Beat;02:33:53\n"+
                        "Kuehni Martin;02:33:36\n";
        textGood = createSortedText(createStream(rangliste));
        textTest = new RankingStreamServer().createSortedText(new RankingStreamServer().createStream(rangliste));
    }
    private String clean(String s) {
        return s.trim();
    }

    @Test
    public void testCreateText() {
        String[] good,test;
        good = textGood.split("\n");
        test = textTest.split("\n");
        assertEquals("length",good.length,test.length);
        for (int i = 0; i < good.length;i++) {
            assertEquals("rangliste["+i+"]",clean(good[i]),clean(test[i]));
        }
    }

}

