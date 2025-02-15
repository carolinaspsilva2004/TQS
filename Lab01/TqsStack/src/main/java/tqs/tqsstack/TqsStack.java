package tqs.tqsstack;


import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {
    private LinkedList<T> collection = new LinkedList<>();

    public void push(T item) {
        collection.addFirst(item);    }

    public T pop() {
        if (collection.isEmpty()) {
            throw new NoSuchElementException("A pilha está vazia!");
        }
        return collection.removeFirst();    }

    public T peek() {
        if (collection.isEmpty()) {
            throw new NoSuchElementException("A pilha está vazia!");
        }
        return collection.getFirst();    }

    public int size() {
        return collection.size();    }

    public boolean isEmpty() {
        return collection.isEmpty();    }
}
