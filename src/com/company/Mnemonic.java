package com.company;
public class Mnemonic
{
    String mn; //mnemonic
    String format;
    String opcode;

    public Mnemonic()
    {

    }

    public Mnemonic(String mn, String format, String opcode) {
        this.mn = mn;
        this.format = format;
        this.opcode = opcode;
    }

    public String getMn() {
        return mn;
    }

    public String getFormat() {
        return format;
    }

    public String getOpcode() {
        return opcode;
    }

    @Override
    public String toString() {
        return mn + "  " + format + "  " + opcode;
    }
}

