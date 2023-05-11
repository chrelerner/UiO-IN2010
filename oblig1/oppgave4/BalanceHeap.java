import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BalanceHeap {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        PriorityQueue<Integer> heap = new PriorityQueue<>();

        for (String line = br.readLine(); line != null; line = br.readLine()) {
            heap.offer(Integer.parseInt(line));
        }

        PriorityQueue<Integer> noDupes = new PriorityQueue<>();

        balancedIndexPrinter(heap, noDupes, 0, heap.size()-1);

        
    }

    public static void balancedIndexPrinter (PriorityQueue<Integer> heap, PriorityQueue<Integer> noDupes, int start, int end) {
        Boolean check = true;
        PriorityQueue<Integer> heap2 = new PriorityQueue<>();
        PriorityQueue<Integer> noDupes2 = new PriorityQueue<>();

        if (start == end) {

            // Sjekker om denne indeksen finner i noDupes, og kopierer alt innholdet til en
            // midlertidig heap.
            while (noDupes.size() != 0) {
                int number  = noDupes.poll();
                noDupes2.offer(number);
                if (number == start) {
                    check = false;
                }
            }

            // Legger alle verdiene tilbake i den originale noDupes.
            while (noDupes2.size() != 0) {
                noDupes.offer(noDupes2.poll());
            }

            // Hvis vi ikke har med en duplikat indeks å gjoere, printes den korrekte
            // verdien fra heapen ut.
            if (check) {
                int size = heap.size();
                for (int i = 0; i < size; i ++) {
                    int number = heap.poll();
                    heap2.offer(number);
                    if (i == start) {
                        System.out.println(number);
                        noDupes.offer(i);
                    }
                }
                while (heap2.size() != 0) {
                    heap.offer(heap2.poll());
                }
            }

            return;
        }

        int middle = (start + end) / 2;

        while (noDupes.size() != 0) {
            int number  = noDupes.poll();
            noDupes2.offer(number);
            if (number == middle) {
                check = false;
            }
        }

        while (noDupes2.size() != 0) {
            noDupes.offer(noDupes2.poll());
        }

        if (check) {
            int size = heap.size();
            for (int i = 0; i < size; i ++) {
                int number = heap.poll();
                heap2.offer(number);
                if (i == middle) {
                    System.out.println(number);
                    noDupes.offer(i);
                }
            }
            while (heap2.size() != 0) {
                heap.offer(heap2.poll());
            }
        }

        balancedIndexPrinter(heap, noDupes, middle+1, end);
        balancedIndexPrinter(heap, noDupes, start, middle);
    }
}
