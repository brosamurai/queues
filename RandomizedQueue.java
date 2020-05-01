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
    private int head = 0;
    private int tail = 0;

    /*
    1. My dequeue function has a while loop that is used to find a random non-null
    item to return. Same problem with the sample() function

    2. Dequeue function has a flaw. When the 'head' gets detached,
    I have to use a loop to find the next non-null element and make that the new head

    3. Iterator is working but not to performance standards.
    Need a way to return a random item but then also ensure that the same item
    is not returned again
     */

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
    // what if every time you enqueue a new item, it gets swapped at random?
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Can't be doing that dawg...");
        }
        // add item to tail + 1
        // for first addition, head = tail
        if (n == 0) {
            head = 0;
            tail = 0;
            q[head] = item;
        }
        else if (tail == q.length - 1 && n < q.length) {
            resize(q.length);
            q[++tail] = item;
        }
        else if (n < q.length) {
            // can do ++tail
            q[++tail] = item;
        }
        else {
            // we gotta resize the queue
            resize(2 * q.length);
            q[++tail] = item;
        }
        n++;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int i = head;
        int k = 0;
        while (i < tail + 1) {
            if (q[i] != null) {
                copy[k] = q[i];
                k++;
            }
            i++;
        }
        q = copy;
        head = 0;
        tail = n - 1;
    }

    // remove and return a random item
    public Item dequeue() {
        // if (test) {
        //     StdRandom.shuffle(q);
        // }
        if (!isEmpty()) {
            int rand = StdRandom.uniform(head, tail + 1);
            while (q[rand] == null) {
                rand = StdRandom.uniform(head, tail + 1);
            }

            // Will this be garbage collected? Is it even necessary?
            Item itemToReturn = q[rand];

            if (n == 1) {
                head = 0;
                tail = 0;
                n--;
                return q[head];
            }
            else if (rand == tail) {
                tail--;
            }
            else if (rand == head) {
                // TODO: big problem... how can we know where the new head must be?
                // is this even necessary? yes
                // what if we reset the array when this happens? ... not efficient
                while (q[++head] == null) {
                }
            }

            q[rand] = null;
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
            int rand = StdRandom.uniform(head, tail + 1);
            while (q[rand] == null) {
                rand = StdRandom.uniform(head, tail + 1);
            }
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

    private class ListIterator implements Iterator<Item> {
        private boolean shuffled = false;
        private int numOfItemsProcessed = 0;
        private int i = 0;
        private boolean lastItem = false;
        private Item[] copyOfq = q.clone();

        public boolean hasNext() {
            if (!shuffled) {
                StdRandom.shuffle(copyOfq);
                shuffled = true;
            }
            return !lastItem;
        }

        public void remove() {
            throw new UnsupportedOperationException("we don't do that around these parts");
        }

        public Item next() {
            if (numOfItemsProcessed == n) {
                throw new NoSuchElementException("The randomized queue is empty my dawg");
            }

            while (copyOfq[i] == null) {
                i++;
            }
            Item item = copyOfq[i];
            numOfItemsProcessed++;
            i++;

            if (numOfItemsProcessed == n) {
                lastItem = true;
            }

            return item;
        }
    }

    public static void main(String[] args) {
        // RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        // q.enqueue(1);
        // q.enqueue(2);
        // q.enqueue(3);
        // q.enqueue(4);
        // q.enqueue(5);
        // q.enqueue(6);
        // q.enqueue(7);
        // q.dequeue();
        // for (Integer i : q) {
        //     System.out.println(i);
        // }
        // q.dequeue();
        // q.dequeue();
        // q.dequeue();
        // q.dequeue();
        // q.dequeue();
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
