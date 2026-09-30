import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class IntArrayTests {
    @Test
    public void testEquals() {
        IntArray first = new IntArray(new int[]{10, 20, 30}, 3);
        IntArray second = new IntArray(new int[]{10, 20, 30}, 3);

        assertTrue(first.equals(second));
    }

    @Test
    public void testEqualElts() {
        IntArray first = new IntArray(new int[]{10, 20, 30}, 3);
        IntArray second = new IntArray(new int[]{10, 20, 30, 0, 0}, 3);

        assertTrue(first.equalElts(second));
    }

    @Test
    public void testEmpty() {
        IntArray original = new IntArray(new int[]{10, 20, 30}, 3);

        IntArray empty = original.empty();

        assertEquals(0, empty.length());
    }

    @Test
    public void testLength() {
        IntArray a = new IntArray(new int[]{10, 20, 30, 0, 0}, 3);

        assertEquals(3, a.length());
    }

    @Test
    public void testGet() {
        IntArray a = new IntArray(new int[]{10, 20, 30}, 3);

        assertEquals(30, a.get(2));
    }

    @Test
    public void testSet() {
        IntArray a = new IntArray(new int[]{10, 20, 30}, 3);

        a.set(1, 70);

        assertEquals(70, a.get(1));
        assertEquals(3, a.length());
    }

    @Test
    public void testInsert() {
        IntArray a = new IntArray(new int[]{10, 20, 30, 0}, 3);

        a.insert(1, 70);

        assertEquals(4, a.length());
        assertEquals(10, a.get(0));
        assertEquals(70, a.get(1));
        assertEquals(20, a.get(2));
        assertEquals(30, a.get(3));
    }

    @Test
    public void testAddToEnd() {
        IntArray a = new IntArray(new int[]{10, 20, 30}, 3);

        a.addToEnd(40);

        assertEquals(4, a.length());
        assertEquals(40, a.get(3));
    }

    @Test
    public void testAddToStart() {
        IntArray a = new IntArray(new int[]{10, 20, 30}, 3);

        a.addToStart(40);

        assertEquals(4, a.length());
        assertEquals(40, a.get(0));
        assertEquals(10, a.get(1));
        assertEquals(20, a.get(2));
        assertEquals(30, a.get(3));
    }

    @Test
    public void testRemove() {
        IntArray a = new IntArray(new int[]{10, 20, 30}, 3);

        a.remove(1);

        assertEquals(2, a.length());
        assertEquals(10, a.get(0));
        assertEquals(30, a.get(1));
    }

}
