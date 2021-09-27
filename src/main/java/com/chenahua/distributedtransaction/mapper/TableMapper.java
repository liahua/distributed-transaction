package com.chenahua.distributedtransaction.mapper;

import com.chenahua.distributedtransaction.dto.Table;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableMapper {


    void insertTable(int content);

    @Insert("insert into liahua.table_01 (content) values (#{content})")
    void insertTable0(int content);
    @Insert("insert into liahua.table_02 (content) values (#{content})")
    void insertTable1(int content);
    @Select("select * from liahua.table_01 where content = #{content}")
    List<Table> selectTableByContent0(int content);
    @Select("select * from liahua.table_02 where content = #{content}")
    List<Table> selectTableByContent1(int content);
}
