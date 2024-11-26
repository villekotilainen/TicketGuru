package ticketguru.guru.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; 
    }

    @GetMapping("/sell")
    public String myynticlient() {
        return "myynticlient"; 
    }

    @GetMapping("/editevent")
    public String editevent() {
        return "editevent";
    }
    
}

