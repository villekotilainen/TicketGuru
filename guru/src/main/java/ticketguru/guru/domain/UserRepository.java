package ticketguru.guru.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<TGUser, Long> {

    TGUser findByEmail(String email);

    TGUser findByFirstNameAndLastName(String firstName, String lastName);
}
