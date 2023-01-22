package ua.masaltsev.rickandmorty.service;

import ua.masaltsev.rickandmorty.model.MovieCharacter;
import java.util.List;

public interface MovieCharacterService {
    void syncExternalCharacters();
    MovieCharacter getRandomCharacter();
    List<MovieCharacter> findAllByNameContains(String namePart);
}
