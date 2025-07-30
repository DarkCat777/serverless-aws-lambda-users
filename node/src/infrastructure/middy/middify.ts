import middy from 'middy'
import { jsonBodyParser, httpErrorHandler, validator} from 'middy/middlewares'
import type {Handler, APIGatewayProxyResult} from 'aws-lambda'

export const middify = (
    handler: Handler<any, APIGatewayProxyResult>,
    schema?: object
) => {
    const middleware = middy(handler)
        .use(httpErrorHandler())

    if (schema) {
        middleware
            .use(jsonBodyParser())
            .use(validator({inputSchema: schema}))
    }

    return middleware
}
