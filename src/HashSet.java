public class HashSet<T> {

    @SuppressWarnings("unchecked")
    private LinkedList<T>[] buckets = new LinkedList[16];
    private static final float LOAD_FACTOR = 0.75f;
    private int size = 0;

    private int getIndex(T key) {
        int hash = key.hashCode();
        hash ^= hash >>> 16;
        return hash & buckets.length-1;
    }

    public boolean add(T val) {
        if(contains(val)) {
            return false;
        }
        if(size >= LOAD_FACTOR * buckets.length) {
            resize();
        }
        int index = getIndex(val);
        if(buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }
        buckets[index].add(0, val);
        ++size;
        return true;
    }

    public boolean contains(T val) {
        int index = getIndex(val);
        LinkedList<T> bucket = buckets[index];
        if (bucket == null) {
            return false;
        }
        return bucket.contains(val);
    }

    public boolean remove(T val) {
        int index = getIndex(val);
        LinkedList<T> bucket = buckets[index];
        if (bucket == null || !bucket.removeElement(val)) {
            return false;
        }
        --size;
        return true;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        LinkedList<T>[] oldBuckets = buckets;
        buckets = new LinkedList[oldBuckets.length * 2];
        size = 0;
        for (LinkedList<T> bucket : oldBuckets) {
            if (bucket == null) {
                continue;
            }
            LinkedElement<T> bucketIterator = bucket.iterate();
            for(int i = 0; i < bucket.getLength(); ++i) {
                add(bucketIterator.getValue());
                bucketIterator = bucketIterator.getNext();
            }
        }
    }
}
