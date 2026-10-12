import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class AStackTests {

    // Helper method to create a stack with given values
    private AStack makeStack(String... values) {
        return new AStack(new ArrayList<>(Arrays.asList(values)));
    }

    // ---------- emptyStack tests ----------

    @Test
    public void testEmptyStackIsEmpty() {
        AStack stack = AStack.emptyStack();

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    public void testEmptyStackCreatesIndependentStacks() {
        AStack first = AStack.emptyStack();
        AStack second = AStack.emptyStack();

        first.push("Apple");

        assertEquals(1, first.size());
        assertEquals(0, second.size());
        assertFalse(first.equals(second));
    }

    // ---------- push tests ----------

    @Test
    public void testPushOneElement() {
        AStack stack = AStack.emptyStack();

        stack.push("Apple");

        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
        assertEquals("Apple", stack.peek());
    }

    @Test
    public void testPushMultipleElements() {
        AStack stack = AStack.emptyStack();

        stack.push("Apple");
        stack.push("Banana");
        stack.push("Cherry");

        assertEquals(3, stack.size());
        assertEquals("Cherry", stack.peek());
    }

    @Test
    public void testPushNullValue() {
        AStack stack = AStack.emptyStack();

        stack.push(null);

        assertEquals(1, stack.size());
        assertNull(stack.peek());
        assertNull(stack.pop());
        assertTrue(stack.isEmpty());
    }

    // ---------- pop tests ----------

    @Test
    public void testPopReturnsTopElement() {
        AStack stack = makeStack("Apple", "Banana", "Cherry");

        assertEquals("Cherry", stack.pop());
        assertEquals(2, stack.size());
        assertEquals("Banana", stack.peek());
    }

    @Test
    public void testPopLastElement() {
        AStack stack = makeStack("Apple");

        assertEquals("Apple", stack.pop());
        assertEquals(0, stack.size());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testPopEmptyStackThrowsException() {
        AStack stack = AStack.emptyStack();

        assertThrows(
                NoSuchElementException.class,
                () -> stack.pop()
        );
    }

    @Test
    public void testPopUntilEmptyThenThrows() {
        AStack stack = makeStack("Apple", "Banana");

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
        AStack stack = makeStack("Apple", "Banana");

        assertEquals("Banana", stack.peek());
        assertEquals("Banana", stack.peek());
        assertEquals(2, stack.size());
    }

    @Test
    public void testPeekSingleElement() {
        AStack stack = makeStack("Apple");

        assertEquals("Apple", stack.peek());
        assertEquals(1, stack.size());
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testPeekEmptyStackThrowsException() {
        AStack stack = AStack.emptyStack();

        assertThrows(
                NoSuchElementException.class,
                () -> stack.peek()
        );
    }

    // ---------- size tests ----------

    @Test
    public void testSizeChangesAfterPushAndPop() {
        AStack stack = AStack.emptyStack();

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
        AStack stack = makeStack("Apple", "Banana", "Cherry");

        assertEquals(3, stack.size());
    }

    // ---------- isEmpty tests ----------

    @Test
    public void testIsEmptyBeforeAndAfterOperations() {
        AStack stack = AStack.emptyStack();

        assertTrue(stack.isEmpty());

        stack.push("Apple");
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testIsEmptyForConstructedStacks() {
        AStack empty = makeStack();
        AStack nonEmpty = makeStack("Apple");

        assertTrue(empty.isEmpty());
        assertFalse(nonEmpty.isEmpty());
    }

    // ---------- constructor tests ----------

    @Test
    public void testConstructorCopiesInputList() {
        ArrayList<String> values =
                new ArrayList<>(Arrays.asList("Apple", "Banana"));

        AStack stack = new AStack(values);

        values.add("Cherry");

        assertEquals(2, stack.size());
        assertEquals("Banana", stack.peek());
    }

    @Test
    public void testConstructorWithEmptyList() {
        AStack stack = new AStack(new ArrayList<String>());

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    // ---------- equals tests ----------

    @Test
    public void testEqualsSameObject() {
        AStack stack = makeStack("Apple");

        assertEquals(stack, stack);
    }

    @Test
    public void testEqualsEqualStacks() {
        AStack first = makeStack("Apple", "Banana");
        AStack second = makeStack("Apple", "Banana");

        assertEquals(first, second);
        assertEquals(second, first);
    }

    @Test
    public void testEqualsDifferentContents() {
        AStack first = makeStack("Apple");
        AStack second = makeStack("Banana");

        assertNotEquals(first, second);
    }

    @Test
    public void testEqualsDifferentSizes() {
        AStack first = makeStack("Apple");
        AStack second = makeStack("Apple", "Banana");

        assertNotEquals(first, second);
    }

    @Test
    public void testEqualsDifferentOrder() {
        AStack first = makeStack("Apple", "Banana");
        AStack second = makeStack("Banana", "Apple");

        assertNotEquals(first, second);
    }

    @Test
    public void testEqualsNull() {
        AStack stack = makeStack("Apple");

        assertNotEquals(null, stack);
    }

    @Test
    public void testEqualsDifferentType() {
        AStack stack = makeStack("Apple");

        assertNotEquals("Apple", stack);
    }

    @Test
    public void testEqualsTwoEmptyStacks() {
        AStack first = AStack.emptyStack();
        AStack second = AStack.emptyStack();

        assertEquals(first, second);
    }

    @Test
    public void testEqualsStacksContainingNull() {
        AStack first = makeStack((String) null);
        AStack second = makeStack((String) null);
        AStack third = makeStack("Apple");

        assertEquals(first, second);
        assertNotEquals(first, third);
    }
}