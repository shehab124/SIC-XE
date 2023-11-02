package com.company;

public class Literal_Table {
    String literal;
    String address;

    public Literal_Table(String literal, String address) {
        this.literal = literal;
        this.address = address;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return
                literal + "\t" +address;
    }
}
