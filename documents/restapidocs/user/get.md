# Show all users

**URL** : `/api/users`

**Method** : `/GET`

**Path parameters** : None

**Query parameters** : None

## Success Response 

**Code** : `200 OK`

**Response body**

```json
[
    {
        "userId": 1,
        "email": "pekka.puupaa@hotmail.com",
        "firstName": "Pekka",
        "lastName": "Puupää",
        "password": "khfdnslfåpdfj",
        "address": "Kaivokatu 1, Helsinki",
        "phone": "4587624",
        "userrole": null
    },
    {
        "userId": 2,
        "email": "mikko.mallikas@hotmail.com",
        "firstName": "Mikko",
        "lastName": "Mallikas",
        "password": "dfhdgjfsfgj",
        "address": "Työnjohtajankatu 7, Helsinki",
        "phone": "4587624",
        "userrole": null
    }
]
```

# Show user by ID

**URL** : `/events/{id}`

**Method** : `GET`

**Path parameters** : `id`

**Query parameters** : None

## Success Response

**Code** : `200 OK`

**Response body**

```json
{
    "userId": 1,
    "email": "pekka.puupaa@hotmail.com",
    "firstName": "Pekka",
    "lastName": "Puupää",
    "password": "khfdnslfåpdfj",
    "address": "Kaivokatu 1, Helsinki",
    "phone": "4587624",
    "userrole": null
}
```

## Error Response 

**Code** : `404 NOT FOUND`