package ru.netology.i18n;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.entity.Country.*;

public class LocalizationServiceImplTest {
    LocalizationServiceImpl localizationService;

    @BeforeEach
    public void init() {
        System.out.println("GeoServiceTest started");
        localizationService = new LocalizationServiceImpl();
    }

    @BeforeAll
    public static void started() {
        System.out.println("started tests");
    }

    @AfterEach
    public void finished() {
        System.out.println("test completed");
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("tests completed");
    }

    @ParameterizedTest
    @MethodSource("sourceCountry")
    public void testLocale(Country country, String expectedWelcoming) {
        assertEquals(localizationService.locale(country), expectedWelcoming);

    }

    public static Stream<Arguments> sourceCountry() {
        String ruWelcome = "Добро пожаловать";
        String enWelcome = "Welcome";
        return Stream.of(Arguments.of(RUSSIA, ruWelcome),
                Arguments.of(GERMANY, enWelcome),
                Arguments.of(USA, enWelcome),
                Arguments.of(BRAZIL, enWelcome));
    }

}
