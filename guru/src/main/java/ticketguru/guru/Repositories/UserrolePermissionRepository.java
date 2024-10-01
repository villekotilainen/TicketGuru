package ticketguru.guru.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ticketguru.guru.Entities.UserrolePermissions;

@Repository
public interface UserrolePermissionRepository extends JpaRepository <UserrolePermissions, Long>{

}
