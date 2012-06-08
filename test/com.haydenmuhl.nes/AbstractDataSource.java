package com.haydenmuhl.nes;

public abstract class AbstractDataSource<T> implements DataSource<T> {
    public abstract T output();
}
