# Update existing ticket

**URL** : `/api/tickets/{id}`

**Method** : `/PUT`

**Path parameters** : `id`

**Query parameters** : None

## Success Response 

**Code** : `200 OK`

**Request body**

```json
{
    "hashcode": "62742b11b766e3f6958108e57734d823",
    "ticketUsedDate": "2024-10-08T11:09:20",
    "transaction": null,
    "ticketType": null
}
```

**Response body**

```json
{
    "ticketId": 1,
    "hashcode": "62742b11b766e3f6958108e57734d823",
    "ticketUsedDate": "2024-10-08T11:09:20",
    "transaction": null,
    "ticketType": null
}
```

## Error Response 

**Code** : `404 NOT FOUND`