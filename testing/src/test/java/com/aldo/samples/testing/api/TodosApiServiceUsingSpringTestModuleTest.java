package com.aldo.samples.testing.api;

import com.aldo.samples.testing.domain.Todo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;


// The Spring Test module includes a mock server named MockRestServiceServer.
// With this approach, we configure the server to return a particular object
// when a specific request is dispatched through our RestTemplate instance.
// In addition, we can verify() on that server instance whether all
// expectations have been met.
// MockRestServiceServer actually works by intercepting the HTTP API calls
// using a MockClientHttpRequestFactory. Based on our configuration, it
// creates a list of expected requests and corresponding responses. When the
// RestTemplate instance calls the API, it looks up the request in its list
// of expectations, and returns the corresponding response.
public class TodosApiServiceUsingSpringTestModuleTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private RestTemplateBuilder mockRestTemplateBuilder;
    private MockRestServiceServer mockServer;

    @BeforeEach
    public void beforeEach() {
        this.mockRestTemplateBuilder = Mockito.mock(RestTemplateBuilder.class);
        RestTemplate restTemplate = new RestTemplate();
        this.mockServer = MockRestServiceServer.createServer(restTemplate);
        Mockito.when(this.mockRestTemplateBuilder.build()).thenReturn(restTemplate);
    }

    @Test
    @DisplayName("FetchTodos should return a list of Todo objects")
    public void  testFetchTodosShouldReturnAList()
            throws URISyntaxException, JsonProcessingException {
        // arrange
        Todo[] responsePayload = {
            new Todo() {{ setId(1); setUserId(1); setTitle("delectus aut autem"); setCompleted(false); }},
            new Todo() {{ setId(2); setUserId(1); setTitle("quis ut nam facilis et officia qui"); setCompleted(false); }},
            new Todo() {{ setId(3); setUserId(1); setTitle("fugiat veniam minus"); setCompleted(true); }},
        };
        this.mockServer.expect(
                ExpectedCount.once(),
                MockRestRequestMatchers
                        .requestTo(new URI("https://jsonplaceholder.typicode.com/todos"))
        )
        .andExpect(
                MockRestRequestMatchers
                        .method(HttpMethod.GET))
        .andRespond(
                MockRestResponseCreators
                        .withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(responsePayload))
        );

        // act
        var sut = new TodosApiService(this.mockRestTemplateBuilder);
        Todo[] todos = sut.fetchTodos();

        // assert
        this.mockServer.verify();
        Assertions.assertNotNull(todos);
        Assertions.assertEquals(3, todos.length);
        Assertions.assertEquals(2, todos[1].getId());
        Assertions.assertEquals(1, todos[1].getUserId());
        Assertions.assertEquals("quis ut nam facilis et officia qui", todos[1].getTitle());
        Assertions.assertFalse(todos[1].isCompleted());
    }

    @Test
    @DisplayName("FetchTodo should return a single todo")
    public void  testFetchTodoShouldReturnASingleTodo()
            throws URISyntaxException, JsonProcessingException {
        // arrange
        var responsePayload = new Todo() {{ setId(17); setUserId(1); setTitle("delectus aut autem"); setCompleted(false); }};
        this.mockServer.expect(
                ExpectedCount.once(),
                MockRestRequestMatchers
                        .requestTo(new URI("https://jsonplaceholder.typicode.com/todos/17"))
        )
        .andExpect(
                MockRestRequestMatchers
                        .method(HttpMethod.GET))
        .andRespond(
                MockRestResponseCreators
                        .withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(responsePayload))
        );

        // act
        var sut = new TodosApiService(this.mockRestTemplateBuilder);
        Todo todo = sut.fetchTodo(17);

        // assert
        this.mockServer.verify();
        Assertions.assertNotNull(todo);
        Assertions.assertEquals(17, todo.getId());
        Assertions.assertEquals(1, todo.getUserId());
        Assertions.assertEquals("delectus aut autem", todo.getTitle());
        Assertions.assertFalse(todo.isCompleted());
    }

}
