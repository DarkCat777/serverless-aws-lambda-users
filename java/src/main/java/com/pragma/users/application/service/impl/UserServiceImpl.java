package com.pragma.users.application.service.impl;

import com.pragma.users.application.dto.UpdateUserCommand;
import com.pragma.users.application.dto.UserResponse;
import com.pragma.users.application.service.UserService;
import com.pragma.users.domain.model.User;
import com.pragma.users.domain.usecase.UserUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserUseCase userUseCase;

    @Override
    public UserResponse updateUser(String id, UpdateUserCommand updateUserCommand) {
        User updatedUser = userUseCase.update(
                User.builder()
                        .id(id)
                        .name(updateUserCommand.getName())
                        .email(updateUserCommand.getEmail())
                        .build()
        );
        return UserResponse.builder()
                .id(updatedUser.getId())
                .name(updatedUser.getName())
                .email(updatedUser.getEmail())
                .build();
    }

    @Override
    public void deleteUserById(String id) {
        userUseCase.deleteById(id);
    }
}
