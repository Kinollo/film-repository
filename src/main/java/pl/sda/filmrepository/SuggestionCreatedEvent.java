package pl.sda.filmrepository;

import lombok.Value;

import java.time.Instant;

@Value
public class SuggestionCreatedEvent {
    private Instant timestamp;
    private Suggestion createdSuggestion;
}
