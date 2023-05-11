import java.util.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BalanceArray {

    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        ArrayList<Integer> array = new ArrayList<>();

        for (String line = br.readLine(); line != null; line = br.readLine()) {
            array.add(Integer.parseInt(line));
        }

        int[] sortedArray = new int[array.size()];
        for (int i = 0; i < array.size(); i ++) {
            sortedArray[i] = array.get(i);
        }

        balanceArray(sortedArray);
    }

    public static void balanceArray (int[] sortedArray) {

        int l = sortedArray.length;
        ArrayList<Integer> indexArray = new ArrayList<>();
        int[] balancedArray = new int[l];
    
        findMiddlePointIndex(indexArray, 0, l-1);
    
        for (int i = 0; i < l; i ++) {
            balancedArray[i] = sortedArray[indexArray.get(i)];
        }

        for (int i = 0; i < l; i ++) {
            System.out.println(balancedArray[i]);
        }
    }

    public static void findMiddlePointIndex(ArrayList<Integer> indexArray, int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            if (!indexArray.contains(startIndex)) {
                indexArray.add(startIndex);
            }
            
            return;
        }
    
        int middleIndex = (startIndex + endIndex) / 2;
        if (!indexArray.contains(middleIndex)) {
            indexArray.add(middleIndex);
        }
    
        findMiddlePointIndex(indexArray, middleIndex+1, endIndex);
        findMiddlePointIndex(indexArray, startIndex, middleIndex);
    }
}