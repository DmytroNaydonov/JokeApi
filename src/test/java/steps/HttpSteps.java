package steps;

import context.HttpScenarioContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.val;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static utils.ResourceUtils.readAsStream;

public class HttpSteps {

    private final HttpScenarioContext scenarioContext;

    public HttpSteps(HttpScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Then("response status should be {int}")
    public void responseStatusShouldBe(int status) {
        val response = scenarioContext.getLastResponse().extract().statusCode();
        assertThat(response, is(status));
    }

    @And("returned JSON should match the schema {string}")
    public void returnedJSONShouldMatchTheSchema(String schemaFilePath) {
        val is = readAsStream(schemaFilePath, HttpSteps.class);
        scenarioContext.getLastResponse().assertThat().body(matchesJsonSchema(is));
    }
}
