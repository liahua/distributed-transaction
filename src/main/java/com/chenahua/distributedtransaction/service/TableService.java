package com.chenahua.distributedtransaction.service;

import com.chenahua.distributedtransaction.dto.Table;

import java.util.List;


public interface TableService {


    void insertTable(int content) throws Exception;

    List<Table> selectTableByContent(int content);

    void call();
}
