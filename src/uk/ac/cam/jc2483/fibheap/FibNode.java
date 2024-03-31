public class FibNode<T> extends CDLL<T> {
    T value;
    int priority;
    boolean loser = false;
    FibNode<T> prev = null;
    FibNode<T> next = null;
    FibNode<T> parent = null;

    FibNode(T x, int k) {
        super();
        value = x;
        priority = k;
    }

    void insert(FibNode<T> node) {
        super.insert(node);
        node.parent = this;
    }

    FibNode<T> merge(FibNode<T> tree) {
        if (priority < tree.priority) {
            insert(tree);
            return this;
        } else {
            tree.insert(this);
            return tree;
        }
    }
}
