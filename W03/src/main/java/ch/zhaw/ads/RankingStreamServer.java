package ch.zhaw.ads;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class RankingStreamServer implements CommandExecutor {

    public Stream<Competitor> createStream(String rankingText) {
        return Arrays.stream(rankingText.split("\n"))
                .map(s -> s.split(";", 2))
                .peek(strings -> {
                    assert strings.length == 2;
                })
                .map(strings -> new Competitor(0, strings[0], strings[1]));
    }

    public String createSortedText(Stream<Competitor> competitorStream) {
        Stream<Competitor> sortedStream = createSortedStream(competitorStream);

        StringBuilder sb = new StringBuilder();

        sortedStream.forEach(competitor -> sb.append(competitor).append("\n"));

        return sb.toString();
    }

    private Stream<Competitor> createSortedStream(Stream<Competitor> competitorList) {
        AtomicInteger rank = new AtomicInteger(1);

        return competitorList
                .sorted()
                .peek(competitor -> competitor.setRank(rank.getAndIncrement()));
    }

    public String execute(String rankingList) {
        Stream<Competitor> competitorStream = createStream(rankingList);
        return "Rangliste (Stream)\n" + createSortedText(competitorStream);
    }

    public static void main(String[] args) {
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
        RankingStreamServer server = new RankingStreamServer();
        System.out.println(server.execute(rangliste));
    }
}