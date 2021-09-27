package com.chenahua.distributedtransaction;

import com.chenahua.distributedtransaction.dto.Table;
import com.chenahua.distributedtransaction.service.TableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * bean加载机制假想：
 * 1.扫描当前class对象根路径下java文件，找到含有注解的所有对象包括@bean @Configuration  ,存 <beanName,class> beanDefineMap
 * 2.foreach beanDefineMap  根据构造方法进行 newInstance 存入 <beanName,Instance> beanInstanceMap
 * 3.需要其他bean作为依赖的 放入<beanName,class> OnCreatingMap
 * 4.foreach OnCreatingMap  会进行 get(beanName)==null?  如果发现!=null,则判断循环依赖
 */
@SpringBootTest
public class ApplicationTests {

    @Autowired
    private TableService tableService;


    @Test
    void OneDataBase() throws Exception {
        tableService.insertTable(150);
    }
    @Test
    void queryDataBase(){
        int content=8;
        List<Table> tables = tableService.selectTableByContent(content);
        System.out.println(tables);
    }

    @Test
    void testCircle() {
        tableService.call();
    }
}
