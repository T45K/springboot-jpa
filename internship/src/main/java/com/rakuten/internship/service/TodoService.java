package com.rakuten.internship.service;

import com.rakuten.internship.entity.Todo;
import com.rakuten.internship.repository.TodoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * このクラスは、データベースとのトランザクションをサポートするサービスクラスです。。
 */
@Service
@Transactional
public class TodoService {

    private final TodoRepository repository;

    public TodoService(final TodoRepository repository) {
        this.repository = repository;
    }

    public List<Todo> findTodoList() {
        return repository.findAll();
    }

    public void save(final Todo todo) {
        repository.save(todo);
    }

    public void delete(final long key) {
        repository.deleteById(key);
    }
}
