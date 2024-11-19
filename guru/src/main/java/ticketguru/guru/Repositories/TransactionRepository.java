package ticketguru.guru.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ticketguru.guru.Entities.TGUser;
import ticketguru.guru.Entities.Transaction;

import java.util.List;
import java.time.LocalDateTime;
import java.util.Date;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(TGUser user);
    List<Transaction> findBySucceeded(Boolean succeeded);
    List<Transaction> findByTransactionDate(LocalDateTime transactionDate);
}
