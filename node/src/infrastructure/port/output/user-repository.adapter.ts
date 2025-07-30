import {DynamoDBClient, PutItemCommand, ScanCommand} from '@aws-sdk/client-dynamodb'
import {marshall, unmarshall} from '@aws-sdk/util-dynamodb'
import {injectable} from 'inversify'
import {UserRepository} from "../../../domain/port/input/user.repository";
import {User} from "../../../domain/model/User";
import {v4 as uuid} from "uuid";

@injectable()
export class UserRepositoryAdapter implements UserRepository {
    private client = new DynamoDBClient({})

    private tableName = process.env.USERS_TABLE_NAME!

    async save(user: Omit<User, 'id'>): Promise<User> {
        const newUser: User = {id: uuid(), ...user}
        await this.client.send(new PutItemCommand({
            TableName: this.tableName,
            Item: marshall(newUser)
        }));
        return newUser;
    }

    async findAll(): Promise<User[]> {
        const data = await this.client.send(new ScanCommand({TableName: this.tableName}))
        return (data.Items ?? []).map(item => unmarshall(item) as User)
    }
}
