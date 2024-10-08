# Show all transactions

**URL** : `/api/transactions`

**Method** : `/GET`

**Path parameters** : None

**Query parameters** : None

## Success Response 

**Code** : `200 OK`

**Response body**

```json
[
    {
        "transactionId": 1,
        "transactionDate": "2024-10-05T12:12:00",
        "totalSum": 22.9,
        "succeeded": true,
        "user": {
            "userId": 1,
            "email": "pekka.puupaa@hotmail.com",
            "firstName": "Pekka",
            "lastName": "Puupää",
            "password": "khfdnslfåpdfj",
            "address": "Kaivokatu 1, Helsinki",
            "phone": "4587624",
            "userrole": null
        },
        "tickets": []
    },
    {
        "transactionId": 2,
        "transactionDate": "2024-09-06T11:15:00",
        "totalSum": 25.2,
        "succeeded": true,
        "user": {
            "userId": 1,
            "email": "pekka.puupaa@hotmail.com",
            "firstName": "Pekka",
            "lastName": "Puupää",
            "password": "khfdnslfåpdfj",
            "address": "Kaivokatu 1, Helsinki",
            "phone": "4587624",
            "userrole": null
        },
        "tickets": []
    }
]
```

# Show transaction by ID

**URL** : `/transactions/{id}`

**Method** : `GET`

**Path parameters** : `id`

**Query parameters** : None

## Success Response 

**Code** : `200 OK`

**Response body**

```json
{
    "transactionId": 1,
    "transactionDate": "2024-10-05T12:12:00",
    "totalSum": 22.9,
    "succeeded": true,
    "user": {
        "userId": 1,
        "email": "pekka.puupaa@hotmail.com",
        "firstName": "Pekka",
        "lastName": "Puupää",
        "password": "khfdnslfåpdfj",
        "address": "Kaivokatu 1, Helsinki",
        "phone": "4587624",
        "userrole": null
    },
    "tickets": []
}
```

## Error Response 

**Code** : `404 NOT FOUND`