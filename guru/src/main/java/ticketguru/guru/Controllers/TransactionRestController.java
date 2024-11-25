package ticketguru.guru.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ticketguru.guru.Entities.Transaction;
import ticketguru.guru.Services.TransactionService;
import ticketguru.guru.dto.TransactionDTO;
import ticketguru.guru.dto.TransactionRequest;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRestController {

    @Autowired
    private TransactionService transactionService;

    // GET: Fetch all transactions
    @GetMapping
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // GET: Fetch a single transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(transactionService::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // POST: Create a new transaction
    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request) {
        try {
            Transaction transaction = transactionService.createTransactionWithTickets(request.getTicketTypeIds(), request.getUserId());
            return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.convertToDTO(transaction));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the transaction.");
        }
    }

    // PUT: Update an existing transaction
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDetails) {
        Optional<Transaction> optionalTransaction = transactionService.getTransactionById(id);

        if (optionalTransaction.isPresent()) {
            Transaction existingTransaction = optionalTransaction.get();
            existingTransaction.setTransactionDate(transactionDetails.getTransactionDate());
            existingTransaction.setTotalSum(transactionDetails.getTotalSum());
            existingTransaction.setSucceeded(transactionDetails.getSucceeded());

            Transaction updatedTransaction = transactionService.saveTransaction(existingTransaction);
            return ResponseEntity.ok(transactionService.convertToDTO(updatedTransaction));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // DELETE: Delete a transaction by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        if (transactionService.deleteTransactionById(id)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
