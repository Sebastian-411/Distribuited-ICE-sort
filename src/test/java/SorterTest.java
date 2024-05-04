package src.test.java;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.InitializationData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeEach;
import java.util.Random;

public class SorterTest {
    private Sorting.SorterPrx sorter;

    @BeforeEach
    public void setUp() {
        String[] args = {"--Ice.Default.Locator=IceGrid/Locator:tcp -h localhost -p 10001"};
        InitializationData initData = new InitializationData();
        initData.properties = com.zeroc.Ice.Util.createProperties();
        initData.properties.setProperty("Ice.Default.Locator", "IceGrid/Locator:tcp -h localhost -p 10001");
        
        try (Communicator communicator = com.zeroc.Ice.Util.initialize(args, initData)) {
            ObjectPrx base = communicator.stringToProxy("SimpleSorter:tcp -h localhost -p 10001");
            sorter = Sorting.SorterPrx.checkedCast(base);
            if (sorter == null) {
                throw new Error("Invalid proxy");
            }
        }
    }

    // Test 1: Sorting a large integer data set
    @Test
    public void testLargeIntegerDataSetSorting() {
        int[] input = generateRandomIntArray(10000);
        int[] expectedOutput = input.clone();
        Arrays.sort(expectedOutput);

        int[] sortedArray = sorter.divideAndSort(input);
        assertArrayEquals(expectedOutput, sortedArray);
    }

    // Test 2: Sorting a large double data set
    @Test
    public void testLargeDoubleDataSetSorting() {
        double[] input = generateRandomDoubleArray(10000);
        double[] expectedOutput = input.clone();
        Arrays.sort(expectedOutput);

        double[] sortedArray = sorter.divideAndSortD(input);

        assertArrayEquals(expectedOutput, sortedArray);
    }

    // Test 3: Sorting a large string data set
    @Test
    public void testLargeStringDataSetSorting() {
        String[] input = generateRandomStringArray(10000);
        String[] expectedOutput = input.clone();
        Arrays.sort(expectedOutput);

        String[] sortedArray = sorter.divideAndSortS(input);

        assertArrayEquals(expectedOutput, sortedArray);
    }

    // Test 4: Sorting a data set with duplicates
    @Test
    public void testSortingDataSetWithDuplicates() {
        int[] input = {3, 2, 5, 3, 1, 2};
        int[] expectedOutput = {1, 2, 2, 3, 3, 5};

        int[] sortedArray = sorter.divideAndSort(input);
        assertArrayEquals(expectedOutput, sortedArray);
    }

    // Test 5: Sorting already sorted integer data
    @Test
    public void testAlreadySortedIntegerData() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expectedOutput = {1, 2, 3, 4, 5};

        int[] sortedArray = sorter.divideAndSort(input);
        assertArrayEquals(expectedOutput, sortedArray);
    }

    // Test 6: Sorting already sorted double data
    @Test
    public void testAlreadySortedDoubleData() {
        double[] input = {1.2, 2.3, 3.4, 4.5, 5.6};
        double[] expectedOutput = {1.2, 2.3, 3.4, 4.5, 5.6};

        double[] sortedArray = sorter.divideAndSortD(input);
        assertArrayEquals(expectedOutput, sortedArray);
    }

    // Test 7: Sorting already sorted string data
    @Test
    public void testAlreadySortedStringData() {
        String[] input = {"apple", "banana", "grape", "orange"};
        String[] expectedOutput = {"apple", "banana", "grape", "orange"};

        String[] sortedArray = sorter.divideAndSortS(input);
        assertArrayEquals(expectedOutput, sortedArray);
    }

    // Test 8: Sorting data in reverse order
    @Test
    public void testSortingDataInReverseOrder() {
        int[] input = {5, 4, 3, 2, 1};
        int[] expectedOutput = {1, 2, 3, 4, 5};

        int[] sortedArray = sorter.divideAndSort(input);
        assertArrayEquals(expectedOutput, sortedArray);
    }

    // Test 9: Sorting a data set with all elements the same (int)
    @Test
    public void testSortingDataSetWithAllSameElements_Integer() {
        int[] input = {5, 5, 5, 5, 5};
        int[] expectedOutput = {5, 5, 5, 5, 5};

        int[] sortedArray = sorter.divideAndSort(input);
        assertArrayEquals(expectedOutput, sortedArray);
    }

    // Test 10: Sorting a data set with all elements the same (double)
    @Test
    public void testSortingDataSetWithAllSameElements_Double() {
        double[] input = {2.5, 2.5, 2.5, 2.5, 2.5};
        double[] expectedOutput = {2.5, 2.5, 2.5, 2.5, 2.5};

        double[] sortedArray = sorter.divideAndSortD(input);
        assertArrayEquals(expectedOutput, sortedArray);
    }

    // Test 11: Sorting a data set with all elements the same (string)
    @Test
    public void testSortingDataSetWithAllSameElements_String() {
        String[] input = {"apple", "apple", "apple", "apple", "apple"};
        String[] expectedOutput = {"apple", "apple", "apple", "apple", "apple"};

        String[] sortedArray = sorter.divideAndSortS(input);
        assertArrayEquals(expectedOutput, sortedArray);
    }

    // Utility method to generate a random integer array
    private int[] generateRandomIntArray(int size) {
        Random random = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt();
        }
        return array;
    }

    // Utility method to generate a random double array
    private double[] generateRandomDoubleArray(int size) {
        Random random = new Random();
        double[] array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextDouble();
        }
        return array;
    }

    // Utility method to generate a random string array
    private String[] generateRandomStringArray(int size) {
        Random random = new Random();
        String[] array = new String[size];
        for (int i = 0; i < size; i++) {
            array[i] = generateRandomString(random);
        }
        return array;
    }
    
    private String generateRandomString(Random random) {
        int length = random.nextInt(10) + 1; // Random length between 1 and 10 characters
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a'); // Random lowercase letter
            builder.append(randomChar);
        }
        return builder.toString();
    }
}