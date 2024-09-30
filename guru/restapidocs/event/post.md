# Create new event

**URL** : `/event`

**Method** : `/POST`

**Path parameters** : None

**Query parameters** : None

## Success Response 

**Code** : `201 CREATED`

**Request body**

```json
{
  "eventId": 4,
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
    "eventId": 4,
    "eventName": "Testi",
    "startTime": "2025-07-25",
    "endTime": "2025-07-27",
    "eventDescription": "Testi",
    "venue": {
        "venueId": 2,
        "venueName": "Ruissalo",
        "address": "Kansanpuistontie 20, Turku",
        "venueDescription": "Ruissalo on Turun kaupunkiin kuuluva saari."
    }
}
```