package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.*;
import io.restassured.specification.*;
import java.util.Locale;
import static io.restassured.RestAssured.given;

public class DataGenerator {
    private DataGenerator() {
    }

    public static class Registration {
        private Registration() {
        }

        private static Faker faker = new Faker(new Locale("en"));

        private static final RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();

        private static void sendRequest(RegistrationInfo user) {
            given() // "дано"
                    .spec(requestSpec)
                    .body(new Gson().toJson(user))
                    .when()
                    .post("/api/system/users")
                    .then()
                    .statusCode(200);
        }

        public static String getRandomLogin() {
            String login = faker.name().firstName();
            return login;
        }

        public static String getRandomPassword() {
            String password = faker.internet().password();
            return password;
        }

        public static RegistrationInfo getUser(String status) {
            RegistrationInfo user = new RegistrationInfo(getRandomLogin(), getRandomPassword(), status);
            return user;
        }

        public static RegistrationInfo getRegisteredUser(String status) {
            RegistrationInfo registeredUser = getUser(status);
            sendRequest(registeredUser);
            return registeredUser;
        }
    }
}
