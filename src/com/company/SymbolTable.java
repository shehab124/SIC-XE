package com.company;

public class SymbolTable
{
    String symbol;
    String address;

    public SymbolTable(String symbol, String address) {
        this.symbol = symbol;
        this.address = address;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return  symbol + '\t'+ address;
    }
}
