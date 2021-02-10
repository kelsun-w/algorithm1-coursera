import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private final int INIT_CAPACITY = 8;

    private Item[] q;
    private int lastIndex;

    // construct an empty randomized queue
    public RandomizedQueue() {
        Item[] arr = (Item[]) new Object[INIT_CAPACITY]; 
        q = arr;
        lastIndex = -1;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // return the number of items on the randomized queue
    public int size() { 
        return lastIndex + 1;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Argument cannot be null");

        if (size() == q.length) 
            resize(2 * q.length);

        q[++lastIndex] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Empty Queue");

        int i = StdRandom.uniform(size());
        Item removed = q[i];
        q[i] = q[lastIndex];
        q[lastIndex--] = null;

        if (size() > 0 && size() == q.length/4)
            resize(q.length / 2);

        return removed;
    }

    // return a random item (but do not remove it)
    public Item sample() { 
        if (isEmpty())
            throw new NoSuchElementException("Empty queue");

        Item sample = null;
        while (sample == null) {
            sample = q[StdRandom.uniform(size())];
        }
        return sample;
    }

    private void resize(int capacity) {
        Item[] newArr = (Item[]) new Object[capacity];

        int i = 0;
        while (i <= lastIndex) {
            newArr[i] = q[i];
            i++;
        }
        q = newArr;
        lastIndex = i - 1;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedIterator();
    }

    private class RandomizedIterator implements Iterator<Item> {
        private Item[] copiedArray;
        private int copiedLastIndex;

        RandomizedIterator() {
            Item[] a = (Item[]) new Object[size()];
            for (int i = 0; i <= lastIndex; i++) {
                a[i] = q[i];
            }
            copiedArray = a;
            copiedLastIndex = lastIndex;
        }

        @Override
        public boolean hasNext() {
            return lastIndex >= 0;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("No more item.");

            int i = StdRandom.uniform(copiedLastIndex + 1);
            Item item = copiedArray[i];
            copiedArray[i] = copiedArray[copiedLastIndex];
            copiedArray[copiedLastIndex--] = null;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove unsupported.");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> arr = new RandomizedQueue<>();
        StdOut.println("Testing start.");
        StdOut.println("Test 1A passed? " + arr.isEmpty());
        arr.enqueue(1);
        arr.enqueue(2);
        StdOut.println("Test 1B passed? " + (arr.size() == 2));
        int prevSize = arr.size();
        int r1 = arr.dequeue();
        StdOut.println("Test 1C passed? " + (r1 == 1 || r1 == 2));
        StdOut.println("Test 1D passed? " + (arr.size() == (prevSize - 1)));
        prevSize = arr.size();
        int r2 = arr.sample();
        StdOut.println("Test 1E passed? " + (r2 == 1 || r2 == 2));
        StdOut.println("Test 1F passed? " + (prevSize == arr.size()));
        
        arr.enqueue(3);
        int r3 = arr.iterator().next();
        StdOut.println("Test 1G passed? " + (arr.iterator().hasNext() == true));
        StdOut.println("Test 1H passed? " + (r3 == 1 || r3 == 2 || r3 == 3)); 
        StdOut.println("Test 1I passed? " + (arr.iterator() != arr.iterator()));

        RandomizedQueue<Integer> arr2 = new RandomizedQueue<>();
        StdOut.print("Test 2A passed? ");
        try {
            arr2.enqueue(null);
            StdOut.println(false);
        } catch(IllegalArgumentException e) {
            StdOut.println(true);
        }
        StdOut.print("Test 2B passed? ");
        try {
            arr2.dequeue();
            StdOut.println(false);
        } catch(NoSuchElementException e) {
            StdOut.println(true);
        }
        StdOut.print("Test 2C passed? ");
        try {
            arr2.sample();
            StdOut.println(false);
        } catch(NoSuchElementException e) {
            StdOut.println(true);
        }
        StdOut.print("Test 2D passed? ");
        try {
            arr2.iterator().next();
            StdOut.println(false);
        } catch(NoSuchElementException e) {
            StdOut.println(true);
        }
        StdOut.print("Test 2E passed? ");
        try {
            arr2.iterator().remove();
            StdOut.println(false);
        } catch(UnsupportedOperationException e) {
            StdOut.println(true);
        }

        StdOut.println("Testing complete");
    }
}
