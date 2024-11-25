package ticketguru.guru.Services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ticketguru.guru.Entities.TGUser;
import ticketguru.guru.Entities.Ticket;
import ticketguru.guru.Entities.TicketType;
import ticketguru.guru.Entities.Transaction;
import ticketguru.guru.Repositories.TGUserRepository;
import ticketguru.guru.Repositories.TicketRepository;
import ticketguru.guru.Repositories.TicketTypeRepository;
import ticketguru.guru.Repositories.TransactionRepository;
import ticketguru.guru.dto.TicketDTO;
import ticketguru.guru.dto.TransactionDTO;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Autowired
    private TGUserRepository tgUserRepository;

    @Transactional
    public Transaction createTransactionWithTickets(List<Long> ticketTypeIds, Long userId) {
    // Step 1: Validate user
    TGUser user = tgUserRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

    // Step 2: Fetch ticket types and create tickets
    List<Ticket> tickets = ticketTypeIds.stream().map(ticketTypeId -> {
        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket type ID: " + ticketTypeId));
        Ticket ticket = new Ticket();
        ticket.setHashcode(UUID.randomUUID().toString()); // Generate a unique hashcode
        ticket.setTicketType(ticketType);
        return ticket;
    }).collect(Collectors.toList());

    // Step 3: Calculate total sum
    double totalSum = tickets.stream()
            .mapToDouble(ticket -> ticket.getTicketType().getTicketPrice())
            .sum();

    // Step 4: Create and save the transaction
    Transaction transaction = new Transaction(totalSum, user);
    Transaction savedTransaction = transactionRepository.save(transaction); // Save transaction

    // Step 5: Associate tickets with the saved transaction
    tickets.forEach(ticket -> ticket.setTransaction(savedTransaction));

    // Step 6: Save updated tickets
    ticketRepository.saveAll(tickets);

    // Step 7: Set tickets in the saved transaction and return it
    savedTransaction.setTickets(tickets);
    return savedTransaction;
}

    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public boolean deleteTransactionById(Long id) {
        if (transactionRepository.existsById(id)) {
            transactionRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Transaction saveTransaction(Transaction existingTransaction) {
        return transactionRepository.save(existingTransaction);
    }

    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setTransactionId(transaction.getTransactionId());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setTotalSum(transaction.getTotalSum());
        dto.setSucceeded(transaction.getSucceeded());
        dto.setUserId(transaction.getUser() != null ? transaction.getUser().getUserId() : null);

        List<TicketDTO> ticketDTOs = transaction.getTickets().stream().map(ticket -> {
            TicketDTO ticketDTO = new TicketDTO();
            ticketDTO.setTicketId(ticket.getTicketId());
            ticketDTO.setHashcode(ticket.getHashcode());
            ticketDTO.setTicketTypeId(ticket.getTicketType().getTicketTypeId());
            ticketDTO.setTicketPrice(ticket.getTicketType().getTicketPrice());
            return ticketDTO;
        }).collect(Collectors.toList());

        dto.setTickets(ticketDTOs);
        return dto;
    }
}
