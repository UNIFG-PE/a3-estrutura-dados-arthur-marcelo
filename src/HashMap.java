import java.util.Objects;

public class HashMap<K, V> {

    @SuppressWarnings("unchecked")
    private LinkedList<Pair<K, V>>[] buckets = new LinkedList[16];
    private static final float LOAD_FACTOR = 0.75f;
    private int size = 0;

    private int getIndex(K key) {
        int hash = key.hashCode();
        hash ^= hash >>> 16;
        return hash & buckets.length-1;
    }

    public V get(K key) {
        int index = getIndex(key);
        LinkedList<Pair<K, V>> bucket = buckets[index];
        if (bucket == null) {
            return null;
        }
        LinkedElement<Pair<K, V>> cur = bucket.iterate();
        while(cur != null) {
            if(Objects.equals(cur.getValue().key(), key)) return cur.getValue().value();
            cur = cur.getNext();
        }
        return null;
    }

    public boolean add(K key, V value) {
        int index = getIndex(key);
        LinkedList<Pair<K, V>> bucket = buckets[index];
        if(bucket != null) {
            LinkedElement<Pair<K, V>> cur = bucket.iterate();
            while (cur != null) {
                if(Objects.equals(cur.getValue().key(), key)) {
                    cur.setValue(new Pair<>(key, value));
                    return false;
                }
                cur = cur.getNext();
            }
        }
        if (size >= LOAD_FACTOR * buckets.length) {
            resize();
            index = getIndex(key);
        }
        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }
        buckets[index].add(0, new Pair<>(key, value));
        ++size;
        return true;
    }

    public boolean contains(K key) {
        int index = getIndex(key);
        LinkedList<Pair<K, V>> bucket = buckets[index];
        if (bucket == null) {
            return false;
        }
        LinkedElement<Pair<K, V>> cur = bucket.iterate();
        while(cur != null) {
            if(Objects.equals(cur.getValue().key(), key)) return true;
            cur = cur.getNext();
        }
        return false;
    }

    public boolean remove(K key) {
        int index = getIndex(key);
        LinkedList<Pair<K, V>> bucket = buckets[index];
        if (bucket == null) {
            return false;
        }
        LinkedElement<Pair<K, V>> cur = bucket.iterate();
        LinkedElement<Pair<K, V>> prev = null;
        while(cur != null) {
            if(Objects.equals(cur.getValue().key(), key)) {
                if(prev == null) {
                    bucket.setStart(cur.getNext());
                }else {
                    prev.setNext(cur.getNext());
                }
                bucket.setLength(bucket.getLength()-1);
                --size;
                return true;
            }
            prev = cur;
            cur = cur.getNext();
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        LinkedList<Pair<K, V>>[] oldBuckets = buckets;
        buckets = new LinkedList[oldBuckets.length * 2];
        size = 0;
        for (LinkedList<Pair<K, V>> bucket : oldBuckets) {
            if (bucket == null) {
                continue;
            }
            LinkedElement<Pair<K, V>> bucketIterator = bucket.iterate();
            for(int i = 0; i < bucket.getLength(); ++i) {
                add(bucketIterator.getValue().key(), bucketIterator.getValue().value());
                bucketIterator = bucketIterator.getNext();
            }
        }
    }

    public int getSize() {
        return size;
    }

    private record Pair<K, V>(K key, V value) {}
}
