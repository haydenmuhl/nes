package com.haydenmuhl.nes;

public class DataSourceImpl<T> implements DataSource<T> {
    private DataSource<T> dataSource;
    
    public void setDataSource(DataSource<T> source) {
        dataSource = source;
        return;
    }
    
    public T output() {
        return dataSource.output();
    }
}
