package PART2;

import java.util.NoSuchElementException;

public class ArrayStack<T> {
    private Object[] data;
    private int size;

    public ArrayStack() {
        data = new Object[10];
        size = 0;
    }

    /*
     * Purpose:
     * Adds a value to the top of the stack.
     *
     * Example:
     * If the stack contains [10, 20], then push(30)
     * changes the stack to [10, 20, 30].
     */
    public void push(T value) {
        if (size == data.length) {
            Object[] newData = new Object[data.length * 2];

            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }

            data = newData;
        }

        data[size] = value;
        size++;
    }

    /*
     * Purpose:
     * Removes value from the top of the stack and returns it.
     *
     * Example:
     * If the stack contains [10, 20], then pop()
     * changes the stack to [10].
     */
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        T lastEl = (T) data[size-1];
        size--;
        return lastEl;
    }

    /*
     * Purpose:
     * Returns value from the top of the stack without removing it.
     *
     * Example:
     * If the stack contains [10, 20], then peek()
     * returns 20 while the stack has [10, 20].
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        return (T) data[size-1];
    }

    /*
     * Purpose:
     * Returns boolean indicating whether stack is empty
     *
     * Example:
     * If the stack contains [10, 20], then isEmpty()
     * returns false.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /*
     * Purpose:
     * Returns integer indicating size of stack
     *
     * Example:
     * If the stack contains [10, 20], then size()
     * returns 2.
     */
    public int size() {
        return size;
    }

}
