package com.todoservice.todoservice.resource;

import com.todoservice.todoservice.dataaccess.TodoDataAccess;
import com.todoservice.todoservice.datamodel.Operation;
import com.todoservice.todoservice.datamodel.Todo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping(value = "/todo")
public class TodoResourceImpl implements TodoResource {

    @Override
    public List<Todo> getTodos() throws SQLException {
        TodoDataAccess dataAccess = new TodoDataAccess();
        return dataAccess.getAllTodos();
    }

    @Override
    public Todo insertTodo(Todo todo) throws SQLException {
        TodoDataAccess dataAccess = new TodoDataAccess();
        return dataAccess.updateTodo(todo, Operation.INSERT);
    }

    @Override
    public Todo updateTodo(Todo todo) throws SQLException {
        TodoDataAccess dataAccess = new TodoDataAccess();
        return dataAccess.updateTodo(todo, Operation.UPDATE);
    }

    @Override
    public boolean deleteTodo(int id) throws SQLException {
        TodoDataAccess dataAccess = new TodoDataAccess();
        return dataAccess.deleteTodo(id);
    }
}