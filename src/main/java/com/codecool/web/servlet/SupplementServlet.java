package com.codecool.web.servlet;


import com.codecool.web.dao.AccountDataDao;
import com.codecool.web.dao.SupplementDao;
import com.codecool.web.dao.database.DatabaseAccountDataDao;
import com.codecool.web.dao.database.DatabaseSupplementDao;
import com.codecool.web.model.Account;
import com.codecool.web.model.AccountData;
import com.codecool.web.model.CountedSupplement;
import com.codecool.web.model.Supplement;
import com.codecool.web.service.AccountDataService;
import com.codecool.web.service.SupplementService;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.SimpleAccountDataService;
import com.codecool.web.service.simple.SimpleSupplementService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/protected/supplements")
public class SupplementServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            SupplementDao supplementDao = new DatabaseSupplementDao(connection);
            AccountDataDao accountDataDao = new DatabaseAccountDataDao(connection);
            SupplementService supplementService = new SimpleSupplementService(supplementDao);
            AccountDataService accountDataService = new SimpleAccountDataService(accountDataDao);

            Account account = (Account) req.getSession().getAttribute("account");
            AccountData accountData = accountDataService.findById(account.getId());
            List<Object> stuffs = new ArrayList<>();
            stuffs.add(account);

            if(accountData.isRegular()){
                List<CountedSupplement> supplements = supplementService.findAll();
                stuffs.add(supplements);
                sendMessage(resp, HttpServletResponse.SC_OK, stuffs);
            }else{
                List<CountedSupplement> supplements = supplementService.findAllNotPremium();
                stuffs.add(supplements);
                sendMessage(resp, HttpServletResponse.SC_OK, stuffs);
            }

        }catch (SQLException e){
            handleSqlError(resp, e);
        }catch (ServiceException ex){
            ex.getMessage();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            SupplementDao supplementDao = new DatabaseSupplementDao(connection);
            SupplementService supplementService = new SimpleSupplementService(supplementDao);

            String name = req.getParameter("name");
            String supplement_type = req.getParameter("supplement_type");
            String img = req.getParameter("img");
            int price = Integer.parseInt(req.getParameter("price"));
            boolean premium = Boolean.valueOf(req.getParameter("premium"));

            supplementService.add(name,supplement_type,img,price,premium);

            sendMessage(resp, HttpServletResponse.SC_OK, "Added to shop.");

        }catch (SQLException e){
            handleSqlError(resp, e);
        }
    }
}
