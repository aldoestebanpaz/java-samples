package com.aldo.samples.testing.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Todo {
    private int id;
    private int userId;
    private String title;
    private boolean completed;
}
