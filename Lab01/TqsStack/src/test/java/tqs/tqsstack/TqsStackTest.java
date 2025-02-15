package tqs.tqsstack;

import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.*;

public class TqsStackTest {

    @Test
    void testStackIsInitiallyEmpty() {
        TqsStack<Integer> stack = new TqsStack<>();
        assertTrue(stack.isEmpty(), "A pilha deve estar vazia inicialmente");
        assertEquals(0, stack.size(), "O tamanho inicial deve ser 0");
    }

    @Test
    void testPushIncreasesSize() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(10);
        assertFalse(stack.isEmpty(), "A pilha não deve estar vazia após um push");
        assertEquals(1, stack.size(), "O tamanho deve ser 1 após um push");
    }

    @Test
    void testPopReturnsLastPushedValue() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(5);
        stack.push(15);
        assertEquals(15, stack.pop(), "Pop deve retornar o último valor inserido");
        assertEquals(1, stack.size(), "O tamanho deve diminuir após um pop");
    }

    @Test
    void testPeekDoesNotRemoveElement() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(42);
        assertEquals(42, stack.peek(), "Peek deve retornar o último valor inserido");
        assertEquals(1, stack.size(), "Peek não deve remover elementos da pilha");
    }

    @Test
    void testPopOnEmptyStackThrowsException() {
        TqsStack<Integer> stack = new TqsStack<>();
        assertThrows(RuntimeException.class, stack::pop, "Pop em pilha vazia deve lançar exceção");
    }

    @Test
    void testStackIsEmptyOnConstruction() {
        TqsStack<Integer> stack = new TqsStack<>();
        assertTrue(stack.isEmpty(), "A pilha deve estar vazia ao ser criada");
    }

    @Test
    void testStackHasSizeZeroOnConstruction() {
        TqsStack<Integer> stack = new TqsStack<>();
        assertEquals(0, stack.size(), "O tamanho inicial da pilha deve ser 0");
    }


    @Test
    void testPushThenPop() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(20);
        assertEquals(20, stack.pop(), "Pop deve retornar o último valor inserido");
        assertTrue(stack.isEmpty(), "A pilha deve estar vazia após um push seguido de um pop");
    }

    @Test
    void testPushThenPeek() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(30);
        assertEquals(30, stack.peek(), "Peek deve retornar o último valor inserido");
        assertEquals(1, stack.size(), "O tamanho da pilha não deve mudar após um peek");
    }

    @Test
    void testMultiplePushThenMultiplePop() {
        TqsStack<Integer> stack = new TqsStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.isEmpty(), "A pilha deve estar vazia após remover todos os elementos");
    }

    @Test
    void testPopFromEmptyStackThrowsException() {
        TqsStack<Integer> stack = new TqsStack<>();
        assertThrows(NoSuchElementException.class, stack::pop, "Pop em pilha vazia deve lançar exceção");
    }

    @Test
    void testPeekIntoEmptyStackThrowsException() {
        TqsStack<Integer> stack = new TqsStack<>();
        assertThrows(NoSuchElementException.class, stack::peek, "Peek em pilha vazia deve lançar exceção");
    }

}
