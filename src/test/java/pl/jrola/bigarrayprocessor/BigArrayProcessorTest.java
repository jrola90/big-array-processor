package pl.jrola.bigarrayprocessor;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BigArrayProcessorTest {

    @Test
    void should_ProcessFewElements() {
        int[] input = {-10, -20, -30, 2, -40};
        List<Integer> result = BigArrayProcessor.process(input);

        assertEquals(List.of(-10, -30, -40), result);
    }

    @Test
    void should_NotRemoveElementsOutOfBound() {
        int[] input = {-1, -2, 5, -3};
        List<Integer> result = BigArrayProcessor.process(input);

        assertEquals(List.of(-1, -2, -3), result);
    }

    @Test
    void when_ZeroInTheArray_should_DoNothing() {
        // 0 should be ignored
        // 1 should remove the first element
        int[] input = {-5, 0, -10, 1};
        List<Integer> result = BigArrayProcessor.process(input);

        assertEquals(List.of(-10), result);
    }

    @Test
    void when_InputIsNull_should_ReturnEmptyList() {
        assertTrue(BigArrayProcessor.process(null).isEmpty());
    }

    @Test
    void when_InputIsEmpty_should_ReturnEmptyList() {
        assertTrue(BigArrayProcessor.process(new int[]{}).isEmpty());
    }

    @Test
    void when_10mArrayProvided_should_ProcessInLessThan1s() {
        // Stress test: 10,000,000 elements
        int size = 10_000_000;
        int[] input = new int[size];
        for (int i = 0; i < size; i++) {
            // Add negative numbers, then remove the 1st one repeatedly
            input[i] = (i % 2 == 0) ? -i - 1 : 1;
        }

        long start = System.currentTimeMillis();
        List<Integer> result = BigArrayProcessor.process(input);
        long end = System.currentTimeMillis();

        // Should process 10M elements in a less than 1 second
        assertTrue((end - start) < 1000, "Processing took too long: " + (end - start) + "ms");
    }
}