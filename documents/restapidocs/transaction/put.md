# Update existing transaction

**URL** : `/api/transactions/{id}`

**Method** : `/PUT`

**Path parameters** : `id`

**Query parameters** : None

## Success Response 

**Code** : `200 OK`

**Request body**

```json
{
    "transactionDate": "2024-04-02T09:11:20",
    "totalSum": 25.6,
    "succeeded": true,
    "user": {"userId": 1}
}
```

**Response body**

```json
{
    "transactionId": 2,
    "transactionDate": "2024-04-02T09:11:20",
    "totalSum": 25.6,
    "succeeded": true,
    "user": {
        "userId": 1,
        "email": null,
        "firstName": null,
        "lastName": null,
        "password": null,
        "address": null,
        "phone": null,
        "userrole": null
    }
}
```

## Error Response 

**Code** : `404 NOT FOUND`