package PART1;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class ArrayStackTest {

    @Test
    public void testPushOneElement() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        stack.push(10);

        assertEquals(1, stack.size());
        assertEquals(10, stack.peek());
    }

    @Test
    public void testPushMultipleElements() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(3, stack.size());
        assertEquals(30, stack.peek());
    }

    @Test
    public void testPushPastInitialCapacity() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        for (int i = 1; i <= 11; i++) {
            stack.push(i);
        }

        assertEquals(11, stack.size());
        assertEquals(11, stack.peek());
    }

    @Test
    public void testPopTopElement() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        stack.push(10);
        stack.push(20);

        assertEquals(20, stack.pop());
        assertEquals(1, stack.size());
    }

    @Test
    public void testPopMultipleElements() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(1, stack.size());
    }

    @Test
    public void testPopEmptyStackThrowsException() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        assertThrows(
                NoSuchElementException.class,
                () -> stack.pop()
        );
    }

    @Test
    public void testPeekTopElement() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        stack.push(10);
        stack.push(20);

        assertEquals(20, stack.peek());
    }

    @Test
    public void testPeekDoesNotRemoveElement() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        stack.push(10);
        stack.push(20);

        assertEquals(20, stack.peek());
        assertEquals(2, stack.size());
        assertEquals(20, stack.peek());
    }

    @Test
    public void testPeekEmptyStackThrowsException() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        assertThrows(
                NoSuchElementException.class,
                () -> stack.peek()
        );
    }

    @Test
    public void testIsEmptyNewStack() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        assertTrue(stack.isEmpty());
    }

    @Test
    public void testIsEmptyAfterPush() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        stack.push(10);

        assertFalse(stack.isEmpty());
    }

    @Test
    public void testIsEmptyAfterRemovingAllElements() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        stack.push(10);
        stack.pop();

        assertTrue(stack.isEmpty());
    }

    @Test
    public void testSizeEmptyStack() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        assertEquals(0, stack.size());
    }

    @Test
    public void testSizeAfterPushes() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(3, stack.size());
    }

    @Test
    public void testSizeAfterPop() {
        ArrayStack<Integer> stack = new ArrayStack<>();

        stack.push(10);
        stack.push(20);

        stack.pop();

        assertEquals(1, stack.size());
    }
}
