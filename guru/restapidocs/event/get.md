# Show all events

**Base URL** : `

**URL** : `/events`

**Method** : `/GET`

**Path parameters** : None

**Query parameters** : None

## Success Response 

**Code** : `200 OK`

**Response body**

```json
[
  {
    "eventId": 1,
    "eventName": "Tuska",
    "startTime": "2025-06-27",
    "endTime": "2025-06-29",
    "eventDescription": "Tuska Festivaali on pohjoismaiden suurimpia raskaanmusiikin tapahtumia.",
    "venue": {
      "venueId": 1,
      "venueName": "Suvilahti",
      "address": "Sörnäisten rantatie 22, Helsinki",
      "venueDescription": "Suvilahden tapahtumakenttä."
    }
  },
  {
    "eventId": 2,
    "eventName": "Ruisrock",
    "startTime": "2025-08-16",
    "endTime": "2025-08-17",
    "eventDescription": "Ruisrock on Turun Ruissalossa heinäkuun ensimmäisenä viikonloppuna järjestettävä Suomen merkittävimpiin festivaaleihin kuuluva musiikkifestivaali.",
    "venue": {
      "venueId": 2,
      "venueName": "Ruissalo",
      "address": "Kansanpuistontie 20, Turku",
      "venueDescription": "Ruissalo on Turun kaupunkiin kuuluva saari."
    }
  },
  {
    "eventId": 3,
    "eventName": "Blockfest",
    "startTime": "2025-07-04",
    "endTime": "2025-07-06",
    "eventDescription": "Ratinan stadionin ja Ratinanniemen tapahtumapuiston alueella vuosittain järjestettävä Pohjoismaiden suurin hiphop-festivaali.",
    "venue": {
      "venueId": 3,
      "venueName": "Ratinan stadion",
      "address": "Ratinan rantatie 1, Tampere",
      "venueDescription": "Tampereella Ratinan kaupungiosassa sijaitseva stadion"
    }
  }
]
```

# Show one event

**URL** : `/events/{id}`

**Method** : `GET`

**Path parameters** : `id`

**Query parameters** : None

## Success Response

**Code** : `200 OK`

**Response body**

```json
{
  "eventId": 1,
  "eventName": "Tuska",
  "startTime": "2025-06-27",
  "endTime": "2025-06-29",
  "eventDescription": "Tuska Festivaali on pohjoismaiden suurimpia raskaanmusiikin tapahtumia.",
  "venue": {
    "venueId": 1,
    "venueName": "Suvilahti",
    "address": "Sörnäisten rantatie 22, Helsinki",
    "venueDescription": "Suvilahden tapahtumakenttä."
  }
}
```





