# Provider Endpoints

| Path                  | Method | Res Status | Res Body                 | Req Body         |
| --------------------- | ------ | ---------- | ------------------------ | ---------------- |
| /api/v1/provider      | POST   | 201        | provider-response        | provider-request |
| /api/v1/provider/{id} | GET    | 200        | provider-response        |                  |
| /api/v1/provider      | GET    | 200        | page [provider-response] |                  |
| /api/v1/provider/{id} | PUT    | 204        | -                        | provider-request |
| /api/v1/provider/{id} | DEL    | 204        | -                        | -                |

## Provider Request

| name     | type             | validations          |
| -------- | ---------------- | -------------------- |
| type     | ProviderTypeEnum | not-null             |
| eanValue | int              | min: 0, max: 9999999 |

## Provider Response

| name     | type             |
| -------- | ---------------- |
| id       | long             |
| type     | ProviderTypeEnum |
| eanValue | int              |

## Provider Type Enum

* MERCADONA_ESPANYA
* MERCADONA_PORTUGAL
* ALMACENES
* MERCADONA_OFICINAS
* COLMENAS
