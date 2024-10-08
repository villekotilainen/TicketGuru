# Update existing venue

**URL** : `/api/venues`

**Method** : `/PUT`

**Path parameters** : `id`

**Query parameters** : None

## Success Response 

**Code** : `200 OK`

**Request body**

```json
{
    "venueName": "Testi",
    "address": "Testi1234",
    "venueDescription": "TestiTesti."
}
```

**Response body**

```json
{
    "venueId": 3,
    "venueName": "Testi",
    "address": "Testi1234",
    "venueDescription": "TestiTesti."
}
```

## Error Response 

**Code** : `404 NOT FOUND`