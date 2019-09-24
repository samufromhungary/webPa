package com.codecool.web.dao;

import com.codecool.web.model.CountedSupplement;
import com.codecool.web.model.Supplement;

import java.sql.SQLException;
import java.util.List;

public interface SupplementDao {

    enum type { Whey, Casein, Egg, Rice, Mixed, Beef, Pea }

    List<CountedSupplement> findAll() throws SQLException;

    List<CountedSupplement> findAllById(int id) throws SQLException;

    CountedSupplement findById(int id) throws SQLException;

    CountedSupplement add(String name, String supplement_type, String img, int price, boolean premium) throws SQLException;

    void buy(int accounts_id, int supplements_id) throws SQLException;

    List<CountedSupplement> findAllNotPremium() throws SQLException;

    int find(int accounts_ids, int supplements_id) throws SQLException;

}
