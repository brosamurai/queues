import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first; // top of stack (most recently added node)
    private Node last; // bottom of stack (least recently added node)
    private int n; // number of items

    private class Node {
        private Item item;
        private Node next;
        private Node previous;

        public Node getPrevious() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("cannot have a null argument");
        }
        if (!isEmpty()) {
            Node oldfirst = first;
            first = new Node();
            first.setItem(item);
            first.setNext(oldfirst);
            oldfirst.setPrevious(first);
            n++;
        }
        else {
            first = new Node();
            last = first;
            first.setItem(item);
            n++;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        // TODO: move n++ out of the if-statement?
        if (item == null) {
            throw new IllegalArgumentException("cannot have a null argument");
        }
        if (!isEmpty()) {
            // get reference to lastNode
            Node newLast = new Node();
            newLast.setItem(item);
            newLast.setPrevious(last);
            last.setNext(newLast);
            last = newLast;
            n++;
        }
        else {
            first = new Node();
            last = first;
            first.setItem(item);
            n++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (!isEmpty()) {
            // get reference to firstNode
            Node oldFirst = first;

            // if more than 1 node, update first to first.next
            // If only one node then empty the deque
            if (n > 1) {
                first = first.getNext();
                first.setPrevious(null);
                n--;
            }
            else {
                first = null;
                last = null;
                n--;
            }

            return oldFirst.getItem();
        }
        else {
            throw new NoSuchElementException("deque is empty dawg");
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        Node oldLast = last;
        if (isEmpty()) {
            throw new NoSuchElementException("deque is empty dawg");
        }
        // if only one node in deque
        // TODO: we're doing two comparisons here ... see removeFirst()
        else if (!isEmpty() && n == 1) {
            first = null;
            last = null;
            n--;
        }
        // More than one node? Find 2nd last node and make that the new last
        else if (n > 1) {
            last = last.getPrevious();
            last.setNext(null);
            n--;
        }
        return oldLast.getItem();
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current.getNext() != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("we don't do that around these parts");
        }

        public Item next() {
            if (current.getNext() == null) {
                throw new NoSuchElementException("no more elements in the deque dawg");
            }
            Item item = current.getItem();
            current = current.getNext();
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deq = new Deque<>();

        // TESTS FOR ERROR CATCHING
        try {
            deq.removeFirst();
        }
        catch (NoSuchElementException ex) {
            System.out.println("PASSED: " + ex.getMessage());
        }
        try {
            deq.removeLast();
        }
        catch (NoSuchElementException ex) {
            System.out.println("PASSED: " + ex.getMessage());
        }
        try {
            deq.addLast(null);
        }
        catch (IllegalArgumentException ex) {
            System.out.println("PASSED: " + ex.getMessage());
        }
        try {
            deq.addFirst(null);
        }
        catch (IllegalArgumentException ex) {
            System.out.println("PASSED: " + ex.getMessage());
        }

        // TEST THE isEmpty() FUNCTION
        if (deq.isEmpty()) {
            System.out.println("PASSED: isEmpty()");
        }

        // TEST THE addFirst() AND addLast() FUNCTIONS
        deq.addFirst("first");
        if (deq.first.getItem().equals("first")) {
            System.out.println(
                    "PASSED: successfully added " + deq.first.getItem() + " to start of deque");
        }
        else {
            System.out.println("FAILED to add 'first' to start of deque");
        }

        deq.addLast("last");
        if (deq.last.getItem().equals("last")) {
            System.out.println(
                    "PASSED: successfully added " + deq.last.getItem() + " to end of deque");
        }
        else {
            System.out.println("FAILED to add 'last' to end of deque");
        }

        deq.addFirst("new first");
        if (deq.first.getItem().equals("new first")) {
            System.out.println(
                    "PASSED: successfully added " + deq.first.getItem() + " to start of deque");
        }
        else {
            System.out.println("FAILED to add 'new first' to start of deque");
        }

        deq.addLast("new last");
        if (deq.last.getItem().equals("new last")) {
            System.out.println(
                    "PASSED: successfully added " + deq.last.getItem() + " to end of deque");
        }
        else {
            System.out.println("FAILED to add 'new last' to end of deque");
        }

        // TEST THE removeLast() and removeFirst() FUNCTIONS
        String last = deq.removeLast();
        if (deq.last.getItem().equals("last")) {
            System.out.println(
                    "PASSED: successfully removed and extracted '" + last + "' from end of deque");
        }
        else {
            System.out.println("FAILED to remove '" + last + "' from end of deque");
        }

        String first = deq.removeFirst();
        if (deq.first.getItem().equals("first")) {
            System.out.println(
                    "PASSED: successfully removed and extracted '" + first
                            + "' from start of deque");
        }
        else {
            System.out.println("FAILED to remove '" + first + "' from end of deque");
        }

        // TEST THE isEmpty() FUNCTION AGAIN
        if (!deq.isEmpty()) {
            System.out.println("PASSED: deque is NOT empty");
        }

        // TEST THE size() FUNCTION
        int size = deq.size();
        if (size == 2) {
            System.out.println("PASSED: deque has 2 elements");
        }

        // TEST emptying, refilling and emptying deque
        deq.removeFirst();
        deq.removeFirst();
        if (deq.size() == 0) {
            System.out.println("PASSED: deque is empty");
        }
        deq.addLast("test1");
        if (deq.first.getItem().equals("test1")) {
            System.out.println("PASSED: 'test1' was added to deque");
        }
        deq.addLast("test2");
        if (deq.first.getItem().equals("test1")) {
            System.out.println("PASSED: 'test1' is still first in the deque");
        }
        deq.removeLast();
        deq.removeLast();
        if (deq.size() == 0) {
            System.out.println("PASSED: deque is empty again");
        }

        // TODO: write tests for iterator!
    }
}
