package ru.netology.delivery.test;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.delivery.data.DataGenerator;
import static com.codeborne.selenide.Selenide.*;

class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @AfterEach
    void memoryClear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    //зарегистрированный пользователь имеет доступ к личному кабинету
    @Test
    void shouldSendFormRegisteredActive(){
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(Condition.exactText("  Личный кабинет"));
    }

    //зарегистрированный пользователь заблокирован
    @Test
    void shouldSendFormRegisteredBlocked() {
        var validUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Пользователь заблокирован"));
    }

    //зарегистрированный пользователь рандомный логин
    @Test
    void shouldSendFormRegisteredRandomLogin() {
        var validUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id='password'] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    //зарегистрированный пользователь рандомный пароль
    @Test
    void shouldSendFormRegisteredRandomPassword() {
        var validUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    //зарегистрированный пользователь рандомный логин и пароль
    @Test
    void shouldSendFormRegisteredRandomLoginAndPassword() {
        var validUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id='password'] .input__box .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }
    //незарегистрированный пользователь рандомный логин
    @Test
    void shouldSendFormRandomLogin(){
        var invalidUser = DataGenerator.Registration.getUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id='password'] .input__box .input__control").val(invalidUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    //незарегистрированный пользователь рандомный пароль
    @Test
    void shouldSendFormRandomPassword(){
        var invalidUser = DataGenerator.Registration.getUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(invalidUser.getLogin());
        $("[data-test-id='password'] .input__box .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    //незарегистрированный пользователь рандомные логин и пароль
    @Test
    void shouldSendFormRandomLoginAndPassword() {
        var invalidUser = DataGenerator.Registration.getUser("blocked");
        $("[data-test-id='login'] .input__box .input__control").val(DataGenerator.Registration.getRandomLogin());
        $("[data-test-id='password'] .input__box .input__control").val(DataGenerator.Registration.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! " + "Неверно указан логин или пароль"));
    }

    //Зарегистрированный пользователь пустое поле логина
    @Test
    void shouldSendFormWithoutlogin() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val();
        $("[data-test-id=password] .input__box .input__control").val(validUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    //зарегистрированный пользователь пустое поле пароля
    @Test
    void shouldSendFormWithoutPassword() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val(validUser.getLogin());
        $("[data-test-id=password] .input__box .input__control").val();
        $("[data-test-id=action-login]").click();
        $("[data-test-id=password].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    //зарегистрированный пользователь пустые поля логина и пароля
    @Test
    void shouldSendFormWithoutLoginAndPassword() {
        var validUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id=login] .input__box .input__control").val();
        $("[data-test-id=password] .input__box .input__control").val();
        $("[data-test-id=action-login]").click();
        $("[data-test-id=login].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
        $("[data-test-id=password].input_invalid .input__sub")
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }
}
