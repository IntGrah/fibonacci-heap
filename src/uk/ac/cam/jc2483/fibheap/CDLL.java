import java.util.ArrayList;
import java.util.List;

class CDLL<T> {
    FibNode<T> head = null;
    int size = 0;

    void insert(FibNode<T> n) {
        if (size++ == 0) {
            head = n.prev = n.next = n;
        } else {
            FibNode<T> last = head.prev;
            n.prev = last;
            n.next = head;
            head.prev = n;
            last.next = n;
        }
    }

    void delete(FibNode<T> n) {
        for (FibNode<T> a : toList()) {
            if (a == n) {
                FibNode<T> b = a.next;
                FibNode<T> z = a.prev;
                b.prev = z;
                z.next = b;
                if (a == head)
                    head = a == b ? null : b;
                size--;
                return;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    List<FibNode<T>> toList() {
        List<FibNode<T>> list = new ArrayList<>();
        FibNode<T> cur = head;
        if (size > 0) {
            do {
                list.add(cur);
            } while ((cur = cur.next) != head);
        }
        return list;
    }
}
