import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Sorting.*;
import com.zeroc.Ice.Current;

public class SorterI implements Sorter{

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
}