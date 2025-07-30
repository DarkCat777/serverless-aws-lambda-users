package com.pragma.users.domain.persistence;

import com.pragma.users.domain.model.User;

public interface UserRepositoryPort {

    User findById(String id);

    User updateUser(User user);

    void deleteById(String id);

}
