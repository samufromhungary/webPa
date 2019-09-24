package com.codecool.web.dao.database;

import com.codecool.web.dao.AccountDao;
import com.codecool.web.model.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseAccountDao extends AbstractDao implements AccountDao {


    public DatabaseAccountDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<Account> findAll() throws SQLException {
        String sql ="SELECT * from accounts";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<Account> accounts = new ArrayList<>();
            while(resultSet.next()){
                accounts.add(fetchAccount(resultSet));
            }
            return accounts;
        }
    }

    @Override
    public Account findByEmail(String email) throws SQLException {
        if(email == null || "".equals(email) ) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        String sql = "SELECT * FROM accounts WHERE email = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, email);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return fetchAccount(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Account findById(int id) throws SQLException {
        if(id < 1) {
            throw new IllegalArgumentException("Id cannot be less than 1");
        }
        String sql = "SELECT * FROM accounts WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return fetchAccount(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Account add(String email, String password, boolean permission) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO accounts (email, password, permission) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setBoolean(3, permission);
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            connection.commit();
            return new Account(id, email, password, permission);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public boolean accountAlreadyExists(String email) throws SQLException {
        List<Account> accounts = findAll();
        boolean valid = false;
        for (Account acc:accounts) {
            if(acc.getEmail().equalsIgnoreCase(email)){
                valid = true;
            }
        }return valid;
    }

    private Account fetchAccount(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        boolean permission = resultSet.getBoolean("permission");
        return new Account(id, email, password, permission);
    }
}
