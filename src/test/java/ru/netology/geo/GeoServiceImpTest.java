package ru.netology.geo;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GeoServiceImpTest {
    GeoService geoService;

    @BeforeEach
    public void init() {
        System.out.println("GeoServiceTest started");
        geoService = new GeoServiceImpl();
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
    @MethodSource("sourceIp")
    public void testByIp(String ip, Location expectedLocation) {
        //aсt
        Location actualLocation = geoService.byIp(ip);

        //assert JUnit
        assertEquals(expectedLocation.getCountry(), actualLocation.getCountry());
    }

    @ParameterizedTest
    @MethodSource("sourceByCoordinates")
    public void testByCoordinates(double latitude, double longitude, String expectedExceptionMessage) {

        try {
            geoService.byCoordinates(latitude, longitude);
        } catch (RuntimeException e) {
            assertEquals(expectedExceptionMessage, e.getMessage());
        }

    }

    public static Stream<Arguments> sourceIp() {
        return Stream.of(Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)));
    }

    public static Stream<Arguments> sourceByCoordinates() {
        return Stream.of(Arguments.of(1.0, 8.5, "Not implemented"),
                Arguments.of(0.0, 0.0, "Not implemented"),
                Arguments.of(1.3, 7.3, "Not implemented"));
    }

}
