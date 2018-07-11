import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DequeTest {
    private Deque<Integer> deque;


    @org.junit.jupiter.api.Test
    void iterator() {
        deque = new Deque<>();
        for (int i = 0; i < 100; i++)
            deque.addFirst(i);

        Iterator<Integer> iter = deque.iterator();
        assertTrue(iter.hasNext());
        for (int i = 0; i < 100; i++) {
            Iterator<Integer> iter2 = deque.iterator();
            assertEquals((int) iter.next(), 99 - i);
            for (int j = 0; j < 100; j++) {
                assertEquals((int) iter2.next(), 99 - j);
            }
        }

        assertFalse(iter.hasNext());
    }

    @org.junit.jupiter.api.Test
    void addRemoveFirst() {
        deque = new Deque<>();
        for (int n = 0; n < 100; n++) {
            assertTrue(deque.isEmpty());
            for (int i = 0; i < 100; i++)
                deque.addFirst(i);

            assertFalse(deque.isEmpty());
            for (int i = 0; i < 100; i++)
                assertEquals((int) deque.removeFirst(), 99 - i);

            assertTrue(deque.isEmpty());
        }
    }

    @org.junit.jupiter.api.Test
    void addRemoveLast() {
        deque = new Deque<>();
        for (int n = 0; n < 100; n++) {
            assertTrue(deque.isEmpty());
            for (int i = 0; i < 100; i++)
                deque.addLast(i);

            assertFalse(deque.isEmpty());
            for (int i = 0; i < 100; i++)
                assertEquals((int) deque.removeLast(), 99 - i);

            assertTrue(deque.isEmpty());
        }
    }

    @org.junit.jupiter.api.Test
    void addFirstRemoveLast() {
        deque = new Deque<>();
        for (int n = 0; n < 100; n++) {
            assertTrue(deque.isEmpty());
            for (int i = 0; i < 100; i++)
                deque.addFirst(i);

            assertFalse(deque.isEmpty());
            for (int i = 0; i < 100; i++)
                assertEquals((int)deque.removeLast(), i);

            assertTrue(deque.isEmpty());
        }
    }

    void addLastRemoveFirst() {
        deque = new Deque<>();
        for (int n = 0; n < 100; n++) {
            assertTrue(deque.isEmpty());
            for (int i = 0; i < 100; i++)
                deque.addLast(i);

            assertFalse(deque.isEmpty());
            for (int i = 0; i < 100; i++)
                assertEquals((int) deque.removeFirst(), i);

            assertTrue(deque.isEmpty());
        }
    }

}