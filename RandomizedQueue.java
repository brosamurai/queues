/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n = 0;
    private int tail = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't be doing that dawg...");
        }
        // for first addition, head = tail
        if (n == 0) {
            tail = 0;
            q[0] = item;
        }

        else if (n < q.length) {
            q[++tail] = item;
            swap();
        }
        else {
            resize(2 * q.length);
            q[++tail] = item;
            swap();
        }
        n++;
    }

    private void swap() {
        int randInt = StdRandom.uniform(tail + 1);
        Item temp = q[randInt];
        q[randInt] = q[tail];
        q[tail] = temp;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int i = 0;
        int k = 0;
        while (i < tail + 1) {
            if (q[i] != null) {
                copy[k] = q[i];
                k++;
            }
            i++;
        }
        q = copy;
        tail = n - 1;
    }

    // remove and return a random item
    public Item dequeue() {
        if (!isEmpty()) {
            // uniformly generate random number between 0 and tail
            int rand = StdRandom.uniform(tail + 1);
            Item itemToReturn = q[rand];

            if (n == 1) {
                tail = 0;
                n--;
                return q[0];
            }

            // put the item in tail in place of removed item
            q[rand] = q[tail];
            q[tail] = null;
            tail--;
            n--;
            // q.length is always gonna be a power of 2
            if (n == q.length / 4) {
                resize(q.length / 2);
            }
            return itemToReturn;
        }
        else {
            throw new NoSuchElementException("queue is empty dawg");
        }
    }

    // return a random item (but dont remove)
    public Item sample() {
        if (!isEmpty()) {
            int rand = StdRandom.uniform(tail + 1);
            return q[rand];
        }
        else {
            throw new NoSuchElementException("queue is empty dawhg");
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private Item[] shuffle() {
        Item[] shuffledQ = (Item[]) new Object[n];
        for (int i = 0; i < n; i++) {
            // copy over items from q
            shuffledQ[i] = q[i];
            // random int between 0 and i
            int r = StdRandom.uniform(i + 1);
            Item temp = shuffledQ[i];
            shuffledQ[i] = shuffledQ[r];
            shuffledQ[r] = temp;
        }
        return shuffledQ;
    }

    private class ListIterator implements Iterator<Item> {
        private int i = 0;
        private boolean lastItem = false;
        private Item[] copyOfq = shuffle();

        public boolean hasNext() {
            return !lastItem;
        }

        public void remove() {
            throw new UnsupportedOperationException("we don't do that around these parts");
        }

        public Item next() {
            if (i == n) {
                throw new NoSuchElementException("The randomized queue is empty my dawg");
            }

            Item item = copyOfq[i];
            i++;

            if (i == n) {
                lastItem = true;
            }

            return item;
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        q.enqueue(6);
        q.enqueue(7);
        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.dequeue();
        q.dequeue();

        // expect error here
        try {
            q.dequeue();
        }
        catch (NoSuchElementException ex) {
            System.out.println("PASSED: threw exception cus queue is empty");
        }

        q.enqueue(1);
        q.enqueue(2);
        q.enqueue(3);
        q.enqueue(4);
        q.enqueue(5);
        q.enqueue(6);
        q.enqueue(7);


        // TEST FOR ITERATOR
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
        System.out.println(queue);
    }
}
