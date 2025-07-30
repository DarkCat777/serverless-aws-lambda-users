import {container} from "../../../di/inversify.config";
import {UserService} from "../../../../application/service/user.service";
import {TYPES} from "../../../di/types";
import {formatJSONResponse, ValidatedEventAPIGatewayProxyEvent} from "../../../api-gateway/gateway";
import {middify} from "../../../middy/middify";

const handler: ValidatedEventAPIGatewayProxyEvent<never> = async () => {
    const service = container.get<UserService>(TYPES.UserService)
    const users = await service.findAllUsers()
    return formatJSONResponse(users as unknown as Record<string, unknown>[], 200)
}

export const main = middify(handler);
