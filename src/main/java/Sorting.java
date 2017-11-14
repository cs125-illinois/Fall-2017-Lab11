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

    /**
     * Bubble sorting algorithm that runs in O(n^2).
     *
     * @param arr unsorted input array
     * @param n size of the input
     */
    static void bubbleSort(final int[] arr, final int n) {
    }

    /**
     * Merge function that merges two sorted arrays into a single sorted array.
     * <p>
     * Implement an in place merge algorithm that repeatedly picks the smaller of two numbers from
     * "right" and "left" subarray and copies it to the "arr" array to produce a bigger sorted array
     *
     * @param arr array contains sorted subarrays, should contain the resulting merged sorted array
     * @param l start position of sorted left subarray
     * @param m ending position of sorted left and start position of sorted right subarray
     * @param r ending position of sorted right subarray
     */
    static void merge(final int[] arr, final int l, final int m, final int r) {
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
    }

    /**
     * Merge sorting algorithm that runs in O(n log n).
     *
     * @param arr array that needs to be sorted
     * @param l start position of unsorted array
     * @param r ending position of unsorted subarray
     */
    static void mergeSort(final int[] arr, final int l, final int r) {
    }

    /**
     * Selective sorting algorithm that runs in O(n^2).
     *
     * @param arr unsorted input array
     * @param n size of the input
     */
    static void selectionSort(final int[] arr, final int n) {
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

        Scanner user = new Scanner(System.in);
        String dataFilename = "";
        while (true) {
            System.out.println("Enter the type of data to sort "
                    + "(1 for sorted, 2 for almost sorted, 3 for reverse sorted): ");
            int datatype = user.nextInt();
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

        String numbers = null;
        String numbersFilePath = //
                Sorting.class.getClassLoader().getResource(dataFilename).getFile();
        numbersFilePath = new URI(numbersFilePath).getPath();
        File numbersFile = new File(numbersFilePath);
        Scanner numbersScanner = new Scanner(numbersFile, "UTF-8");

        int totaln = 1000000;
        int[] allnumbers = new int[totaln];
        for (int i = 0; i < totaln; i++) {
            allnumbers[i] = numbersScanner.nextInt();
        }
        numbersScanner.close();

        int[] timeValues = new int[100];

        int alg;
        while (true) {
            System.out.println("Enter the sorting algorithm that you want to use"
                    + " (1 for bubble sort, 2 for insertion sort, 3 for merge sort): ");
            alg = user.nextInt();
            if (alg > 0 && alg < 4) {
                break;
            }
        }

        for (int i = 1; i <= 100; i++) {
            // Create a new array of 10000, 20000, 30000... integers to sort
            int[] sortingarray = new int[i * 10000];
            for (int j = 0; j < (i * 10000); j++) {
                sortingarray[j] = allnumbers[j];
            }

            // Sort the array using algorithm specified by user
            long startTime = System.currentTimeMillis();
            switch (alg) {
                case 1 :
                    bubbleSort(sortingarray, (i * 10000));
                    break;
                case 2 :
                    selectionSort(sortingarray, (i * 10000));
                    break;
                default :
                    mergeSort(sortingarray, 0, (i * 10000) - 1);
                    break;
            }
            long endTime = System.currentTimeMillis();

            // Append the time taken to sort to the timeValues array
            timeValues[i - 1] = (int) (endTime - startTime);
        }
        user.close();

        /*
         * Plot a graph corresponding to the values in the timeValues array Graphics in Java has a
         * Frame has the parent component which we initialize below
         */
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new GraphPlotter(timeValues));
        // Sets the size of the frame
        f.setSize(400, 400);
        // Location of the frame relative to the top-left part of the screen
        f.setLocation(200, 200);
        f.setVisible(true);
    }
}
