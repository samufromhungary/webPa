package com.codecool.web.dao.database;

import com.codecool.web.dao.AccountDataDao;
import com.codecool.web.model.AccountData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final  class DatabaseAccountDataDao extends AbstractDao implements AccountDataDao {

    public DatabaseAccountDataDao(Connection connection) {
        super(connection);
    }

    @Override
    public List<AccountData> findAll() throws SQLException {
        String sql ="SELECT * from accountData INNER JOIN accounts on accountData.accounts_id = accounts.id";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<AccountData> accountDatas = new ArrayList<>();
            while(resultSet.next()){
                accountDatas.add(fetchAccountData(resultSet));
            }
            return accountDatas;
        }
    }

    @Override
    public AccountData findById(int id) throws SQLException {
        String sql = "SELECT * FROM accountData INNER JOIN accounts on accountData.accounts_id = accounts.id WHERE accounts_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()){
                    return fetchAccountData(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public void modifyAdress(int id, String adress) throws SQLException {
        String sql = "UPDATE accountData SET adress = ? WHERE accounts_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, adress);
            statement.setInt(2,id);
            statement.executeUpdate();
        }catch (SQLException ex){
            throw ex;
        }
    }

    @Override
    public void modifyPurchases(int id, int purchasesAdd) throws SQLException {
        String sql = "UPDATE accountData SET purchases = purchases + ? WHERE accounts_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, purchasesAdd);
            statement.setInt(2,id);
            statement.executeUpdate();
        }catch (SQLException ex){
            throw ex;
        }
    }

    @Override
    public void modifyName(int id, String name) throws SQLException {
        String sql = "UPDATE accountData SET name = ? WHERE accounts_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, name);
            statement.setInt(2,id);
            statement.executeUpdate();
        }catch (SQLException ex){
            throw ex;
        }

    }

    @Override
    public void modifyPhone(int id, int phone) throws SQLException {
        String sql = "UPDATE accountData SET phone = ? WHERE accounts_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, phone);
            statement.setInt(2,id);
            statement.executeUpdate();
        }catch (SQLException ex){
            throw ex;
        }
    }

    @Override
    public void modifyBalance(int id, int balanceC, boolean increase) throws SQLException {
        if (increase){
            String sql = "UPDATE accountData SET balance = balance + ? WHERE accounts_id = ?";
            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(1, balanceC);
                statement.setInt(2,id);
                statement.executeUpdate();
            }catch (SQLException ex){
                throw ex;
            }
        }else{
            String sql = "UPDATE accountData SET balance = balance - ? WHERE accounts_id = ?";
            try(PreparedStatement statement = connection.prepareStatement(sql)){
                statement.setInt(1, balanceC);
                statement.setInt(2,id);
                statement.executeUpdate();
            }catch (SQLException ex){
                throw ex;
            }
        }
    }

    @Override
    public void modifyRegular(int id, boolean regular) throws SQLException{
        String sql = "UPDATE accountData SET regular = ? WHERE accounts_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setBoolean(1, regular);
            statement.setInt(2, id);
            statement.executeUpdate();
        }catch (SQLException ex){
            throw ex;
        }
    }

    @Override
    public void setBalance(int id, int balance) throws SQLException{
        String sql = "UPDATE accountData SET balance = ? WHERE accounts_id = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, balance);
            statement.setInt(2, id);
            statement.executeUpdate();
        }catch (SQLException ex){
            throw ex;
        }
    }

    private AccountData fetchAccountData(ResultSet resultSet)throws SQLException{
        int id = resultSet.getInt("id");
        String adress = resultSet.getString("adress");
        int purchases = resultSet.getInt("purchases");
        String name = resultSet.getString("name");
        int phone = resultSet.getInt("phone");
        int balance = resultSet.getInt("balance");
        boolean regular = resultSet.getBoolean("regular");
        String email = resultSet.getString("email");
        String password = resultSet.getString("password");
        boolean permission = resultSet.getBoolean("permission");
        return new AccountData(id, email, password, permission, adress, purchases, name, phone, balance, regular);
    }
}
