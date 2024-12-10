package ticketguru.guru.Services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    TGUser user = tgUserRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));

    // Group ticketTypeIds by their counts
    Map<Long, Long> ticketTypeCountMap = ticketTypeIds.stream()
        .collect(Collectors.groupingBy(id -> id, Collectors.counting()));

    List<Ticket> tickets = new ArrayList<>();

    for (Map.Entry<Long, Long> entry : ticketTypeCountMap.entrySet()) {
        Long ticketTypeId = entry.getKey();
        Long requestedCount = entry.getValue();

        TicketType ticketType = ticketTypeRepository.findById(ticketTypeId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid ticket type ID: " + ticketTypeId));

        // Synchronized block for atomic decrement
        synchronized (ticketType) {
            if (!ticketType.hasSufficientTickets(requestedCount.intValue())) {
                throw new IllegalStateException("Not enough tickets available for ticket type ID: " + ticketTypeId);
            }

            // Deduct from totalCount directly
            ticketType.setTotalCount(ticketType.getTotalCount() - requestedCount.intValue());
            ticketTypeRepository.save(ticketType);
        }

        // Create tickets
        for (int i = 0; i < requestedCount; i++) {
            Ticket ticket = new Ticket();
            ticket.setHashcode(UUID.randomUUID().toString());
            ticket.setTicketType(ticketType);
            tickets.add(ticket);
        }
    }

    // Calculate total sum
    double totalSum = tickets.stream()
        .mapToDouble(ticket -> ticket.getTicketType().getTicketPrice())
        .sum();

    // Create and save the transaction
    Transaction transaction = new Transaction(totalSum, user);
    Transaction savedTransaction = transactionRepository.save(transaction);

    // Link tickets to the transaction
    tickets.forEach(ticket -> ticket.setTransaction(savedTransaction));
    ticketRepository.saveAll(tickets);

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

    public List<TransactionDTO> getTransactionsByEventId(Long eventId) {
        List<Transaction> transactions = transactionRepository.findAll();
    
        // Suodata transaktiot tapahtuman perusteella
        return transactions.stream()
                .filter(transaction -> transaction.getTickets().stream()
                        .anyMatch(ticket -> ticket.getTicketType().getEvent().getEventId().equals(eventId)))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
}
