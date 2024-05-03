import java.io.BufferedReader;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Client{
    public static void main(String[] args){
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)){
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimpleSorter:tcp -h localhost -p 10001");
            Sorting.SorterPrx sorter = Sorting.SorterPrx.checkedCast(base);
            if(sorter == null){
                throw new Error("Invalid proxy");
            }

            Scanner sc = new Scanner(System.in);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean flag = true;
            while(flag){
            System.out.println("What kind of array do you want to order? Type the number: \n 1. Array of integers [7, 5, 3]" 
            + "\n 2. Array of Strings [A, b, a, D] \n 3. Array of double [2.5, 2.3, 5.0, 5.7, 5.5]");

            int typeA = sc.nextInt();

            if(typeA == 1 || typeA == 2 || typeA == 3){
                System.out.println("Type the array");

                    try {
                        String message = br.readLine();
                        if (isValidArray(message)) {
                            long startTime = 0;
                            long endTime = 0;
                            long duration = 0;
                            switch (typeA) {
                                case 1:
                                int[] array = extractIntArray(message);
                                startTime = System.nanoTime();
                                int[] sortedArray =  sorter.divideAndSort(array);
                                endTime = System.nanoTime();
                                duration = (endTime - startTime) / 1000000; // Convertir nanosegundos a milisegundos
                                System.out.println("La línea tardó " + duration + " milisegundos en ejecutarse.");
                                printSortedArrayI(sortedArray);
                                break;

                                case 2:
                                String[] arrayS = extractStringArray(message);
                                startTime = System.nanoTime();
                                String[] sortedArrayS = sorter.divideAndSortS(arrayS);
                                endTime = System.nanoTime();
                                duration = (endTime - startTime) / 1000000; // Convertir nanosegundos a milisegundos
                                System.out.println("La línea tardó " + duration + " milisegundos en ejecutarse.");
                                printSortedArrayS(sortedArrayS);

                                case 3:
                                double[] arrayD = extractDoubleArray(message);
                                startTime = System.nanoTime();
                                double[] sortedArrayD = sorter.divideAndSortD(arrayD);
                                endTime = System.nanoTime();
                                duration = (endTime - startTime) / 1000000; // Convertir nanosegundos a milisegundos
                                System.out.println("La línea tardó " + duration + " milisegundos en ejecutarse.");
                                printSortedArrayD(sortedArrayD);
                            }    
                        } else {
                            System.out.println("Invalid input. Please type an array of correct.");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Do you want to order an other array? 1. Yes 2. No");
                    String msg = br.readLine();
                    if(msg.equals("2")){
                        flag = false;
                    }

            }
            else{
                System.out.println("Incorrect type of data select.");
            }
            }
            sc.close();
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static boolean isValidArray(String message) {
        Pattern integerPattern = Pattern.compile("\\[\\s*\\d+(\\s*,\\s*\\d+)*\\s*\\]");
        Pattern stringPattern = Pattern.compile("\\[\\s*\\w+(\\s*,\\s*\\w+)*\\s*\\]");
        Pattern doublePattern = Pattern.compile("\\[\\s*\\d+(\\.\\d+)?(\\s*,\\s*\\d+(\\.\\d+)?)*\\s*\\]");

        Matcher integerMatcher = integerPattern.matcher(message);
        Matcher stringMatcher = stringPattern.matcher(message);
        Matcher doubleMatcher = doublePattern.matcher(message);

        return integerMatcher.matches() || stringMatcher.matches() || doubleMatcher.matches();
    }

    private static int[] extractIntArray(String message) {
        String[] parts = message.replaceAll("[\\[\\]\\s]+", "").split(",");
        int[] array = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            array[i] = Integer.parseInt(parts[i]);
        }
        return array;
    }

    private static double[] extractDoubleArray(String message) {
        String[] parts = message.replaceAll("[\\[\\]\\s]+", "").split(",");
        double[] array = new double[parts.length];
        for (int i = 0; i < parts.length; i++) {
            array[i] = Double.parseDouble(parts[i]);
        }
        return array;
    }

    private static String[] extractStringArray(String message) {
        String[] parts = message.replaceAll("[\\[\\]\\s]+", "").split(",");
        return parts;
    }

    private static void printSortedArrayI(int[] sArray){
        System.out.print("Sorted Array: [");
        for (int num : sArray) {
            System.out.print(num + " ");
        }
        System.out.print("]");
        System.out.println();
    }

    private static void printSortedArrayS(String[] sArray){
        System.out.print("Sorted Array: [");
        for (String num : sArray) {
            System.out.print(num + " ");
        }
        System.out.print("]");
        System.out.println();
    }

    private static void printSortedArrayD(double[] sArray){
        System.out.print("Sorted Array: [");
        for (double num : sArray) {
            System.out.print(num + " ");
        }
        System.out.print("]");
        System.out.println();
    }
}