import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Sorting.*;


public class Client{
    public static void main(String[] args){
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)){
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimpleSortAdapter: localhost -p 10000");
            Sorting.SorterPrx sorter = Sorting.SorterPrx.checkedCast(base);
                if(sorter == null){
                    throw new Error("Invalid proxy");
                }
                System.out.println("Type an array of integers. Example: [2, 3, 4]");
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                boolean flag = true;

                while(flag){
                    try {
                        String message = br.readLine();
                        if (isValidArray(message)) {
                            flag = false;
                            int[] array = extractIntArray(message);
                            
                            // Llamamos al método divideAndSort del objeto remoto sorter
                            int[] sortedArray =  sorter.divideAndSort(array);

                            //int[] sortedIntArray = sortedArray.toArray();

                            System.out.println("Sorted Array: " + sortedArray);
                        } else {
                            System.out.println("Invalid input. Please type an array of integers. Example: [2, 3, 4]");
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }   
                }
                br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static boolean isValidArray(String message) {
        Pattern pattern = Pattern.compile("\\[\\s*\\d+(\\s*,\\s*\\d+)\\s\\]");
        Matcher matcher = pattern.matcher(message);
        return matcher.matches();
    }

    private static int[] extractIntArray(String message) {
        String[] parts = message.replaceAll("[\\[\\]\\s]+", "").split(",");
        int[] array = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            array[i] = Integer.parseInt(parts[i]);
        }
        return array;
    }

}