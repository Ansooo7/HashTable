import java.util.*;

class Trade {
    String id;
    int volume;

    public Trade(String id, int volume) {
        this.id = id;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return id + ":" + volume;
    }
}

public class TradeVolumeAnalysis {

    // Merge Sort (ascending, stable)
    public static void mergeSort(Trade[] trades, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(trades, left, mid);
            mergeSort(trades, mid + 1, right);
            merge(trades, left, mid, right);
        }
    }

    private static void merge(Trade[] trades, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Trade[] L = new Trade[n1];
        Trade[] R = new Trade[n2];

        for (int i = 0; i < n1; i++) L[i] = trades[left + i];
        for (int j = 0; j < n2; j++) R[j] = trades[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].volume <= R[j].volume) {
                trades[k++] = L[i++];
            } else {
                trades[k++] = R[j++];
            }
        }
        while (i < n1) trades[k++] = L[i++];
        while (j < n2) trades[k++] = R[j++];
    }

    // Quick Sort (descending)
    public static void quickSort(Trade[] trades, int low, int high) {
        if (low < high) {
            int pi = partition(trades, low, high);
            quickSort(trades, low, pi - 1);
            quickSort(trades, pi + 1, high);
        }
    }

    private static int partition(Trade[] trades, int low, int high) {
        Trade pivot = trades[high]; // Lomuto partition
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (trades[j].volume >= pivot.volume) { // Descending
                i++;
                Trade temp = trades[i];
                trades[i] = trades[j];
                trades[j] = temp;
            }
        }
        Trade temp = trades[i + 1];
        trades[i + 1] = trades[high];
        trades[high] = temp;
        return i + 1;
    }

    // Merge two sorted lists
    public static Trade[] mergeLists(Trade[] morning, Trade[] afternoon) {
        Trade[] merged = new Trade[morning.length + afternoon.length];
        int i = 0, j = 0, k = 0;

        while (i < morning.length && j < afternoon.length) {
            if (morning[i].volume <= afternoon[j].volume) {
                merged[k++] = morning[i++];
            } else {
                merged[k++] = afternoon[j++];
            }
        }
        while (i < morning.length) merged[k++] = morning[i++];
        while (j < afternoon.length) merged[k++] = afternoon[j++];
        return merged;
    }

    // Compute total volume
    public static int totalVolume(Trade[] trades) {
        int sum = 0;
        for (Trade t : trades) sum += t.volume;
        return sum;
    }

    public static void main(String[] args) {
        Trade[] trades = {
            new Trade("trade3", 500),
            new Trade("trade1", 100),
            new Trade("trade2", 300)
        };

        // Merge Sort (ascending)
        Trade[] mergeSorted = Arrays.copyOf(trades, trades.length);
        mergeSort(mergeSorted, 0, mergeSorted.length - 1);
        System.out.println("MergeSort result (asc): " + Arrays.toString(mergeSorted));

        // Quick Sort (descending)
        Trade[] quickSorted = Arrays.copyOf(trades, trades.length);
        quickSort(quickSorted, 0, quickSorted.length - 1);
        System.out.println("QuickSort result (desc): " + Arrays.toString(quickSorted));

        // Merge two lists (morning + afternoon)
        Trade[] morning = { new Trade("m1", 200), new Trade("m2", 400) };
        Trade[] afternoon = { new Trade("a1", 300) };
        Trade[] merged = mergeLists(morning, afternoon);
        System.out.println("Merged list: " + Arrays.toString(merged));
        System.out.println("Total volume: " + totalVolume(merged));
    }
}
