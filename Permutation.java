/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        final int k;
        if (args.length == 0) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        try {
            k = Integer.parseInt(args[0]);
        }
        catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Must input an integer");
        }

        RandomizedQueue<String> q = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            q.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            System.out.println(q.dequeue());
        }

    }
}
