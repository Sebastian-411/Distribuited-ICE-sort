import Sorting.*;
import com.zeroc.Ice.Current;

import java.util.Arrays;

public class NodeSorter implements Sorter{

    @Override
    public int[] divideAndSort(int[] arr, Current current) {
        if (arr.length <= 1) {
            return arr;
        }
        int mid = arr.length / 2;
        int[] left = divideAndSort(Arrays.copyOfRange(arr, 0, mid), current);
        int[] right = divideAndSort(Arrays.copyOfRange(arr, mid, arr.length), current);
        return merge(left, right, current);
    }

    @Override
    public int[] merge(int[] arr1, int[] arr2, Current current) {
        int[] result = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }
        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }
        return result;
    }

    private int[] connectToServer(int[] arr, String conf) {
        com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize();
        com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy(conf);
        Sorting.SorterPrx sortService = Sorting.SorterPrx.checkedCast(base);
        if (sortService == null) {
            throw new Error("Invalid proxy");
        }
        return sortService.divideAndSort(arr);

    }

    @Override
    public String[] divideAndSortS(String[] arr, Current current) {
        if (arr.length <= 1) {
            return arr;
        }
        int mid = arr.length / 2;
        String[] left = divideAndSortS(Arrays.copyOfRange(arr, 0, mid), current);
        String[] right = divideAndSortS(Arrays.copyOfRange(arr, mid, arr.length), current);
        return mergeS(left, right, current);
    }

    @Override
    public String[] mergeS(String[] arr1, String[] arr2, Current current) {
        String[] result = new String[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i].compareTo(arr2[j]) < 0) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }
        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }
        return result;
    }

    @Override
    public double[] divideAndSortD(double[] arr, Current current) {
        if (arr.length <= 1) {
            return arr;
        }
        int mid = arr.length / 2;
        double[] left = divideAndSortD(Arrays.copyOfRange(arr, 0, mid), current);
        double[] right = divideAndSortD(Arrays.copyOfRange(arr, mid, arr.length), current);
        return mergeD(left, right, current);
    }

    @Override
    public double[] mergeD(double[] arr1, double[] arr2, Current current) {
        double[] result = new double[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }
        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }
        return result;
    }
}
