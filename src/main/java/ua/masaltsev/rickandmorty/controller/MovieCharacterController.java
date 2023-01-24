package ua.masaltsev.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.rickandmorty.dto.CharacterResponseDto;
import ua.masaltsev.rickandmorty.dto.mapper.MovieCharacterMapper;
import ua.masaltsev.rickandmorty.model.MovieCharacter;
import ua.masaltsev.rickandmorty.service.MovieCharacterService;
import java.util.List;

@RestController
@RequestMapping("/movie-characters")
@Tag(description = "endpoints to store and receive characters info",
        name = "character controller")
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper mapper;

    public MovieCharacterController(MovieCharacterService movieCharacterService,
                                    MovieCharacterMapper mapper) {
        this.movieCharacterService = movieCharacterService;
        this.mapper = mapper;
    }

    @GetMapping("/load")
    @Operation(summary = "load data to the DB")
    public String loadCharactersToDb() {
        movieCharacterService.syncExternalCharacters();
        return "Done!";
    }

    @GetMapping("/random")
    @Operation(summary = "get a random character out of DB")
    public CharacterResponseDto getRandom() {
        MovieCharacter character = movieCharacterService.getRandomCharacter();
        return mapper.toDto(character);
    }

    @GetMapping("by-name")
    @Operation(summary = "find all characters whose name contains input string")
    public List<CharacterResponseDto> findAllByNameContains(
            @RequestParam("name") String namePart) {
        return movieCharacterService.findAllByNameContains(namePart).stream()
                .map(mapper::toDto)
                .toList();
    }
}
