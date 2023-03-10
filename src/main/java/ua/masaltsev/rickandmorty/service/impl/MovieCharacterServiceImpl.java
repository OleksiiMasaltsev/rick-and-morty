package ua.masaltsev.rickandmorty.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.masaltsev.rickandmorty.dto.external.ApiCharacterDto;
import ua.masaltsev.rickandmorty.dto.external.ApiResponseDto;
import ua.masaltsev.rickandmorty.dto.mapper.MovieCharacterMapper;
import ua.masaltsev.rickandmorty.model.MovieCharacter;
import ua.masaltsev.rickandmorty.repository.MovieCharacterRepository;
import ua.masaltsev.rickandmorty.service.MovieCharacterService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Log4j2
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private static final String URL_CHARACTER = "https://rickandmortyapi.com/api/character";
    private final HttpClient httpClient;
    private final MovieCharacterRepository repository;
    private final MovieCharacterMapper mapper;

    public MovieCharacterServiceImpl(HttpClient client, MovieCharacterRepository repository,
                                     MovieCharacterMapper mapper) {
        this.httpClient = client;
        this.repository = repository;
        this.mapper = mapper;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 8 * * *")
    @Override
    public void syncExternalCharacters() {
        log.info("syncExternalCharacters was called at " + LocalDateTime.now());
        ApiResponseDto apiResponseDto = httpClient.get(URL_CHARACTER, ApiResponseDto.class);
        saveDtoToDb(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtoToDb(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long count = repository.count();
        long randomId = (long) (Math.random() * count);
        return repository.getReferenceById(randomId);
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return repository.findAllByNameContains(namePart);
    }

    // TODO Test the method
    private void saveDtoToDb(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtoMap = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));
        Set<Long> externalIds = externalDtoMap.keySet();

        List<MovieCharacter> existingCharacters = repository.findAllByExternalIdIn(externalIds);

        Map<Long, MovieCharacter> existingDtoMap = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));
        Set<Long> existingIds = existingDtoMap.keySet();

        externalIds.removeAll(existingIds);

        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> mapper.toModel(externalDtoMap.get(i)))
                .toList();
        repository.saveAll(charactersToSave);
    }
}
