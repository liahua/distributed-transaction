package com.chenahua.distributedtransaction.service.impl;

import com.chenahua.distributedtransaction.service.ItemService;
import com.chenahua.distributedtransaction.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TableService tableService;

    @Override
    public void call() {
        System.out.println("item call");
        tableService.call();
    }

    /**
     * 分布式锁:
     * 1.可重入性
     * 2.超时处理机制(不可死锁)
     *
     *
     * 1.set(当前线程id,当前时间+超时时间)
     * 2.客户端维持定时任务,检测当前线程是否结束,如果尚未结束 继续更新
     *
     *
     * 购买商品-->
     * @param num
     * @param itemName
     * @return
     */
    @Override
    public String buy(Integer num, String itemName) {

        return null;
    }
}
