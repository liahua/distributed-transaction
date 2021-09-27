package com.chenahua.distributedtransaction.service.impl;


import com.chenahua.distributedtransaction.annotation.DataSource;
import com.chenahua.distributedtransaction.conf.DataSourceEnum;
import com.chenahua.distributedtransaction.dto.Table;
import com.chenahua.distributedtransaction.proxy.TableProxy;
import com.chenahua.distributedtransaction.service.ItemService;
import com.chenahua.distributedtransaction.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {


    private final TableProxy tableProxy;

    @Autowired
    private ItemService itemService;

    @Override
    public void call() {
        System.out.println("table call");
        itemService.call();
    }


    public TableServiceImpl(TableProxy tableProxy) {
        this.tableProxy = tableProxy;
    }

    @Override
    @DataSource(datasource = DataSourceEnum.TWO)
    @Transactional(rollbackFor = Exception.class)
    public void insertTable(int content) throws Exception {
        System.out.println("com.chenahua.distributedtransaction.service.impl.TableServiceImpl.insertTable begin");
        try {
            tableProxy.insertTable(content);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(content);
//        throw new Exception("ready roll back ");
    }

    @Override
    public List<Table> selectTableByContent(int content) {
        try {
            return tableProxy.selectTableByContent(content);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


}
