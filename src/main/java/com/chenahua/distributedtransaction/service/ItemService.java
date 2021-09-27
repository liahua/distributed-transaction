package com.chenahua.distributedtransaction.service;

public interface ItemService {
    String buy(Integer num, String itemName);

    void call();
}
