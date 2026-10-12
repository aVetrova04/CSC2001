import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LLStack {
    private LinkedList<String> elements;

    // Constructor that accepts values for all fields
    public LLStack(LinkedList<String> elements) {
        this.elements = new LinkedList<>(elements);
    }

    // Returns an empty stack
    public static LLStack emptyStack() {
        return new LLStack(new LinkedList<String>());
    }

    // Adds an element to the top of the stack
    public void push(String value) {
        elements.addFirst(value);
    }

    // Removes and returns the top element
    public String pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot pop an empty stack.");
        }

        return elements.removeFirst();
    }

    // Returns the top element without removing it
    public String peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Cannot peek at an empty stack.");
        }

        return elements.getFirst();
    }

    // Returns the number of elements in the stack
    public int size() {
        return elements.size();
    }

    // Returns true if the stack is empty
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    // Tests value equality
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof LLStack)) {
            return false;
        }

        LLStack other = (LLStack) obj;
        return Objects.equals(this.elements, other.elements);
    }
}