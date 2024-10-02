# Update existing event

**URL** : `/api/events`

**Method** : `/PUT`

**Path parameters** : `id`

**Query parameters** : None

## Success Response 

**Code** : `200 OK`

**Request body**

```json
{
  "eventId": 1,
  "eventName": "Testi",
  "startTime": "2025-07-25",
  "endTime": "2025-07-27",
  "eventDescription": "Testi",
  "venue": {"venueId": 2}
}
```

**Response body**

```json
{
    "eventId": 1,
    "eventName": "Testi",
    "startTime": "2025-07-25",
    "endTime": "2025-07-27",
    "eventDescription": "Testi",
    "venue": {
        "venueId": 2,
        "venueName": null,
        "address": null,
        "venueDescription": null
    }
}
```

## Error Response 

**Code** : `404 NOT FOUND`