package ch.zhaw.ads;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class QuickerSortServer extends RecursiveAction implements CommandExecutor {
    private final int DATARANGE = 10000000;
    public int dataElems; // number of data
    public static int insertion_threshold;

    // for FJ
    private final int SPLIT_THRESHOLD = 10000;
    private int[] a;
    private int left;
    private int right;
    ForkJoinPool forkJoinPool;

    public boolean isSorted(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public int[] randomData() {
        int[] a = new int[dataElems];
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (Math.random() * DATARANGE);
        }
        return a;
    }

    public void swap(int[] a, int i, int j) {
        int h = a[i]; a[i] = a[j]; a[j] = h;
    }

    // insertion sort over interval
    void insertionSort(int[] a, int left, int right) {
        for (int i = left + 1; i <= right; ++i) {
            int key = a[i];
            int j = i - 1;

            while (j >= left && a[j] > key) {
                a[j + 1] = a[j];
                j = j - 1;
            }
            a[j + 1] = key;
        }
    }

    int partition(int[] arr, int left, int right) {
        int i = left;
        int j = right - 1;
        int pivot = arr[right];

        while (i < j) {
            while (i < j && arr[i] <= pivot) {
                i++;
            }

            while (j > i && arr[j] > pivot) {
                j--;
            }

            if (arr[i] > arr[j]) {
                swap(arr, i, j);
            }
        }

        if (arr[i] > pivot) {
            swap(arr, i, right);
        } else {
            i = right;
        }

        return i;
    }

    void quickerSort(int[] arr, int left, int right) {
        if (left < right) {
            if (right - left > insertion_threshold) {
                int part = partition(arr, left, right);
                quickerSort(arr, left, part - 1);
                quickerSort(arr, part + 1, right);
            } else {
                insertionSort(arr, left, right);
            }
        }
    }

    void quickerSort(int[] arr) {
        quickerSort(arr, 0, arr.length - 1);
    }

    public QuickerSortServer() {}

    public QuickerSortServer(int[] a, int left, int right) {
        this.a = a;
        this.left = left;
        this.right = right;
    }

    public void compute() {
        if (left < right) {
            int part = partition(this.a, left, right);
            if (right - left > SPLIT_THRESHOLD) {
                QuickerSortServer leftComp = new QuickerSortServer(a, left, part - 1);
                QuickerSortServer rightComp = new QuickerSortServer(a, part + 1, right);

                leftComp.fork();
                rightComp.fork();

                leftComp.join();
                rightComp.join();
            } else {
                this.quickerSort(a, left, part - 1);
                this.quickerSort(a, part + 1, right);
            }
        }
    }

    void quickerSortFJ(int[] a) {
        QuickerSortServer quickerSortServer = new QuickerSortServer(a, 0, a.length - 1);

        new ForkJoinPool().invoke(quickerSortServer);
    }

    void arraySort(int[] a) {
        Arrays.sort(a);
    }

    public double measureTime(Supplier<int[]> generator, Consumer<int[]> sorter) throws Exception {
        double elapsed;

        int[] a = generator.get();
        int[] b = new int[dataElems];

        long startTime = System.currentTimeMillis();
        long endTime = startTime;
        int count = 0;
        while (endTime < startTime + 1000) {
            System.arraycopy(a, 0, b, 0, a.length);
            sorter.accept(b);
            count++;
            endTime = System.currentTimeMillis();
        }
        elapsed = (double) (endTime - startTime) / count;
        if (!isSorted(b)) throw new Exception ("ERROR not sorted");
        return elapsed;
    }

    public String execute(String arg) {
        // Java 9: use Map.of instead
        Map<String,Consumer<int[]>> sorter =  new HashMap<>();
        sorter.put("QUICKER", this::quickerSort);
        sorter.put("QUICKERFJ", this::quickerSortFJ);
        sorter.put("ARRAY", this::arraySort);

        Map<String,Supplier<int[]>> generator =  new HashMap<>();
        generator.put("RANDOM", this::randomData);

        String[] args = arg.toUpperCase().split("\\s+");
        dataElems = Integer.parseInt(args[2]);
        insertion_threshold = Integer.parseInt(args[3]);
        try {
            double time = measureTime(generator.get(args[1]), sorter.get(args[0]));
            return arg + " "+ time +" ms\n";
        } catch (Exception ex){
            return arg + " "+ ex.getMessage()+"\n";
        }
    }

    public static void main(String[] args) throws Exception {
        QuickerSortServer sorter = new QuickerSortServer();
        String sort;
        sort = "QUICKER RANDOM 1000000 22"; System.out.print(sorter.execute(sort));
        sort = "QUICKERFJ RANDOM 1000000 20"; System.out.print(sorter.execute(sort));
        sort = "ARRAY RANDOM 1000000 20"; System.out.print(sorter.execute(sort));
    }

}
