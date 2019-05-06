package pl.sda.filmrepository;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {

    SuggestioRepository suggestioRepository;

    public SuggestionController(SuggestioRepository suggestioRepository) {
        this.suggestioRepository = suggestioRepository;
    }

    @GetMapping
    Iterable<Suggestion> getAllSuggestions(){
        return suggestioRepository.findAll();
    }

    @PostMapping
    void addSuggestion(@RequestBody Suggestion suggestion){
        suggestioRepository.save(suggestion);

    }

}
