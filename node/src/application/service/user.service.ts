import {UserResponse} from "../dto/user.response";
import {CreateUserRequest} from "../dto/create-user.request";

export interface UserService {
    createUser(userRequest: CreateUserRequest): Promise<UserResponse>;

    findAllUsers(): Promise<UserResponse[]>;
}