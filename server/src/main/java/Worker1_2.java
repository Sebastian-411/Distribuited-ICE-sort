public class Worker1_2 {
    public static void main(String[] args) {
        try (
                com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(
                        args)) {
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints(
                    "SimpleSortAdapter",
                    "tcp -h localhost -p 10003");
            com.zeroc.Ice.Object object = new NodeSorter();
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("Service2/2"));
            adapter.activate();
            System.out.println("Running Server");
            communicator.waitForShutdown();
        }
    }
}
