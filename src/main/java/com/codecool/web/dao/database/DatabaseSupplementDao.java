package com.codecool.web.dao.database;

import com.codecool.web.dao.SupplementDao;
import com.codecool.web.model.CountedSupplement;
import com.codecool.web.model.Supplement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseSupplementDao extends AbstractDao implements SupplementDao {
    public DatabaseSupplementDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<CountedSupplement> findAll() throws SQLException {
        String sql ="SELECT * from supplements";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<CountedSupplement> supplements = new ArrayList<>();
            while(resultSet.next()){
                supplements.add(fetchSupplement(resultSet));
            }
            return supplements;
        }
    }


    @Override
    public List<CountedSupplement> findAllById(int id) throws SQLException {
        String sql ="SELECT * from inventory i INNER JOIN supplements s ON i.supplements_id = s.id WHERE accounts_id = ?";
        List<CountedSupplement> bought = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    bought.add(fetchSupplement(resultSet));
                }
            }
        }
        return bought;
    }

    @Override
    public CountedSupplement findById(int id) throws SQLException {
        String sql = "SELECT * FROM supplements WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return fetchSupplement(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public CountedSupplement add(String name, String supplement_type, String img, int price, boolean premium) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        String sql = "INSERT INTO supplements (name, supplement_type, img, price, premium) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setString(2, supplement_type);
            statement.setString(3, img);
            statement.setInt(4, price);
            statement.setBoolean(5, premium);
            executeInsert(statement);
            int id = fetchGeneratedId(statement);
            connection.commit();
            return new CountedSupplement(id, name, supplement_type, img, price, premium, 0);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        } finally {
            connection.setAutoCommit(autoCommit);
        }
    }

    @Override
    public int find(int accounts_id, int supplements_id) throws SQLException{
        String sql = "SELECT COUNT(*) FROM inventory WHERE accounts_id = ? AND supplements_id = ?";
        int answer = -1;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accounts_id);
            statement.setInt(2, supplements_id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    answer = resultSet.getInt(1);
                    return answer;
                }
            }
        }return answer;
    }

    @Override
    public void buy(int accounts_id, int supplements_id) throws SQLException {
        boolean autoCommit = connection.getAutoCommit();
        connection.setAutoCommit(false);
        int amount = find(accounts_id, supplements_id);
        if(amount < 1){
            String sql = "INSERT INTO inventory(accounts_id, supplements_id, amount) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, accounts_id);
                statement.setInt(2, supplements_id);
                statement.setInt(3, 1);
                executeInsert(statement);
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            }finally {
                connection.setAutoCommit(autoCommit);
            }
        }else{
            String sql = "UPDATE inventory SET amount = amount + 1 WHERE accounts_id = ? AND supplements_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setInt(1, accounts_id);
                statement.setInt(2, supplements_id);
                executeInsert(statement);
                connection.commit();
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            }finally {
                connection.setAutoCommit(autoCommit);
            }
        }

    }

    @Override
    public List<CountedSupplement> findAllNotPremium() throws SQLException{
        String sql ="SELECT * from supplements WHERE premium = false";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<CountedSupplement> supplements = new ArrayList<>();
            while(resultSet.next()){
                supplements.add(fetchSupplement(resultSet));
            }
            return supplements;
        }
    }

    private CountedSupplement fetchSupplement(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String supplement_type = resultSet.getString("supplement_type");
        String img = resultSet.getString("img");
        int price = resultSet.getInt("price");
        boolean premium = resultSet.getBoolean("premium");
        int amount = resultSet.getInt("amount");
        return new CountedSupplement(id, name, supplement_type, img, price, premium, amount);
    }
}
