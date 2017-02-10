import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;
/**
 * Created by Paul007 on 2017-02-10.
 */

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] itemsArray;
    private int numberOfItems;

    public RandomizedQueue() {
        this.itemsArray = (Item[]) new Object[0];
        this.numberOfItems = 0;
    }                 // construct an empty randomized queue


    public boolean isEmpty() {
        return numberOfItems==0;
    }               // is the queue empty?
    public int size() {
        return numberOfItems;
    }                     // return the number of items on the queue
    public int queueLength() { return itemsArray.length; }
    public Item getItem(int index) {
        return this.itemsArray[index];
    }
    public int queueSize() {
        return itemsArray.length;
    }
    public void enqueue(Item item)  {
        if(item == null)
            throw new NullPointerException("Attempted to add null item, naughty client!");
        final int queueSize = queueSize();
        if(isEmpty())
            resize(1);
        else if (numberOfItems >= queueSize)
                resize(2*queueSize);
        itemsArray[numberOfItems++] = item;

    }  // add the item
    public Item dequeue() {
        if(isEmpty())
            throw new NoSuchElementException("Randomized queue is already empty!");
        int randomNumber = StdRandom.uniform(numberOfItems);
        Item randomItem = getItem(randomNumber);
        if(randomNumber != numberOfItems - 1) {
            itemsArray[randomNumber] = itemsArray[--numberOfItems];
            itemsArray[numberOfItems] = null;
        }
        else
            itemsArray[--numberOfItems] = null; // make java garbage collector work work work work
        final int queueSize = itemsArray.length;
        if(numberOfItems > 0 && queueSize == 4 * numberOfItems)
            resize(queueSize/2);
        return randomItem;
    }   // remove and return a random item

    public void resize(int newSize) {
        Item[] resizedQueue = (Item[]) new Object[newSize];
        System.arraycopy(itemsArray, 0, resizedQueue, 0, numberOfItems);
        itemsArray = resizedQueue;
        resizedQueue = null;
    }
    public Item sample() {
        if(isEmpty())
            throw new NoSuchElementException("Randomized queue is already empty!");
        return getItem(StdRandom.uniform(numberOfItems));
    }  // return (but do not remove) a random item

    public void showItems() {
        RandomizedQueueIterator<Item> iter = (RandomizedQueueIterator<Item>) this.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println();
    }

    private static class RandomizedQueueIterator<Item> implements Iterator<Item> {
        // why not use already implemented RandomizedQueue as internal data structure...
        RandomizedQueue <Item> iteratorQueue = new RandomizedQueue<Item>();

        public RandomizedQueueIterator(Object []queueItems) {
            for (Object item : queueItems) {
                if (item == null)
                    break;
                iteratorQueue.enqueue((Item) item);
            } // this one takes linear time construct
        }
        @Override
        public boolean hasNext(){
            return !iteratorQueue.isEmpty();
        }

        @Override
        public Item next() {
            if(!hasNext())
                throw new NoSuchElementException("No Items in Randomized Queue!");
            return iteratorQueue.dequeue(); // I assume that we can use dequeue method here, which takes amortized constant time to perform
                                            // dequeue() method is used, because if sample() was used it could return duplicates
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove method not implemented");
        }
    }
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator(itemsArray);
    }  // return an independent iterator over items in random order
    public static void main(String[] args) {
            RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<Integer>();
            randomizedQueue.enqueue(1);
            System.out.println(randomizedQueue.sample() + "\n");
            randomizedQueue.enqueue(2);
            randomizedQueue.enqueue(3);
            randomizedQueue.showItems();
            System.out.println(randomizedQueue.dequeue());
            //randomizedQueue.showItems();
    }   // unit testing (optional)
}