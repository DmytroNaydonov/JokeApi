package service;

import config.ConfigHolder;
import context.HttpScenarioContext;

import static io.restassured.RestAssured.given;
import static io.restassured.http.Method.*;

public class JokeService extends BaseRestService {

    public JokeService(ConfigHolder configHolder, HttpScenarioContext httpScenarioContext) {
        super(configHolder, httpScenarioContext);
    }

    public void getRandomJoke() {
        request(
                given(),
                GET,
                "/random_joke"
        );
    }

    public void getRandomJokeByType(String type) {
        request(
                given(),
                GET,
                String.format("/jokes/%s/random", type)
        );
    }

    public void getRandomJokeWithoutType() {
        request(
                given(),
                GET,
                "/jokes//random"
        );
    }

    public void getTenRandomJokesByType(String type) {
        request(
                given(),
                GET,
                String.format("/jokes/%s/ten", type)
        );
    }

    public void getJokeById(int id) {
        request(
                given(),
                GET,
                String.format("/jokes/%d", id)
        );
    }
}
