package com.chenahua.distributedtransaction.proxy;

import com.chenahua.distributedtransaction.dto.Table;
import com.chenahua.distributedtransaction.mapper.TableMapper;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author a8517
 */
@Configuration
public class TableProxy {

    private final TableMapper tableMapper;

    public TableProxy(TableMapper tableMapper) {
        this.tableMapper = tableMapper;
    }

    public void insertTable(int content) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int mod = content % 2;
        Method method = TableMapper.class.getMethod("insertTable" + mod, int.class);
        method.invoke(tableMapper, content);
    }

    @SuppressWarnings("unchecked")
    public List<Table> selectTableByContent(int content) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int mod = content % 2;
        Method method = TableMapper.class.getMethod("selectTableByContent" + mod, int.class);
        Object invoke = method.invoke(tableMapper, content);
        System.out.println("invoke = " + invoke);
        return (List<Table>) invoke;
    }
}
