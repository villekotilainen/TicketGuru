package RepositoryTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import ticketguru.guru.GuruApplication;
import ticketguru.guru.Entities.Venue;
import ticketguru.guru.Repositories.VenueRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@DataJpaTest
@ContextConfiguration(classes = GuruApplication.class)
public class VenueRepositoryTest {

    @Autowired
    private VenueRepository venueRepository;

    @BeforeEach
    void setUp() {
        // Set up sample venues
        Venue venue1 = new Venue();
        venue1.setVenueName("Concert Hall");
        venue1.setAdress("123 Music Avenue");

        Venue venue2 = new Venue();
        venue2.setVenueName("Sports Arena");
        venue2.setAdress("456 Sports Lane");

        Venue venue3 = new Venue();
        venue3.setVenueName("Theater District");
        venue3.setAdress("789 Art Street");

        venueRepository.save(venue1);
        venueRepository.save(venue2);
        venueRepository.save(venue3);
    }

    @Test
    void findByVenueName_shouldReturnCorrectVenue() {
        // Act
        Venue result = venueRepository.findByVenueName("Concert Hall");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getVenueName()).isEqualTo("Concert Hall");
        assertThat(result.getAddress()).isEqualTo("123 Music Avenue");
    }

    @Test
    void findByVenueName_shouldReturnNullForNonExistentVenue() {
        // Act
        Venue result = venueRepository.findByVenueName("Nonexistent Venue");

        // Assert
        assertThat(result).isNull();
    }

    @Test
    void findByAddressContaining_shouldReturnVenuesWithMatchingAddress() {
        // Act
        List<Venue> results = venueRepository.findByAddressContaining("Lane");

        // Assert
        assertThat(results).isNotEmpty();
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getVenueName()).isEqualTo("Sports Arena");
    }

    @Test
    void findByAddressContaining_shouldReturnEmptyListForNonMatchingAddress() {
        // Act
        List<Venue> results = venueRepository.findByAddressContaining("Boulevard");

        // Assert
        assertThat(results).isEmpty();
    }

    @Test
    void repository_shouldSaveAndRetrieveVenues() {
        // Arrange
        Venue newVenue = new Venue();
        newVenue.setVenueName("Opera House");
        newVenue.setAdress("321 Performance Circle");
        venueRepository.save(newVenue);

        // Act
        Venue retrievedVenue = venueRepository.findByVenueName("Opera House");

        // Assert
        assertThat(retrievedVenue).isNotNull();
        assertThat(retrievedVenue.getVenueName()).isEqualTo("Opera House");
        assertThat(retrievedVenue.getAddress()).isEqualTo("321 Performance Circle");
    }
}
