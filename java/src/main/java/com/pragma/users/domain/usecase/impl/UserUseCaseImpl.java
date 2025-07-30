package com.pragma.users.domain.usecase.impl;

import com.pragma.users.domain.model.User;
import com.pragma.users.domain.persistence.UserRepositoryPort;
import com.pragma.users.domain.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    @Override
    public User update(User user) {
        User existingUser = userRepositoryPort.findById(user.getId());
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        return userRepositoryPort.updateUser(user);
    }

    @Override
    public void deleteById(String id) {
        User existingUser = userRepositoryPort.findById(id);
        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }
        userRepositoryPort.deleteById(id);
    }
}
