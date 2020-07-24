package com.learning.flightsorting;

import java.util.List;
import java.util.function.Predicate;

public class Option implements Predicate<Flight> {

    List<Predicate<Flight>> flighPredicatetList;

    public Option(List<Predicate<Flight>> flightList) {
        this.flighPredicatetList = flightList;
    }

    @Override
    public boolean test(Flight flight) {
        return flighPredicatetList.stream()
                .reduce(flight1 -> true, (flightPredicate, flightPredicate2) -> flightPredicate.and(flightPredicate2))
                .test(flight);

    }
}
