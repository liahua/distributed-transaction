package com.chenahua.distributedtransaction.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    int id;
    String name;


    public User(int id, String Name) {
        this.id = id;
        this.name = Name;

    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize start");
        super.finalize();
        System.out.println("finalize final");
    }
}
