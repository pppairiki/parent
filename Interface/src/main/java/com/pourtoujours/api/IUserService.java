package com.pourtoujours.api;

import com.pourtoujours.model.User;
public interface IUserService {
    int saveUser(User obj);

    User getUserById(int id);

    User getUserByAccount(String account);

    int signUp(User obj);
}
