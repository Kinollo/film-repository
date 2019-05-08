package pl.sda.filmrepository;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

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
    Suggestion addSuggestion(@RequestBody Suggestion suggestion, Principal principal){
        suggestion.setAuthor(principal.getName());
        return suggestioRepository.save(suggestion);
    }

    @GetMapping(params = "author")
    Iterable<Suggestion> findSuggestionByAuthor (@RequestParam(name = "author", required = true) String author){
        return suggestioRepository.findByAuthor(author);
    }

    @GetMapping("/{id}")
    Optional<Suggestion> findById (@PathVariable Long id){
        return suggestioRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    void deleteSuggestionById (@PathVariable Long id){
        suggestioRepository.deleteById(id);
    }

}
