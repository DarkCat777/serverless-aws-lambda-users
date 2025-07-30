import "reflect-metadata";

import {Container} from 'inversify'
import {TYPES} from './types'
import {UserRepository} from "../../domain/port/input/user.repository";
import {UserRepositoryAdapter} from "../port/output/user-repository.adapter";
import {UserService} from "../../application/service/user.service";
import {UserUseCase} from "../../domain/usecase/user.usecase";
import {UserUsecaseImpl} from "../../domain/usecase/impl/user.usecase.impl";
import {UserServiceImpl} from "../../application/service/impl/user.service.impl";

const container = new Container()

container.bind<UserRepository>(TYPES.UserRepository).to(UserRepositoryAdapter)
container.bind<UserUseCase>(TYPES.UserUseCase).to(UserUsecaseImpl)
container.bind<UserService>(TYPES.UserService).to(UserServiceImpl)

export {container}
