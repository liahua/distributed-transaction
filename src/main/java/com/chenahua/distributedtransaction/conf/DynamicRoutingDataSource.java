package com.chenahua.distributedtransaction.conf;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    static ThreadLocal<DataSourceEnum> dataSourceThreadLocal = new ThreadLocal<>();



    public DynamicRoutingDataSource(@Qualifier("dataSourceTwo") DataSource dataSourceTwo, @Qualifier("dataSourceOne") DataSource dataSourceOne) {
        HashMap<Object, Object> map = new HashMap<>();
        map.put(DataSourceEnum.ONE, dataSourceOne);
        map.put(DataSourceEnum.TWO, dataSourceTwo);
        super.setTargetDataSources(map);
        super.setDefaultTargetDataSource(dataSourceOne);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return dataSourceThreadLocal.get();
    }

    public static void setDataSource(DataSourceEnum datasource) {
        dataSourceThreadLocal.set(datasource);
    }

    public static void remove() {
        dataSourceThreadLocal.remove();
    }
}
