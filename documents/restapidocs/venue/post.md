# Create new venue

**URL** : `/api/venues`

**Method** : `/POST`

**Path parameters** : None

**Query parameters** : None

## Success Response 

**Code** : `201 CREATED`

**Request body**

```json
{
    "venueId": 4,
    "venueName": "Helsingin jäähalli",
    "address": "Nordenskiöldinkatu 11-13",
    "venueDescription": "Helsingin jäähalli."
}
```

**Response body**

```json
{
    "venueId": 4,
    "venueName": "Helsingin jäähalli",
    "address": "Nordenskiöldinkatu 11-13",
    "venueDescription": "Helsingin jäähalli."
}
```