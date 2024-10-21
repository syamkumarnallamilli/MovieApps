package MovieApp.MovieApp.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DemoController {

    // Get Mapping to return "Hello World"
    @GetMapping("/getdata")
    public String getdata() {
        return "Hello World";
    }

    // Post Mapping to return "Hello {name}"
    @PostMapping("/postdata/{name}")
    public String postdata(@PathVariable String name) {
        return "Hello " + name;
    }
}
