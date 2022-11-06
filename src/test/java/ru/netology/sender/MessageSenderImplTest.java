package ru.netology.sender;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;

class MessageSenderImplTest {

    Location location;
    GeoService geoService;
    LocalizationService localizationService;

    @BeforeEach
    public void init() {
        System.out.println("test started");

        location = Mockito.mock(Location.class);
        geoService = Mockito.mock(GeoService.class);
        localizationService = new LocalizationServiceImpl();
    }

    @BeforeAll
    public static void started() {
        System.out.println("started MessageSenderImplTest");
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
    @MethodSource("sourceSend")
    public void testSend(String ip, String expected) {

        //arrange
        Mockito.when(location.getCountry()).thenReturn(new GeoServiceImpl().byIp(ip).getCountry());
        Mockito.when(geoService.byIp(any())).thenReturn(location);
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        //act
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);

        //assert
        Assertions.assertEquals(expected, messageSender.send(headers));
    }

    public static Stream<Arguments> sourceSend() {
        String ruWelcome = "Добро пожаловать";
        String enWelcome = "Welcome";
        return Stream.of(Arguments.of("172.", ruWelcome),
                Arguments.of("172.0.32.11", ruWelcome),
                Arguments.of("172.66.22.11", ruWelcome),
                Arguments.of("96.", enWelcome),
                Arguments.of("96.10.132.11", enWelcome),
                Arguments.of("96.44.183.149", enWelcome));

    }
}