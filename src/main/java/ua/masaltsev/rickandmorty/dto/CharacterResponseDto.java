package ua.masaltsev.rickandmorty.dto;

import lombok.Data;
import ua.masaltsev.rickandmorty.model.Gender;
import ua.masaltsev.rickandmorty.model.Status;

@Data
public class CharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
