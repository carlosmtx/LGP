package com.lgp.lgp;

public class Person {
    private static Person instance = null;
    private String name;
    private int  saldo;
    protected Person() {
        name = "Joao Soares";
        saldo = 2000;
    }
    public static Person getInstance() {
        if(instance == null) {
            instance = new Person();
        }
        return instance;
    }

    public String getName()
    {
        return name;
    }

    public int getSaldo()
    {
        return saldo;
    }

    public void setName(String n)
    {
        name = n;
    }

    public void setSaldo(int s)
    {
        saldo = s;
    }
}