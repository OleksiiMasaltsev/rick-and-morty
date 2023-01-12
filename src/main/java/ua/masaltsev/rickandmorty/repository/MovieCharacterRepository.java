package ua.masaltsev.rickandmorty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.masaltsev.rickandmorty.model.MovieCharacter;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
}
