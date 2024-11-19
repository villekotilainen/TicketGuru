package ticketguru.guru.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ticketguru.guru.Entities.Venue;
import ticketguru.guru.Repositories.VenueRepository;

@RestController
@RequestMapping("/api/venues") // Base path for venues-related requests
public class VenueRestController {

    @Autowired
    private VenueRepository venueRepository;

    // GET: Hae kaikki tapahtumapaikat
    @GetMapping
    public List<Venue> getAllVenues() {
        return venueRepository.findAll();
    }

    // GET: Hae yksittäinen tapahtumapaikka ID:n perusteella
    @GetMapping("/{id}")
    public ResponseEntity<Venue> getVenueById(@PathVariable Long id) {
        Optional<Venue> venue = venueRepository.findById(id);
        return venue.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST: Lisää uusi tapahtumapaikka
    @PostMapping
    public ResponseEntity<Venue> createVenue(@RequestBody Venue newVenue) {
        Venue savedVenue = venueRepository.save(newVenue);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVenue);
    }

    // PUT: Päivitä olemassa oleva tapahtumapaikka
    @PutMapping("/{id}")
    public ResponseEntity<Venue> updateVenue(@PathVariable Long id, @RequestBody Venue venueDetails) {
        Optional<Venue> optionalVenue = venueRepository.findById(id);

        if (optionalVenue.isPresent()) {
            Venue existingVenue = optionalVenue.get();
            existingVenue.setVenueName(venueDetails.getVenueName());
            existingVenue.setAddress(venueDetails.getAddress());
            existingVenue.setVenueDescription(venueDetails.getVenueDescription());

            Venue updatedVenue = venueRepository.save(existingVenue);
            return ResponseEntity.ok(updatedVenue);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE: Poista tapahtumapaikka ID:n perusteella
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenue(@PathVariable Long id) {
        if (venueRepository.existsById(id)) {
            venueRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
