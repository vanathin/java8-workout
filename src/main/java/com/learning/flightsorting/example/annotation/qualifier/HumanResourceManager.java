package com.learning.flightsorting.example.annotation.qualifier;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Component
public class HumanResourceManager implements Manager {
    @Override
    public String getManagerName() {
        return "Human Resource Manager";
    }
}
