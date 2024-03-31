public class Main {
    public static void main(String[] args) {
        FibHeap<Integer> fh = new FibHeap<>();
        fh.push(4, 4);
        fh.push(5, 5);
        fh.push(6, 6);
        System.out.println(fh.popmin());
        fh.decreasekey(6, 1);
        System.out.println(fh.popmin());
        System.out.println(fh.popmin());
    }
}
