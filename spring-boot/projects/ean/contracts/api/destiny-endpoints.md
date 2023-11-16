# Delivery Endpoints

| Path                 | Method | Res Status | Res Body                | Req Body        |
| -------------------- | ------ | ---------- | ----------------------- | --------------- |
| /api/v1/destiny      | POST   | 201        | destiny-response        | destiny-request |
| /api/v1/destiny/{id} | GET    | 200        | destiny-response        |                 |
| /api/v1/destiny      | GET    | 200        | page [destiny-response] |                 |
| /api/v1/destiny/{id} | PUT    | 204        | -                       | destiny-request |
| /api/v1/destiny/{id} | DEL    | 204        | -                       | -               |

## Destiny Request

| name        | type            | validations    |
| ----------- | --------------- | -------------- |
| minEanValue | int             | min: 0, max: 9 |
| maxEanValue | int             | min: 0, max: 9 |
| address     | String          | max-size: 200  |
| type        | DestinyTypeEnum | not-null       |

## Destiny Response

| name        | type            |
| ----------- | --------------- |
| id          | long            |
| minEanValue | int             |
| maxEanValue | int             |
| address     | String          |
| type        | DestinyTypeEnum |

## Destiny Type Enum

* HACENDADO
* MERCADONA
