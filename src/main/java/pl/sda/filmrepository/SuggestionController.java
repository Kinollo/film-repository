package pl.sda.filmrepository;

import org.springframework.web.bind.annotation.*;
import pl.sda.filmrepository.dto.CreateSuggestionDTO;

import java.util.Optional;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {

    private SuggestionService service;

    public SuggestionController(SuggestionService service) {
        this.service = service;
    }

    @GetMapping
    Iterable<Suggestion> getAllSuggestions(){
        return service.getAllSuggestions();
    }

    @PostMapping
    Suggestion addSuggestion(@RequestBody CreateSuggestionDTO createSuggestionDTO){
        return service.addSuggestion(createSuggestionDTO);
    }

    @GetMapping(params = "author")
    Iterable<Suggestion> findSuggestionByAuthor (@RequestParam(name = "author", required = true) String author){
        return service.findSuggestionByAuthor(author);
    }

    @GetMapping("/{id}")
    Optional<Suggestion> findById (@PathVariable Long id){
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    void deleteSuggestionById (@PathVariable Long id){
        service.deleteSuggestionById(id);
    }

}
