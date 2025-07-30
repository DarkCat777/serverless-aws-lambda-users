import type {JSONSchema} from 'json-schema-to-ts'

export const userSchema: JSONSchema = {
    type: "object",
    required: ["body"],
    properties: {
        body: {
            type: 'object',
            properties: {
                name: {type: 'string'},
                email: {type: 'string', format: 'email'}
            },
            required: ['name', 'email'],
            additionalProperties: false
        }
    }
};