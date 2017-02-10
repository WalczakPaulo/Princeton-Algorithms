import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Paul on 2017-02-10.
 */
class RandomizedQueueTest {

    RandomizedQueue<String> randomizedQueue;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        randomizedQueue = new RandomizedQueue<String>();
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertTrue(randomizedQueue.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(0,randomizedQueue.size());
    }

    @Test
    public void shouldDoubleInLengthIfArrIsFull() {
        String item = "newItem";
        randomizedQueue.enqueue(item);
        assertEquals(1, randomizedQueue.size());
        assertEquals(1, randomizedQueue.queueLength());
        item = "newItem2";
        randomizedQueue.enqueue(item);
        assertEquals(2, randomizedQueue.size());
        assertEquals(2, randomizedQueue.queueLength());
    }
}