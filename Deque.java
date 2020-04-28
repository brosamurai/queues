/* *****************************************************************************
 *  Name: Deque
 *  Date: 2020-04-28
 *  Description: Deques implementation in java
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first; // top of stack (most recently added node)
    private Node last; // bottom of stack (least recently added node)
    private int n; // number of items

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty deque
    public Deque() {
        if (!isEmpty()) {
            first.next = null;
            last = first;
        }
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
        if (!isEmpty()) {
            // get reference to oldFirst
            Node oldfirst = first;
            // change the item of the first node to item from argument
            first.item = item;
            // set the next node as the oldFirst node and increment n
            first.next = oldfirst;
            n++;
        }
        else {
            first.item = item;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (!isEmpty()) {
            // get reference to lastNode
            Node oldLast = last;
            // change the item of the last node to item from argument
            last.item = item;
            // set the next for oldLast to the new last and increment n
            oldLast.next = last;
            n++;
        }
        else {
            first.item = item;
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
                first = first.next;
                n--;
            }
            else {
                first.item = null;
                first.next = null;
                last.item = null;
                last.next = null;
                n--;
            }

            return oldFirst.item;
        }
    }

    // remove and return the item from the back
    public Item removeLast() {
        Node oldLast = last;

        // if only one node in deque
        if (!isEmpty() && n == 1) {
            last.item = null;
            last.next = null;
            first.item = null;
            first.next = null;
            n--;
            return oldLast.item;
        }
        // More than one node? Find  2nd last node and make that the new last
        else {
            last = findSecondLastNode();
            last.next = null;
            n--;
        }
        return oldLast.item;
    }

    private Node findSecondLastNode() {
        Node secondLast = new Node();
        Node currentNode = first;
        while (currentNode.next.next != null) {
            secondLast = first;
            currentNode = currentNode.next;
        }
        return secondLast;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
    }
}
