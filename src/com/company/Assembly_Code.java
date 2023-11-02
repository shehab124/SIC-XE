package com.company;

public class Assembly_Code {
    String address = null;
    String symbol = null;
    String mnemonic;
    String variable;
    String objectCode;
    String format = "0";
    String opcode;

    public Assembly_Code(String symbol, String mnemonic, String variable) {
        this.symbol = symbol;
        this.mnemonic = mnemonic;
        this.variable = variable;
    }

    public Assembly_Code(String mnemonic, String variable) {
        this.mnemonic = mnemonic;
        this.variable = variable;
    }

    public Assembly_Code(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public String getVariable() {
        return variable;
    }

    public String getAddress() {

         if (address.length() == 1) {
            address = "000" + address;
        } else if (address.length() == 2) {
            address = "00" + address;
        } else if (address.length() == 3) {
            address = "0" + address;
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setObjectCode(String objectCode)
    {
        this.objectCode = objectCode;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    @Override
    public String toString() {
        if(symbol == null && variable  == null)
        {
            return "\t\t" + mnemonic;
        }
        else if(symbol == null)
        {
            return "\t\t" + mnemonic + "\t" + variable;
        }
        else if(variable == null)
        {
            return symbol + "\t" + mnemonic;
        }
        else {
            return symbol + "\t" + mnemonic + "\t" + variable;
        }
    }
}