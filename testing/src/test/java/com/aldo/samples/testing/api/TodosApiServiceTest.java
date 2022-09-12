package com.aldo.samples.testing.api;

import com.aldo.samples.testing.domain.Todo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

// These tests use the classic method of mocking methods of RestTemplate using Mockito.
// See TodosApiServiceTest2 for examples using MockRestServiceServer from the
// Spring Test module for intercepting calls.
public class TodosApiServiceTest {

    private RestTemplateBuilder mockRestTemplateBuilder;
    private RestTemplate mockRestTemplate;

    @BeforeEach
    public void beforeEach() {
        this.mockRestTemplateBuilder = Mockito.mock(RestTemplateBuilder.class);
        this.mockRestTemplate = Mockito.mock(RestTemplate.class);
        Mockito.when(this.mockRestTemplateBuilder.build()).thenReturn(this.mockRestTemplate);
    }

    @Test
    @DisplayName("FetchTodos should return a list of Todo objects")
    public void  testFetchTodosShouldReturnAList() {
        // arrange
        Todo[] responsePayload = {
            new Todo() {{ setId(1); setUserId(1); setTitle("delectus aut autem"); setCompleted(false); }},
            new Todo() {{ setId(2); setUserId(1); setTitle("quis ut nam facilis et officia qui"); setCompleted(false); }},
            new Todo() {{ setId(3); setUserId(1); setTitle("fugiat veniam minus"); setCompleted(true); }},
        };
        Mockito.when(
                this.mockRestTemplate.getForObject(
                        ArgumentMatchers.any(URI.class),
                        ArgumentMatchers.eq(Todo[].class)
                )
        ).thenReturn(responsePayload);

        // act
        var sut = new TodosApiService(this.mockRestTemplateBuilder);
        Todo[] todos = sut.fetchTodos();

        // assert
        var uriCaptor = ArgumentCaptor.forClass(URI.class);
        Mockito.verify(this.mockRestTemplate, Mockito.only())
                .getForObject(
                        uriCaptor.capture(),
                        ArgumentMatchers.eq(Todo[].class)
                );
        Assertions.assertEquals("https://jsonplaceholder.typicode.com/todos", uriCaptor.getValue().toString());
        Assertions.assertNotNull(todos);
        Assertions.assertEquals(3, todos.length);
        Assertions.assertEquals(2, todos[1].getId());
        Assertions.assertEquals(1, todos[1].getUserId());
        Assertions.assertEquals("quis ut nam facilis et officia qui", todos[1].getTitle());
        Assertions.assertFalse(todos[1].isCompleted());
    }

    @Test
    @DisplayName("FetchTodo should return a single todo")
    public void  testFetchTodoShouldReturnASingleTodo() {
        // arrange
        var responsePayload = new Todo() {{ setId(17); setUserId(1); setTitle("delectus aut autem"); setCompleted(false); }};
        Mockito.when(
                this.mockRestTemplate.getForObject(
                        ArgumentMatchers.any(URI.class),
                        ArgumentMatchers.eq(Todo.class)
                )
        ).thenReturn(responsePayload);

        // act
        var sut = new TodosApiService(this.mockRestTemplateBuilder);
        Todo todo = sut.fetchTodo(17);

        // assert
        var uriCaptor = ArgumentCaptor.forClass(URI.class);
        Mockito.verify(this.mockRestTemplate, Mockito.only())
                .getForObject(
                        uriCaptor.capture(),
                        ArgumentMatchers.eq(Todo.class)
                );
        Assertions.assertEquals("https://jsonplaceholder.typicode.com/todos/17", uriCaptor.getValue().toString());
        Assertions.assertNotNull(todo);
        Assertions.assertEquals(17, todo.getId());
        Assertions.assertEquals(1, todo.getUserId());
        Assertions.assertEquals("delectus aut autem", todo.getTitle());
        Assertions.assertFalse(todo.isCompleted());
    }

}
