public class Client{
    public static void main(String[] args){
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)){
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimpleSortAdapter:tcp -p 10000");
            Sorting.SorterPrx sorter = Sorting.SorterPrx.checkedCast(base);
                if(sorter == null){
                throw new Error("Invalid proxy");
                }
            
        }catch(Exception e){}
    }
    
}
