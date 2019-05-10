package pl.sda.filmrepository.event;

import lombok.Value;
import pl.sda.filmrepository.model.Suggestion;

import java.time.Instant;

@Value
public class SuggestionCreatedEvent {
    private Instant timestamp;
    private Suggestion createdSuggestion;
}
