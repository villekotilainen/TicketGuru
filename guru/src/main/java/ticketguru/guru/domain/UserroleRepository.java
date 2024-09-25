package ticketguru.guru.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserroleRepository extends JpaRepository<Userrole, Long> {

    Userrole findByRole(String role);
}
