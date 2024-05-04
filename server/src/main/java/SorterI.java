import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.TimeoutException;

public class SorterI implements Sorting.SortService {
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    @Override
    public int[] intSort(int[] data, Current current) {
        BinaryTree<Sorting.SortServicePrx> servers = Server.servers;
        List<TreeNode<Sorting.SortServicePrx>> serverNodes = getServerNodes(servers.root);
        List<int[]> dividedData = divideIntArray(data, serverNodes.size());
        List<Future<int[]>> futures = new ArrayList<>();
    
        for (int i = 0; i < serverNodes.size(); i++) {
            int[] subset = dividedData.get(i);
            Sorting.SortServicePrx server = serverNodes.get(i).data;
            Map<String, String> emptyMap = new HashMap<>(); // Crear un Map vacío
            System.out.println("Iniciando otro servidor...");
            futures.add(executor.submit(() -> {
                try {
                    System.out.println("Llamada al método en el servidor secundario...");
                    return server.intSort(subset, emptyMap);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }));
            System.out.println("Llamada al método en el servidor secundario finalizada.");
        }
    
        int[] result = new int[data.length];
        int pos = 0;
        System.out.println("Esperando resultados de los servidores...");
        for (Future<int[]> future : futures) {
            try {
                System.out.println("Esperando resultado de un servidor...");
                int[] sortedSubset = future.get(); // Esperar 30 segundos para obtener el resultado
                System.out.println("Resultado recibido de un servidor.");
                if (sortedSubset != null) {
                    System.arraycopy(sortedSubset, 0, result, pos, sortedSubset.length);
                    pos += sortedSubset.length;
                }
            } catch (InterruptedException | ExecutionException  e) {
                e.printStackTrace();
            }
        }
    
        System.out.println("Proceso de fusión completado.");
        return mergeIntArray(result);
    }
    
    private List<int[]> divideIntArray(int[] data, int parts) {
        List<int[]> dividedData = new ArrayList<>();
        int size = data.length / parts;
        int remainder = data.length % parts;
        int startIndex = 0;
        for (int i = 0; i < parts; i++) {
            int length = size + (i < remainder ? 1 : 0);
            int[] subset = Arrays.copyOfRange(data, startIndex, startIndex + length);
            dividedData.add(subset);
            startIndex += length;
        }
        return dividedData;
    }

    private int[] mergeIntArray(int[] arr) {
        if (arr.length <= 1) {
            return arr;
        }

        int mid = arr.length / 2;
        int[] left = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);

        // Recursivamente ordenar ambas mitades
        left = mergeIntArray(left);
        right = mergeIntArray(right);

        System.out.println("por aqui tambien");
        return mergeIntArrays(left, right);
    }

    private int[] mergeIntArrays(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }
        System.out.println(result);
        return result;
    }

    private List<TreeNode<Sorting.SortServicePrx>> getServerNodes(TreeNode<Sorting.SortServicePrx> node) {
        List<TreeNode<Sorting.SortServicePrx>> serverNodes = new ArrayList<>();
        getServerNodesHelper(node, serverNodes);
        return serverNodes;
    }

    private void getServerNodesHelper(TreeNode<Sorting.SortServicePrx> node,
            List<TreeNode<Sorting.SortServicePrx>> serverNodes) {
        if (node != null) {
            getServerNodesHelper(node.left, serverNodes);
            serverNodes.add(node);
            getServerNodesHelper(node.right, serverNodes);
        }
    }

    @Override
    public String[] stringSort(String[] data, Current current) {
        BinaryTree<Sorting.SortServicePrx> servers = Server.servers;
        List<TreeNode<Sorting.SortServicePrx>> serverNodes = getServerNodes(servers.root);
        List<String[]> dividedData = divideStringArray(data, serverNodes.size());
        List<Future<String[]>> futures = new ArrayList<>();

        for (int i = 0; i < serverNodes.size(); i++) {
            String[] subset = dividedData.get(i);
            Sorting.SortServicePrx server = serverNodes.get(i).data;
            futures.add(executor.submit(() -> stringSort(subset, current)));
        }

        String[] result = new String[data.length];
        int pos = 0;
        for (Future<String[]> future : futures) {
            try {
                String[] sortedSubset = future.get();
                System.arraycopy(sortedSubset, 0, result, pos, sortedSubset.length);
                pos += sortedSubset.length;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return mergeStringArray(result);
    }

    @Override
    public double[] doubleSort(double[] data, Current current) {
        BinaryTree<Sorting.SortServicePrx> servers = Server.servers;
        List<TreeNode<Sorting.SortServicePrx>> serverNodes = getServerNodes(servers.root);
        List<double[]> dividedData = divideDoubleArray(data, serverNodes.size());
        List<Future<double[]>> futures = new ArrayList<>();

        for (int i = 0; i < serverNodes.size(); i++) {
            double[] subset = dividedData.get(i);
            Sorting.SortServicePrx server = serverNodes.get(i).data;
            futures.add(executor.submit(() -> doubleSort(subset, current)));
        }

        double[] result = new double[data.length];
        int pos = 0;
        for (Future<double[]> future : futures) {
            try {
                double[] sortedSubset = future.get();
                System.arraycopy(sortedSubset, 0, result, pos, sortedSubset.length);
                pos += sortedSubset.length;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return mergeDoubleArray(result);
    }

    private List<String[]> divideStringArray(String[] data, int parts) {
        List<String[]> dividedData = new ArrayList<>();
        int size = data.length / parts;
        int remainder = data.length % parts;
        int startIndex = 0;
        for (int i = 0; i < parts; i++) {
            int length = size + (i < remainder ? 1 : 0);
            String[] subset = Arrays.copyOfRange(data, startIndex, startIndex + length);
            dividedData.add(subset);
            startIndex += length;
        }
        return dividedData;
    }

    private List<double[]> divideDoubleArray(double[] data, int parts) {
        List<double[]> dividedData = new ArrayList<>();
        int size = data.length / parts;
        int remainder = data.length % parts;
        int startIndex = 0;
        for (int i = 0; i < parts; i++) {
            int length = size + (i < remainder ? 1 : 0);
            double[] subset = Arrays.copyOfRange(data, startIndex, startIndex + length);
            dividedData.add(subset);
            startIndex += length;
        }
        return dividedData;
    }

    private String[] mergeStringArray(String[] arr) {
        if (arr.length <= 1) {
            return arr;
        }

        int mid = arr.length / 2;
        String[] left = Arrays.copyOfRange(arr, 0, mid);
        String[] right = Arrays.copyOfRange(arr, mid, arr.length);

        left = mergeStringArray(left);
        right = mergeStringArray(right);

        return mergeStringArrays(left, right);
    }

    private String[] mergeStringArrays(String[] left, String[] right) {
        String[] result = new String[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i].compareTo(right[j]) <= 0) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        return result;
    }

    private double[] mergeDoubleArray(double[] arr) {
        if (arr.length <= 1) {
            return arr;
        }

        int mid = arr.length / 2;
        double[] left = Arrays.copyOfRange(arr, 0, mid);
        double[] right = Arrays.copyOfRange(arr, mid, arr.length);

        left = mergeDoubleArray(left);
        right = mergeDoubleArray(right);

        return mergeDoubleArrays(left, right);
    }

    private double[] mergeDoubleArrays(double[] left, double[] right) {
        double[] result = new double[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) {
            result[k++] = left[i++];
        }

        while (j < right.length) {
            result[k++] = right[j++];
        }

        return result;
    }

}
