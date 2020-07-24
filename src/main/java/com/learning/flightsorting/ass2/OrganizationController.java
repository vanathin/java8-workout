package com.learning.flightsorting.ass2;

import com.learning.flightsorting.ResponseDTO;
import com.learning.flightsorting.ass2.Department;
import com.learning.flightsorting.ass2.Employee;
import com.learning.flightsorting.ass2.Organization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import static java.util.stream.Collectors.*;

@RestController
public class OrganizationController {

    static Organization organization = null;

    static {
        List<Department> dep = new ArrayList<>();
        List<Employee> adminEmp = new ArrayList<>();
        List<Employee> itEmp = new ArrayList<>();
        dep.add(new Department(1L, "Admin", adminEmp));
        dep.add(new Department(2L, "IT", itEmp));
        adminEmp.add(new Employee(1L, "vanathi", 80000d, 35, 5, "Admin"));
        adminEmp.add(new Employee(2L, "Karthik", 60000d, 30, 6, "Admin"));
        adminEmp.add(new Employee(5L, "vanathi-vanathi", 70000d, 35, 5, "Admin"));
        adminEmp.add(new Employee(6L, "vanathi-vanathi-vanathi", 80000d, 35, 5, "Admin"));
        itEmp.add(new Employee(3L, "Anirv", 40000d, 32, 4, "IT"));
        itEmp.add(new Employee(4L, "Anish", 90000d, 30, 10, "IT"));
        adminEmp.add(new Employee(7L, "vanathi-vanathi", 70000d, 35, 5, "IT"));
        organization = new Organization(1L, dep);
    }

    @GetMapping(value = "/second-best-salaries")
    public ResponseEntity<Employee> secondBestSalariedEmp() {

        //Employee List
        Employee employee = organization.getDepartments()
                .stream()

                .flatMap(department -> {
                    return department.getEmployeeList().stream();
                })
                .sorted(Comparator.comparing(Employee::getSalary, Double::compareTo).reversed())
                .skip(1)
                .findFirst()
                .orElseGet(Employee::new);

        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @GetMapping(value = "/high-salary-from-departments")
    public ResponseEntity<Map<String, Employee>> highestSalariedEmpFromEachDep() {
        Map<String, Optional<Employee>> employeeMap = organization.getDepartments()
                .stream()
                .filter(
                        department -> {
                            return !department.getName().equals("");
                        })
                .flatMap(
                        department -> {
                            System.out.println(department);
                            return department.getEmployeeList().stream();
                        })
                .filter(department -> {
                    return !department.getName().equals("");
                })
                .map(employee -> {
                    System.out.println(employee);
                    return employee;
                })
                .collect(
                        groupingBy(Employee::getDepName,
                                maxBy(Comparator.comparingDouble(
                                        Employee::getSalary))));
        Map<String, Employee> employees = employeeMap.values()
                .stream()
                .map(employee -> {
                    return employee.orElseGet(Employee::new);
                })
                .collect(
                        toMap(employee -> employee.getDepName(), Function.identity()));
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(value = "/least-salary-from-departments")
    public ResponseEntity<Map<String, Employee>> leastSalariedEmpFromEachDep() {
        Map<String, Optional<Employee>> map = organization.getDepartments()
                .stream()
                .flatMap(department -> department.getEmployeeList().stream())
                .collect(groupingBy(Employee::getDepName, minBy(Comparator.comparing(Employee::getSalary))));
        Map<String, Employee> finalOp = map.values()
                .stream()
                .map(employee -> employee.orElseGet(Employee::new))
                .collect(toMap(Employee::getDepName, Function.identity()));
        return new ResponseEntity<>(finalOp, HttpStatus.OK);
    }

    @GetMapping(value = "/same-salary-from-departments")
    public ResponseEntity<Map<String, List<Employee>>> sameSalariedEmpFromEachDep() {
        Map<String, List<Employee>> map = organization.getDepartments()
                .stream()
                .map(Department::getEmployeeList)
                .flatMap(Collection::stream)
                .collect(groupingBy(Employee::getDepName, groupingBy(Employee::getSalary)))
                .entrySet()
                .stream()
                .map(stringMapEntry -> {
                    return Optional.of(stringMapEntry)
                            .stream()
                            .map(Map.Entry::getValue)
                            .map(Map::values)
                            .flatMap(Collection::stream)
                            .filter(employees -> employees.size() > 1)
                            .flatMap(Collection::stream)
                            .collect(Collectors.toList());
                })
                .flatMap(Collection::stream)
                .collect(groupingBy(Employee::getDepName, toList()));

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping(value = "/salary-ratio")
    public ResponseEntity<List<ResponseDTO>> salaryRatioEmpFromEachDep() {
        List<ResponseDTO> list = organization.getDepartments()
                .stream()
                .map(Department::getEmployeeList)
                .flatMap(employees -> employees.stream())
                .map(employee -> {
                    double numerator = employee.getExpInTear();
                    double denominator = employee.getSalary();
                    double divisor = getGCD(numerator, denominator);
                    return getResponseDTO(employee, (int)Math.ceil(numerator /= divisor) + ":" +  (int)Math.ceil(denominator /= divisor));
                }).collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    private ResponseDTO getResponseDTO(Employee employee, String ratio){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setEmpId(employee.getId());
        responseDTO.setExpInYear(employee.getExpInTear());
        responseDTO.setName(employee.getName());
        responseDTO.setRatio(ratio);
        responseDTO.setSalary(employee.getSalary());
        return responseDTO;
    }
    private double getGCD(double numerator, double denominator) {
        return Optional.of(numerator)
                .filter(numerator1 -> numerator1 == 0)
                .map(numerator1 -> {
                    return Optional.of(denominator)
                            .filter(denominator1 -> denominator1 == 0)
                            .map(denominator2 -> 1d)
                            .orElseGet(() -> denominator);
                })
                .orElseGet(() -> {
                    return Optional.of(denominator)
                            .filter(denominator1 -> denominator1 < numerator)
                            .map(denominator2 -> getGCD(denominator2, numerator))
                            .orElseGet(() -> getGCD(denominator % numerator, numerator));
                });
    }

}