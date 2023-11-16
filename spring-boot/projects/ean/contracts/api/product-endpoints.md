# Product Endpoints

| Path                 | Method | Res Status | Res Body                | Req Body        |
| -------------------- | ------ | ---------- | ----------------------- | --------------- |
| /api/v1/product      | POST   | 201        | product-response        | product-request |
| /api/v1/product/{id} | GET    | 200        | product-response        |                 |
| /api/v1/product      | GET    | 200        | page [product-response] |                 |
| /api/v1/product/{id} | PUT    | 204        | -                       | product-request |
| /api/v1/product/{id} | DEL    | 204        | -                       | -               |

## Product Request

| name        | type   | validations             |
| ----------- | ------ | ----------------------- |
| name        | String | not-blank, max-size: 20 |
| description | String | max-size: 200           |
| price       | double | min: 0                  |
| stock       | long   | min: 0                  |
| eanValue    | int    | min: 0, max: 99999      |
| providerId  | long   |                         |

## Product Response

| name        | type   |
| ----------- | ------ |
| id          | long   |
| name        | String |
| description | String |
| price       | double |
| stock       | long   |
| eanValue    | int    |
| providerId  | long   |
