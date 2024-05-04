module Sorting {
    sequence<int> IntSeq;
    sequence<string> StringSeq;
    sequence<double> DoubleSeq;


    class Response{
        long responseTime;
        string value;
    }

    interface SortService{
        IntSeq intSort(IntSeq data);
        StringSeq stringSort(StringSeq data);
        DoubleSeq doubleSort(DoubleSeq data);

    }

    interface Callback{
        void callbackClient(Response response);
    }
}
