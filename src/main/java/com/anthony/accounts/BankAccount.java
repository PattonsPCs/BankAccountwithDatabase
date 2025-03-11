package com.anthony.accounts;

import com.anthony.Database;

public class BankAccount {
    private final Database db;
    private final String accountHolder;
    private int accountID;

    public BankAccount(String accountHolder) {
        db = new Database();
        this.accountHolder = accountHolder;
    }

    public void createAccountTable() {
        db.createTable();
    }

    public void addAccount() {
        db.addEntry(Integer.toString(accountID), accountHolder);
    }

    public void deleteAccount() {
        db.deleteEntry(Integer.toString(accountID), accountHolder);
    }

    public int getAllAccounts() {
        return db.getTableLength();
    }

    public String getAccountHolder(){
        return db.getColumnName();
    }


    public String getAccountID() {
        return db.getValue();
    }

    public void setAccountID(int ID){
        this.accountID = ID;
    }

    public int getLatestID(){
        return db.getLatestID();
    }

}

