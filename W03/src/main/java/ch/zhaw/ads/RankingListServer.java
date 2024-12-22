package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class RankingListServer implements CommandExecutor {
    public List<Competitor> createList(String rankingText) {
        List<Competitor> competitors = new LinkedList<>();
        for (String line : rankingText.split("\n")) {
            String[] parts = line.split(";", 2);
            assert parts.length == 2;

            competitors.add(new Competitor(0, parts[0], parts[1]));
        }

        return competitors;
    }

    public String createSortedText(List<Competitor> competitorList) {
        List<Competitor> sortedList = createSortedList(competitorList);

        StringBuilder sb = new StringBuilder();
        for (Competitor c : sortedList) {
            sb.append(c).append("\n");
        }

        return sb.toString();
    }

    private List<Competitor> createSortedList(List<Competitor> competitorList) {
        AtomicInteger rank = new AtomicInteger(1);

        return competitorList
                .stream()
                .sorted()
                .peek(competitor -> competitor.setRank(rank.getAndIncrement()))
                .collect(Collectors.toList());
    }

    public String execute(String rankingList) {
        List<Competitor> competitorList = createList(rankingList);
        return "Rangliste (List)\n" + createSortedText(competitorList);
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
        RankingListServer server = new RankingListServer();
        System.out.println(server.execute(rangliste));
    }
}