
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> q = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String input = StdIn.readString(); 
            q.enqueue(input);
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(q.dequeue());
        }
    }
}
