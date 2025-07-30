package com.pragma.users.infrastructure.port.input.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.users.application.service.UserService;
import com.pragma.users.infrastructure.port.output.dynamodb.entity.UserDynamoDb;
import com.pragma.users.infrastructure.utils.DependencyFactory;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.Collections;
import java.util.Map;

public class DeleteUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final UserService userService;

    public DeleteUserHandler() {
        this.userService = DependencyFactory.userService();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {
        Map<String, String> pathParameters = request.getPathParameters();
        try {
            if (pathParameters != null) {
                final String id = pathParameters.get(UserDynamoDb.PARTITION_KEY);
                if (id != null && !id.isEmpty()) {
                    userService.deleteUserById(id);
                    context.getLogger().log(String.format("User with id %s was deleted", id));
                }
            }
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withHeaders(Collections.emptyMap())
                    .withIsBase64Encoded(Boolean.FALSE)
                    .withBody("{\"error\": \"" + e.getMessage() + "\"}");
        }
        return new APIGatewayProxyResponseEvent().withStatusCode(204)
                .withIsBase64Encoded(Boolean.FALSE)
                .withHeaders(Collections.emptyMap());
    }
}
