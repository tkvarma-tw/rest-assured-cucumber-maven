package stepDefinitions;

import apiEngine.model.requests.AuthorizationRequest;
import cucumber.TestContext;
import io.cucumber.java.en.Given;

public class AccountSteps extends BaseSteps {
    public AccountSteps(TestContext testContext){
        super(testContext);
    }

    @Given("^I am an authorized user$")
    public void iAmAnAuthorizedUser() {
        AuthorizationRequest authRequest = new AuthorizationRequest("Test", "Testing@12345");
        getEndPoints().authenticateUser(authRequest);
    }
}
