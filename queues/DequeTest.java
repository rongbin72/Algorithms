import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class DequeTest {
    private Deque<String> deque;

    public DequeTest() {
        deque = new Deque<>();
    }

    @org.junit.jupiter.api.Test
    void iter() {
        deque.addFirst("Second");
        deque.addFirst("First");
        deque.addLast("Last");
        Iterator<String> iter = deque.iterator();
        assertTrue(iter.hasNext());
        assertEquals(iter.next(), "First");
        assertEquals(iter.next(), "Second");
        assertEquals(iter.next(), "Last");
        assertFalse(iter.hasNext());
    }

    @org.junit.jupiter.api.Test
    void addRemoveFirst() {
        deque = new Deque<>();
        assertTrue(deque.isEmpty());
        deque.addFirst("3");
        deque.addFirst("2");
        deque.addFirst("1");
        assertFalse(deque.isEmpty());
        assertEquals(deque.removeFirst(), "1");
        assertEquals(deque.removeFirst(), "2");
        assertEquals(deque.removeFirst(), "3");
        assertTrue(deque.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void addRemoveLast() {
        deque = new Deque<>();
        assertTrue(deque.isEmpty());
        deque.addLast("3");
        deque.addLast("2");
        deque.addLast("1");
        assertFalse(deque.isEmpty());
        assertEquals(deque.removeLast(), "1");
        assertEquals(deque.removeLast(), "2");
        assertEquals(deque.removeLast(), "3");
        assertTrue(deque.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void addFirstRemoveLast() {
        deque = new Deque<>();
        assertTrue(deque.isEmpty());
        deque.addFirst("3");
        deque.addFirst("2");
        deque.addFirst("1");
        assertFalse(deque.isEmpty());
        assertEquals(deque.removeLast(), "3");
        assertEquals(deque.removeLast(), "2");
        assertEquals(deque.removeLast(), "1");
        assertTrue(deque.isEmpty());
    }

    void addLastRemoveFirst() {
        deque = new Deque<>();
        assertTrue(deque.isEmpty());
        deque.addLast("3");
        deque.addLast("2");
        deque.addLast("1");
        assertFalse(deque.isEmpty());
        assertEquals(deque.removeFirst(), "3");
        assertEquals(deque.removeFirst(), "2");
        assertEquals(deque.removeFirst(), "1");
        assertTrue(deque.isEmpty());
    }

}