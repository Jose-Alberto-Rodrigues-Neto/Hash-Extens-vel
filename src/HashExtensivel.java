import java.util.ArrayList;
import java.util.List;

class HashExtensivel<K, V> {
    private int size;
    private List<List<Entry<K, V>>> table;
    private int profundidadeGlobal;

    public HashExtensivel(int initialSize) {
        this.size = initialSize;
        this.table = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            table.add(null);
        }
        this.profundidadeGlobal = 0;
    }

    private int hashFunction(K key) {
        return key.hashCode() % size;
    }

    public void inserir(K key, V value) {
        int index = hashFunction(key);
        if (table.get(index) == null) {
            table.set(index, new ArrayList<>());
        }
        table.get(index).add(new Entry<>(key, value));
    }

    public int remover(K key) {
        int index = hashFunction(key);
        List<Entry<K, V>> bucket = table.get(index);
        if (bucket != null) {
            for (Entry<K, V> entry : bucket) {
                if (entry.getKey().equals(key)) {
                    bucket.remove(entry);
                    return index;
                }
            }
        }
        return index;
    }

    public V verificar(K key) {
        int index = hashFunction(key);
        List<Entry<K, V>> bucket = table.get(index);
        if (bucket != null) {
            for (Entry<K, V> entry : bucket) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public int getProfundidadeGlobal() {
        return profundidadeGlobal;
    }

    public int getProfundidadeLocal(K key) {
        int index = hashFunction(key);
        List<Entry<K, V>> bucket = table.get(index);
        return bucket != null ? bucket.size() : 0;
    }

    public void duplicar() {
        size *= 2;
        List<List<Entry<K, V>>> newTable = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            newTable.add(null);
        }
        for (List<Entry<K, V>> bucket : table) {
            if (bucket != null) {
                for (Entry<K, V> entry : bucket) {
                    int newIndex = hashFunction(entry.getKey());
                    if (newTable.get(newIndex) == null) {
                        newTable.set(newIndex, new ArrayList<>());
                    }
                    newTable.get(newIndex).add(entry);
                }
            }
        }
        table = newTable;
        profundidadeGlobal++;
    }

    private static class Entry<K, V> {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}

