package com.epam.practice.java17;

import java.util.List;

public record Team(String name, List<String> members) {
    public Team {
        members = List.copyOf(members); // unmodifiable copy
    }
}