package com.codecool.web.service.simple;

import com.codecool.web.dao.AccountDao;
import com.codecool.web.model.Account;
import com.codecool.web.service.AccountService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public final class SimpleAccountService implements AccountService {

    private final AccountDao accountDao;

    public SimpleAccountService(AccountDao accountDao){
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAll() throws SQLException, ServiceException {
        return accountDao.findAll();
    }

    @Override
    public Account findByEmail(String email) throws SQLException, ServiceException {
        return accountDao.findByEmail(email);
    }

    @Override
    public Account findById(int id) throws SQLException, ServiceException {
        return accountDao.findById(id);
    }

    @Override
    public Account add(String email, String password, boolean permission) throws SQLException, ServiceException {
        return accountDao.add(email, password, permission);
    }

    @Override
    public boolean accountAlreadyExists(String email) throws SQLException, ServiceException {
        return accountDao.accountAlreadyExists(email);
    }

    @Override
    public Account login(String email, String password) throws SQLException, ServiceException {
        try{
            Account account = accountDao.findByEmail(email);
            if(account == null || !password.equals(account.getPassword())){
                throw new ServiceException("Bad login");
            }return account;
        }catch (IllegalArgumentException e){
            throw new ServiceException(e.getMessage());
        }
    }
}
