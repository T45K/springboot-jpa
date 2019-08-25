package com.rakuten.internship;

import com.rakuten.internship.entity.Todo;
import com.rakuten.internship.service.TodoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

        final List<Todo> todoToBeAlertedList = getTodoToBeAlerted(todoList);
        model.addAttribute("todoToBeAlertedList", todoToBeAlertedList);

        return "home";
    }

    private List<Todo> getTodoToBeAlerted(final List<Todo> todoList) {
        return todoList.stream()
                .filter(this::isToBeAlerted)
                .collect(Collectors.toList());
    }

    private boolean isToBeAlerted(final Todo todo) {
        if (todo.getDeadline() == null) {
            return false;
        }

        final Date today = new Date();
        final Calendar todayOnCalender = Calendar.getInstance();
        todayOnCalender.setTime(today);

        final Calendar deadlineOnCalender = convertDeadlineToCalender(parseFormat(todo.getDeadline()));

        return isOneDayBefore(deadlineOnCalender, todayOnCalender);
    }

    private Date parseFormat(final String dateString) {

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return format.parse(dateString);
        } catch (final ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    private Calendar convertDeadlineToCalender(final Date date) {
        final Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        return calender;
    }

    private boolean isOneDayBefore(final Calendar deadline, final Calendar today) {
        if (deadline.after(today)) {
            deadline.add(Calendar.DATE, -1);
            return deadline.before(today);
        }

        return false;
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

        if (!todo.getDeadline().isEmpty()) {
            todo.setDeadline(todo.getDeadline().replace('T', ' '));
        }

        service.save(todo);
        return "complete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("delBtn") final String id) {
        service.delete(Long.parseLong(id));
        return "delete";
    }
}
