package com.codecool.web.service;

import com.codecool.web.model.CountedSupplement;
import com.codecool.web.model.Supplement;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public interface SupplementService {

    List<CountedSupplement> findAll() throws SQLException, ServiceException;

    List<CountedSupplement> findAllById(int id) throws SQLException, ServiceException;

    CountedSupplement findById(int id) throws SQLException;

    CountedSupplement add(String name, String supplement_type, String img, int price, boolean premium) throws SQLException;

    void buy(int accounts_id, int supplements_id) throws SQLException;

    List<CountedSupplement> findAllNotPremium() throws SQLException, ServiceException;
}
