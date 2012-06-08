package com.haydenmuhl.nes;

public class PullRegister<T> implements Clocked, DataSource<T> {
    private T stored;
    private T toStore;
    private DataSource<T> dataSource;

    public PullRegister(T initialData) {
        toStore = null;
        stored = initialData;
    }
    
    public void setDataSource(DataSource<T> source) {
        dataSource = source;
    }
    
    public void pull() {
        toStore = dataSource.output();
    }

    public void tick() {
        if (toStore != null) {
            stored = toStore;
            toStore = null;
        }
    };
    
    public T output() {
        return stored;
    }
}
