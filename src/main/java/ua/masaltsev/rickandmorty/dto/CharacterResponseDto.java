package ua.masaltsev.rickandmorty.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.masaltsev.rickandmorty.model.Gender;
import ua.masaltsev.rickandmorty.model.Status;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
}
