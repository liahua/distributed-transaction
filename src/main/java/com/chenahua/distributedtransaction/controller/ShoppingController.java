package com.chenahua.distributedtransaction.controller;

import com.chenahua.distributedtransaction.dto.Table;
import com.chenahua.distributedtransaction.service.ItemService;
import com.chenahua.distributedtransaction.service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShoppingController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private TableService tableService;


    /**
     * 这是啥玩意
     *
     * @param num
     * @param itemName
     * @return
     */
    @RequestMapping("/item/{itemName}/{num}")
    public String buy(@PathVariable Integer num, @PathVariable String itemName) {
        System.out.println("something in");
        return itemService.buy(num, itemName);
    }

    @RequestMapping("/item/{itemName}")
    public String getItemInfo(@PathVariable String itemName) {
        return itemName;
    }

    @RequestMapping("/table/{itemName}")
    public String insertTable(@PathVariable int itemName) throws Exception {
        tableService.insertTable(itemName);
        return "test";
    }

    @RequestMapping("/table/find/{content}")
    public void selectTableByContent(@PathVariable int content) {
        List<Table> tables = tableService.selectTableByContent(content);
        System.out.println(content);
    }

    @RequestMapping("/table/test")
    public void tableCall() {
        tableService.call();
    }



}
