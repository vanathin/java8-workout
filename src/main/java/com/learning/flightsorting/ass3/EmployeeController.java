package com.learning.flightsorting.ass3;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {
    static List<Employee> employeeList = new ArrayList<Employee>();

    static {
        employeeList.add(new Employee(111, "Jiya Brein", 32, "Female", "HR", 2011, 25000.0));
        employeeList.add(new Employee(122, "Paul Niksui", 25, "Male", "Sales And Marketing", 2015, 13500.0));
        employeeList.add(new Employee(133, "Martin Theron", 29, "Male", "Infrastructure", 2012, 18000.0));
        employeeList.add(new Employee(144, "Murali Gowda", 28, "Male", "Product Development", 2014, 32500.0));
        employeeList.add(new Employee(155, "Nima Roy", 27, "Female", "HR", 2013, 22700.0));
        employeeList.add(new Employee(166, "Iqbal Hussain", 43, "Male", "Security And Transport", 2016, 10500.0));
        employeeList.add(new Employee(177, "Manu Sharma", 35, "Male", "Account And Finance", 2010, 27000.0));
        employeeList.add(new Employee(188, "Wang Liu", 31, "Male", "Product Development", 2015, 34500.0));
        employeeList.add(new Employee(199, "Amelia Zoe", 24, "Female", "Sales And Marketing", 2016, 11500.0));
        employeeList.add(new Employee(200, "Jaden Dough", 38, "Male", "Security And Transport", 2015, 11000.5));
        employeeList.add(new Employee(211, "Jasna Kaur", 27, "Female", "Infrastructure", 2014, 15700.0));
        employeeList.add(new Employee(222, "Nitin Joshi", 25, "Male", "Product Development", 2016, 28200.0));
        employeeList.add(new Employee(233, "Jyothi Reddy", 27, "Female", "Account And Finance", 2013, 21300.0));
        employeeList.add(new Employee(244, "Nicolus Den", 24, "Male", "Sales And Marketing", 2017, 10700.5));
        employeeList.add(new Employee(255, "Ali Baig", 23, "Male", "Infrastructure", 2018, 12700.0));
        employeeList.add(new Employee(266, "Sanvi Pandey", 26, "Female", "Product Development", 2015, 28900.0));
        employeeList.add(new Employee(277, "Anuj Chettiar", 31, "Male", "Product Development", 2012, 35700.0));
    }

    @GetMapping(value = "/gender-counts")
    public ResponseEntity<Map<String, Long>> findGenderCount() {
        Map<String, Long> map = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping(value = "/all-departments")
    public ResponseEntity<List<String>> getDepartments() {
        List<String> map = employeeList.stream()
                .map(employee -> employee.getDepartment())
                .distinct()
                .collect(Collectors.toList());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping(value = "/gender-avg-age")
    public ResponseEntity<Map<String, Double>> getAvgAge() {
        Map<String, Double> avgAgeMap = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingInt(Employee::getAge)));
        return new ResponseEntity<>(avgAgeMap, HttpStatus.OK);
    }

    @GetMapping(value = "/highest-salary")
    public ResponseEntity<Employee> getHighestSalariedEmployee() {
        Employee avgAgeMap = employeeList.stream()
                .max(Comparator.comparing(Employee::getSalary))
                .orElseGet(() -> new Employee());
        return new ResponseEntity<>(avgAgeMap, HttpStatus.OK);
    }

    @GetMapping(value = "/employees-after-2015")
    public ResponseEntity<List<Employee>> getAllEmployeeAfter2015() {
        List<Employee> employees = employeeList.stream()
                .collect(Collectors.filtering(employee -> employee.getYearOfJoining() > 2015, Collectors.toList()));
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(value = "/employees-count")
    public ResponseEntity<Map<String, Long>> getEmployeeCountByDepartment() {
        Map<String, Long> employeesCount = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));
        return new ResponseEntity<>(employeesCount, HttpStatus.OK);
    }

    @GetMapping(value = "/avg-salary")
    public ResponseEntity<Map<String, Double>> getAvgSalaryByDepartment() {
        Map<String, Double> employeesCount = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingDouble(Employee::getSalary)));
        return new ResponseEntity<>(employeesCount, HttpStatus.OK);
    }


    @GetMapping(value = "/youngest-male-employee")
    public ResponseEntity<Employee> getYoungestMaleEmployee() {
        Employee emp = employeeList.stream()
                .filter(employee -> "Product Development".equalsIgnoreCase(employee.getDepartment()))
                .min(Comparator.comparing(Employee::getAge))
                .orElseGet(Employee::new);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @GetMapping(value = "/most-experience-employee")
    public ResponseEntity<Employee> getMostExperienceEmployee() {
        Employee emp = employeeList.stream()
                .min(Comparator.comparing(Employee::getYearOfJoining))
                .orElseGet(Employee::new);
        return new ResponseEntity<>(emp, HttpStatus.OK);
    }

    @GetMapping(value = "/employee-count")
    public ResponseEntity<Map<String, Long>> getGenderCount() {
        Map<String, Long> mapEmp = employeeList.stream()
                .filter(employee -> "Sales And Marketing".equalsIgnoreCase(employee.getDepartment()))
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.counting()));
        return new ResponseEntity<>(mapEmp, HttpStatus.OK);
    }

    @GetMapping(value = "/employee-avg-salary")
    public ResponseEntity<Map<String, Double>> getEmployeeAvgSalary() {
        Map<String, Double> employeesCount = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getGender, Collectors.averagingDouble(Employee::getSalary)));
        return new ResponseEntity<>(employeesCount, HttpStatus.OK);
    }

    @GetMapping(value = "/employee-names")
    public ResponseEntity<Map<String, List<String>>> getEmployeeNames() {
        Map<String, List<String>> employeesCount = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.mapping(Employee::getName, Collectors.toList())));
        return new ResponseEntity<>(employeesCount, HttpStatus.OK);
    }
    @GetMapping(value = "/employee-avg-total-salary")
    public ResponseEntity<Map<String, Double>> getEmployeesAvgAndTotalSalary() {
        Double avgSalary =employeeList.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        Double totalSalary = employeeList.stream().mapToDouble(Employee::getSalary).sum();
        Map<String, Double> map = new HashMap<>();
        map.put("avgSalary", avgSalary);
        map.put("totalSalary", totalSalary);
        //Another way
        DoubleSummaryStatistics avgSalaryStatistics =employeeList.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @GetMapping(value = "/employee-by-age")
    public ResponseEntity<Map<Boolean, List<Employee>>> getEmployeesByAge() {
        Map<Boolean, List<Employee>> map = employeeList.stream()
        .collect(Collectors.partitioningBy(employee -> employee.getAge() >= 25));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}
