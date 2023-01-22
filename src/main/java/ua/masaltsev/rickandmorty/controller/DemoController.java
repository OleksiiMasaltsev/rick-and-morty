package ua.masaltsev.rickandmorty.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.rickandmorty.service.MovieCharacterService;

// TODO remove the controller
@RestController
@RequestMapping("/demo")
@Log4j2
public class DemoController {
    private final MovieCharacterService movieCharacterService;

    public DemoController(MovieCharacterService movieCharacterService) {
        this.movieCharacterService = movieCharacterService;
    }

    @GetMapping
    public String runDemo() {
        movieCharacterService.syncExternalCharacters();
        return "Done!";
    }
}
