package pl.sda.filmrepository.dto;

import lombok.Data;

@Data
public class CreateSuggestionDTO {
    String title;
    String link;
    int score;

}
