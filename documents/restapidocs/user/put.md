# Update existing user

**URL** : `/api/users/{id}`

**Method** : `/PUT`

**Path parameters** : `id`

**Query parameters** : None

## Success Response 

**Code** : `200 OK`

**Request body**

```json
{
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
    "userId": 1,
    "email": "niilo.partanen@gmail.com",
    "firstName": "Niilo",
    "lastName": "Partanen",
    "password": "määlyaivo22",
    "address": "Vattajantie 14, Lohtaja",
    "phone": "0506887045",
    "userrole": null
}
```

## Error Response 

**Code** : `404 NOT FOUND`