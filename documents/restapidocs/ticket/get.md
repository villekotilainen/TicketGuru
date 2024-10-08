# Show all tickets

**URL** : `/api/tickets`

**Method** : `/GET`

**Path parameters** : None

**Query parameters** : None

## Success Response 

**Code** : `200 OK`

**Response body**

```json
[
    {
        "ticketId": 1,
        "hashcode": "b3991bbf55624bef783856baec821d86",
        "ticketUsedDate": "2024-10-05T12:12:00",
        "transaction": null,
        "ticketType": null
    },
    {
        "ticketId": 2,
        "hashcode": "4c9a8daae6643d5bd51f43c917d90150",
        "ticketUsedDate": "2024-01-06T11:13:00",
        "transaction": null,
        "ticketType": null
    }
]
```

# Show ticket by ID

**URL** : `/tickets/{id}`

**Method** : `GET`

**Path parameters** : `id`

**Query parameters** : None

## Success Response

**Code** : `200 OK`

**Response body**

```json
{
    "ticketId": 1,
    "hashcode": "b3991bbf55624bef783856baec821d86",
    "ticketUsedDate": "2024-10-05T12:12:00",
    "transaction": null,
    "ticketType": null
}
```

## Error Response 

**Code** : `404 NOT FOUND`