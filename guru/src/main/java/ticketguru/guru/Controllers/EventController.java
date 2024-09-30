package ticketguru.guru.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import ticketguru.guru.Entities.Event;
import ticketguru.guru.Repositories.EventRepository;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    //GET
    @RequestMapping(value = "/events", method=RequestMethod.GET)
    public @ResponseBody List<Event> getEvents() {
        List <Event> events = (List<Event>) eventRepository.findAll();
        return events;
    }

    @RequestMapping(value="/events/{id}", method=RequestMethod.GET)
    public @ResponseBody Optional<Event> getOneEvent(
        @PathVariable(name ="id") Long eventId) {
            return eventRepository.findById(eventId);
    }

    //POST
    @RequestMapping(value="/events", method=RequestMethod.POST)
    public ResponseEntity<Event> saveNewEvent(@RequestBody Event newEvent){
        Event savedEvent = eventRepository.save(newEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEvent);
    }
}
