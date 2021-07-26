package com.octo.utils.retry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RetryExecutorTest {

    class Counter {
        public int count;
    }

    @Test
    public void testRetry3TimesAndFail() throws InterruptedException {
        RuntimeException expected = new RuntimeException("test retry");
        Counter counter = new Counter();

        RetryExecutor<Boolean> executor = new RetryExecutor<Boolean>(3, 100, expected);

        Exception actual = null;
        try {
            executor.execute(() -> {
                counter.count++;
                throw new RetryException("test");
            });
        } catch (RuntimeException e) {
            actual = e;
        }

        assertEquals(expected, actual);
        assertEquals(3, counter.count);
    }

    @Test
    public void testRetry2TimesAndSuccess() throws InterruptedException {
        RuntimeException expected = new RuntimeException("test retry");
        Counter counter = new Counter();

        RetryExecutor<Boolean> executor = new RetryExecutor<Boolean>(3, 100, expected);

        assertTrue(executor.execute(() -> {
            counter.count++;
            if (counter.count < 2) {
                throw new RetryException("test");
            }
            return true;
        }));
        assertEquals(2, counter.count);
    }

    @Test
    public void testRetry0TimesAndSuccess() throws InterruptedException {
        RuntimeException expected = new RuntimeException("test retry");
        Counter counter = new Counter();

        RetryExecutor<Boolean> executor = new RetryExecutor<Boolean>(3, 100, expected);

        assertTrue(executor.execute(() -> {
            counter.count++;
            return true;
        }));
        assertEquals(1, counter.count);
    }

    @Test
    public void testRetryWithUnexpectedException() throws InterruptedException {
        RuntimeException expected = new RuntimeException("test retry");
        RuntimeException unexpected = new RuntimeException("other exception");
        Counter counter = new Counter();

        RetryExecutor<Boolean> executor = new RetryExecutor<Boolean>(3, 100, expected);

        RuntimeException exception = null;
        try {
            assertTrue(executor.execute(() -> {
                counter.count++;
                throw unexpected;
            }));
        } catch (RuntimeException e) {
            exception = e;
        }
        assertEquals(1, counter.count);
        assertEquals(unexpected, exception);
    }
}
