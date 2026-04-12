import java.util.*;

class Transaction {
    String id;
    double fee;
    String timestamp; // HH:mm format

    public Transaction(String id, double fee, String timestamp) {
        this.id = id;
        this.fee = fee;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return id + ": " + fee + "@" + timestamp;
    }
}

public class TransactionSorter {

    // Bubble Sort: Ascending by fee
    public static void bubbleSort(List<Transaction> transactions) {
        int n = transactions.size();
        boolean swapped;
        int passes = 0, swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            passes++;
            for (int j = 0; j < n - i - 1; j++) {
                if (transactions.get(j).fee > transactions.get(j + 1).fee) {
                    Collections.swap(transactions, j, j + 1);
                    swapped = true;
                    swaps++;
                }
            }
            if (!swapped) break; // Early termination
        }

        System.out.println("BubbleSort result: " + transactions);
        System.out.println("Passes: " + passes + ", Swaps: " + swaps);
    }

    // Insertion Sort: Ascending by fee, then timestamp
    public static void insertionSort(List<Transaction> transactions) {
        int n = transactions.size();
        int shifts = 0;

        for (int i = 1; i < n; i++) {
            Transaction key = transactions.get(i);
            int j = i - 1;

            while (j >= 0 && compare(transactions.get(j), key) > 0) {
                transactions.set(j + 1, transactions.get(j));
                j--;
                shifts++;
            }
            transactions.set(j + 1, key);
        }

        System.out.println("InsertionSort result: " + transactions);
        System.out.println("Shifts: " + shifts);
    }

    // Comparison: fee first, then timestamp
    private static int compare(Transaction t1, Transaction t2) {
        if (t1.fee != t2.fee) {
            return Double.compare(t1.fee, t2.fee);
        }
        return t1.timestamp.compareTo(t2.timestamp); // stable by timestamp
    }

    // Flag high-fee outliers
    public static void flagOutliers(List<Transaction> transactions) {
        System.out.print("High-fee outliers (> $50): ");
        boolean found = false;
        for (Transaction t : transactions) {
            if (t.fee > 50.0) {
                System.out.print(t + " ");
                found = true;
            }
        }
        if (!found) System.out.print("none");
        System.out.println();
    }

    public static void main(String[] args) {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("id1", 10.5, "10:00"));
        transactions.add(new Transaction("id2", 25.0, "09:30"));
        transactions.add(new Transaction("id3", 5.0, "10:15"));

        // Example: Bubble Sort for small batch
        bubbleSort(new ArrayList<>(transactions));

        // Example: Insertion Sort for medium batch
        insertionSort(new ArrayList<>(transactions));

        // Outlier detection
        flagOutliers(transactions);
    }
}
