package RepositoryTests;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ticketguru.guru.GuruApplication;
import ticketguru.guru.Entities.TGUser;
import ticketguru.guru.Entities.Transaction;
import ticketguru.guru.Repositories.TransactionRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(classes = GuruApplication.class)
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        // Sample user
        TGUser user = new TGUser();
        user.setUserId(1L);
        user.setFirstName("testuser");

        // Save the user to the database (if user is a separate entity)
        // Assuming you have a TGUserRepository, uncomment the following line:
        // tgUserRepository.save(user);

        // Sample transactions
        Transaction transaction1 = new Transaction();
        transaction1.setUser(user);
        transaction1.setTransactionDate(LocalDateTime.now()); 
        transaction1.setSucceeded(true);

        Transaction transaction2 = new Transaction();
        transaction2.setUser(user);
        transaction2.setTransactionDate(LocalDateTime.now());
        transaction2.setSucceeded(false);

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);
    }

   

    @Test
    void findByUser_shouldReturnTransactionsForGivenUser() {
        TGUser user = new TGUser();
        user.setUserId(1L);
        user.setFirstName("testuser");

        List<Transaction> transactions = transactionRepository.findByUser(user);
        assertThat(transactions).isNotEmpty();
        assertThat(transactions.size()).isEqualTo(2);
    }

    @Test
    void findBySucceeded_shouldReturnTransactionsWithSucceededStatus() {
        List<Transaction> succeededTransactions = transactionRepository.findBySucceeded(true);
        List<Transaction> failedTransactions = transactionRepository.findBySucceeded(false);

        assertThat(succeededTransactions).hasSize(1);
        assertThat(failedTransactions).hasSize(1);
    }

    @Test
    void findByTransactionDate_shouldReturnTransactionsForGivenDate() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS); // Truncate to seconds for consistency

        // Save transactions with the exact same timestamp
        Transaction transaction1 = new Transaction();
        transaction1.setUser(new TGUser(1L, "testuser", null, null, null, null, null, null)); // Assuming TGUser constructor exists
        transaction1.setTransactionDate(now); // Exact timestamp
        transaction1.setSucceeded(true);

        Transaction transaction2 = new Transaction();
        transaction2.setUser(new TGUser(1L, "testuser", null, null, null, null, null, null));
        transaction2.setTransactionDate(now); // Exact timestamp
        transaction2.setSucceeded(false);

        transactionRepository.save(transaction1);
        transactionRepository.save(transaction2);

        // Query for the exact timestamp
        List<Transaction> transactions = transactionRepository.findByTransactionDate(now);

        // Assertions
        assertThat(transactions).isNotEmpty();
        assertThat(transactions.size()).isEqualTo(2); // Expect 2 transactions
    }

}
