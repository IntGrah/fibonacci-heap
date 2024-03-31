from __future__ import annotations


class CDLL[T]:
    def __init__(self):
        self.head: FibNode[T] = None
        self.deg = 0

    def insert(self, n: FibNode[T]):
        if self.head is None:
            self.head = n.prev = n.next = n
        else:
            a = self.head
            z = a.prev
            z.next = n
            n.prev = z
            n.next = a
            a.prev = n
        self.deg += 1

    def delete(self, n: FibNode[T]):
        for a in self.list():
            if a == n:
                b = a.next
                z = a.prev
                b.prev = z
                z.next = b
                if a == self.head:
                    self.head = None if a == b else b
                self.deg -= 1
                return
        raise IndexError

    def list(self):
        l: list[FibNode[T]] = []
        a = self.head
        while a is not None:
            l.append(a)
            a = a.next
            if a == self.head:
                break
        return l


class FibNode[T](CDLL[T]):
    def __init__(self, x: T, k: int):
        super().__init__()
        self.x = x
        self.k = k
        self.loser = False
        self.prev: FibNode[T] = None
        self.next: FibNode[T] = None
        self.parent: FibNode[T] = None

    def insert(self, n: FibNode[T]):
        super().insert(n)
        n.parent = self

    def merge(self, t):
        if self.k < t.k:
            self.insert(t)
            return self
        else:
            t.insert(self)
            return t


class FibHeap[T](CDLL[T]):
    def __init__(self):
        super().__init__()
        self.nodes: dict[T, FibNode[T]] = {}

    def insert(self, n: FibNode[T]):
        super().insert(n)
        n.loser = False
        n.parent = None
        if n.k < self.head.k:
            self.head = n

    def push(self, x: T, k: int):
        if x in self.nodes:
            raise IndexError
        self.nodes[x] = n = FibNode(x, k)
        self.insert(n)

    def decreasekey(self, x: T, k: int):
        n = self.nodes[x]
        assert k <= n.k
        n.k = k
        if n.parent is None:
            if k < self.head.k:
                self.head = n
        else:
            while True:
                p = n.parent
                p.delete(n)
                self.insert(n)
                if not p.loser:
                    if p.parent is not None:
                        p.loser = True
                    return
                n = p    

    def popmin(self):
        m = self.head
        if m is None:
            raise IndexError
        self.delete(m)
        del self.nodes[m.x]
        for child in m.list():
            self.insert(child)
        d: dict[int, FibNode[T]] = {}
        for t in self.list():
            while t.deg in d:
                t = t.merge(d[t.deg])
                del d[t.deg - 1]
            d[t.deg] = t
        self.head = None
        self.deg = 0
        for root in d.values():
            self.insert(root)
        return m.x

    def __contains__(self, x: T):
        return x in self.nodes

    def __bool__(self):
        return self.deg > 0


def main():
    fh = FibHeap()
    fh.push(5, 5)
    fh.push(4, 4)
    fh.push(3, 3)
    fh.decreasekey(5, 1)
    print(fh.popmin())
    print(fh.popmin())

if __name__ == "__main__":
    main()
