package com.learning.flightsorting.ass2;

import java.util.List;


public class Organization {
    private Long id;
    List<Department> departments;

    public Organization(Long id, List<Department> departments) {
        this.id = id;
        this.departments = departments;
    }

    public Long getId() {
        return id;
    }

    public List<Department> getDepartments() {
        return departments;
    }


}
