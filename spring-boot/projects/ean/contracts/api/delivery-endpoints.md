# Delivery Endpoints

| Path                         | Method | Res Status | Res Body                 | Req Body         |
| ---------------------------- | ------ | ---------- | ------------------------ | ---------------- |
| /api/v1/delivery             | POST   | 201        | delivery-response        | delivery-request |
| /api/v1/delivery/{id}        | GET    | 200        | delivery-response        |                  |
| /api/v1/delivery/{productId} | GET    | 200        | page [delivery-response] |                  |
| /api/v1/delivery/{id}        | PUT    | 204        | -                        | delivery-request |
| /api/v1/delivery/{id}        | DEL    | 204        | -                        | -                |

## Delivery Request

| name      | type | validations |
| --------- | ---- | ----------- |
| quantity  | long | min: 1      |
| productId | long |             |
| destinyId | long |             |

## Delivery Response

| name      | type |
| --------- | ---- |
| id        | long |
| quantity  | long |
| productId | long |
| destinyId | long |
