import {UserUseCase} from "../user.usecase";
import {User} from "../../model/User";
import {UserRepository} from "../../port/input/user.repository";
import {inject, injectable} from "inversify";
import {TYPES} from "../../../infrastructure/di/types";

@injectable()
export class UserUsecaseImpl implements UserUseCase {

    constructor(
        @inject(TYPES.UserRepository)
        private readonly userRepository: UserRepository
    ) {
    }

    create(user: Omit<User, 'id'>): Promise<User> {
        return this.userRepository.save(user);
    }

    findAll(): Promise<User[]> {
        return this.userRepository.findAll();
    }
}