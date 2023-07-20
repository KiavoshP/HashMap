import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

///**
// * This is a basic set of unit tests for ExternalChainingHashMap.
// *
// * Passing these tests doesn't guarantee any grade on these assignments. These
// * student JUnits that we provide should be thought of as a sanity check to
// * help you get started on the homework and writing JUnits in general.
// *
// * We highly encourage you to write your own set of JUnits for each homework
// * to cover edge cases you can think of for each data structure. Your code must
// * work correctly and efficiently in all cases, which is why it's important
// * to write comprehensive tests to cover as many cases as possible.
// *
// * @author CS 1332 TAs
// * @version 1.0
// */
public class ExternalChainingHashMapStudentTest {

    private static final int TIMEOUT = 200;
    private ExternalChainingHashMap<Integer, String> map;

    @Before
    public void setUp() {
        map = new ExternalChainingHashMap<>();
    }

    @Test(timeout = TIMEOUT)
    public void testInitialization() {
        assertEquals(0, map.size());
        assertArrayEquals(new ExternalChainingMapEntry[ExternalChainingHashMap
                .INITIAL_CAPACITY], map.getTable());
    }

    @Test(timeout = TIMEOUT)
    @SuppressWarnings("unchecked")
    public void testPut() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        assertEquals(5, map.size());
        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, map.getTable());
    }
    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testPutWithInvalidKey() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        map.put(null, "X");
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testPutWithInvalidValue() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        map.put(7, null);
    }

    @Test(timeout = TIMEOUT)
    public void testPutWithDuplicateKey() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));

        assertEquals("C", map.put(2, "X"));
    }

    @Test(timeout = 2000000000)
    public void testPutWithExceedingLoadFactor() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(5, "E"));
        map.resizeBackingTable(10);
        assertNull(map.put(6, "E"));
        assertNull(map.put(7, "E"));
        assertEquals(21, map.getTable().length);

    }

    @Test(timeout = TIMEOUT)
    public void testPutWithNegativeKey() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertNull(map.put(5, "E"));
        map.resizeBackingTable(10);
        assertNull(map.put(-6, "F"));
    }


    @Test(timeout = TIMEOUT)
    @SuppressWarnings("unchecked")
    public void testRemove() {
        String temp = "D";

        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, temp));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        // [(0, A), (1, B), (2, C), _, (4, E), _, _, _, _, _, _, _, _]
        assertSame(temp, map.remove(3));

        assertEquals(4, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[ExternalChainingHashMap
                                .INITIAL_CAPACITY];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testRemoveWithInvalidKey() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        map.remove(null);
    }

    @Test(expected = NoSuchElementException.class, timeout = TIMEOUT)
    public void testRemoveWithKeyNotFound() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());
        map.resizeBackingTable(10);
        map.remove(7);
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveChainHead() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());
        map.resizeBackingTable(20);
        assertNull(map.put(22, "Z"));
        assertNull(map.put(23, "Y"));
        assertNull(map.put(43, "X"));
        assertEquals("X", map.remove(43));
        assertEquals("Z", map.remove(22));

        assertEquals("C", map.getTable()[2].getValue());
        assertEquals("Y", map.getTable()[3].getValue());
        assertEquals("D", map.getTable()[3].getNext().getValue());
        assertEquals(6, map.size());
    }
    //
    @Test(timeout = TIMEOUT)
    public void testRemoveChain() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        map.resizeBackingTable(20);
        assertNull(map.put(23, "Y"));
        assertNull(map.put(43, "R"));
        assertNull(map.put(63, "X"));
        assertEquals("Y", map.remove(23));

        assertEquals("X", map.getTable()[3].getValue());
        assertEquals("R", map.getTable()[3].getNext().getValue());
        assertEquals("D", map.getTable()[3].getNext().getNext().getValue());
        assertEquals(7, map.size());
    }

    @Test(timeout = TIMEOUT)
    public void testRemoveChainEnd() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());
        map.resizeBackingTable(20);
        assertNull(map.put(23, "Y"));
        assertNull(map.put(43, "R"));
        assertNull(map.put(63, "X"));
        assertEquals("D", map.remove(3));

        assertEquals("X", map.getTable()[3].getValue());
        assertEquals("R", map.getTable()[3].getNext().getValue());
        assertEquals("Y", map.getTable()[3].getNext().getNext().getValue());
        assertEquals(7, map.size());
    }

    @Test(timeout = TIMEOUT)
    public void testGet() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        assertEquals("A", map.get(0));
        assertEquals("B", map.get(1));
        assertEquals("C", map.get(2));
        assertEquals("D", map.get(3));
        assertEquals("E", map.get(4));
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testGetWithInvalidKey() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        map.get(null);
    }

    @Test(expected = NoSuchElementException.class, timeout = TIMEOUT)
    public void testGetWithKeyNotFound() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        map.get(7);
    }

    @Test(timeout = TIMEOUT)
    public void testContainsKey() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        assertTrue(map.containsKey(3));
        assertFalse(map.containsKey(5));
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    public void testContainsKeyWithInvalidKey() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        map.containsKey(null);
    }

    @Test(timeout = TIMEOUT)
    public void testKeySet() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        Set<Integer> expected = new HashSet<>();
        expected.add(0);
        expected.add(1);
        expected.add(2);
        expected.add(3);
        expected.add(4);
        assertEquals(expected, map.keySet());
    }

    @Test(timeout = TIMEOUT)
    public void testValues() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        List<String> expected = new LinkedList<>();
        expected.add("A");
        expected.add("B");
        expected.add("C");
        expected.add("D");
        expected.add("E");
        assertEquals(expected, map.values());
    }

    @Test(timeout = TIMEOUT)
    @SuppressWarnings("unchecked")
    public void testResize() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        // [(0, A), (1, B), (2, C), (3, D), (4, E)]
        map.resizeBackingTable(5);

        assertEquals(5, map.size());

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[5];
        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(expected = IllegalArgumentException.class, timeout = TIMEOUT)
    @SuppressWarnings("unchecked")
    public void testResizeWithInvalidLength() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        // [(0, A), (1, B), (2, C), (3, D), (4, E)]
        map.resizeBackingTable(3);
    }

    @Test(timeout = TIMEOUT)
    @SuppressWarnings("unchecked")
    public void testResizeWithNullSpots() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        map.resizeBackingTable(10);

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[10];

        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = 2000000000)
    @SuppressWarnings("unchecked")
    public void testResizeWithChains() {
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        map.resizeBackingTable(10);
        assertNull(map.put(12, "E"));
        assertNull(map.put(22, "E"));
        assertNull(map.put(20, "E"));
        map.resizeBackingTable(25);

        ExternalChainingMapEntry<Integer, String>[] expected =
                (ExternalChainingMapEntry<Integer, String>[])
                        new ExternalChainingMapEntry[25];

        expected[0] = new ExternalChainingMapEntry<>(0, "A");
        expected[1] = new ExternalChainingMapEntry<>(1, "B");
        expected[2] = new ExternalChainingMapEntry<>(2, "C");
        expected[3] = new ExternalChainingMapEntry<>(3, "D");
        expected[4] = new ExternalChainingMapEntry<>(4, "E");
        expected[22] = new ExternalChainingMapEntry<>(22, "E");
        expected[20] = new ExternalChainingMapEntry<>(20, "E");
        expected[12] = new ExternalChainingMapEntry<>(12, "E");
        assertArrayEquals(expected, map.getTable());
    }

    @Test(timeout = TIMEOUT)
    public void testClear() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(0, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(2, "C"));
        assertNull(map.put(3, "D"));
        assertNull(map.put(4, "E"));
        assertEquals(5, map.size());

        map.clear();
        assertEquals(0, map.size());
        assertArrayEquals(new ExternalChainingMapEntry[ExternalChainingHashMap
                .INITIAL_CAPACITY], map.getTable());
    }
    //Olivia Blanchette Tests
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullKey() {
        assertNull(map.put(14, "A"));
        assertNull(map.put(null, "B"));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testPutNullValue() {
        assertNull(map.put(14, "A"));
        assertNull(map.put(5, null));
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNullKey() {
        assertNull(map.put(14, "A"));
        assertNull(map.put(3, "B"));
        map.remove(null);
    }

    @Test(timeout = TIMEOUT, expected= NoSuchElementException.class)
    public void testRemoveKeyNotInMap(){
        assertNull(map.put(4, "D"));
        assertNull(map.put(6, "F"));
        assertNull(map.put(1, "A"));
        map.remove(2);
    }

    @Test(timeout = TIMEOUT, expected= NoSuchElementException.class)
    public void testRemoveKeyNotInMapChain(){
        map.resizeBackingTable(12);
        assertNull(map.put(3, "D"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(0, "A"));
        assertNull(map.put(12, "M"));
        assertNull(map.put(24, "Y"));
        map.remove(48);
    }

    @Test(timeout = TIMEOUT, expected= NoSuchElementException.class)
    public void testRemoveKeyAlreadyRemoved(){
        map.resizeBackingTable(12);
        assertNull(map.put(3, "D"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(0, "A"));
        assertNull(map.put(12, "M"));
        assertNull(map.put(24, "Y"));
        assertEquals(map.remove(12), "M");
        map.remove(12);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNullKey() {
        assertNull(map.put(14, "A"));
        assertNull(map.put(3, "B"));
        map.get(null);
    }

    @Test(timeout = TIMEOUT, expected= NoSuchElementException.class)
    public void testGetKeyNotInMap(){
        assertNull(map.put(4, "D"));
        assertNull(map.put(6, "F"));
        assertNull(map.put(1, "A"));
        map.get(2);
    }

    @Test(timeout = TIMEOUT, expected= NoSuchElementException.class)
    public void testGetKeyNotInMapChain(){
        map.resizeBackingTable(12);
        assertNull(map.put(3, "D"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(0, "A"));
        assertNull(map.put(12, "M"));
        assertNull(map.put(24, "Y"));
        map.get(48);
    }

    @Test(timeout = TIMEOUT, expected= NoSuchElementException.class)
    public void testGetKeyAlreadyRemoved(){
        map.resizeBackingTable(12);
        assertNull(map.put(3, "D"));
        assertNull(map.put(5, "F"));
        assertNull(map.put(0, "A"));
        assertNull(map.put(12, "M"));
        assertNull(map.put(24, "Y"));
        assertEquals(map.remove(12), "M");
        map.get(12);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testContainsNullKey() {
        assertNull(map.put(14, "A"));
        assertNull(map.put(3, "B"));
        map.containsKey(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testResizeSmall(){
        assertNull(map.put(14, "A"));
        assertNull(map.put(3, "B"));
        assertNull(map.put(2, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(0, "A"));
        assertNull(map.put(13, "B"));
        assertNull(map.put(59, "A"));
        assertNull(map.put(12, "B"));
        assertEquals(map.size(), 8);
        map.resizeBackingTable(7);
    }

    @Test(timeout = TIMEOUT)
    public void testResizeExact(){
        assertNull(map.put(14, "A"));
        assertNull(map.put(3, "B"));
        assertNull(map.put(2, "A"));
        assertNull(map.put(1, "B"));
        assertNull(map.put(0, "A"));
        assertNull(map.put(13, "B"));
        assertNull(map.put(59, "A"));
        assertNull(map.put(12, "B"));
        map.resizeBackingTable(8);
        assertEquals(map.size(), 8);
        assertEquals(map.getTable().length, 8);
    }
}