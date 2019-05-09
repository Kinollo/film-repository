package pl.sda.filmrepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.sda.filmrepository.dto.CreateSuggestionDTO;

@Controller
@RequestMapping("/app/suggestions")
public class AllSuggestionsViewController {
    SuggestionService service;

    public AllSuggestionsViewController(SuggestionService service) {
        this.service = service;
    }

    @GetMapping
    String allSuggestions(Model model){
        model.addAttribute("allSuggestions",service.getAllSuggestions());
        return "suggestions";
    }

    @PostMapping
    String addSuggestion (CreateSuggestionDTO suggestion){
        service.addSuggestion(suggestion);
        return "redirect:/app/suggestions";
    }


}
