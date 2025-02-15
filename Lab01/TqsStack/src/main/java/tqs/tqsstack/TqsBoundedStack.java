package tqs.tqsstack;


import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsBoundedStack<T> {
    private LinkedList<T> collection = new LinkedList<>();
    private final int capacity;

    public TqsBoundedStack(int capacity) {
        this.capacity = capacity;
    }

    public void push(T item) {
        if (collection.size() >= capacity) {
            throw new IllegalStateException("A pilha atingiu sua capacidade máxima!");
        }
        collection.addFirst(item);
    }

    public T pop() {
        if (collection.isEmpty()) {
            throw new NoSuchElementException("A pilha está vazia!");
        }
        return collection.removeFirst();
    }

    public int size() {
        return collection.size();
    }
}
