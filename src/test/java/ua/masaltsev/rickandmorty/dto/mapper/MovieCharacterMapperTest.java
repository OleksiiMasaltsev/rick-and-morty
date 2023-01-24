package ua.masaltsev.rickandmorty.dto.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.masaltsev.rickandmorty.dto.CharacterResponseDto;
import ua.masaltsev.rickandmorty.dto.external.ApiCharacterDto;
import ua.masaltsev.rickandmorty.model.Gender;
import ua.masaltsev.rickandmorty.model.MovieCharacter;
import ua.masaltsev.rickandmorty.model.Status;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieCharacterMapperTest {
    private static final String RICK_NAME = "Rick";
    private MovieCharacterMapper mapper;
    private final Gender male = Gender.MALE;
    private final Status alive = Status.ALIVE;
    @BeforeEach
    void setUp() {
        mapper = new MovieCharacterMapper();
    }

    @Test
    void toModel_ok() {
        ApiCharacterDto dto = new ApiCharacterDto();
        dto.setId(1L);
        dto.setName(RICK_NAME);
        dto.setGender(male.toString());
        dto.setStatus(alive.toString());

        MovieCharacter actual = mapper.toModel(dto);

        MovieCharacter expected = new MovieCharacter();
        expected.setExternalId(1L);
        expected.setName(RICK_NAME);
        expected.setGender(male);
        expected.setStatus(alive);

        assertEquals(expected.getExternalId(), actual.getExternalId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getGender(), actual.getGender());
    }

    @Test
    void toDto_ok() {
        MovieCharacter character = new MovieCharacter();
        character.setId(2L);
        character.setExternalId(1L);
        character.setName(RICK_NAME);
        character.setGender(male);
        character.setStatus(alive);

        CharacterResponseDto actual = mapper.toDto(character);

        ApiCharacterDto expected = new ApiCharacterDto();
        expected.setId(1L);
        expected.setName(RICK_NAME);
        expected.setGender(male.toString());
        expected.setStatus(alive.toString());

        assertEquals(expected.getId(), actual.getExternalId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getStatus(), actual.getStatus().toString());
        assertEquals(expected.getGender(), actual.getGender().toString());
    }
}
