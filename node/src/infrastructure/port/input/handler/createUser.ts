import {container} from "../../../di/inversify.config";
import {UserService} from "../../../../application/service/user.service";
import {TYPES} from "../../../di/types";
import {formatJSONResponse, ValidatedEventAPIGatewayProxyEvent} from "../../../api-gateway/gateway";
import {middify} from "../../../middy/middify";
import {userSchema} from "../schema/user.schema";

const handler: ValidatedEventAPIGatewayProxyEvent<typeof userSchema> = async (event) => {
    const service = container.get<UserService>(TYPES.UserService)
    const {name, email}: { [key: string]: any } = event.body as any;
    const user = await service.createUser({name, email});
    return formatJSONResponse(user as unknown as Record<string, unknown>, 201)
}

export const main = middify(handler);
