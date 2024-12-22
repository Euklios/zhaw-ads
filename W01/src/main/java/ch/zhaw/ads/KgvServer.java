package ch.zhaw.ads;

public class KgvServer implements CommandExecutor {
    @Override
    public String execute(String command) throws Exception {
        String[] numbers = command.split("[ ,]+");
        int a = Integer.parseInt(numbers[0]);
        int b = Integer.parseInt(numbers[1]);
        return Integer.toString(kgv(a,b));
    }

    public int ggt(int a, int b) {
        while (a != b) {
            if (a > b) a = a - b;
            else b = b -a;
        }

        return a;
    }

    public int kgv(int a, int b) {
        return Math.abs(a * b) / ggt(a, b);
    }
}
