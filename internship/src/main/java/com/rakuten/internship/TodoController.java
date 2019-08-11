package com.rakuten.internship;

import com.rakuten.internship.entity.Todo;
import com.rakuten.internship.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * このクラスは、ウェブアプリケーションの挙動を制御するためのコントローラークラスです。。
 * コントローラーとして使えるように、コードを記入してください。
 */
@Controller
public class TodoController {

    @Autowired
    private TodoRepository repository;

    @GetMapping("/")
    public String home(final Model model) {
        final List<Todo> todoList = repository.findAll();
        model.addAttribute("todoList", todoList);
        return "home";
    }

    @GetMapping("/create")
    public String create() {
        return "create";
    }

    @PostMapping("/create")
    public String createTodo(@ModelAttribute final Todo todo) {
        // TODO 必要なコードを作成してください。
        return null;
    }
}
