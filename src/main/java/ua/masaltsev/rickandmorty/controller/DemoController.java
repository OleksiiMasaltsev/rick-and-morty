package ua.masaltsev.rickandmorty.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.masaltsev.rickandmorty.dto.ApiResponseDto;
import ua.masaltsev.rickandmorty.service.HttpClient;

@RestController
@RequestMapping("/demo")
@Log4j2
public class DemoController {
    private final HttpClient httpClient;

    public DemoController(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @GetMapping
    public String runDemo() {
        ApiResponseDto responseDto = httpClient.get("https://rickandmortyapi.com/api/character",
                ApiResponseDto.class);
        log.info("API response {}", responseDto);
        return "Done!";
    }
}
