public class Server {
    public static void main(String[] args) {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            com.zeroc.Ice.ObjectAdapter adapter =
            communicator.createObjectAdapterWithEndpoints("SimpleSortAdapter", "tcp -h localhost -p 10001");
            com.zeroc.Ice.Object object = new SorterI();
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("SimpleSorter"));
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
