package com.demoQa.tests;

import com.demoQa.pojo.AllBooks;
import com.demoQa.pojo.Book;
import com.demoQa.pojo.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.Test;
import utilities.ConfigReader;

import io.restassured.http.*;
import net.serenitybdd.junit5.*;
import net.serenitybdd.rest.*;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.rest.SerenityRest.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SerenityTest
public class Tests extends BaseTest {

    static User user1 = new User();
    static String userID;
    static String token;

    @DisplayName("Create a user")
    @Test
    public void test1() {

        Faker faker = new Faker();
        String userName = ConfigReader.getProperty("user_name") + faker.number().numberBetween(1, 10);
        System.out.println("userName = " + userName);

        user1.setUserName(userName);
        user1.setPassword(ConfigReader.getProperty("user_password"));

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(user1).log().all()
                .when().post("/Account/v1/User")
                .then().extract().response();

        response.prettyPrint();
        userID = response.jsonPath().getString("userID");
        System.out.println("userID = " + userID);

        Ensure.that("UserID not null", vRes -> vRes.body("userID", is(notNullValue())));

        System.out.println(ConfigReader.getProperty("base.url"));
    }

    @DisplayName("Generate Authentication Token")
    @Test
    @Order(2)
    public void generateAuthenticationTest(){

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(user1).log().all()
                .when().post("/Account/v1/GenerateToken")
                .then().statusCode(200);

        Ensure.that("Generate Authentication Token", valRes -> valRes.body("token",is(notNullValue())));

        System.out.println("token = " + token);

    }

    @DisplayName("Get List of Books")
    @Test
    @Order(3)
    public void getListOfBooksTest(){

        given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .when().get("/BookStore/v1/Books")
                .then().statusCode(200).extract().response().as(AllBooks.class);

        Ensure.that("UserID not null", valRes -> {
            AllBooks books = valRes.extract().response().as(AllBooks.class);
            System.out.println("books.getBooks().get(0) = " + books.getBooks().get(0));
            for (Book book : books.getBooks()) {
                assertThat(book.getAuthor(), is(notNullValue()));
                assertThat(book.getIsbn(), is(notNullValue()));
                assertThat(book.getDescription(), is(notNullValue()));
                assertThat(book.getPages(), is(notNullValue()));
                assertThat(book.getWebsite(), is(notNullValue()));
                assertThat(book.getSubTitle(), is(notNullValue()));
                assertThat(book.getPublish_date(), is(notNullValue()));
                assertThat(book.getPublisher(), is(notNullValue()));
            }
        });

    }

}
