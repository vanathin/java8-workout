package com.learning.flightsorting.ass2;

public class Employee {
    private Long id;
    private String name;
    private Double salary;
    private int age;
    private int expInTear;
    private String depName;
    public Employee() {

    }

    public Employee(Long id, String name, Double salary, int age, int expInTear, String depName) {
        this.depName = depName;
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.age = age;
        this.expInTear = expInTear;
    }


    public String getDepName() {
        return depName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getExpInTear() {
        return expInTear;
    }

    public void setExpInTear(int expInTear) {
        this.expInTear = expInTear;
    }
}
