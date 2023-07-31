package com.demoQa.tests;

import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.baseURI;

public abstract class BaseTest {

    @BeforeAll
    public  static void init(){
        baseURI="https://demoqa.com";
    }
}
