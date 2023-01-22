package ua.masaltsev.rickandmorty.dto.mapper;

import org.springframework.stereotype.Component;
import ua.masaltsev.rickandmorty.dto.ApiCharacterDto;
import ua.masaltsev.rickandmorty.model.Gender;
import ua.masaltsev.rickandmorty.model.MovieCharacter;
import ua.masaltsev.rickandmorty.model.Status;

@Component
public class MovieCharacterMapper {
    public MovieCharacter toModel(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setName(dto.getName());
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setExternalId(dto.getId());
        return movieCharacter;
    }
}
