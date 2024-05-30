package stepDefinitions;

import apiEngine.model.Book;
import apiEngine.model.requests.AddBookRequest;
import apiEngine.model.requests.ISBN;
import apiEngine.model.requests.RemoveBookRequest;
import apiEngine.model.responses.Books;
import apiEngine.model.responses.Token;
import apiEngine.model.responses.UserAccount;
import cucumber.TestContext;
import enums.Context;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;

public class BooksSteps extends BaseSteps{
    private Response response;
    public BooksSteps(TestContext testContext){
        super(testContext);
    }

    @Given("A list of books are available")
    public void a_list_of_books_are_available() {
        response = getEndPoints().getBooks();
        Book book = response.getBody().as(Books.class).books.get(4);
        getScenarioContext().setContext(Context.BOOK,book);
    }
    @When("I add a book to my reading list")
    public void i_add_a_book_to_my_reading_list() {
        Book book = (Book) getScenarioContext().getContext(Context.BOOK);
        String userId = (String) getScenarioContext().getContext(Context.USER_ID);
        ISBN isbn = new ISBN(book.isbn);
        AddBookRequest addBooksRequest = new AddBookRequest(userId, isbn);
        response = getEndPoints().addBook(addBooksRequest);
        getScenarioContext().setContext(Context.USER_ACCOUNT_RESPONSE, response);
    }
    @Then("the book is added")
    public void the_book_is_added() {
        Book book = (Book) getScenarioContext().getContext(Context.BOOK);
        String userId = (String) getScenarioContext().getContext(Context.USER_ID);
        Response UserResponse = (Response) getScenarioContext().getContext(Context.USER_ACCOUNT_RESPONSE);
        Assert.assertEquals(201, UserResponse.getStatusCode());
        Assert.assertEquals(book.isbn, UserResponse.body().jsonPath().getObject("$", Books.class).books.get(0).isbn);
    }
    @When("I remove a book from my reading list")
    public void i_remove_a_book_from_my_reading_list() {
        Book book = (Book) getScenarioContext().getContext(Context.BOOK);
        String userId = (String) getScenarioContext().getContext(Context.USER_ID);

        RemoveBookRequest removeBookRequest = new RemoveBookRequest(userId, book.isbn);
        response = getEndPoints().removeBook(removeBookRequest);
        getScenarioContext().setContext(Context.BOOK_REMOVE_RESPONSE, response);
    }
    @Then("the book is removed")
    public void the_book_is_removed() {
        String userId = (String) getScenarioContext().getContext(Context.USER_ID);
        Response response = (Response) getScenarioContext().getContext(Context.BOOK_REMOVE_RESPONSE);

        Assert.assertEquals(204, response.getStatusCode());

        response = getEndPoints().getUserAccount(userId);
        Assert.assertEquals(200, response.getStatusCode());

        UserAccount userAccount = response.getBody().as(UserAccount.class);
        Assert.assertEquals(0, userAccount.books.size());
    }

}
