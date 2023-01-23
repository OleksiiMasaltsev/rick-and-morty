package ua.masaltsev.rickandmorty.controller;

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
public class MovieCharacterController {
    private final MovieCharacterService movieCharacterService;
    private final MovieCharacterMapper mapper;

    public MovieCharacterController(MovieCharacterService movieCharacterService,
                                    MovieCharacterMapper mapper) {
        this.movieCharacterService = movieCharacterService;
        this.mapper = mapper;
    }

    @GetMapping("/load")
    public String runDemo() {
        movieCharacterService.syncExternalCharacters();
        return "Done!";
    }

    @GetMapping("/random")
    public CharacterResponseDto getRandom() {
        MovieCharacter character = movieCharacterService.getRandomCharacter();
        return mapper.toDto(character);
    }

    @GetMapping("by-name")
    public List<CharacterResponseDto> findAllByNameContains(
            @RequestParam("name") String namePart) {
        return movieCharacterService.findAllByNameContains(namePart).stream()
                .map(mapper::toDto)
                .toList();
    }
}
