package ua.masaltsev.rickandmorty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.masaltsev.rickandmorty.model.MovieCharacter;
import java.util.List;
import java.util.Set;

public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
    List<MovieCharacter> findAllByExternalIdIn(Set<Long> externalIds);
    List<MovieCharacter> findAllByNameContains(String namePart);
}
