import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator; 
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private final Node head; 
    private final Node tail; 
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;

        Node(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {
        head = new Node(null);
        tail = new Node(null);
        head.next = tail;
        tail.prev = head;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0; 
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) 
            throw new IllegalArgumentException("Element e cannot be null");

        Node node = new Node(item);
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
        size++;
    } 

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) 
            throw new IllegalArgumentException("Element e cannot be null");

        Node node = new Node(item);
        node.next = tail;
        node.prev = tail.prev;
        tail.prev.next = node; 
        tail.prev = node;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) 
            throw new NoSuchElementException("Deque is empty");

        Node node = head.next;
        head.next = node.next;
        head.next.prev = head;
        size--;
        return node.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) 
            throw new NoSuchElementException("Deque is empty");

        Node node = tail.prev;
        tail.prev = node.prev;
        node.prev.next = tail;
        size--;
        return node.item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new HeadFirstIterator();
    }

    private class HeadFirstIterator implements Iterator<Item> {
        private Node curr = head;

        @Override
        public boolean hasNext() {
            return curr.next != tail;
        }

        @Override
        public Item next() {
            if(!hasNext()) 
                throw new NoSuchElementException("No more item");

            curr = curr.next;
            return curr.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove unsupported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        StdOut.println("Testing start");

        // Public methods tests
        Deque<Integer> d1 = new Deque<>(); 
        StdOut.println("Test 1A passed? " + d1.isEmpty()); 
        d1.addFirst(1);
        d1.addLast(2);
        StdOut.println("Test 1D passed? " + (d1.removeFirst() == 1));
        StdOut.println("Test 1E passed? " + (d1.removeLast() == 2));
        d1.addFirst(0);
        StdOut.println("Test 1F passed? " + (d1.iterator().next() == 0));
        d1.removeFirst();
        StdOut.println("Test 1G passed? " + (d1.isEmpty()));
        StdOut.println("Test 1H passed? " + (!d1.iterator().hasNext()));

        // Exception tests
        Deque<Integer> d2 = new Deque<>();
        StdOut.print("Test 2A passed? ");
        try {
            d2.removeFirst();
            StdOut.println(false);
        } catch(NoSuchElementException e) {
            StdOut.println(true);
        }

        StdOut.print("Test 2B passed? ");
        try {
            d2.removeLast();
            StdOut.println(false);
        } catch(NoSuchElementException e) {
            StdOut.println(true);
        } 

        StdOut.print("Test 2C passed? ");
        try {
            d2.addFirst(null);
            StdOut.println(false);
        } catch(IllegalArgumentException e) {
            StdOut.println(true);
        }

        StdOut.print("Test 2D passed? ");
        try {
            d2.addLast(null);
            StdOut.println(false);
        } catch(IllegalArgumentException e) {
            StdOut.println(true);
        }  

        StdOut.print("Test 2E passed? ");
        try {
            d2.iterator().next();
            StdOut.println(false);
        } catch(NoSuchElementException e) {
            StdOut.println(true);
        }  

        StdOut.print("Test 2F passed? ");
        try {
            d2.iterator().remove();
            StdOut.println(false);
        } catch(UnsupportedOperationException e) {
            StdOut.println(true);
        }  

        StdOut.println("Testing complete");
    }
}
