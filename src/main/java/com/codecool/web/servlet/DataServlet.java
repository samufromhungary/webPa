package com.codecool.web.servlet;

import com.codecool.web.dao.AccountDataDao;
import com.codecool.web.dao.database.DatabaseAccountDataDao;
import com.codecool.web.model.Account;
import com.codecool.web.model.AccountData;
import com.codecool.web.service.AccountDataService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleAccountDataService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/data")
public class DataServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            AccountDataDao accountDataDao = new DatabaseAccountDataDao(connection);
            AccountDataService accountDataService = new SimpleAccountDataService(accountDataDao);
            Account account = (Account) req.getSession().getAttribute("account");

            int accountId = account.getId();
            AccountData accountData = accountDataService.findById(accountId);
            sendMessage(resp, HttpServletResponse.SC_OK, accountData);
        } catch (SQLException exc) {
            handleSqlError(resp, exc);
        }catch (ServiceException ex){
            ex.getMessage();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            AccountDataDao accountDataDao = new DatabaseAccountDataDao(connection);
            AccountDataService accountDataService = new SimpleAccountDataService(accountDataDao);
            Account account = (Account) req.getSession().getAttribute("account");

            int accountId = account.getId();
            String address = req.getParameter("address");
            String name = req.getParameter("name");
            String phone = req.getParameter("phone");
            String balance = req.getParameter("balance");
            System.out.println(address);

            if (address != null && !address.equals("")) {
                accountDataService.modifyAdress(accountId, address);
            }if(name != null && !name.equals("")) {
                accountDataService.modifyName(accountId, name);
            }if(phone != null && !phone.equals("")) {
                accountDataService.modifyPhone(accountId, Integer.valueOf(phone));
            }if(balance != null && !balance.equals("")){
                accountDataService.setBalance(accountId, Integer.valueOf(balance));
            }

            req.getSession().setAttribute("account", account);
            sendMessage(resp, HttpServletResponse.SC_OK, "Updated");
        } catch (SQLException exc) {
            handleSqlError(resp, exc);
        } catch (ServiceException ex){
            ex.getMessage();
        }
    }
}
