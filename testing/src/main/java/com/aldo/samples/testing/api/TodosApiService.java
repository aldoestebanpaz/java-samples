package com.aldo.samples.testing.api;

import com.aldo.samples.testing.domain.Todo;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class TodosApiService {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    private RestTemplate restTemplate;

    public TodosApiService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public Todo fetchTodo(int id) {

        try {
            URI url = new URI(BASE_URL).resolve(
                    String.format("/todos/%d", id)
            );
            return this.restTemplate.getForObject(url, Todo.class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Todo[] fetchTodos() {
        try {
            URI url = new URI(BASE_URL).resolve("/todos");
            return this.restTemplate.getForObject(url, Todo[].class);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
