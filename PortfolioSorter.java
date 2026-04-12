import java.util.*;

class Asset {
    String name;
    double returnRate;   // in %
    double volatility;   // risk measure

    public Asset(String name, double returnRate, double volatility) {
        this.name = name;
        this.returnRate = returnRate;
        this.volatility = volatility;
    }

    @Override
    public String toString() {
        return name + ":" + returnRate + "% (vol=" + volatility + ")";
    }
}

public class PortfolioSorter {

    // Merge Sort (ascending by returnRate, stable)
    public static void mergeSort(Asset[] assets, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(assets, left, mid);
            mergeSort(assets, mid + 1, right);
            merge(assets, left, mid, right);
        }
    }

    private static void merge(Asset[] assets, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        Asset[] L = new Asset[n1];
        Asset[] R = new Asset[n2];

        for (int i = 0; i < n1; i++) L[i] = assets[left + i];
        for (int j = 0; j < n2; j++) R[j] = assets[mid + 1 + j];

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i].returnRate <= R[j].returnRate) {
                assets[k++] = L[i++];
            } else {
                assets[k++] = R[j++];
            }
        }
        while (i < n1) assets[k++] = L[i++];
        while (j < n2) assets[k++] = R[j++];
    }

    // Quick Sort (descending by returnRate, volatility ASC)
    public static void quickSort(Asset[] assets, int low, int high, String pivotStrategy) {
        if (low < high) {
            int pi = partition(assets, low, high, pivotStrategy);
            quickSort(assets, low, pi - 1, pivotStrategy);
            quickSort(assets, pi + 1, high, pivotStrategy);
        }
    }

    private static int partition(Asset[] assets, int low, int high, String pivotStrategy) {
        int pivotIndex = choosePivot(assets, low, high, pivotStrategy);
        Asset pivot = assets[pivotIndex];

        // Move pivot to end
        Asset temp = assets[pivotIndex];
        assets[pivotIndex] = assets[high];
        assets[high] = temp;

        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (compare(assets[j], pivot) > 0) { // descending by returnRate, volatility ASC
                i++;
                Asset swap = assets[i];
                assets[i] = assets[j];
                assets[j] = swap;
            }
        }
        Asset swap = assets[i + 1];
        assets[i + 1] = assets[high];
        assets[high] = swap;
        return i + 1;
    }

    // Pivot selection: random or median-of-three
    private static int choosePivot(Asset[] assets, int low, int high, String strategy) {
        if ("random".equalsIgnoreCase(strategy)) {
            return low + new Random().nextInt(high - low + 1);
        } else if ("median3".equalsIgnoreCase(strategy)) {
            int mid = (low + high) / 2;
            Asset a = assets[low], b = assets[mid], c = assets[high];
            if (compare(a, b) > 0) { Asset tmp = a; a = b; b = tmp; }
            if (compare(b, c) > 0) { Asset tmp = b; b = c; c = tmp; }
            if (compare(a, b) > 0) { Asset tmp = a; a = b; b = tmp; }
            // b is median
            if (b == assets[low]) return low;
            if (b == assets[mid]) return mid;
            return high;
        }
        return high; // default
    }

    // Comparison: returnRate DESC, volatility ASC
    private static int compare(Asset a1, Asset a2) {
        if (a1.returnRate != a2.returnRate) {
            return Double.compare(a1.returnRate, a2.returnRate);
        }
        return Double.compare(a2.volatility, a1.volatility); // lower volatility preferred
    }

    public static void main(String[] args) {
        Asset[] assets = {
            new Asset("AAPL", 12.0, 0.25),
            new Asset("TSLA", 8.0, 0.40),
            new Asset("GOOG", 15.0, 0.30)
        };

        // Merge Sort (ascending)
        Asset[] mergeSorted = Arrays.copyOf(assets, assets.length);
        mergeSort(mergeSorted, 0, mergeSorted.length - 1);
        System.out.println("MergeSort result (asc): " + Arrays.toString(mergeSorted));

        // Quick Sort (descending, median-of-three pivot)
        Asset[] quickSorted = Arrays.copyOf(assets, assets.length);
        quickSort(quickSorted, 0, quickSorted.length - 1, "median3");
        System.out.println("QuickSort result (desc): " + Arrays.toString(quickSorted));
    }
}
