package com.capstone.todobackend.service;

import com.capstone.todobackend.entity.Todo;
import com.capstone.todobackend.repository.TodoSpringDataJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService implements TodoServiceContract {


    private final TodoSpringDataJPARepository todoRepository;

    @Autowired
    public TodoService(TodoSpringDataJPARepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public List<Todo> fetchAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public Todo getTodoByID(Long todoID) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoID);
        return optionalTodo.orElse(null);
    }

    @Override
    public Todo createTodo(Todo todo) {
        todo.setCreated(LocalDateTime.now());
        return todoRepository.save(todo);
    }

    @Override
    public Todo deleteTodoById(Long todoID) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoID);
        if (optionalTodo.isPresent()) {
            todoRepository.deleteById(todoID);
            return optionalTodo.get();
        }
        return null;
    }

    @Override
    public Todo updateTodo(Todo todo) {
        if (todo.getId() == null || !todoRepository.existsById(todo.getId())) {
            return null;
        }
        return todoRepository.save(todo);
    }
}
