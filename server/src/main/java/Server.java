import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Server {

    private static List<Worker> servers = new ArrayList<>();


    public static void main(String[] args) {
        
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            String port;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el puerto (10000 para el servidor principal): ");
            port = scanner.nextLine();
            com.zeroc.Ice.ObjectAdapter adapter =
                    communicator.createObjectAdapterWithEndpoints("SimpleSortAdapter", "tcp -p "+port);
            if (port.equals("10000")) {
                while (!port.equals("exit")) {
                    System.out.println("Ingrese el puerto del otro servidor al que desea conectarse (o escriba 'exit' para salir):");
                    port = scanner.nextLine();
                    if (!port.equals("exit")) {
                        connectToServer(port);
                    }
                }
            }
            communicator.waitForShutdown();
        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void connectToServer(String port) {
        com.zeroc.Ice.Communicator comm = com.zeroc.Ice.Util.initialize();
        com.zeroc.Ice.ObjectPrx base = comm.stringToProxy("SimpleSorter:tcp -p " + port);
        Sorting.SorterPrx sortService = Sorting.SorterPrx.checkedCast(base);
        if(sortService != null) {
            //servers.add(sortService);
            System.out.println("Conexión establecida correctamente con el servidor en el puerto " + port);
        }else {
            System.out.println("No se pudo establecer conexión con el servidor en el puerto " + port);
        }
        // try {
        //     sortService = Sorting.SorterPrx.checkedCast(base);
        // } catch (Exception e) {
        //     System.out.println("Error al conectarse al servidor: " + e.getMessage());
        // }
        // if (sortService != null) {
        //     servers.add(sortService);
        //     System.out.println("Conexión establecida correctamente con el servidor en el puerto " + port);
        // } else {
        //     System.out.println("No se pudo establecer conexión con el servidor en el puertoX " + port);
        // }
    }


}
