package cucumber;

import apiEngine.EndPoints;
import enums.Context;

public class TestContext {
    private final String BASE_URL = "https://bookstore.toolsqa.com";
    private String USER_ID = "42c805a2-43be-41fb-8e55-ace802556298";
    private EndPoints endPoints;
    private ScenarioContext scenarioContext;

    public TestContext() {
        scenarioContext = new ScenarioContext();
        endPoints = new EndPoints(BASE_URL);
        scenarioContext.setContext(Context.USER_ID, USER_ID);
    }

    public EndPoints getEndPoints() {
        return endPoints;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }
}
