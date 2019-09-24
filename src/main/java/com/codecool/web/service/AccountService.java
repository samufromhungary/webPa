package com.codecool.web.service;

import com.codecool.web.model.Account;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface AccountService {

    List<Account> findAll() throws SQLException, ServiceException;

    Account findByEmail(String email) throws SQLException, ServiceException;

    Account findById(int id) throws SQLException, ServiceException;

    Account add(String email, String password, boolean permission) throws SQLException, ServiceException;

    boolean accountAlreadyExists(String email) throws SQLException, ServiceException;

    Account login(String email, String password) throws SQLException, ServiceException;
}
