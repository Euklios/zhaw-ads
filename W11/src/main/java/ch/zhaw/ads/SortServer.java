package ch.zhaw.ads;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SortServer implements CommandExecutor {
    private final int DATARANGE = 10000000;
    public int dataElems; // number of data

    public void bubbleSort(int[] a) {
        int length = a.length;
        for (int b = 1; b < length; b++) {
            for (int k = 0; k < length - b; k++) {
                if (a[k] > a[k + 1]) {
                    int c = a[k];
                    a[k] = a[k + 1];
                    a[k + 1] = c;
                }
            }
        }
    }

    public void insertionSort(int[] a) {
        int n = a.length;
        for (int i = 1; i < n; ++i) {
            int key = a[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j = j - 1;
            }
            a[j + 1] = key;
        }
    }

    public void selectionSort(int[] a) {
        int n = a.length;
        for (int i = 0; i < n - 1; i++) {

            int min_idx = i;
            for (int j = i + 1; j < n; j++) {
                if (a[j] < a[min_idx]) {
                    min_idx = j;
                }
            }

            int temp = a[i];
            a[i] = a[min_idx];
            a[min_idx] = temp;
        }
    }

    public boolean isSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public int[] randomData() {
        Random random = new Random();

        int[] a = new int[dataElems];
        for (int i = 0; i < dataElems; i++) {
            a[i] = random.nextInt(DATARANGE);
        }

        return a;
    }

    public int[] ascendingData() {
        int[] a = new int[dataElems];

        for (int i = 0; i < dataElems; i++) {
            a[i] = i;
        }

        return a;
    }

    public int[] descendingData() {
        int[] a = new int[dataElems];

        for (int i = 0; i < dataElems; i++) {
            a[i] = dataElems - i;
        }

        return a;
    }

    // measure time of sorting algorithm
    // generator to generate the data
    // consumer sorts the data
    // return elapsed time in ms
    // if data is not sorted an exception is thrown
    public double measureTime(Supplier<int[]> generator, Consumer<int[]> sorter) throws Exception {
        double elapsed = 0;

        int[] a = generator.get();
        int[] b = new int[dataElems];

        long end, start = System.currentTimeMillis();
        int count = 0;
        do {
            System.arraycopy(a, 0, b, 0, a.length);
            sorter.accept(b);
            count++;
            end = System.currentTimeMillis();

        } while (end - start < 1000);
        System.out.println("time="+(double)(end-start)/count);

        if (!isSorted(b)) throw new Exception ("ERROR not sorted");

        return elapsed;
    }

    public String execute(String arg)  {
        // Java 9: use Map.of instead
        Map<String,Consumer<int[]>> sorter =  new HashMap<>();
        sorter.put("BUBBLE", this::bubbleSort);
        sorter.put("INSERTION", this::insertionSort);
        sorter.put("SELECTION", this::selectionSort);

        Map<String,Supplier<int[]>> generator =  new HashMap<>();
        generator.put("RANDOM", this::randomData);
        generator.put("ASC", this::ascendingData);
        generator.put("DESC", this::descendingData);

        String[] args = arg.toUpperCase().split("\\s+");
        dataElems = Integer.parseInt(args[2]);
        try {
            double time = measureTime(generator.get(args[1]), sorter.get(args[0]));
            return arg + " " + time + " ms\n";
        } catch (Exception ex){
            return arg + " "+ ex.getMessage()+"\n";
        }
    }

    public static void main(String[] args) {
        SortServer sorter = new SortServer();
        String sort;
        sort = "BUBBLE RANDOM 10000"; System.out.print(sorter.execute(sort));
        sort = "SELECTION RANDOM 10000"; System.out.print(sorter.execute(sort));
        sort = "INSERTION RANDOM 10000"; System.out.print(sorter.execute(sort));

        sort = "BUBBLE ASC 10000"; System.out.print(sorter.execute(sort));
        sort = "SELECTION ASC 10000"; System.out.print(sorter.execute(sort));
        sort = "INSERTION ASC 10000"; System.out.print(sorter.execute(sort));
    }
}
