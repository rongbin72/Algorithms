import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DequeTest {
    private Deque<String> deque;

    public DequeTest() {
        deque = new Deque<>();
    }

    @org.junit.jupiter.api.Test
    void iter() {
        Iterator<String> iter = deque.iterator();
        assertFalse(iter.hasNext());
        deque.addFirst("Second");
        deque.addFirst("First");
        deque.addLast("Last");
        assertEquals(iter.next(), "First");
        assertEquals(iter.next(), "Second");
        assertEquals(iter.next(), "Last");
        assertFalse(iter.hasNext());
        deque = new Deque<>();
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertTrue(deque.isEmpty());
        deque.addFirst("Second");
        assertFalse(deque.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void addFirst() {
        deque.addFirst("First");
        assertFalse(deque.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void addLast() {
        deque.addLast("Last");
        assertFalse(deque.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void removeFirst() {
        String str;
        str = deque.removeFirst();
        assertEquals(str, "First");
    }

    @org.junit.jupiter.api.Test
    void removeLast() {
        String str;
        str = deque.removeLast();
        assertEquals(str, "Last");
    }

    @org.junit.jupiter.api.Test
    void main() {
        assertFalse(deque.isEmpty());
        assertEquals(deque.removeFirst(), "Second");
        assertTrue(deque.isEmpty());
    }
}