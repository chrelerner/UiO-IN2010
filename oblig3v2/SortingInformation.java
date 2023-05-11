public class SortingInformation {
    
    private int comparisons = 0;
    private int swaps = 0;

    public void increaseComparisons () {
        this.comparisons++;
    }

    public void increaseSwaps () {
        this.swaps++;
    }

    public int getComparisons () {
        return this.comparisons;
    }

    public int getSwaps () {
        return this.swaps;
    }
}
