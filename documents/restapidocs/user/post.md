# Create new user

**URL** : `/api/events`

**Method** : `/POST`

**Path parameters** : None

**Query parameters** : None

## Success Response 

**Code** : `201 CREATED`

**Request body**

```json
{
    "userID": 3,
    "email": "niilo.partanen@gmail.com",
    "firstName": "Niilo",
    "lastName": "Partanen",
    "password": "määlyaivo22",
    "address": "Vattajantie 14, Lohtaja",
    "phone": "0506887045"
}
```

**Response body**

```json
{
    "userId": 3,
    "email": "niilo.partanen@gmail.com",
    "firstName": "Niilo",
    "lastName": "Partanen",
    "password": "määlyaivo22",
    "address": "Vattajantie 14, Lohtaja",
    "phone": "0506887045",
    "userrole": null
}
```