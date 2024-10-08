# Create new transaction

**URL** : `/api/transactions`

**Method** : `/POST`

**Path parameters** : None

**Query parameters** : None

## Success Response 

**Code** : `201 CREATED`

**Request body**

```json
{
    "transactionId": 3,
    "transactionDate": "2024-04-02T09:11:20",
    "totalSum": 21.5,
    "succeeded": true,
    "user": {"userId": 2}
}
```

**Response body**

```json
{
    "transactionId": 3,
    "transactionDate": "2024-04-02T09:11:20",
    "totalSum": 21.5,
    "succeeded": true,
    "user": {
        "userId": 2,
        "email": "mikko.mallikas@hotmail.com",
        "firstName": "Mikko",
        "lastName": "Mallikas",
        "password": "dfhdgjfsfgj",
        "address": "Työnjohtajankatu 7, Helsinki",
        "phone": "4587624",
        "userrole": null
    },
    "tickets": null
}
```