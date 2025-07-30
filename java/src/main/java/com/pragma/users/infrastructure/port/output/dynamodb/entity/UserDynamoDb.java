package com.pragma.users.infrastructure.port.output.dynamodb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDbBean
public class UserDynamoDb {
    public static final String PARTITION_KEY = "id";
    @JsonProperty(PARTITION_KEY)
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }
}
