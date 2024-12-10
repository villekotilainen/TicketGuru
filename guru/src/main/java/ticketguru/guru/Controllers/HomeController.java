package ticketguru.guru.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/myynticlient")
    public String myynticlient() {
        return "myynticlient";
    }

    @GetMapping("/editevent")
    public String editevent() {
        return "editevent";
    }

    @GetMapping("/events/{eventId}/edit")
    public String editEvent(@PathVariable Long eventId) {
        return "muokkaa-tapahtumaa";
    }

    @GetMapping("/uusi-tapahtuma")
    public String uusiTapahtuma() {
        return "uusi-tapahtuma";
    }

    @GetMapping("/raportti")
    public String raportti() {
        return "raportti";
    }

    @GetMapping("/luo-kayttaja")
    public String luoKayttaja() {
        return "luo-kayttaja";
    }

    @GetMapping("/kayttajat")
    public String kayttajat() {
        return "kayttajat";
    }

    @GetMapping("/lipun-tarkastus")
    public String lipunTarkastus() {
        return "lipun-tarkastus";
    }
}
