package com.capstone.todobackend.repository;


import com.capstone.todobackend.entity.Todo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class TodoJPARepository {
    @PersistenceContext
    private EntityManager entityManager;

    public long save(Todo todo) {
        return entityManager.merge(todo).getId();
    }

}
