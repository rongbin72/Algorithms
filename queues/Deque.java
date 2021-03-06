import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;
    private Node first;
    private Node last;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    public Deque() {
        first = null;
        last = null;
        n = 0;
        assert check();
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (isEmpty()) {
            first = new Node();
            first.item = item;
            first.prev = null;
            first.next = null;
            last = first;
        }
        else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            first.prev = null;
            oldFirst.prev = first;

        }
        n++;
        assert check();
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (isEmpty()) {
            last = new Node();
            last.item = item;
            last.prev = null;
            last.next = null;
            first = last;
        }
        else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.next = null;
            last.prev = oldLast;
            oldLast.next = last;
        }
        n++;
        assert check();
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = first.item;
        if (first.next == null) {
            first = last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }
        n--;
        assert check();
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = last.item;
        if (last.prev == null) {
            last = first = null;
        }
        else {
            last = last.prev;
            last.next = null;
        }
        n--;
        assert check();
        return item;
    }


    private boolean check() {
        // check a few properties of instance variable 'first'
        if (n < 0) {
            return false;
        }
        if (n == 0) {
            if (first != null) return false;
        }
        else if (n == 1) {
            if (first == null) return false;
            if (first.next != null) return false;
        }
        else {
            if (first == null) return false;
            if (first.next == null) return false;
        }

        // check internal consistency of instance variable n
        int numberOfNodes = 0;
        for (Node x = first; x != null && numberOfNodes <= n; x = x.next) {
            numberOfNodes++;
        }
        if (numberOfNodes != n) return false;

        return true;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }
}
