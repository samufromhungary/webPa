package com.codecool.web.service;

import com.codecool.web.model.AccountData;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface AccountDataService {

    List<AccountData> findAll() throws SQLException, ServiceException;

    AccountData findById(int id) throws SQLException, ServiceException;

    void modifyAdress(int id, String adress) throws SQLException, ServiceException;

    void modifyPurchases(int id, int purchasesAdd) throws SQLException, ServiceException;

    void modifyName(int id, String name) throws SQLException, ServiceException;

    void modifyPhone(int id, int phone) throws SQLException, ServiceException;

    void modifyBalance(int id, int balanceC, boolean increase) throws SQLException, ServiceException;

    void modifyRegular(int id, boolean regular) throws SQLException, ServiceException;

    void setBalance(int id, int balance) throws SQLException, ServiceException;
}
