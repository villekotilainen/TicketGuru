package ticketguru.guru.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ticketguru.guru.Entities.TGUser;

@Repository
public interface TGUserRepository extends JpaRepository<TGUser, Long> {

    TGUser findByEmail(String email);

    TGUser findByFirstNameAndLastName(String firstName, String lastName);
}
