package com.chenahua.distributedtransaction;

public abstract class FO {
    void start() {
        before();
        after();
    }

    protected abstract void after();

    protected void before() {
    }
}
