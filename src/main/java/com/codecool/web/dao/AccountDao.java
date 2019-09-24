package com.codecool.web.dao;

import com.codecool.web.model.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {

    List<Account> findAll() throws SQLException;

    Account findByEmail(String email) throws SQLException;

    Account findById(int id) throws SQLException;

    Account add(String email, String password, boolean permission) throws SQLException;

    boolean accountAlreadyExists(String email) throws SQLException;
}
