package pl.sda.filmrepository.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginFormController {
    @GetMapping("/app/login")
    public String login() {
        return "login";
    }
}
