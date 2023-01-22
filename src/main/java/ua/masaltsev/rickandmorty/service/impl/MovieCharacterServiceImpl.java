package ua.masaltsev.rickandmorty.service.impl;

import org.springframework.stereotype.Service;
import ua.masaltsev.rickandmorty.dto.ApiCharacterDto;
import ua.masaltsev.rickandmorty.dto.ApiResponseDto;
import ua.masaltsev.rickandmorty.dto.mapper.MovieCharacterMapper;
import ua.masaltsev.rickandmorty.model.MovieCharacter;
import ua.masaltsev.rickandmorty.repository.MovieCharacterRepository;
import ua.masaltsev.rickandmorty.service.HttpClient;
import ua.masaltsev.rickandmorty.service.MovieCharacterService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository repository;
    private final MovieCharacterMapper mapper;

    public MovieCharacterServiceImpl(HttpClient client, MovieCharacterRepository repository,
                                     MovieCharacterMapper mapper) {
        this.httpClient = client;
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public void syncExternalCharacters() {
        ApiResponseDto apiResponseDto = httpClient.get("https://rickandmortyapi.com/api/character",
                ApiResponseDto.class);
        saveDtoToDb(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtoToDb(apiResponseDto);
        }
    }

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
