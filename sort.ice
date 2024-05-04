module Sorting {
        sequence<int> IntSeq;
        sequence<string> IntSeqS;
        sequence<double> IntSeqD;
    interface Sorter {
        IntSeq divideAndSort(IntSeq arr);
        IntSeq merge(IntSeq arr1, IntSeq arr2);
        IntSeqS divideAndSortS(IntSeqS arr);
        IntSeqS mergeS(IntSeqS arr1, IntSeqS arr2);
        IntSeqD divideAndSortD(IntSeqD arr);
        IntSeqD mergeD(IntSeqD arr1, IntSeqD arr2);
    }
    class Response{
        long responseTime;
        string value;
    }

    interface Callback{
        void callbackClient(Response response);
    }
}