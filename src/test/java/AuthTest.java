import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        $("[name='login']").setValue(registeredUser.getLogin());
        $("[name='password']").setValue(registeredUser.getPassword());
        $(".button").click();
        $("h2").shouldHave(Condition.exactText("Личный кабинет"), Condition.visible);

    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        $("[name='login']").setValue(notRegisteredUser.getLogin());
        $("[name='password']").setValue(notRegisteredUser.getPassword());
        $(".button").click();
        $("[data-test-id='error-notification'] .notification__title").shouldHave(Condition.exactText("Ошибка"), Condition.visible);
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Condition.visible);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[name='login']").setValue(blockedUser.getLogin());
        $("[name='password']").setValue(blockedUser.getPassword());
        $(".button").click();
        $("[data-test-id='error-notification'] .notification__title").shouldHave(Condition.exactText("Ошибка"), Condition.visible);
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"), Condition.visible);
    }


    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongLogin = DataGenerator.getRandomLogin();
        $("[name='login']").setValue(wrongLogin);
        $("[name='password']").setValue(registeredUser.getPassword());
        $(".button").click();
        $("[data-test-id='error-notification'] .notification__title").shouldHave(Condition.exactText("Ошибка"), Condition.visible);
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Condition.visible);
    }


    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongPassword = DataGenerator.getRandomPassword();
        $("[name='login']").setValue(registeredUser.getLogin());
        $("[name='password']").setValue(wrongPassword);
        $(".button").click();
        $("[data-test-id='error-notification'] .notification__title").shouldHave(Condition.exactText("Ошибка"), Condition.visible);
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Condition.visible);
    }
}
