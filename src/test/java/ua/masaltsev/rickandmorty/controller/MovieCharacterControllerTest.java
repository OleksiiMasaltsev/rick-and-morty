package ua.masaltsev.rickandmorty.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.masaltsev.rickandmorty.model.Gender;
import ua.masaltsev.rickandmorty.model.MovieCharacter;
import ua.masaltsev.rickandmorty.model.Status;
import ua.masaltsev.rickandmorty.service.MovieCharacterService;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MovieCharacterControllerTest {
    @MockBean
    private MovieCharacterService movieCharacterService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void loadCharactersToDb() {
    }

    @Test
    void getRandom() {
        MovieCharacter movieCharacter = new MovieCharacter(
                8L, 8L, "Adjudicator Rick", Status.DEAD, Gender.MALE);
        Mockito.when(movieCharacterService.getRandomCharacter()).thenReturn(movieCharacter);

        RestAssuredMockMvc
                .when()
                    .get("/movie-characters/random")
                .then()
                    .statusCode(200)
                    .body("id", Matchers.equalTo(8))
                    .body("externalId", Matchers.equalTo(8))
                    .body("name", Matchers.equalTo("Adjudicator Rick"))
                    .body("status", Matchers.equalTo(Status.DEAD.name()))
                    .body("gender", Matchers.equalTo(Gender.MALE.name()));
    }

    @Test
    void findAllByNameContains_validParam_ok() {
        List<MovieCharacter> movieCharacterList = List.of(
                new MovieCharacter(8L, 8L, "Adjudicator Rick", Status.DEAD, Gender.MALE),
                new MovieCharacter(379L, 379L, "Wedding Bartender", Status.UNKNOWN, Gender.MALE),
                new MovieCharacter(563L, 578L, "Snake Soldier", Status.ALIVE, Gender.FEMALE)
        );
        String name = "di";
        Mockito.when(movieCharacterService.findAllByNameContains(name)).thenReturn(movieCharacterList);

        RestAssuredMockMvc
                .given()
                    .queryParam("name", name)
                .when()
                    .get("/movie-characters/by-name")
                .then()
                    .statusCode(200)
                    .body("size()", Matchers.equalTo(3))
                    .body("[0].id", Matchers.equalTo(8))
                    .body("[0].externalId", Matchers.equalTo(8))
                    .body("[0].name", Matchers.equalTo("Adjudicator Rick"))
                    .body("[0].status", Matchers.equalTo(Status.DEAD.name()))
                    .body("[0].gender", Matchers.equalTo(Gender.MALE.name()))
                    .body("[1].id", Matchers.equalTo(379))
                    .body("[1].externalId", Matchers.equalTo(379))
                    .body("[1].name", Matchers.equalTo("Wedding Bartender"))
                    .body("[1].status", Matchers.equalTo(Status.UNKNOWN.name()))
                    .body("[1].gender", Matchers.equalTo(Gender.MALE.name()))
                    .body("[2].id", Matchers.equalTo(563))
                    .body("[2].externalId", Matchers.equalTo(578))
                    .body("[2].name", Matchers.equalTo("Snake Soldier"))
                    .body("[2].status", Matchers.equalTo(Status.ALIVE.name()))
                    .body("[2].gender", Matchers.equalTo(Gender.FEMALE.name()));
    }

    @AfterEach
    void tearDown() {
    }
}