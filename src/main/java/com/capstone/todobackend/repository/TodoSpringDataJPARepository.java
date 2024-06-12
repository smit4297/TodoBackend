package com.capstone.todobackend.repository;

import com.capstone.todobackend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TodoSpringDataJPARepository extends JpaRepository<Todo, Long> {

}
