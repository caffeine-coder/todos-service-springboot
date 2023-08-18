package com.todoservice.todoservice.resource;

import com.todoservice.todoservice.datamodel.Todo;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

public interface TodoResource {

    @RequestMapping(value = "/todos", method = RequestMethod.GET)
    List<Todo> getTodos() throws SQLException;

    @RequestMapping(value = "/insertTodo", method = RequestMethod.POST)
    Todo insertTodo(@RequestBody Todo todo) throws SQLException;

    @RequestMapping(value = "/updateTodo", method = RequestMethod.POST)
    Todo updateTodo(@RequestBody Todo todo) throws SQLException;

    @DeleteMapping("/delete/{id}")
    boolean deleteTodo(@PathVariable int id) throws SQLException;
}
