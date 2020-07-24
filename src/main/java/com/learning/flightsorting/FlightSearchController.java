package com.learning.flightsorting;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class FlightSearchController {
    static List<Flight> flightList = new ArrayList<>();
    static Map<String, Comparator<Flight>> map = new HashMap<>();

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

        Comparator<Flight> priceComparator = Comparator.comparing(Flight::getPrice, Double::compareTo);
        Comparator<Flight> stopComparator = Comparator.comparing(Flight::getStop, Integer::compareTo);
        Comparator<Flight> originComparator = Comparator.comparing(Flight::getOrigin, String::compareTo);
        Comparator<Flight> destinationComparator = Comparator.comparing(Flight::getDestination, String::compareTo);
        Comparator<Flight> airlineType = Comparator.comparing(Flight::getAirlineType, String::compareTo);
        map.put("1", priceComparator);
        map.put("2", stopComparator);
        map.put("3", airlineType);
        map.put("4", originComparator);
        map.put("5", destinationComparator);
    }


    public List<Predicate<Flight>> getPredicates(){
        Predicate<Flight> predicate = o -> o.getAirlineType().equals("EK");
        Predicate<Flight> originPredicate = o -> o.getDestination().equals("DXB");
        List<Predicate<Flight>> list = new ArrayList<>();
        list.add(predicate);
        list.add(originPredicate);
        return list;
    }

    public List<Flight> getFlights(List<Predicate<Flight>> predicates, List<Flight> list){
        Option option = new Option(predicates);
        return list.stream()
                .filter(option)
                .collect(Collectors.toList());
    }



    /**
     * sortCategory = 1 - price
     * sortCategory = 2 - stop
     * sortCategory = 3 - airlineType
     * sortCategory = 4 - origin
     * sortCategory = 5 - destination
     * sortType = 1 - ASC
     * sortType = 2 - DESC
     *
     * @param sortType
     * @return
     */
    @GetMapping(value = "/flights")
    public ResponseEntity<List<Flight>> getFlights(
            @RequestParam(value = "", required = false, name = "sortCategory", defaultValue = "0") String sortCategory,
            @RequestParam(value = "", required = false, name = "sortType", defaultValue = "0") String sortType) {
        List<Flight> flights =
                Optional.ofNullable(sortCategory)
                        .map(integer -> {
                            switch (integer) {
                                case "2": {
                                    return flightList.stream()
                                            .sorted(getComparator(Comparator.comparing(Flight::getStop, Integer::compareTo), sortType));
                                }
                                case "3": {
                                    return flightList.stream()
                                            .sorted(getComparator(Comparator.comparing(Flight::getAirlineType, String::compareTo), sortType));
                                }
                                case "4": {
                                    return flightList.stream()
                                            .sorted(getComparator(Comparator.comparing(Flight::getOrigin, String::compareTo), sortType));
                                }
                                case "5": {
                                    return flightList.stream()
                                            .sorted(getComparator(Comparator.comparing(Flight::getDestination, String::compareTo), sortType));
                                }
                                case "1":
                                default: {
                                    return flightList.stream()
                                            .sorted(getComparator(Comparator.comparing(Flight::getPrice, Double::compareTo), sortType));
                                }
                            }
                        }).orElseGet(() -> {
                    return flightList.stream()
                            .sorted(getComparator(Comparator.comparing(Flight::getPrice, Double::compareTo), sortType));
                }).collect(Collectors.toList());

        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @GetMapping(value = "/sortFlights")
    public ResponseEntity<List<Flight>> getFlights(
            @RequestParam(value = "", required = false, name = "sortCategory", defaultValue = "0") List<String> sortCategory,
            @RequestParam(value = "", required = false, name = "sortType", defaultValue = "0") String sortType) {

        //Get  list of comparator for given sortCategory
        List<Comparator<Flight>> comparatorList = sortCategory.stream()
                .map(this::applyComparatorChaining)
                .collect(Collectors.toList());

        //apply comparator on flights
        FlightComparator flightComparator = new FlightComparator(comparatorList);

        List<Flight> flights = flightList.stream()
                .sorted(flightComparator)
                .collect(Collectors.toList());
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    private Comparator<Flight> applyComparatorChaining(String sortCategory) {
        return map.get(sortCategory);
    }

    private <T> Comparator<T> getComparator(Comparator<T> comparing, String sortType) {
        if ("2".equalsIgnoreCase(sortType)) {
            return comparing.reversed();
        }
        return comparing;
    }
}
