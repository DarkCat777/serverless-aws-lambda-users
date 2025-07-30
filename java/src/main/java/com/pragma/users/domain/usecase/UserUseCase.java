package com.pragma.users.domain.usecase;

import com.pragma.users.domain.model.User;

public interface UserUseCase {
    User update(User user);

    void deleteById(String id);
}
