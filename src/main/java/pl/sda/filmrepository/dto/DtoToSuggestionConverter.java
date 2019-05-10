package pl.sda.filmrepository.dto;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.sda.filmrepository.Suggestion;

@Component
public class DtoToSuggestionConverter implements Converter<CreateSuggestionDTO, Suggestion> {
    @Override
    public Suggestion convert(CreateSuggestionDTO createSuggestionDTO) {
        Suggestion suggestion = new Suggestion();
        suggestion.setLink(createSuggestionDTO.getLink());
        suggestion.setTitle(createSuggestionDTO.getTitle());
        suggestion.setScore(createSuggestionDTO.getScore());
        return suggestion;
    }
}
