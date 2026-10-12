import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class LLStackTests {

    // Helper for making stack
    private LLStack makeStack(String... values) {
        LLStack stack = LLStack.emptyStack();

        for (String value : values) {
            stack.push(value);
        }

        return stack;
    }

    // ---------- emptyStack tests ----------

    @Test
    public void testEmptyStack() {
        LLStack stack = LLStack.emptyStack();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    public void testEmptyStacksAreIndependent() {
        LLStack first = LLStack.emptyStack();
        LLStack second = LLStack.emptyStack();

        first.push("Apple");

        assertEquals(1, first.size());
        assertEquals(0, second.size());
        assertNotEquals(first, second);
    }

    // ---------- push tests ----------

    @Test
    public void testPushOneElement() {
        LLStack stack = LLStack.emptyStack();

        stack.push("Apple");

        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
        assertEquals("Apple", stack.peek());
    }

    @Test
    public void testPushMultipleElements() {
        LLStack stack = LLStack.emptyStack();

        stack.push("Apple");
        stack.push("Banana");
        stack.push("Cherry");

        assertEquals(3, stack.size());
        assertEquals("Cherry", stack.peek());
    }

    @Test
    public void testPushNull() {
        LLStack stack = LLStack.emptyStack();

        stack.push(null);

        assertEquals(1, stack.size());
        assertNull(stack.peek());
        assertNull(stack.pop());
        assertTrue(stack.isEmpty());
    }

    // ---------- pop tests ----------

    @Test
    public void testPopReturnsTopElement() {
        LLStack stack = makeStack("Apple", "Banana", "Cherry");

        assertEquals("Cherry", stack.pop());
        assertEquals(2, stack.size());
        assertEquals("Banana", stack.peek());
    }

    @Test
    public void testPopLastElement() {
        LLStack stack = makeStack("Apple");

        assertEquals("Apple", stack.pop());
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testPopEmptyStackThrowsException() {
        LLStack stack = LLStack.emptyStack();

        assertThrows(
                NoSuchElementException.class,
                () -> stack.pop()
        );
    }

    @Test
    public void testPopUntilEmpty() {
        LLStack stack = makeStack("Apple", "Banana");

        assertEquals("Banana", stack.pop());
        assertEquals("Apple", stack.pop());
        assertTrue(stack.isEmpty());

        assertThrows(
                NoSuchElementException.class,
                () -> stack.pop()
        );
    }

    // ---------- peek tests ----------

    @Test
    public void testPeekDoesNotRemoveElement() {
        LLStack stack = makeStack("Apple", "Banana");

        assertEquals("Banana", stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    public void testPeekSingleElement() {
        LLStack stack = makeStack("Apple");

        assertEquals("Apple", stack.peek());
        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testPeekEmptyStackThrowsException() {
        LLStack stack = LLStack.emptyStack();

        assertThrows(
                NoSuchElementException.class,
                () -> stack.peek()
        );
    }

    // ---------- size tests ----------

    @Test
    public void testSizeChangesAfterPushAndPop() {
        LLStack stack = LLStack.emptyStack();

        assertEquals(0, stack.size());

        stack.push("Apple");
        assertEquals(1, stack.size());

        stack.push("Banana");
        assertEquals(2, stack.size());

        stack.pop();
        assertEquals(1, stack.size());

        stack.pop();
        assertEquals(0, stack.size());
    }

    @Test
    public void testSizeOfConstructedStack() {
        LLStack stack = makeStack("Apple", "Banana", "Cherry");

        assertEquals(3, stack.size());
    }

    // ---------- isEmpty tests ----------

    @Test
    public void testIsEmptyBeforeAndAfterOperations() {
        LLStack stack = LLStack.emptyStack();

        assertTrue(stack.isEmpty());

        stack.push("Apple");
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testIsEmptyForConstructedStacks() {
        LLStack empty = makeStack();
        LLStack nonEmpty = makeStack("Apple");

        assertTrue(empty.isEmpty());
        assertFalse(nonEmpty.isEmpty());
    }

    // ---------- constructor tests ----------

    @Test
    public void testConstructorCopiesInputList() {
        LinkedList<String> values =
                new LinkedList<>(Arrays.asList("Apple", "Banana"));

        LLStack stack = new LLStack(values);

        values.add("Cherry");

        assertEquals(2, stack.size());
        assertEquals("Apple", stack.peek());
    }

    @Test
    public void testConstructorWithEmptyList() {
        LLStack stack = new LLStack(new LinkedList<String>());

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    // ---------- equals tests ----------

    @Test
    public void testEqualsSameObject() {
        LLStack stack = makeStack("Apple");

        assertEquals(stack, stack);
    }

    @Test
    public void testEqualsEqualStacks() {
        LLStack first = makeStack("Apple", "Banana");
        LLStack second = makeStack("Apple", "Banana");

        assertEquals(first, second);
        assertEquals(second, first);
    }

    @Test
    public void testEqualsDifferentContents() {
        LLStack first = makeStack("Apple");
        LLStack second = makeStack("Banana");

        assertNotEquals(first, second);
    }

    @Test
    public void testEqualsDifferentSizes() {
        LLStack first = makeStack("Apple");
        LLStack second = makeStack("Apple", "Banana");

        assertNotEquals(first, second);
    }

    @Test
    public void testEqualsDifferentOrder() {
        LLStack first = makeStack("Apple", "Banana");
        LLStack second = makeStack("Banana", "Apple");

        assertNotEquals(first, second);
    }

    @Test
    public void testEqualsNull() {
        LLStack stack = makeStack("Apple");

        assertNotEquals(null, stack);
    }

    @Test
    public void testEqualsDifferentType() {
        LLStack stack = makeStack("Apple");

        assertNotEquals("Apple", stack);
    }

    @Test
    public void testEqualsTwoEmptyStacks() {
        LLStack first = LLStack.emptyStack();
        LLStack second = LLStack.emptyStack();

        assertEquals(first, second);
    }

    @Test
    public void testEqualsStacksContainingNull() {
        LLStack first = makeStack((String) null);
        LLStack second = makeStack((String) null);
        LLStack third = makeStack("Apple");

        assertEquals(first, second);
        assertNotEquals(first, third);
    }
}