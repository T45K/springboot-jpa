package com.rakuten.internship;

import com.rakuten.internship.entity.Todo;
import com.rakuten.internship.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * このクラスは、ウェブアプリケーションの挙動を制御するためのコントローラークラスです。。
 * コントローラーとして使えるように、コードを記入してください。
 */
@Controller
public class TodoController {

    private final TodoService service;

    public TodoController(final TodoService service) {
        this.service = service;
    }

    @PostConstruct
    public void saveDummyData() {
        service.save(createTodo("111", "111"));
        service.save(createTodo("222", "222"));
        service.save(createTodo("222", "222"));
        service.save(createTodo("aaa", "bbb"));
    }

    private Todo createTodo(final String title, final String description) {
        final Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        return todo;
    }

    @GetMapping("/")
    public String home(final Model model) {
        final List<Todo> todoList = service.findTodoList();
        model.addAttribute("todoList", todoList);
        return "home";
    }

    @GetMapping("/create")
    public String create() {
        return "create";
    }

    @PostMapping("/create")
    public String saveTodo(@ModelAttribute final Todo todo) {
        if (todo.getTitle().isEmpty()) {
            return "error";
        }

        service.save(todo);
        return "complete";
    }
}
