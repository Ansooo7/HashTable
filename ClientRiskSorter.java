import java.util.*;

class Client {
    String id;
    int riskScore;
    double accountBalance;

    public Client(String id, int riskScore, double accountBalance) {
        this.id = id;
        this.riskScore = riskScore;
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return id + "(" + riskScore + ", $" + accountBalance + ")";
    }
}

public class ClientRiskSorter {

    // Bubble Sort: Ascending by riskScore, visualize swaps
    public static void bubbleSort(Client[] clients) {
        int n = clients.length;
        int swaps = 0;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (clients[j].riskScore > clients[j + 1].riskScore) {
                    Client temp = clients[j];
                    clients[j] = clients[j + 1];
                    clients[j + 1] = temp;
                    swaps++;
                    System.out.println("Swap: " + clients[j] + " <-> " + clients[j + 1]);
                }
            }
        }
        System.out.println("BubbleSort result (asc): " + Arrays.toString(clients));
        System.out.println("Total swaps: " + swaps);
    }

    // Insertion Sort: Descending by riskScore, then accountBalance
    public static void insertionSort(Client[] clients) {
        int n = clients.length;
        int shifts = 0;

        for (int i = 1; i < n; i++) {
            Client key = clients[i];
            int j = i - 1;

            while (j >= 0 && compare(clients[j], key) < 0) {
                clients[j + 1] = clients[j];
                j--;
                shifts++;
            }
            clients[j + 1] = key;
        }

        System.out.println("InsertionSort result (desc): " + Arrays.toString(clients));
        System.out.println("Shifts: " + shifts);
    }

    // Compare for insertion sort: riskScore DESC, then accountBalance ASC
    private static int compare(Client c1, Client c2) {
        if (c1.riskScore != c2.riskScore) {
            return Integer.compare(c1.riskScore, c2.riskScore);
        }
        return Double.compare(c2.accountBalance, c1.accountBalance); // higher balance first
    }

    // Identify top 10 highest risk clients
    public static void topRisks(Client[] clients, int topN) {
        System.out.print("Top " + topN + " risks: ");
        for (int i = 0; i < Math.min(topN, clients.length); i++) {
            System.out.print(clients[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Client[] clients = {
            new Client("clientC", 80, 5000.0),
            new Client("clientA", 20, 1200.0),
            new Client("clientB", 50, 3000.0)
        };

        // Bubble Sort demo
        bubbleSort(Arrays.copyOf(clients, clients.length));

        // Insertion Sort demo
        Client[] sortedClients = Arrays.copyOf(clients, clients.length);
        insertionSort(sortedClients);

        // Top risks
        topRisks(sortedClients, 3);
    }
}
