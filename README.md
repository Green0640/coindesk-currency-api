# coindesk-currency-api

### 提供幣別資料的新增、查詢、修改與刪除。

| Method | Route                  | 功能             |
|--------|------------------------|----------------|
| GET    | `/api/currencies`      | 查詢所有幣別         |
| GET    | `/api/currencies/{id}` | 查詢指定幣別         |
| POST   | `/api/currencies`      | 新增幣別           |
| PUT    | `/api/currencies/{id}` | 修改幣別           |
| DELETE | `/api/currencies/{id}` | 刪除幣別           |
|GET | `/api/coindesk`        | 呼叫指定API        |
|GET | `/api/coindesk/crypto` | 呼叫指定API並轉換成新資料 |

### SQL Table初始化檔案
src/main/resources/schema.sql