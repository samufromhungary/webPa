package com.codecool.web.service.simple;

import com.codecool.web.dao.AccountDataDao;
import com.codecool.web.model.AccountData;
import com.codecool.web.service.AccountDataService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public final class SimpleAccountDataService implements AccountDataService {

    private final AccountDataDao accountDataDao;

    public SimpleAccountDataService(AccountDataDao accountDataDao){
        this.accountDataDao = accountDataDao;
    }

    @Override
    public List<AccountData> findAll() throws SQLException, ServiceException {
        return accountDataDao.findAll();
    }

    @Override
    public AccountData findById(int id) throws SQLException, ServiceException {
        return accountDataDao.findById(id);
    }

    @Override
    public void modifyAdress(int id, String adress) throws SQLException, ServiceException {
        accountDataDao.modifyAdress(id, adress);
    }

    @Override
    public void modifyPurchases(int id, int purchasesAdd) throws SQLException, ServiceException {
        accountDataDao.modifyPurchases(id, purchasesAdd);
    }

    @Override
    public void modifyName(int id, String name) throws SQLException, ServiceException {
        accountDataDao.modifyName(id, name);
    }

    @Override
    public void modifyPhone(int id, int phone) throws SQLException, ServiceException {
        accountDataDao.modifyPhone(id, phone);
    }

    @Override
    public void modifyBalance(int id, int balanceC, boolean increase) throws SQLException, ServiceException {
        accountDataDao.modifyBalance(id, balanceC, increase);
    }

    @Override
    public void modifyRegular(int id, boolean regular) throws SQLException, ServiceException{
        accountDataDao.modifyRegular(id, regular);
    }

    @Override
    public void setBalance(int id, int balance) throws SQLException, ServiceException{
        accountDataDao.setBalance(id, balance);
    }
}
