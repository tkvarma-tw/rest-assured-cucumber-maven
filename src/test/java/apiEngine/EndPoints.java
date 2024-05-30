package apiEngine;

import apiEngine.model.requests.AddBookRequest;
import apiEngine.model.requests.AuthorizationRequest;
import apiEngine.model.requests.RemoveBookRequest;
import apiEngine.model.responses.Token;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndPoints {
    private final RequestSpecification request;

    public EndPoints(String baseUrl) {
        RestAssured.baseURI = baseUrl;
        request = RestAssured.given().relaxedHTTPSValidation();
        request.header("Content-Type", "application/json");
    }
    public void authenticateUser(AuthorizationRequest authRequest) {

        Response response = request.body(authRequest).post(Route.generateToken());
        Token tokenResponse = response.body().jsonPath().getObject("$", Token.class);
        request.header("Authorization", "Bearer " + tokenResponse.token);
    }

    public Response getBooks() {
        Response response = request.get(Route.books());
        return response;
    }

    public Response addBook(AddBookRequest addBooksRequest) {

        Response response = request.body(addBooksRequest).post(Route.books());
        return response;
    }

    public Response removeBook(RemoveBookRequest removeBookRequest) {

        return request.body(removeBookRequest).delete(Route.book());
    }

    public Response getUserAccount(String userId) {
        return request.get(Route.userAccount(userId));
    }
}
