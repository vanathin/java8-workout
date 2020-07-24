package com.learning.flightsorting;

import java.util.Comparator;
import java.util.List;

public class FlightComparator implements Comparator<Flight> {
    List<Comparator<Flight>> list;

    public FlightComparator(List<Comparator<Flight>> list) {
        this.list = list;
    }

    @Override
    public int compare(Flight o1, Flight o2) {
        return list.stream()
                .mapToInt(flightComparator -> {
                    return flightComparator.compare(o1, o2);
                }).filter(o -> o != 0)
                .findFirst()
                .orElse(0);
    }
}
