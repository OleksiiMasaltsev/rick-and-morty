package ua.masaltsev.rickandmorty.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.rickandmorty.model.MovieCharacter;
import ua.masaltsev.rickandmorty.service.HttpClient;

@RestController
@RequestMapping("/demo")
public class DemoController {
    private final HttpClient httpClient;

    public DemoController(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @GetMapping
    public String runDemo() {
        httpClient.get("https://rickandmortyapi.com/api/character", MovieCharacter.class);
        return "Done!";
    }
}
