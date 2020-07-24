package com.learning.flightsorting.example.annotation.primary;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class HumanResourceManager implements Manager{
    @Override
    public String getManagerName() {
        return "Human Resource Manager";
    }
}
