import java.io.*;
import java.util.*;

public class Oblig3 {
    
    public static void main (String[] args) throws FileNotFoundException {

        String fileName = args[0];

        File inputFile = new File(fileName);
        Scanner scan = new Scanner(inputFile);

        ArrayList<Integer> inputList = new ArrayList<>();

        while (scan.hasNextLine()) {
            inputList.add(scan.nextInt());
        }

        // Makes a normal array "input" of integers and fills it with the integers.
        int[] input = new int[inputList.size()];
        for (int i = 0; i < inputList.size(); i++) {
            input[i] = inputList.get(i);
        }

        try {

            File file1 = new File(fileName + "_results.csv");
            FileWriter writer1 = new FileWriter(file1);

            writer1.append("    n, insertion_cmp, insertion_swaps, insertion_time, quick_cmp, quick_swaps, quick_time" +
                        ", bubble_cmp, bubble_swaps, bubble_time, merge_cmp, merge_swaps, merge_time");

            for (int i = 0; i <= input.length; i++) {
                writer1.append("\n");

                // Operations for insertionsort.
                int[] output1 = controlArray(input, i);
                SortingInformation counters1 = new SortingInformation();

                long t1 = System.nanoTime();
                int[] result1 = insertionSort(output1, counters1);
                long time1 = (System.nanoTime()-t1)/1000;

                // Write to file for insertionSort here.
                String size = String.format("%5d", i);
                String cmp1 = String.format("%14d", counters1.getComparisons());
                String swap1 = String.format("%16d", counters1.getSwaps());
                String timer1 = String.format("%15d", time1);
                writer1.append(size + "," + cmp1 + "," + swap1 + "," + timer1);




                // This if-block fixes a system-related bug with a spike of the
                // timing of the second algorithm (any algorithm as second) during the third iteration of i.
                if (i == 2) {
                    int[] outputx = controlArray(input, i);
                    SortingInformation countersx = new SortingInformation();
                    quickSort(outputx, 0, outputx.length-1, countersx);
                }

                // Operations for quicksort.
                int[] output2 = controlArray(input, i);
                SortingInformation counters2 = new SortingInformation();

                long t2 = System.nanoTime();
                int[] result2 = quickSort(output2, 0, output2.length-1, counters2);
                long time2 = (System.nanoTime()-t2)/1000;

                // Write to file for quickSort here.
                String cmp2 = String.format("%10d", counters2.getComparisons());
                String swap2 = String.format("%12d", counters2.getSwaps());
                String timer2 = String.format("%11d", time2);
                writer1.append("," + cmp2 + "," + swap2 + "," + timer2);




                // Operations for bubblesort.
                int[] output3 = controlArray(input, i);
                SortingInformation counters3 = new SortingInformation();

                long t3 = System.nanoTime();
                int[] result3 = bubbleSort(output3, counters3);
                long time3 = (System.nanoTime()-t3)/1000;

                // Write to file for bubbleSort here.
                String cmp3 = String.format("%11d", counters3.getComparisons());
                String swap3 = String.format("%13d", counters3.getSwaps());
                String timer3 = String.format("%12d", time3);
                writer1.append("," + cmp3 + "," + swap3 + "," + timer3);




                // Operations for mergesort.
                int[] output4 = controlArray(input, i);
                SortingInformation counters4 = new SortingInformation();

                long t4 = System.nanoTime();
                int[] result4 = mergeSort(output4, counters4);
                long time4 = (System.nanoTime()-t4)/1000;

                // Write to file for mergeSort here.
                String cmp4 = String.format("%10d", counters4.getComparisons());
                String swap4 = String.format("%12d", counters4.getSwaps());
                String timer4 = String.format("%11d", time4);
                writer1.append("," + cmp4 + "," + swap4 + "," + timer4);

                // Writes the resulting output arrays to corresponding files for algorithms.
                if (i == input.length) {

                    writeToFile(result1, fileName + "_insertion.out");
                    writeToFile(result2, fileName + "_quick.out");
                    writeToFile(result3, fileName + "_bubble.out");
                    writeToFile(result4, fileName + "_merge.out");
                }
            }

            writer1.close();
        }
        catch (IOException e) {
            System.out.println("Error");
        }

        scan.close();
    }

    // Returns an array with a given amount of input-array's elements.
    public static int[] controlArray (int[] array, int size) {
        int[] copy = new int[size];
        for (int i = 0; i < size; i++) {
            copy[i] = array[i];
        }

        return copy;
    }

    public static void writeToFile (int[] array, String filename) throws IOException {

        File file = new File(filename);

        FileWriter writer = new FileWriter(file);

        for (int i = 0; i < array.length; i++) {
            writer.write(array[i] + "\n");
        }

        writer.close();
    }

    public static int[] copyArray (int[] array) {
        int[] copy = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            copy[i] = array[i];
        }

        return copy;
    }

    public static int[] insertionSort (int[] array, SortingInformation counters) {
        int n = array.length;

        for (int i = 1; i <= n - 1; i++) {
            int j = i;

            while (j > 0 && array[j-1] > array[j]) {
                counters.increaseComparisons();

                int first = array[j-1];
                int second = array[j];

                array[j-1] = second;
                array[j] = first;

                j --;

                counters.increaseSwaps();
            }
            counters.increaseComparisons();
        }

        return array;
    }

    public static int[] quickSort (int[] array, int low, int high, SortingInformation counters) {

        if (low >= high) {
            return array;
        }

        int p = partition(array, low, high, counters);

        quickSort(array, low, p - 1, counters);
        quickSort(array, p + 1, high, counters);

        return array;
    }

    public static int partition (int[] array, int low, int high, SortingInformation counters) {

        int p = (int) ((Math.random() * (high - low)) + low);

        int first = array[high];
        int second = array[p];

        array[p] = first;
        array[high] = second;

        counters.increaseSwaps();

        int pivot = array[high];
        int left = low;
        int right = high - 1;

        while (left <= right) {

            while (left <= right && array[left] <= pivot) {
                // Comparison for inner while-loop returned true.
                counters.increaseComparisons();

                left ++;
            }
            // Comparison for inner while-loop returned false.
            counters.increaseComparisons();

            while (right >= left && array[right] >= pivot) {
                // Comparison for inner while-loop returned true.
                counters.increaseComparisons();
                
                right --;
            }
            // Comparison for inner while-loop returned false.
            counters.increaseComparisons();

            if (left < right) {
                first = array[left];
                second = array[right];

                array[left] = second;
                array[right] = first;

                counters.increaseSwaps();
            }
        }

        first = array[left];
        second = array[high];

        array[left] = second;
        array[high] = first;

        counters.increaseSwaps();

        return left;
    }

    public static int[] bubbleSort (int[] array, SortingInformation counters) {

        int n = array.length;

        outer: for (int i = 0; i <= n - 2; i++) {

            boolean check = true;

            for (int j = 0; j <= n - i - 2; j++) {

                if (array[j] > array[j + 1]) {
                    int first = array[j];
                    int second = array[j + 1];

                    array[j] = second;
                    array[j + 1] = first;

                    counters.increaseSwaps();

                    check = false;
                }
                counters.increaseComparisons();
            }
            // Breaks out of outer loop if no changes occur during an iteration.
            if (check) {
                break outer;
            }
        }

        return array;
    }

    public static int[] mergeSort (int[] array, SortingInformation counters) {

        int n = array.length;
        if (n <= 1) {
            return array;
        }

        int x = n/2;
        int y;

        // If you have 11 / 2, you will end up with [0, 1, 2, 3, 4] and [5, 6, 7, 8, 9, 10].
        if (n % 2 == 0) {
            y = x;
        }
        else {
            y = x + 1;
        }

        int[] array1 = new int[x];
        int[] array2 = new int[y];

        for (int i = 0; i < x; i++) {
            array1[i] = array[i];
        }

        for (int i = 0; i < y; i++) {
            array2[i] = array[x+i];
        }

        // Returns the resulting array, not the array with counters.
        array1 = mergeSort(array1, counters);
        array2 = mergeSort(array2, counters);

        return merge(array1, array2, array, counters);
    }

    public static int[] merge (int[] array1, int[] array2, int[] array, SortingInformation counters) {
        int i = 0;
        int j = 0;

        while (i < array1.length && j < array2.length) {

            if (array1[i] < array2[j]) {
                array[i + j] = array1[i];
                i ++;
            }
            else {
                array[i + j] = array2[j];
                j ++;
            }
            counters.increaseComparisons();
            counters.increaseSwaps();
        }

        while (i < array1.length) {
            array[i + j] = array1[i];
            i ++;

            counters.increaseSwaps();
        }

        while (j < array2.length) {
            array[i + j] = array2[j];
            j ++;

            counters.increaseSwaps();
        }

        return array;
    }
}
