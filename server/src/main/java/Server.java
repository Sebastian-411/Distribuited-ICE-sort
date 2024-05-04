import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.Map;

public class Server {

    public static BinaryTree<Sorting.SortServicePrx> servers = new BinaryTree<>();

    public static void main(String[] args){
        int port = 10000;
        Scanner scanner = new Scanner(System.in);

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)){
            com.zeroc.Ice.ObjectAdapter adapter =
            communicator.createObjectAdapterWithEndpoints("SimpleSortAdapter", "tcp -p " + port);
            com.zeroc.Ice.Object object = new SorterI();


            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("SimpleSorter"));
            adapter.activate();


            System.out.println("Main  rver started");

            System.out.println("How many server u wanna use?");
            int servers_amount = Integer.parseInt(scanner.nextLine());


            while(servers_amount != 0){
                try{
                    port++;
                    createServer(args, port);
                    Thread.sleep(1000);                    
                    com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimpleSorter:tcp -p " + port);
                    Sorting.SortServicePrx sortService = null;
                    try {
                        sortService = Sorting.SortServicePrx.checkedCast(base);
                    } catch (Exception e) {
                        System.out.println("Invalid proxy");
                    }
                    if (sortService != null) {
                        servers.insert(sortService);
                        System.out.println("Connected to server");    
                        servers_amount--;
                    } else {
                        System.out.println("Error");
                    }
                } catch (Exception e) {

                }
            }
            communicator.waitForShutdown();
        }
    }

    public static void createServer(String[] args, int port) {
        new Thread(() -> {
            try {
                com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args);
                com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("SimpleSortAdapter",
                        "tcp -p " + port);
                com.zeroc.Ice.Object object = new SorterI();

                adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("SimpleSorter"));
                adapter.activate();

                System.out.println("Secondary server started");
            } catch (Exception e) {
                throw e;
            }

        }).start();
    }

}
