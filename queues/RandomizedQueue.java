import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int capacity;
    private int n;

    public RandomizedQueue() {
        n = 0;
        capacity = 10;
        arr = (Item[]) new Object[capacity];

    }

    private void resize(int size) {
        assert size >= n;
        arr = Arrays.copyOf(arr, size);
        this.capacity = size;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (n == capacity)
            resize(capacity * 2);
        arr[n] = item;
        n++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int random = StdRandom.uniform(n);
        Item item = arr[random];
        arr[random] = arr[n - 1];
        arr[n - 1] = null;

        if (n > 0 && n == capacity / 4)
            resize(capacity / 2);
        n--;

        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        int random = StdRandom.uniform(n);

        return arr[random];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] array = arr;
        private int num = n;

        @Override
        public boolean hasNext() {
            return num > 0;
        }

        @Override
        public Item next() {
            if (num == 0) throw new NoSuchElementException();

            int random = StdRandom.uniform(num);
            Item item = array[random];
            array[random] = array[num - 1];
            array[num - 1] = null;
            num--;
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

