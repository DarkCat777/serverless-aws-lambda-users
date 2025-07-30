import {User} from "../model/User";

export interface UserUseCase {
    create(user: Omit<User, 'id'>): Promise<User>

    findAll(): Promise<User[]>
}