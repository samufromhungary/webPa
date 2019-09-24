package com.codecool.web.model;

import java.util.Objects;

public final class AccountData extends Account {

    public String adress;
    public int purchases;
    public String name;
    public int phone;
    public int balance;
    public boolean regular;

    public AccountData(int id, String email, String password, boolean permission, String adress, int purchases, String name, int phone, int balance, boolean regular){
        super(id, email, password, permission);
        this.adress = adress;
        this.name = name;
        this.phone = phone;
        this.balance = balance;
        this.regular = regular;
        this.purchases = purchases;
    }


    public String getAdress() {
        return adress;
    }

    public int getPurchases() {
        return purchases;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isRegular() {
        return regular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AccountData accountData = (AccountData) o;
        return Objects.equals(adress, accountData.adress) &&
            Objects.equals(purchases, accountData.purchases) &&
            Objects.equals(name, accountData.name) &&
            Objects.equals(phone, accountData.phone) &&
            Objects.equals(balance, accountData.balance) &&
            Objects.equals(regular, accountData.regular);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), adress, purchases, name, phone, balance, regular);
    }
}
