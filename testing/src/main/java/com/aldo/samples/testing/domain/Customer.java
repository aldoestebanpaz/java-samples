package com.aldo.samples.testing.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
public class Customer {

    @Setter(value = AccessLevel.NONE)
    private int id = 0;
    private String name;
    private String email;

    @Setter(value = AccessLevel.NONE)
    private Instant createdDate = Instant.now();

    public Customer() {}

    public Customer(String name, String email, Instant createdDate) {
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
    }
}
