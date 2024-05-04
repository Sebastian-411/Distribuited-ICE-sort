import java.util.Scanner;

public class Worker {
    private static String port;

    Worker(String numPort){
        this.port = numPort;
    }

    public static void main(String[] args) {
        
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            com.zeroc.Ice.ObjectAdapter adapter =
                    communicator.createObjectAdapterWithEndpoints("SimpleSortAdapter", "tcp -p " + port);
            com.zeroc.Ice.Object object = new SorterI();
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("SimpleSorter"));
            adapter.activate();
            System.out.println("Servidor iniciado correctamente.");

            communicator.waitForShutdown();
        } catch (Exception e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
