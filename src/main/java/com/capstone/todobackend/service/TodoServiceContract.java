package com.capstone.todobackend.service;

import com.capstone.todobackend.entity.Todo;

import java.util.List;

public interface TodoServiceContract {

    List<Todo> fetchAllTodos();
    Todo getTodoByID(Long todoID);
    Todo createTodo(Todo todo);
    Todo deleteTodoById(Long todoID);
    Todo updateTodo(Todo todo);
}
