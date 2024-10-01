package ticketguru.guru.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ticketguru.guru.Entities.Userrole;

@Repository
public interface UserroleRepository extends JpaRepository<Userrole, Long> {

    Userrole findByRole(String role);
}
