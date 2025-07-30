import {CreateUserRequest} from "../../dto/create-user.request";
import {UserResponse} from "../../dto/user.response";
import {UserService} from "../user.service";
import {inject, injectable} from "inversify";
import {TYPES} from "../../../infrastructure/di/types";
import {UserUseCase} from "../../../domain/usecase/user.usecase";

@injectable()
export class UserServiceImpl implements UserService {

    constructor(
        @inject(TYPES.UserUseCase)
        private readonly userUseCase: UserUseCase
    ) {
    }

    createUser(userRequest: CreateUserRequest): Promise<UserResponse> {
        return this.userUseCase.create(userRequest);
    }

    findAllUsers(): Promise<UserResponse[]> {
        return this.userUseCase.findAll();
    }

}