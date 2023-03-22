package context;

import io.restassured.response.ValidatableResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HttpScenarioContext {

    private final List<ValidatableResponse> responses = new ArrayList<>();
    private ValidatableResponse lastResponse;

    public ValidatableResponse saveResponse(ValidatableResponse response) {
        this.responses.add(response);
        this.lastResponse = response;
        return response;
    }
}
