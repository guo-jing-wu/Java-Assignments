package edu.temple.cis.c3238.banksim;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 */
public class Bank {

    public static final int NTEST = 10;
    private Account[] accounts;
    private long ntransacts = 0;
    private int initialBalance;
    private int numAccounts;
    private int transact;
    private boolean open;
    private boolean flag;

    public Bank(int numAccounts, int initialBalance) {
        open = true;
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
        accounts = new Account[numAccounts];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(this, i, initialBalance);
        }
        ntransacts = 0;
    }

    public void transfer(int from, int to, int amount) {
        synchronized (this) {
            while (flag == true){
                try {
                    wait();
                }
                catch (InterruptedException e) {
                    //required InterruptedException for wait()
                }
            }
        }
        accounts[from].waitForAvailableFunds(amount);
        if (!open) return;
        synchronized (this) {
            transact++;
        }
        if (accounts[from].withdraw(amount)) {
            accounts[to].deposit(amount);
        }
        synchronized (this) {
            transact--;
            if(transact == 0){
                notifyAll();
            }
        }
        if (shouldTest()) test();
    }

    public synchronized void test() {
        flag = true;
        while (transact != 0){
            try {
                wait();
            }
            catch (InterruptedException e) {
                //required InterruptedException for wait()
            }
        }
        int sum = 0;
        for (int i = 0; i < accounts.length; i++) {
            System.out.printf("%s %s%n", 
                    Thread.currentThread().toString(),accounts[i].toString());
            sum += accounts[i].getBalance();
        }
        System.out.println(Thread.currentThread().toString() + 
            " Sum: " + sum);
        if (sum != numAccounts * initialBalance) {
            System.out.println(Thread.currentThread().toString() + 
                    " Money was gained or lost");
            System.exit(1);
        } else {
            System.out.println(Thread.currentThread().toString() + 
                    " The bank is in balance");
        }
        flag = false;
        notifyAll();
    }

    public int size() {
        return accounts.length;
    }
    
    public synchronized boolean isOpen() {return open;}
    
    public void closeBank() {
        synchronized (this) {
            open = false;
        }
        for (Account account : accounts) {
            synchronized(account) {
                account.notifyAll();
            }
        }
    }
    
    public synchronized boolean shouldTest() {
        return ++ntransacts % NTEST == 0;
    }

}
