package com.capstone.todobackend.controller;

import com.capstone.todobackend.entity.Todo;
import com.capstone.todobackend.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
@ActiveProfiles("test")
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper; // Use the ObjectMapper bean from the Spring context

    private Todo todo;

    @BeforeEach
    public void setup() {
        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        todo.setDescription("This is a test todo.");
        todo.setCompleted(false);
        todo.setCreated(LocalDateTime.now());
    }

    @Test
    public void fetchAllTodos() throws Exception {
        List<Todo> todos = Arrays.asList(todo);
        Mockito.when(todoService.fetchAllTodos()).thenReturn(todos);

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title").value(todo.getTitle()));
    }

    @Test
    public void createTodo() throws Exception {
        Mockito.when(todoService.createTodo(Mockito.any(Todo.class))).thenReturn(todo);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(todo.getTitle()));
    }

    @Test
    public void getTodoByID() throws Exception {
        Mockito.when(todoService.getTodoByID(1L)).thenReturn(todo);

        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title").value(todo.getTitle()));
    }

    @Test
    public void updateTodo() throws Exception {
        Mockito.when(todoService.updateTodo(Mockito.any(Todo.class))).thenReturn(todo);

        mockMvc.perform(put("/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(todo.getTitle()));
    }

    @Test
    public void markTodoAsComplete() throws Exception {
        todo.setCompleted(true);
        Mockito.when(todoService.getTodoByID(1L)).thenReturn(todo);
        Mockito.when(todoService.updateTodo(Mockito.any(Todo.class))).thenReturn(todo);

        mockMvc.perform(patch("/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    public void deleteTodoByID() throws Exception {
        Mockito.when(todoService.deleteTodoById(1L)).thenReturn(todo);

        mockMvc.perform(delete("/todos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getTotalCountOfTodos() throws Exception {
        Mockito.when(todoService.fetchAllTodos()).thenReturn(Arrays.asList(todo));

        mockMvc.perform(get("/todos/count"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("1"));
    }
}
