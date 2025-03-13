package com.anthony;

import com.anthony.accounts.BankAccount;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        Scanner in = new Scanner(System.in);

        System.out.println("Please enter in your full name for your account: ");
        String name = in.nextLine();

        BankAccount account = new BankAccount(name);

        db.createTable();
        account.addAccount();
        System.out.println("Account added.");
        System.out.println("Number of accounts: " + account.getAllAccounts());
        System.out.println("Account Holder: " + account.getAccountHolder());
        System.out.println("Account #: " + account.getAccountID());
        db.close();
    }
}