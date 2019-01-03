package com.srebranets;

import java.util.Arrays;
import java.util.Objects;

public class HashMap {
    private Entry[] table = new Entry[8];
    private final static int DEFAULT_EXPAND_PROPERTY = 8;
    private int index = 0;

    private class Entry implements Comparable<Entry>{
        private final Integer key;
        private Long value;
        private int hash;

        private Entry(int key, long value) {
            this.key = key;
            this.value = value;
            hash = hash(key);
        }

        @Override
        public int compareTo(Entry o) {
            return hash - o.hash;
        }
    }

    private int hash(int key) {
        key ^= (key << 13);
        key ^= (key >>> 17);
        key ^= (key << 5);

        return key;
    }

    public boolean put(int key, long value) {
        Entry entry = new Entry(key, value);

        if(!isEmpty()) {
            if(containKey(key)) {
                rewriteEntryValue(key, value);
                return true;
            }

            //Using a linear probing method to find empty hash
            while(containHash(entry.hash)) {
                entry.hash++;
            }

            if (index > table.length)
                resize();
        }

        table[index++] = entry;
        sortArray();

        return true;
    }
    
    private boolean containKey(int key) {
        int keyIndex = Arrays.binarySearch(keys(), key);
        return keyIndex >= 0;
    }

    private void rewriteEntryValue(int key, long newValue) {
        int index = findKeyIndex(key);
        table[index].value = newValue;
    }

    private int findKeyIndex(int key) {
        return Arrays.binarySearch(keys(), key);
    }

    private int[] keys() {
        return Arrays.stream(table).filter(Objects::nonNull).mapToInt(entry -> entry.key).toArray();
    }

    private void resize() {
        table = Arrays.copyOf(this.table, table.length + DEFAULT_EXPAND_PROPERTY);
    }

    private void sortArray() {
        int nullIndex = 0;
        while(table[nullIndex] != null )
            nullIndex++;

        Arrays.sort(table, 0, nullIndex);
    }

    public long get(int key) {
        int hashKey = hash(key);

        if (isEmpty() || !containHash(hashKey))
            throw new IllegalArgumentException("Value with input key doesn't exist");

        return getValueByKey(hashKey);
    }

    private boolean isEmpty() {
        for(Entry entry: table) {
            if(entry != null) return false;
        }

        return true;
    }

    private boolean containHash(int key) {
        int i = Arrays.binarySearch(hashKeys(), key);
        return i >= 0;
    }

    private long getValueByKey(int key) {
        return table[Arrays.binarySearch(hashKeys(), key)].value;
    }

    private int[] hashKeys() {
        return Arrays.stream(table).filter(Objects::nonNull).mapToInt(entry -> entry.hash).toArray();
    }

    public long size() {
        return Arrays.stream(table).filter(Objects::nonNull).count();
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();

        for(Entry entry: table) {
            if(entry != null) {
                String format = String.format("{%d: %d}\n", entry.hash, entry.value);
                resultString.append(format);
            }
        }

        return resultString.toString();
    }
}