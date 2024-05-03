import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Server {

    private static List<Sorting.SortServicePrx> servers = new ArrayList<>();


    public static void main(String[] args) {
        String port;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el puerto (10000 para el servidor principal): ");
        port = scanner.nextLine();
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            com.zeroc.Ice.ObjectAdapter adapter =
                    communicator.createObjectAdapterWithEndpoints("SimpleSortAdapter", "tcp -p " + port);
            com.zeroc.Ice.Object object = new SorterI();
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("SimpleSorter"));
            adapter.activate();
            System.out.println("Servidor iniciado correctamente.");
            if (port.equals("10000")) {
                while (!port.equals("exit")) {
                    System.out.println("Ingrese el puerto del otro servidor al que desea conectarse (o escriba 'exit' para salir):");
                    port = scanner.nextLine();
                    if (!port.equals("exit")) {
                        connectToServer(communicator, port);
                    }
                }
            }
            communicator.waitForShutdown();
        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void connectToServer(com.zeroc.Ice.Communicator communicator, String port) {
        com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimpleSorter:tcp -p " + port);
        Sorting.SortServicePrx sortService = null;
        try {
            sortService = Sorting.SortServicePrx.checkedCast(base);
        } catch (Exception e) {
            System.out.println("Error al conectarse al servidor: " + e.getMessage());
        }
        if (sortService != null) {
            servers.add(sortService);
            System.out.println("Conexión establecida correctamente con el servidor en el puerto " + port);
        } else {
            System.out.println("No se pudo establecer conexión con el servidor en el puertoX " + port);
        }
    }


}
