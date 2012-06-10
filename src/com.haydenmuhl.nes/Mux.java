package com.haydenmuhl.nes;

import java.util.HashMap;

public class Mux<K, V> implements DataSource<V> {
    private HashMap<K, DataSource<V>> dataSources = new HashMap<K, DataSource<V>>();
    private K selected;

    public V output() {
        return dataSources.get(selected).output();
    }
    
    public void add(K control, DataSource<V> output) {
        dataSources.put(control, output);
    }
    
    public void select(K control) {
        selected = control;
    }
}
