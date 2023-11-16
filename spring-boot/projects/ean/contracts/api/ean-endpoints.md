# Ean Endpoints

| Path                  | Method | Res Status | Res Body     | Req Body |
| --------------------- | ------ | ---------- | ------------ | -------- |
| /api/v1/ean/{eanCode} | GET    | 200        | ean-response | -        |

## Ean Response

| name               | type   |
| ------------------ | ------ |
| providerType       | String |
| providerEan        | int    |
| productName        | String |
| productDescription | String |
| productPrice       | double |
| productStock       | long   |
| productEan         | int    |
| deliveryQuantity   | long   |
| destinyEan         | int    |
| destinyType        | String |
