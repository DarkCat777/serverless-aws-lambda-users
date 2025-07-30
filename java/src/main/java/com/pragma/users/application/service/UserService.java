package com.pragma.users.application.service;

import com.pragma.users.application.dto.UpdateUserCommand;
import com.pragma.users.application.dto.UserResponse;

public interface UserService {

    UserResponse updateUser(String id, UpdateUserCommand updateUserCommand);

    void deleteUserById(String id);

}
