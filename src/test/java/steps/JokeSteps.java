package steps;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import context.HttpScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import lombok.val;
import model.Joke;
import model.JokeMessage;
import service.JokeService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JokeSteps {

    private final JokeService jokeService;
    private final HttpScenarioContext scenarioContext;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public JokeSteps(HttpScenarioContext scenarioContext,
                     JokeService jokeService) {
        this.jokeService = jokeService;
        this.scenarioContext = scenarioContext;
    }

    @When("user sends request to get a random joke")
    public void userGetsARandomJoke() {
        jokeService.getRandomJoke();
    }

    @When("user sends request to get a random {string} joke")
    public void userSendsRequestToGetARandomProgrammingJoke(String type) {
        jokeService.getRandomJokeByType(type);
    }

    @When("user sends request to get a random joke without type")
    public void userSendsRequestToGetARandomJokeWithoutType() {
        jokeService.getRandomJokeWithoutType();
    }

    @When("user sends request to get ten random {string} jokes")
    public void userSendsRequestToGetTenRandomProgrammingJokes(String type) {
        jokeService.getTenRandomJokesByType(type);
    }

    @SneakyThrows
    @And("joke is valid")
    public void jokeIsValid() {
        val httpResponse = scenarioContext.getLastResponse().extract().body().asString();
        val apiResponse = OBJECT_MAPPER.readValue(httpResponse, Joke.class);
        assertThat(apiResponse.getId(), is(not(nullValue())));
    }

    @SneakyThrows
    @And("all jokes are valid")
    public void allJokesAreValid() {
        val httpResponse = scenarioContext.getLastResponse().extract().body().asInputStream();
        val apiResponse = OBJECT_MAPPER.readValue(httpResponse, new TypeReference<List<Joke>>() { });
        assertThat(apiResponse, is(not(empty())));
        apiResponse.forEach(r -> assertThat(r.getId(), is(not(nullValue()))));
    }

    @When("user sends request to get a random joke by id {int}")
    public void userSendsRequestToGetARandomJokeById(int id) {
        jokeService.getJokeById(id);
    }

    @SneakyThrows
    @And("message with type {string} should say {string}")
    public void errorMessageIsCorrect(String type, String message) {
        val httpResponse = scenarioContext.getLastResponse().extract().body().asInputStream();
        val apiResponse = OBJECT_MAPPER.readValue(httpResponse, JokeMessage.class);
        assertThat(apiResponse.getType(), is(type));
        assertThat(apiResponse.getMessage(), is(message));
    }

    @SneakyThrows
    @And("response should be empty")
    public void responseShouldBeEmpty() {
        val httpResponse = scenarioContext.getLastResponse().extract().body().asInputStream();
        val apiResponse = OBJECT_MAPPER.readValue(httpResponse, new TypeReference<List<Joke>>() { });
        assertThat(apiResponse, is(empty()));
    }
}
