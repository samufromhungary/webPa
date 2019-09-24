package com.codecool.web.service.simple;

import com.codecool.web.dao.SupplementDao;
import com.codecool.web.model.CountedSupplement;
import com.codecool.web.model.Supplement;
import com.codecool.web.service.SupplementService;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public final class SimpleSupplementService implements SupplementService {

    private final SupplementDao supplementDao;

    public SimpleSupplementService(SupplementDao supplementDao){
        this.supplementDao = supplementDao;
    }

    @Override
    public List<CountedSupplement> findAll() throws SQLException {
        return supplementDao.findAll();
    }

    @Override
    public List<CountedSupplement> findAllById(int id) throws SQLException {
        return supplementDao.findAllById(id);
    }

    @Override
    public CountedSupplement findById(int id) throws SQLException {
        return supplementDao.findById(id);
    }

    @Override
    public CountedSupplement add(String name, String supplement_type, String img, int price, boolean premium) throws SQLException {
        return supplementDao.add(name, supplement_type, img, price, premium);
    }

    @Override
    public void buy(int accounts_id, int supplements_id) throws SQLException {
        supplementDao.buy(accounts_id, supplements_id);
    }

    public List<CountedSupplement> findAllNotPremium() throws SQLException, ServiceException{
        return supplementDao.findAllNotPremium();
    }
}
