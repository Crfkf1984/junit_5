package guru.qa;


import chapter.ChapterPage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.openqa.selenium.By;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ManyTestByParametersTest extends BaseTest {

    @ParameterizedTest(name = "Поиск текста {0} в списке строк")
    @ValueSource(strings = {
            "selenide",
            "java"
    })
    public void findTextSelenideOrJavaInListFioTest(String selenideOrJavaName) {
        open("/");
        $("[name='q']").setValue(selenideOrJavaName).pressEnter();
        $("h2").shouldHave(text(selenideOrJavaName));
    }

    @ParameterizedTest(name = "Поиск для {0} ссылки {1}")
    @CsvSource({
            "Allure, https://habr.com",
            "Junit 5, https://junit.org/junit5/"
    })
    public void findLinkForAllureOrJunitTest(String value, String link) {
        open("/");
        $("[name='q']").setValue(value).pressEnter();
        $("[data-testid='result-extras-site-search-link']").find(By.linkText(link));
    }

    @ParameterizedTest(name = "Поиск для {0} что есть хотя бы одна ссылка")
    @CsvFileSource(resources = "/parametr/findByCountForAllureOrJunitTest.csv")
    public void findByCountForAllureOrJunitTest(String value) {
        open("/");
        $("[name='q']").setValue(value).pressEnter();
        $$("[data-testid='result-extras-site-search-link']").shouldBe(sizeGreaterThan(0));
    }

    static Stream<Arguments> checkChapterPageTest() {
        return Stream.of(
                Arguments.of(List.of("Все",
                        "Изображения",
                        "Видео",
                        "Новости",
                        "Карты"), ChapterPage.NEWS, "allure")
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Проверка разделов {0}, а также раздела {1} на странице при помощи текста {2}")
    public void checkChapterPageTest(List<String> names, ChapterPage chapter, String value) {
        open("/");
        $("[name='q']").setValue(value).pressEnter();
        $$(".XvPRmQVeIoCP5lQhICTv.DrcNDXeWs90rE8UOUh96 li").filter(visible).shouldHave(texts(names));
        $(".XvPRmQVeIoCP5lQhICTv").shouldHave(text(chapter.tab));
    }
}