package com.chenahua.distributedtransaction.service;

import com.chenahua.distributedtransaction.dto.User;

public interface IUserService {
    User findUserById(int i);
    User findUserByName(String name);
}
