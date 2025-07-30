package com.pragma.users.infrastructure.utils;

import com.pragma.users.application.service.UserService;
import com.pragma.users.application.service.impl.UserServiceImpl;
import com.pragma.users.domain.persistence.UserRepositoryPort;
import com.pragma.users.domain.usecase.UserUseCase;
import com.pragma.users.domain.usecase.impl.UserUseCaseImpl;
import com.pragma.users.infrastructure.port.output.dynamodb.repository.UserRepositoryAdapter;
import com.pragma.users.infrastructure.port.output.dynamodb.entity.UserDynamoDb;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.SdkSystemSetting;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DependencyFactory {

    private static final String ENV_VARIABLE_TABLE = "USERS_TABLE_NAME";

    private static DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        return DynamoDbEnhancedClient.builder()
                .dynamoDbClient(DynamoDbClient.builder()
                        .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                        .region(Region.of(System.getenv(SdkSystemSetting.AWS_REGION.environmentVariable())))
                        .httpClientBuilder(UrlConnectionHttpClient.builder())
                        .build())
                .build();
    }

    private static String tableName() {
        return System.getenv(ENV_VARIABLE_TABLE);
    }

    private static DynamoDbTable<UserDynamoDb> usersTable() {
        return DependencyFactory.dynamoDbEnhancedClient()
                .table(DependencyFactory.tableName(), TableSchema.fromClass(UserDynamoDb.class));
    }

    private static UserRepositoryPort userRepositoryPort() {
        return new UserRepositoryAdapter(usersTable());
    }

    private static UserUseCase userUseCase() {
        return new UserUseCaseImpl(userRepositoryPort());
    }

    public static UserService userService() {
        return new UserServiceImpl(userUseCase());
    }
}