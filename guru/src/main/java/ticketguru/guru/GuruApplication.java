package ticketguru.guru;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Entities.TGUser;
import ticketguru.guru.Entities.Ticket;
import ticketguru.guru.Entities.TicketType;
import ticketguru.guru.Entities.Transaction;
import ticketguru.guru.Entities.Userrole;
import ticketguru.guru.Entities.Venue;
import ticketguru.guru.Repositories.EventRepository;
import ticketguru.guru.Repositories.TGUserRepository;
import ticketguru.guru.Repositories.TicketRepository;
import ticketguru.guru.Repositories.TicketTypeRepository;
import ticketguru.guru.Repositories.TransactionRepository;
import ticketguru.guru.Repositories.UserroleRepository;
import ticketguru.guru.Repositories.VenueRepository;

@SpringBootApplication
public class GuruApplication {

    private static final Logger log = LoggerFactory.getLogger(GuruApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GuruApplication.class, args);
    }

    @Bean
    public CommandLineRunner createStarterData(
            EventRepository eventRepository,
            VenueRepository venueRepository,
            TGUserRepository tgUserRepository,
            TransactionRepository transactionRepository,
            UserroleRepository userroleRepository,
            TicketRepository ticketRepository,
            TicketTypeRepository ticketTypeRepository,
            PasswordEncoder passwordEncoder) {
        return (args) -> {
            log.info("Creating initial data...");

            // Create and save venues
            Venue venue1 = new Venue(null, "Suvilahti", "Sörnäisten rantatie 22, Helsinki", "Suvilahden tapahtumakenttä.");
            Venue venue2 = new Venue(null, "Ruissalo", "Kansanpuistontie 20, Turku", "Ruissalo on Turun kaupunkiin kuuluva saari.");
            Venue venue3 = new Venue(null, "Ratinan stadion", "Ratinan rantatie 1, Tampere", "Tampereella Ratinan kaupungiosassa sijaitseva stadion");
            venueRepository.save(venue1);
            venueRepository.save(venue2);
            venueRepository.save(venue3);

            // Create and save events
            Event event1 = new Event(null, "Tuska", LocalDate.of(2025, 6, 27), LocalDate.of(2025, 6, 29), "Tuska Festivaali on pohjoismaiden suurimpia raskaanmusiikin tapahtumia.", venue1);
            Event event2 = new Event(null, "Ruisrock", LocalDate.of(2025, 8, 16), LocalDate.of(2025, 8, 17), "Ruisrock on Turun Ruissalossa järjestettävä musiikkifestivaali.", venue2);
            Event event3 = new Event(null, "Blockfest", LocalDate.of(2025, 7, 4), LocalDate.of(2025, 7, 6), "Pohjoismaiden suurin hiphop-festivaali.", venue3);
            eventRepository.save(event1);
            eventRepository.save(event2);
            eventRepository.save(event3);

            // Create and save user roles
            Userrole adminRole = new Userrole(null, "ADMIN");
            Userrole salespersonRole = new Userrole(null, "SALESPERSON");
            userroleRepository.save(adminRole);
            userroleRepository.save(salespersonRole);

            // Create and save users with usernames "salesperson" and "admin" and respective roles
            TGUser tgUser1 = new TGUser(null, "salesperson@gmail.com", "Pekka", "Puupää", 
                    passwordEncoder.encode("salesperson"), "Kaivokatu 1, Helsinki", "4587624", salespersonRole);
            TGUser tgUser2 = new TGUser(null, "admin@gmail.com", "Mikko", "Mallikas", 
                    passwordEncoder.encode("admin"), "Työnjohtajankatu 7, Helsinki", "4587624", adminRole);
            tgUserRepository.save(tgUser1);
            tgUserRepository.save(tgUser2);

            // Create and save tickets
            Ticket ticket1 = new Ticket(null, "b3991bbf55624bef783856baec821d86", LocalDateTime.of(2024, 10, 5, 12, 12));
            Ticket ticket2 = new Ticket(null, "4c9a8daae6643d5bd51f43c917d90150", LocalDateTime.of(2024, 1, 6, 11, 13));
            ticketRepository.save(ticket1);
            ticketRepository.save(ticket2);

            // Create and save transactions
            Transaction transaction1 = new Transaction(null, LocalDateTime.of(2024, 10, 5, 12, 12), 22.90, true, tgUser1);
            Transaction transaction2 = new Transaction(null, LocalDateTime.of(2024, 9, 6, 11, 15), 25.20, true, tgUser2);
            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);

            TicketType ticketType1 = new TicketType(null, 22.90, "Peruslippu", 100, event1);
            TicketType ticketType2 = new TicketType(null, 25.20, "VIP-lippu", 50, event1);

            TicketType ticketType3 = new TicketType(null, 22.90, "Peruslippu", 100, event2);
            TicketType ticketType4 = new TicketType(null, 25.20, "VIP-lippu", 50, event2);

            TicketType ticketType5 = new TicketType(null, 22.90, "Peruslippu", 100, event3);
            TicketType ticketType6 = new TicketType(null, 25.20, "VIP-lippu", 50, event3);

            ticketTypeRepository.save(ticketType1);
            ticketTypeRepository.save(ticketType2);
            ticketTypeRepository.save(ticketType3);
            ticketTypeRepository.save(ticketType4);
            ticketTypeRepository.save(ticketType5);
            ticketTypeRepository.save(ticketType6);

            log.info("Sample data created successfully.");
        };
    }
}
