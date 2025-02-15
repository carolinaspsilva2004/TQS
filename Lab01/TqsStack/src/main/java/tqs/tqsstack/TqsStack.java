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
        return collection.removeFirst(); 
       }

       public T popTopN(int n) {
        if (n <= 0 || n > collection.size()) {
            throw new IllegalArgumentException("Valor de n inválido");
        }
        T top = null;
        for (int i = 0; i < n; i++) {
            top = collection.removeFirst();
        }
        return top;
    }
    

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
