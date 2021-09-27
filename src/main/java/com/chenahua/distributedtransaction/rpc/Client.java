package com.chenahua.distributedtransaction.rpc;

import com.chenahua.distributedtransaction.dto.User;
import com.chenahua.distributedtransaction.service.IUserService;
import com.chenahua.distributedtransaction.service.ItemService;

public class Client {
    public static void main(String[] args) {
        IUserService stub = Stub.getStub(IUserService.class);
        System.out.println(stub.findUserById(456));
        System.out.println(stub.findUserByName("Alice"));
    }
}
