package com.pragma.users.infrastructure.port.output.dynamodb.repository;

import com.pragma.users.domain.model.User;
import com.pragma.users.domain.persistence.UserRepositoryPort;
import com.pragma.users.infrastructure.port.output.dynamodb.entity.UserDynamoDb;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final DynamoDbTable<UserDynamoDb> usersTable;

    @Override
    public User findById(String id) {
        UserDynamoDb userDynamoDb = usersTable.getItem(Key.builder().partitionValue(id).build());
        if (userDynamoDb == null) {
            return null;
        }
        return User.builder()
                .id(userDynamoDb.getId())
                .name(userDynamoDb.getName())
                .email(userDynamoDb.getEmail())
                .build();
    }

    @Override
    public User updateUser(User user) {
        usersTable.putItem(
                UserDynamoDb.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .build()
        );
        return user;
    }

    @Override
    public void deleteById(String id) {
        usersTable.deleteItem(
                Key.builder()
                        .partitionValue(id)
                        .build()
        );
    }
}
