import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Paul on 2017-02-09.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node <Item> first;
    private Node <Item> last;
    private int numberOfItems;

    public Deque() {
        first = null;
        last = null;
        numberOfItems = 0;
    }

    private static class Node <Item> {
        private Item item;
        private Node <Item> next;
        private Node <Item> previous;

        public Node(Item item, Node <Item> next, Node <Item> previous ){
            this.item = item;
            this.next = next;
            this.previous = previous;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            this.current = first;
        }
        @Override
        public void remove(){
            throw new UnsupportedOperationException("Remove method unsupported!");
        }
        @Override
        public boolean hasNext() {
            return current != null;
        }
        @Override
        public Item next(){
            if(!hasNext())
                throw new NoSuchElementException("Emptye deque!");
            else{
                Node <Item> tempNode = current;
                current = current.next;
                return tempNode.item;
            }
        }
    }

    public void showItems(){
        System.out.println();
        for(Item item: this)
            System.out.println(item);
        System.out.println();
    }
    public boolean isEmpty() {
        return numberOfItems==0;
    } // is the deque empty?

    public int size() {
        return numberOfItems;
    } // return the number of items on the deque

    public void addFirst(Item item) {
        checkIfNullItem(item);
        if(this.isEmpty()){
            Node <Item> newNode = new Node <Item> (item, null, null);
            this.first = newNode;
            this.last = newNode;
        } else {
            Node <Item> newFirst = new Node<Item> (item, first, null);
            this.first = newFirst;
        }

        numberOfItems++;
    } // add the item to the front

    public void addLast(Item item){
        checkIfNullItem(item);
        if(this.isEmpty()){
            Node <Item> newNode = new Node <Item> (item, null, null);
            this.first = newNode;
            this.last = newNode;
        } else {
            Node<Item> newLast = new Node<Item>(item, null, last);
            last.next = newLast;
            this.last = newLast;
        }

        numberOfItems++;
    } // add the item to the end

    public Item removeFirst() {
      checkIfRemovePossible();
      Node <Item> firstNode = first;
      first = first.next;
      if(first == null)
          last = null;
      else
          first.previous = null;
      numberOfItems--;
      return firstNode.item;
    } // remove and return the item from the front

    public Item removeLast() {
        checkIfRemovePossible();
        Node <Item> lastNode = last;
        last = last.previous;
        if(last == null)
            first = null;
        else
            last.next= null;
        numberOfItems--;
        return lastNode.item;
    } // remove and return the item from the end

    public Iterator<Item> iterator() {
      return new DequeIterator();
    } // return an iterator over items in order from front to end

    public void checkIfNullItem(Item item){
        if(item == null)
            throw new NullPointerException("You tried to add null item, you naughty client!");
    }

    public void checkIfRemovePossible(){
        if(this.isEmpty())
            throw new NoSuchElementException("Deque is already empty!");
    }
    public static void main(String[] args){
        /**
         * test purposes
         */
        Deque <Integer> testDeque = new Deque <Integer>();
        testDeque.addFirst(1);
        testDeque.addFirst(2);
        testDeque.addFirst(3);
        testDeque.addFirst(4);
        testDeque.addFirst(5);
        testDeque.showItems();
        testDeque.addLast(6);
        testDeque.addLast(7);
        System.out.println(testDeque.size());
        testDeque.showItems();
        System.out.println(testDeque.removeFirst());
        System.out.println(testDeque.removeLast());
        testDeque.showItems();
    }   // unit testing (optional)
}