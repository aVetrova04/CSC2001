import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;

public class AStack {
    private ArrayList<String> elements;

    // Constructor that accepts values for all fields
    public AStack(ArrayList<String> elements) {
        this.elements = new ArrayList<>(elements);
    }

    // Returns an empty stack
    public static AStack emptyStack() {
        return new AStack(new ArrayList<String>());
    }

    // Adds an element to the top of the stack
    public void push(String value) {
        elements.add(value);
    }

    // Removes and returns the top element
    public String pop() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Cannot pop an empty stack."
            );
        }

        return elements.remove(elements.size() - 1);
    }

    // Returns the top element without removing it
    public String peek() {
        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Cannot peek at an empty stack."
            );
        }

        return elements.get(elements.size() - 1);
    }

    // Returns the number of elements
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

        if (!(obj instanceof AStack)) {
            return false;
        }

        AStack other = (AStack) obj;
        return Objects.equals(this.elements, other.elements);
    }
}