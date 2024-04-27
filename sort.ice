module Sorting {
        sequence<int> IntSeq;
    interface Sorter {
        IntSeq divideAndSort(IntSeq arr);
        IntSeq merge(IntSeq arr1, IntSeq arr2);
    }
}
