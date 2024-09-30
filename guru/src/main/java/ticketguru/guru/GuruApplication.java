package ticketguru.guru;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.Venue;
import ticketguru.guru.Repositories.EventRepository;
import ticketguru.guru.Repositories.VenueRepository;

@SpringBootApplication
public class GuruApplication {

	private static final Logger log = LoggerFactory.getLogger(GuruApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GuruApplication.class, args);
	}

	@Bean
	public CommandLineRunner createStarterData(EventRepository eventRepository, VenueRepository venueRepository) {
		return (args) -> {
			log.info("Creating event");
			Venue venue1 = new Venue(1L, "Suvilahti", "Sörnäisten rantatie 22, Helsinki", "Suvilahden tapahtumakenttä.");
			Venue venue2 = new Venue(2L, "Ruissalo", "Kansanpuistontie 20, Turku", "Ruissalo on Turun kaupunkiin kuuluva saari.");
			Venue venue3 = new Venue(3L, "Ratinan stadion", "Ratinan rantatie 1, Tampere", "Tampereella Ratinan kaupungiosassa sijaitseva stadion");
			venueRepository.save(venue1);
			venueRepository.save(venue2);
			venueRepository.save(venue3);
			Event event1 = new Event(1L, "Tuska", LocalDate.of(2025,6,27), LocalDate.of(2025,6,29), "Tuska Festivaali on pohjoismaiden suurimpia raskaanmusiikin tapahtumia.", venue1);
			Event event2 = new Event(2L, "Ruisrock", LocalDate.of(2025,8,16), LocalDate.of(2025,8,17), "Ruisrock on Turun Ruissalossa heinäkuun ensimmäisenä viikonloppuna järjestettävä Suomen merkittävimpiin festivaaleihin kuuluva musiikkifestivaali.", venue2);
			Event event3 = new Event(3L, "Blockfest", LocalDate.of(2025,7,4), LocalDate.of(2025,7,6), "Ratinan stadionin ja Ratinanniemen tapahtumapuiston alueella vuosittain järjestettävä Pohjoismaiden suurin hiphop-festivaali.", venue3);
			eventRepository.save(event1);
			eventRepository.save(event2);
			eventRepository.save(event3);
		};
	}
}