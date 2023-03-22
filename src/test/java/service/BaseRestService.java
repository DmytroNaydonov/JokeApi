package service;

import config.ConfigHolder;
import context.HttpScenarioContext;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;

public class BaseRestService {

    private final HttpScenarioContext httpScenarioContext;
    private final String url;

    public BaseRestService(ConfigHolder configHolder,
                           HttpScenarioContext httpScenarioContext) {
        this.httpScenarioContext = httpScenarioContext;
        this.url = configHolder.getConfig().getUrl();
    }

    public void request(RequestSpecification specification, Method method, String endpoint) {
        httpScenarioContext.saveResponse(
                specification
                        .when()
                        .log()
                        .all()
                        .request(method, url + endpoint)
                        .then()
                        .log()
                        .all()
        );
    }
}
