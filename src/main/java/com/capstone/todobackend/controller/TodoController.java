package com.capstone.todobackend.controller;

import com.capstone.todobackend.entity.Todo;
import com.capstone.todobackend.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<Todo>> fetchAllTodos() {
        List<Todo> todos = todoService.fetchAllTodos();
        return ResponseEntity.ok(todos);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo createdTodo = todoService.createTodo(todo);
        return ResponseEntity.ok(createdTodo);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getTodoByID(@PathVariable Long todoId) {
        Todo todo = todoService.getTodoByID(todoId);
        return todo != null ? ResponseEntity.ok(todo) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long todoId, @RequestBody Todo updatedTodo) {
        updatedTodo.setId(todoId);
        Todo todo = todoService.updateTodo(updatedTodo);
        return todo != null ? ResponseEntity.ok(todo) : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<Todo> markTodoAsComplete(@PathVariable Long todoId) {
        Todo todo = todoService.getTodoByID(todoId);
        if (todo != null) {
            todo.setCompleted(true);
            Todo updatedTodo = todoService.updateTodo(todo);
            return ResponseEntity.ok(updatedTodo);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<Void> deleteTodoByID(@PathVariable Long todoId) {
        Todo todo = todoService.deleteTodoById(todoId);
        return todo != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCountOfTodos() {
        long count = todoService.fetchAllTodos().size();
        return ResponseEntity.ok(count);
    }
}
