# Auth Endpoints

| Path                 | Method | Res Status | Res Body        | Req Body              |
| -------------------- | ------ | ---------- | --------------- | --------------------- |
| /api/v1/auth/access  | POST   | 200        | tokens-response | credentials-request   |
| /api/v1/auth/refresh | POST   | 200        | tokens-response | refresh-token-request |
| /api/v1/auth/logout  | POST   | 204        | -               | refresh-token-request |

## Credentials Request

| name     | type   | validations |
| -------- | ------ | ----------- |
| username | String | not-blank   |
| password | String | not-blank   |

## Refresh Token Request

| name         | type   | validations |
| ------------ | ------ | ----------- |
| refreshToken | String | not-blank   |

## Tokens Response

| name         | type   |
| ------------ | ------ |
| accessToken  | String |
| refreshToken | String |
