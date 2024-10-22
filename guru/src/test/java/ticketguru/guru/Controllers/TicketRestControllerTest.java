package ticketguru.guru.Controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;

import ticketguru.guru.Entities.Ticket;
import ticketguru.guru.Repositories.TicketRepository;

@WebMvcTest(controllers = TicketRestController.class)
public class TicketRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private CommandLineRunner commandLineRunner; 


    //Testaataam että GET-pyyntö osoitteeseen /api/tickets/1 palauttaa oikean HTTP-statuksen ja vastauksen, kun lippu löytyy tietokannasta.
    @Test
    public void testGetTicketById() throws Exception {
        Ticket ticket = new Ticket(1L, "hashcode1", null);
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        mockMvc.perform(get("/api/tickets/1"))
            .andExpect(status().isOk()); //200 Ok palautetaan
    }

    //Testataan että POST-pyyntö osoitteeseen /api/tickets luo uuden lipun ja palauttaa oikean HTTP-statuksen (201 Created).
    @Test
    public void testCreateTicket() throws Exception {
        Ticket newTicket = new Ticket(null, "hashcode3", null);
        Ticket savedTicket = new Ticket(1L, "hashcode3", null);

        when(ticketRepository.save(newTicket)).thenReturn(savedTicket);

        mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"hashcode\": \"hashcode3\"}"))
                .andExpect(status().isCreated()); //201 Created palautetaan
    }

    // Yritetään hakea lippua jota ei ole olamassa
    @Test
    public void testGetTicketByIdNotFound() throws Exception {
    // Simuloidaan, että tietokanta ei löydä lippua ID:llä 99
    when(ticketRepository.findById(99L)).thenReturn(Optional.empty());

    // Teehdään GET-pyyntö lipulle, jota ei ole olemassa, ja varmista että 404 palautetaan
    mockMvc.perform(get("/api/tickets/99"))
        .andExpect(status().isNotFound()); // 404 Not Found palautetaan
    }

    //Yritetään luoda lippu puutteellisilla tai virheellisillä tiedoilla
    @Test
    public void testCreateTicketBadRequest() throws Exception {
    // Lähetetään POST-pyyntö ilman pakollisia kenttiä
    mockMvc.perform(post("/api/tickets")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}")) 
            .andExpect(status().isBadRequest()); // Palauttaa 400 Bad Request 
    }

    //Testataan lipun poistamista
    @Test
    public void testDeleteTicket() throws Exception {
    // Simuloidaan lipun löytyminen ja poistaminen
    Ticket ticket = new Ticket(1L, "hashcode1", null);
    when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));
    
    // Mockataan, että lippu löytyy ennen poistamista
    when(ticketRepository.existsById(1L)).thenReturn(true);

    // Teehdään DELETE-pyyntö ja varmista että 204 No Content palautetaan
    mockMvc.perform(delete("/api/tickets/1"))
        .andExpect(status().isNoContent()); // Palauttaa 204 No Content
    }


    //Testataan olemassa olevan lipun päivittämistä
    @Test
    public void testUpdateTicket() throws Exception {
    // Simuloidaan olemassa oleva lippu
    Ticket existingTicket = new Ticket(1L, "hashcode1", null);
    Ticket updatedTicket = new Ticket(1L, "updatedHashcode", null);

    when(ticketRepository.findById(1L)).thenReturn(Optional.of(existingTicket));
    when(ticketRepository.save(existingTicket)).thenReturn(updatedTicket);

    // Tehdään PUT-pyyntö lipun päivittämiseksi ja varmistetaan että 200 OK palautetaan
    mockMvc.perform(put("/api/tickets/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"ticketId\": 1, \"hashcode\": \"updatedHashcode\"}"))
            .andExpect(status().isOk()); // 200 OK palautetaan
    }

    //Yritään poistaa olematon lippu
    @Test
    public void testDeleteTicketNotFound() throws Exception {
        // Simuloidaan, että lippua ID:llä 99 ei löydy
        when(ticketRepository.findById(99L)).thenReturn(Optional.empty());
    
        // Tehdään DELETE-pyyntö olemattomalle lipulle ja varmistetaan että 404 palautetaan
        mockMvc.perform(delete("/api/tickets/99"))
            .andExpect(status().isNotFound()); // 404 Not Found palautetaan
    }
    



}
