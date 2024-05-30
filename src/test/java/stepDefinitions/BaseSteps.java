package stepDefinitions;

import apiEngine.EndPoints;
import cucumber.ScenarioContext;
import cucumber.TestContext;

public class BaseSteps {
    private ScenarioContext scenarioContext;
    private EndPoints endPoints;

    public BaseSteps(TestContext testContext) {
        scenarioContext = testContext.getScenarioContext();
        endPoints = testContext.getEndPoints();
    }

    public EndPoints getEndPoints() {
        return endPoints;
    }
    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}
