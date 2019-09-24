package com.codecool.web.dao;

import com.codecool.web.model.AccountData;

import java.sql.SQLException;
import java.util.List;

public interface AccountDataDao {

    List<AccountData> findAll() throws SQLException;

    AccountData findById(int id) throws SQLException;

    void modifyAdress(int id, String adress) throws SQLException;

    void modifyPurchases(int id, int purchasesAdd) throws SQLException;

    void modifyName(int id, String name) throws SQLException;

    void modifyPhone(int id, int phone) throws SQLException;

    void modifyBalance(int id, int balanceC, boolean increase) throws SQLException;

    void modifyRegular(int id, boolean regular) throws SQLException;

    void setBalance(int id, int balance) throws SQLException;

}
