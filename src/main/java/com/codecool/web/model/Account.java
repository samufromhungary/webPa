package com.codecool.web.model;

import java.util.Objects;

public class Account extends AbstractModel {

    private final String email;
    private final String password;
    private final boolean permission;

    public Account(int id, String email, String password, boolean permission){
        super(id);
        this.email = email;
        this.password = password;
        this.permission = permission;
    }

    public String getEmail() {return email;}

    public String getPassword() {return password;}

    public boolean isPermission() {return permission;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Account account = (Account) o;
        return Objects.equals(email, account.email) &&
            Objects.equals(password, account.password) &&
            Objects.equals(permission, account.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, permission);
    }
}
