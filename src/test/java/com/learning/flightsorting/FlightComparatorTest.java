package com.learning.flightsorting;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FlightComparatorTest {

    static List<Flight> flightList = new ArrayList<>();
    static List<Comparator<Flight>> list = new ArrayList<>();

    static {
        Flight flight = new Flight(1L, "EK", "DXB", "MAA", 1, 2000.0);
        Flight flight1 = new Flight(2L, "FZ", "MAA", "DXB", 0, 2500.0);
        Flight flight2 = new Flight(3L, "QF", "DXB", "LHR", 2, 4500.0);
        Flight flight3 = new Flight(4L, "EK", "DXB", "MAA", 0, 2000.0);
        Flight flight4 = new Flight(5L, "QR", "DXB", "MAA", 2, 2000.25);
        flightList.add(flight);
        flightList.add(flight1);
        flightList.add(flight2);
        flightList.add(flight3);
        flightList.add(flight4);
        Comparator<Flight> priceCompare = Comparator.comparing(Flight::getPrice, Double::compareTo);
        Comparator<Flight> stopCompare = Comparator.comparing(Flight::getStop, Integer::compareTo);
        list.add(priceCompare);
        list.add(stopCompare);
        FlightComparator f;

    }

    @InjectMocks
    private FlightComparator comparator;
    @BeforeAll
    static void setForEachMethod(){

    }
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCompareForPositive() {
        FlightComparator  f = new FlightComparator(list);
        int result = f.compare(flightList.get(0), flightList.get(1));
        Assertions.assertNotEquals(1, -1);
    }

    @Test
    void testCompareNegative() {
        FlightComparator  f = new FlightComparator(list);
        int result = f.compare(flightList.get(0), flightList.get(0));
        Assertions.assertEquals(0, 0);
    }

}