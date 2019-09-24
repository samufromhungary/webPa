package com.codecool.web.servlet;

import com.codecool.web.dao.AccountDataDao;
import com.codecool.web.dao.SupplementDao;
import com.codecool.web.dao.database.DatabaseAccountDataDao;
import com.codecool.web.dao.database.DatabaseSupplementDao;
import com.codecool.web.model.Account;
import com.codecool.web.model.AccountData;
import com.codecool.web.model.Supplement;
import com.codecool.web.model.CountedSupplement;
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
import java.rmi.ServerError;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/protected/inventory")
public class InventoryServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            SupplementDao supplementDao = new DatabaseSupplementDao(connection);

            SupplementService supplementService = new SimpleSupplementService(supplementDao);

            Account account = (Account) req.getSession().getAttribute("account");

            int accountId = account.getId();

            List<CountedSupplement> purchasedSupplements = supplementService.findAllById(accountId);

            List<CountedSupplement> countedSupplements = new ArrayList<>();

            for (CountedSupplement supp : purchasedSupplements) {
                Optional<CountedSupplement> filter = countedSupplements.stream().filter(p -> p.name.equalsIgnoreCase(supp.name)).findFirst();
                if(!filter.isEmpty()) {
                    CountedSupplement csupp = filter.get();
                    csupp.counter += 1;
                }else{
                    countedSupplements.add(new CountedSupplement(supp.getId(),supp.getName(),supp.getSupplement_type(),supp.getImg(),supp.getPrice(),supp.isPremium(),1));
                }
            }

            sendMessage(resp, HttpServletResponse.SC_OK, purchasedSupplements);
        } catch (SQLException exc) {
            handleSqlError(resp, exc);
        }catch (ServiceException ex){
            ex.getMessage();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            SupplementDao supplementDao = new DatabaseSupplementDao(connection);
            AccountDataDao accountDataDao = new DatabaseAccountDataDao(connection);

            SupplementService supplementService = new SimpleSupplementService(supplementDao);
            AccountDataService accountDataService = new SimpleAccountDataService(accountDataDao);

            Account account = (Account) req.getSession().getAttribute("account");

            int accountId = account.getId();
            int supplementId = Integer.parseInt(req.getParameter("supplementBuyId"));
            CountedSupplement supplement = supplementService.findById(supplementId);
            AccountData acc = accountDataService.findById(accountId);
            int price = supplement.getPrice();

            if (price <= acc.getBalance()){
                accountDataService.modifyBalance(accountId, price, false);
                accountDataService.modifyPurchases(accountId, 1);
                supplementService.buy(accountId, supplementId);
                if(acc.getPurchases() > 9){
                    accountDataService.modifyRegular(accountId, true);
                }
                req.getSession().setAttribute("account", account);
                sendMessage(resp, HttpServletResponse.SC_OK, "Purchased successfully");
            }else{
                req.getSession().setAttribute("account", account);
                sendMessage(resp,HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Not enough money");
            }

        } catch (SQLException exc) {
            handleSqlError(resp, exc);
        }catch (ServiceException ex){
            ex.getMessage();
        }
    }
}
