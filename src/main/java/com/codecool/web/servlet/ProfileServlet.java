package com.codecool.web.servlet;

import com.codecool.web.dao.AccountDao;
import com.codecool.web.dao.database.DatabaseAccountDao;
import com.codecool.web.model.Account;
import com.codecool.web.service.AccountService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleAccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/profile")
public final class ProfileServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = getConnection(request.getServletContext())) {
            AccountDao accountDao = new DatabaseAccountDao(connection);
            AccountService accountService = new SimpleAccountService(accountDao);

            Account account = (Account) request.getSession().getAttribute("account");
            int accountId = account.getId();
            System.out.println(accountId + request.getParameter("email"));

            sendMessage(response, HttpServletResponse.SC_OK, accountService.findById(accountId));
        } catch (SQLException e) {
            handleSqlError(response, e);
        }catch (ServiceException ex){
            ex.getMessage();
        }
    }
}
