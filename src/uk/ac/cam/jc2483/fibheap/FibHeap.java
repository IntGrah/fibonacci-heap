import java.util.HashMap;
import java.util.Map;

public class FibHeap<T> extends CDLL<T> {
    public static class PriorityException extends IllegalArgumentException {}
    private Map<T, FibNode<T>> nodes = new HashMap<>();

    void insert(FibNode<T> n) {
        super.insert(n);
        n.loser = false;
        n.parent = null;
        if (n.priority < head.priority)
            head = n;
    }

    public void push(T x, int k) {
        if (nodes.containsKey(x))
            throw new IndexOutOfBoundsException();
        nodes.put(x, new FibNode<>(x, k));
        insert(nodes.get(x));
    }

    public void decreasekey(T x, int k) {
        FibNode<T> n = nodes.get(x);
        if (k > n.priority)
            throw new PriorityException();
        n.priority = k;
        if (n.parent == null) {
            if (k < head.priority)
                head = n;
        } else {
            while (true) {
                FibNode<T> p = n.parent;
                p.delete(n);
                insert(n);
                if (!p.loser) {
                    if (p.parent != null)
                        p.loser = true;
                    return;
                }
                n = p;
            }
        }
    }

    public T popmin() {
        FibNode<T> m = head;
        if (m == null)
            throw new IndexOutOfBoundsException();
        delete(m);
        nodes.remove(m.value);
        for (FibNode<T> child : m.toList())
            insert(child);
        Map<Integer, FibNode<T>> d = new HashMap<>();
        for (FibNode<T> t : toList()) {
            while (d.containsKey(t.size)) {
                t = t.merge(d.get(t.size));
                d.remove(t.size - 1);
            }
            d.put(t.size, t);
        }
        head = null;
        size = 0;
        for (FibNode<T> root : d.values())
            insert(root);
        return m.value;
    }

    public boolean contains(T x) {
        return nodes.containsKey(x);
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
