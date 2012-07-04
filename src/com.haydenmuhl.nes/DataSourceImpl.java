package com.haydenmuhl.nes;

public class DataSourceImpl<T, D extends DataSource<T>> implements DataSource<T> {
    private D dataSource;
    
    public void setDataSource(D source) {
        dataSource = source;
        return;
    }
    
    public T output() {
        return dataSource.output();
    }
    
    public D getSource() {
        return dataSource;
    }
}
