package com.anthony;

import com.anthony.accounts.BankAccount;

public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("Anthony");
        account.createAccountTable();
        account.setAccountID((account.getLatestID() + 1));
        account.addAccount();
        System.out.println("Number of accounts: " + account.getAllAccounts());
        System.out.println("Account ID: " + account.getAccountID());
        System.out.println("Account Holder: " + account.getAccountHolder());
        account.deleteAccount();
        System.out.println("Number of accounts: " + account.getAllAccounts());
    }
}