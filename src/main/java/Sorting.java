import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 * Class implementing sorting algorithms.
 *
 * @see <a href="https://cs125.cs.illinois.edu/lab/11/">Lab 11 Description</a>
 */

public class Sorting {

    /** Increment to sweep the sort. */
    private static final int SORT_INCREMENT = 10000;

    /** Total number of values to try. */
    private static final int TOTAL_SORT_VALUES = 100;

    /** Total data size. */
    private static final int TOTAL_INTEGER_VALUES = 1000000;

    /**
     * Bubble sort.
     *
     * @param array unsorted input array
     * @return the sorted array, or null on failure
     */
    static int[] bubbleSort(final int[] array) {
        return null;
    }

    /**
     * Selection sort.
     *
     * @param array unsorted input array
     * @return the sorted array, or null on failure
     */
    static int[] selectionSort(final int[] array) {
        return null;
    }

    /**
     * Merge sort.
     *
     * @param array array that needs to be sorted
     * @return the sorted array, or null on failure
     */
    static int[] mergeSort(final int[] array) {
        return null;
    }

    /**
     * Merge helper function that merges two sorted arrays into a single sorted array.
     * <p>
     * Implement an in place merge algorithm that repeatedly picks the smaller of two numbers from
     * "right" and "left" subarray and copies it to the "arr" array to produce a bigger sorted array
     *
     * @param arr array contains sorted subarrays, should contain the resulting merged sorted array
     * @param l start position of sorted left subarray
     * @param m ending position of sorted left and start position of sorted right subarray
     * @param r ending position of sorted right subarray
     * @return the sorted array, or null on failure
     */
    static int[] merge(final int[] arr, final int l, final int m, final int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] left = new int[n1];
        int[] right = new int[n2];

        for (int i = 0; i < n1; ++i) {
            left[i] = arr[l + i];
        }
        for (int j = 0; j < n2; ++j) {
            right[j] = arr[m + 1 + j];
        }

        /* TO DO: Merge left and right array here */
        return arr;
    }

    /**
     * Main method for testing.
     * <p>
     * This method reads numbers from input file of type specified by user, runs different sorting
     * algorithms on different sizes of the input and plots the time taken by each.
     *
     * @param unused unused input arguments
     * @throws FileNotFoundException thrown if the file is not found
     * @throws URISyntaxException thrown if the file is not found
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public static void main(final String[] unused)
            throws FileNotFoundException, URISyntaxException {

        /*
         * Prompt for the input file.
         */
        Scanner userInput = new Scanner(System.in);
        String dataFilename = "";
        while (true) {
            System.out.println("Enter the type of data to sort "
                    + "(1 for sorted, 2 for almost sorted, 3 for reverse sorted): ");
            int datatype = userInput.nextInt();
            switch (datatype) {
                case 1 :
                    dataFilename = "sorted.txt";
                    break;
                case 2 :
                    dataFilename = "almostsorted.txt";
                    break;
                case 3 :
                    dataFilename = "reverse.txt";
                    break;
                default :
                    System.out.println("Please enter 1, 2, or 3");
                    break;
            }
            if (!dataFilename.equals("")) {
                break;
            }
        }

        /*
         * Load the input file.
         */
        String numbersFilePath = //
                Sorting.class.getClassLoader().getResource(dataFilename).getFile();
        numbersFilePath = new URI(numbersFilePath).getPath();
        File numbersFile = new File(numbersFilePath);
        Scanner numbersScanner = new Scanner(numbersFile, "UTF-8");
        int[] allnumbers = new int[TOTAL_INTEGER_VALUES];
        for (int i = 0; i < TOTAL_INTEGER_VALUES; i++) {
            allnumbers[i] = numbersScanner.nextInt();
        }
        numbersScanner.close();

        /*
         * Prompt for the algorithm to use.
         */
        int whichAlgorithm;
        while (true) {
            System.out.println("Enter the sorting algorithm that you want to use"
                    + " (1 for bubble sort, 2 for insertion sort, 3 for merge sort): ");
            whichAlgorithm = userInput.nextInt();
            if (whichAlgorithm > 0 && whichAlgorithm < 4) {
                break;
            }
        }

        int[] timeValues = new int[TOTAL_SORT_VALUES];
        boolean succeeded = true;
        for (int i = 1; i <= TOTAL_SORT_VALUES; i++) {
            /*
             * Sweep in increments of SORT_INCREMENT. Copy the array first. Clone doesn't work here
             * because we only want a certain number of values.
             */
            int[] unsortedArray = new int[i * SORT_INCREMENT];
            for (int j = 0; j < (i * SORT_INCREMENT); j++) {
                unsortedArray[j] = allnumbers[j];
            }

            /*
             * Sort the array using the algorithm requested. Measure and record the time taken.
             */
            int[] sortedArray;
            long startTime = System.currentTimeMillis();
            switch (whichAlgorithm) {
                case 1 :
                    sortedArray = bubbleSort(unsortedArray);
                    break;
                case 2 :
                    sortedArray = selectionSort(unsortedArray);
                    break;
                default :
                    sortedArray = mergeSort(unsortedArray);
                    break;
            }
            if (sortedArray == null) {
                succeeded = false;
                break;
            }
            long endTime = System.currentTimeMillis();
            timeValues[i - 1] = (int) (endTime - startTime);
        }
        userInput.close();

        if (!succeeded) {
            System.out.println("Sorting failed");
            return;
        }

        /*
         * Plot the results if the sorts succeeded.
         */
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new GraphPlotter(timeValues));
        f.setSize(400, 400);
        f.setLocation(200, 200);
        f.setVisible(true);
    }
}
