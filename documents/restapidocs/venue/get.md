# Show all venues

**URL** : `/api/venues`

**Method** : `/GET`

**Path parameters** : None

**Query parameters** : None

## Success Response 

**Code** : `200 OK`

**Response body**

```json
[
    {
        "venueId": 1,
        "venueName": "Suvilahti",
        "address": "Sörnäisten rantatie 22, Helsinki",
        "venueDescription": "Suvilahden tapahtumakenttä."
    },
    {
        "venueId": 2,
        "venueName": "Ruissalo",
        "address": "Kansanpuistontie 20, Turku",
        "venueDescription": "Ruissalo on Turun kaupunkiin kuuluva saari."
    },
    {
        "venueId": 3,
        "venueName": "Ratinan stadion",
        "address": "Ratinan rantatie 1, Tampere",
        "venueDescription": "Tampereella Ratinan kaupungiosassa sijaitseva stadion"
    }
]
```

# Show venue by ID

**URL** : `/venues/{id}`

**Method** : `GET`

**Path parameters** : `id`

**Query parameters** : None

## Success Response

**Code** : `200 OK`

**Response body**

```json
{
    "venueId": 1,
    "venueName": "Suvilahti",
    "address": "Sörnäisten rantatie 22, Helsinki",
    "venueDescription": "Suvilahden tapahtumakenttä."
}
```

## Error Response 

**Code** : `404 NOT FOUND`