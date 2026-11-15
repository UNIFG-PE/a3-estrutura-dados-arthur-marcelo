public final class Util {

    private Util() {}

    public static SearchResult linearSearch(int[] arr, int val) {
        int it = 0;
        for(int i = 0; i < arr.length; ++i) {
            ++it;
            if(arr[i] == val) return new SearchResult(i, it);
        }
        return new SearchResult(-1, it);
    }

    public static SearchResult binarySearch(int[] arr, int val) {
        int l = 0;
        int r = arr.length-1;
        int it = 0;
        while(l <= r) {
            ++it;
            int m = (l+r)/2;
            if(arr[m] == val) {
                return new SearchResult(m, it);
            }else if(arr[m] > val) {
                r = m - 1;
            }else {
                l = m + 1;
            }
        }
        return new SearchResult(-1, it);
    }

    public static <T extends Comparable<? super T>> void quickSort(T[] arr, int begin, int end) {
        if(begin >= end) return;
        T pivot = arr[end];
        int i = begin - 1;
        for(int j = begin; j < end; ++j) {
            if(arr[j].compareTo(pivot) <= 0) swap(arr, ++i, j);
        }
        swap(arr, i+1, end);
        int pivotIndex = i+1;
        quickSort(arr, begin, pivotIndex-1);
        quickSort(arr, pivotIndex+1, end);
    }

    public static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public record SearchResult(int index, int iterations) {}
}
