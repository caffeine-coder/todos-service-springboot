package com.todoservice.todoservice.dataaccess;


import com.todoservice.todoservice.datamodel.Operation;
import com.todoservice.todoservice.datamodel.Todo;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TodoDataAccess {

    private static final Properties properties;

    static {
        properties = new Properties();

        try {
            ClassLoader classLoader = TodoDataAccess.class.getClassLoader();
            InputStream applicationPropertiesStream = classLoader.getResourceAsStream("application.properties");
            properties.load(applicationPropertiesStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final static String DB_URL = properties.getProperty("spring.datasource.url");

    private static final String USERNAME = properties.getProperty("spring.datasource.username");

    private static final String PASSWORD = properties.getProperty("spring.datasource.password");
    private PreparedStatement preparedStatement;
    private final Connection connection;

    private static final String GET_ALL_TODOS = "SELECT * FROM todo";

    private static final String INSERT_TODO =
            "INSERT INTO todo (todo_title, todo_description, todo_status, todo_dttm) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_TODO =
            "UPDATE todo SET todo_title=?, todo_description=?, todo_status=?, todo_dttm=? WHERE todo_id=?";

    private static final String DELETE_TODO = "DELETE FROM todo WHERE todo_id=?";

    public TodoDataAccess() throws SQLException {
        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
    }

    public List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(GET_ALL_TODOS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Todo todo = new Todo();
                todo.setId(resultSet.getInt("todo_id"));
                todo.setTitle(resultSet.getString("todo_title"));
                todo.setDescription(resultSet.getString("todo_description"));
                todo.setStatus(resultSet.getInt("todo_status"));
                todo.setDttm(resultSet.getLong("todo_dttm"));
                todos.add(todo);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return todos;
    }

    public Todo updateTodo(Todo todo, Operation operation) {
        Todo updatedTodo = new Todo();
        try {
            if (operation.equals(Operation.INSERT)) {
                preparedStatement =
                        connection.prepareStatement(INSERT_TODO, Statement.RETURN_GENERATED_KEYS);
            } else {
                preparedStatement =
                        connection.prepareStatement(UPDATE_TODO, Statement.RETURN_GENERATED_KEYS);
            }
            preparedStatement.setString(1, todo.getTitle());
            preparedStatement.setString(2, todo.getDescription());
            preparedStatement.setInt(3, todo.getStatus());
            preparedStatement.setLong(4, todo.getDttm());
            if (operation.equals(Operation.UPDATE)) {
                preparedStatement.setInt(5, todo.getId());
            }
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating todo failed, no rows affected.");
            }
            if (operation == Operation.INSERT) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        updatedTodo.setId(generatedKeys.getInt(1));
                        updatedTodo.setTitle(todo.getTitle());
                        updatedTodo.setDescription(todo.getDescription());
                        updatedTodo.setStatus(todo.getStatus());
                        updatedTodo.setDttm(todo.getDttm());
                    } else {
                        throw new SQLException("Updating todo failed, no rows affected.");
                    }
                }
            } else {
                return todo;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return updatedTodo;
    }

    public boolean deleteTodo(int id) {
        try {
            preparedStatement = connection.prepareStatement(DELETE_TODO);
            preparedStatement.setInt(1, id);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
