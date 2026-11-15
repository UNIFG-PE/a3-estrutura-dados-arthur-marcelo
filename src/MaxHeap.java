public class MaxHeap<T extends Comparable<? super T>> {

    private final T[] array;
    private int ptr = 0;

    @SuppressWarnings({"unchecked", "DataFlowIssue"})
    public MaxHeap(int size) {
        this.array = (T[])new Comparable[size];
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    public void insert(T val) {
        int currentIndex = ptr;
        array[ptr++] = val;
        while(currentIndex > 0 && array[currentIndex].compareTo(array[parent(currentIndex)]) > 0) {
            Util.swap(array, currentIndex, parent(currentIndex));
            currentIndex = parent(currentIndex);
        }
    }

    public T extractMax() {
        if (ptr == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        T max = array[0];
        array[0] = array[--ptr];
        array[ptr] = null;
        int currentIndex = 0;
        while (true) {
            int left = leftChild(currentIndex);
            int right = rightChild(currentIndex);
            if (left >= ptr) {
                break;
            }
            int largest = left;

            if (right < ptr && array[right].compareTo(array[left]) > 0) {
                largest = right;
            }
            if (array[currentIndex].compareTo(array[largest]) >= 0) {
                break;
            }
            Util.swap(array, currentIndex, largest);
            currentIndex = largest;
        }
        return max;
    }

    public T peekMax() {
        if (ptr == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return array[0];
    }
}
