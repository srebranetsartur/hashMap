package testHashMap;

import com.srebranets.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestHashMap {
    private HashMap hashMap;

    @BeforeEach
    void initHashMap() {
        hashMap = new HashMap();
    }

    @Test
    void shouldInitHashMapArray() {
        assertNotNull(hashMap);
    }

    @Test
    void shouldPutOneEntry() {
        assertTrue(hashMap.put(10, 100L));
        assertEquals(1, hashMap.size());
        assertEquals(hashMap.get(10), 100L);
    }

    @Test
    void shouldGetOneEntryWith_10_KeyAnd_100_Value() {
        assertTrue(hashMap.put(10, 100L));
        assertEquals(hashMap.get(10), 100);
    }

    @Test
    void shouldContainTwoEntry() {
        assertTrue(hashMap.put(10, 100L));
        assertTrue(hashMap.put(10, 100L));
        assertEquals(2, hashMap.size());
    }


    private int hash(int key) {
        key ^= (key << 13);
        key ^= (key >>> 17);
        key ^= (key << 5);

        return key;
    }

    @Test
    void shouldOutputInSortedOrder() {
        hashMap.put(10, 1L);
        hashMap.put(5, 2L);
        hashMap.put(3, 10L);
        hashMap.put(7, 7L);
        hashMap.put(5, 100L);

        int[] keys = new int[]{3, 5, 7, 10};
        long[] values = new long[]{10, 100, 7, 1};

        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < keys.length; i++)
            stringBuilder.append("{")
                    .append(hash(keys[i]))
                    .append(": ")
                    .append(values[i])
                    .append("}\n");

        String resultString = stringBuilder.toString();

        assertEquals(4, hashMap.size());
        assertEquals(hashMap.toString(), resultString);
    }

    @Test
    void shouldPutTwoEntryWithEqualsKeyAndRewriteFirst() {
        hashMap.put(1, 100L);
        hashMap.put(1, 200L);
        assertEquals(1, hashMap.size());
        assertEquals(200L, hashMap.get(1));
    }



    @Test
    void shouldThrowAInvalidArgumentException() {
        hashMap.put(1, 100L);
        assertThrows(IllegalArgumentException.class, () -> hashMap.get(2), "Value with input key doesn't exist");
    }
}
