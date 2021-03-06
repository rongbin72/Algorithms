import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomizedQueueTest {
    @Test
    void size() {
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();
        assertTrue(rQueue.isEmpty());
        assertEquals(rQueue.size(), 0);
        for (int n = 0; n < 100; n++) {
            for (int i = 0; i < 100; i++) {
                rQueue.enqueue(i);
            }
            for (int i = 0; i < 100; i++) {
                rQueue.sample();
                assertEquals(rQueue.size(), 100);
            }
            for (int i = 0; i < 100; i++) {
                rQueue.dequeue();
                assertEquals(rQueue.size(), 99 - i);
            }
            assertTrue(rQueue.isEmpty());
            assertEquals(rQueue.size(), 0);
        }
    }
}