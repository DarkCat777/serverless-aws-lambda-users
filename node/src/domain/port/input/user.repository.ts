import {User} from '../../model/User'

export interface UserRepository {
    save(user: Omit<User, 'id'>): Promise<User>

    findAll(): Promise<User[]>
}
