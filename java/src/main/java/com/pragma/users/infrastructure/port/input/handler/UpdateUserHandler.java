package com.pragma.users.infrastructure.port.input.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.users.application.dto.UpdateUserCommand;
import com.pragma.users.application.dto.UserResponse;
import com.pragma.users.application.service.UserService;
import com.pragma.users.infrastructure.port.output.dynamodb.entity.UserDynamoDb;
import com.pragma.users.infrastructure.utils.DependencyFactory;

import java.util.Collections;
import java.util.Map;

public class UpdateUserHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UpdateUserHandler() {
        this.userService = DependencyFactory.userService();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
        Map<String, String> pathParameters = event.getPathParameters();
        String body = event.getBody();
        if (pathParameters == null || pathParameters.isEmpty() || body == null || body.isEmpty()) {
            return new APIGatewayProxyResponseEvent().withStatusCode(400)
                    .withIsBase64Encoded(Boolean.FALSE)
                    .withHeaders(Collections.emptyMap())
                    .withBody("{\"error\": \"Missing path parameters or request body.\"}");
        }
        try {
            String itemPartitionKey = pathParameters.get(UserDynamoDb.PARTITION_KEY);
            if (itemPartitionKey == null || itemPartitionKey.isEmpty()) {
                return new APIGatewayProxyResponseEvent()
                        .withStatusCode(404)
                        .withHeaders(Collections.emptyMap())
                        .withBody("{\"error\": \"User id path is not present.\"}");
            }
            UpdateUserCommand updateUserCommand = objectMapper.readValue(event.getBody(), UpdateUserCommand.class);
            UserResponse updatedUserDynamoDb = userService.updateUser(itemPartitionKey, updateUserCommand);
            context.getLogger().log(String.format("User with id %s was updated", updatedUserDynamoDb.getId()));
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withHeaders(Collections.emptyMap())
                    .withIsBase64Encoded(Boolean.FALSE)
                    .withBody(objectMapper.writeValueAsString(new UserResponse(updatedUserDynamoDb.getId(), updatedUserDynamoDb.getName(), updatedUserDynamoDb.getEmail())));
        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(500)
                    .withHeaders(Collections.emptyMap())
                    .withIsBase64Encoded(Boolean.FALSE)
                    .withBody("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
