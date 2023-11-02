package com.company;

import java.io.PrintWriter;
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    public static int searchMnemonicList(String target) //returns index of mnemonic in mnemonic list
    {
        for(int i=0;i<mnemonicList.size();i++)
        {
            String mnemonic_type123 = mnemonicList.get(i).mn;
            String mnemonic_type4 = "+"+mnemonicList.get(i).mn;
            String mnemonic_type5 = "&"+mnemonicList.get(i).mn;
            String mnemonic_type6 = "$"+mnemonicList.get(i).mn;
            if(target.equals(mnemonic_type123) || target.equals(mnemonic_type4) || target.equals(mnemonic_type5) || target.equals(mnemonic_type6))
            {
                return i;
            }
        }
        return -1;
    }
    public static boolean isInt(String str) {

        try {
            int x = Integer.parseInt(str);
            return true; //String is an Integer
        } catch (NumberFormatException e) {
            return false; //String is not an Integer
        }
    }


    static ArrayList<Mnemonic> mnemonicList = new ArrayList<>(); //array list of all mnemonics
    static ArrayList<Assembly_Code> assembly_codeList = new ArrayList<>(); //list of code
    static ArrayList<SymbolTable> symbolTableArrayList = new ArrayList<>();
    static String baselength;  // has variable of base then its address from symbol table
    static  ArrayList<Literal_Table> literal_tableArrayList = new ArrayList<>(); //literal table
    static String [] External_def;
    static String [] External_ref;


    public static void main(String[] args) {

        String programmeName = null;
        String start = null;
        String Starting_location = "0";
        String Startloc = "0";

        ///////////////////READ MNEMONICS FILE//////////////////////////////////////////////
        try {
            File mnFile = new File("D:\\AAST\\term 7\\Systems programming\\Project SICXE\\src\\mnemonic"); //mnemonics file
            Scanner sc = new Scanner(mnFile); //scanner mnemonics
            while (sc.hasNextLine()) { //read mnemonic file
                String s1 = sc.next();
                String s2 = sc.next();
                String s3 = sc.next();
                Mnemonic mnem = new Mnemonic(s1, s2, s3);
                mnemonicList.add(mnem); //Add all mnemonics from file to this arraylist
            }
            sc.close();
        }
        catch (Exception e)
        {
            System.out.println("Mnemonic File not found!!");
        }

        ////////////////////////////////////////////////////////////////////////////////
        ///////////////////////READ ASSEMBLY CODE FILE AND INSERT INTO ASSEMBLY CODE ARRAYLIST ///////////////////////////////////
        try {
            File assFile = new File("D:\\AAST\\term 7\\Systems programming\\Project SICXE\\src\\input_AssemblyCode");
            Scanner sc = new Scanner(assFile); //file code

            //////FIRST LINE OF CODE///////
            programmeName=sc.next(); //programme name
            programmeName =programmeName.toUpperCase();
            start=sc.next(); //start
            start =start.toUpperCase();
            Starting_location=sc.next(); //starting location
            Startloc = Starting_location;
            Assembly_Code firstline = new Assembly_Code(programmeName,start,Starting_location);
            assembly_codeList.add(firstline);
            assembly_codeList.get(0).setAddress(Starting_location);


            while (sc.hasNextLine()) { //reading code
                int count=3; //some lines have 3 strings and others have 2 strings
                String s1 = sc.next();
                s1 = s1.toUpperCase();
                if(s1.equals("END"))
                {
                    String endlocation = sc.next();
                    Assembly_Code assembly_code = new Assembly_Code(null, s1,endlocation);
                    assembly_codeList.add(assembly_code);
                    break;
                }
                else if(s1.equals("BASE"))    //PASS 2 ,READ BASE LINE
                {
                    count = 2;
                    String s2 = sc.next();
                    s2 = s2.toUpperCase();
                    Assembly_Code assembly_code = new Assembly_Code(null, s1, s2);
                    assembly_codeList.add(assembly_code);
                    continue;
                }
                else if(s1.equals("LTORG"))
                {
                    count =1;
                    Assembly_Code assembly_code = new Assembly_Code(null, s1, null);
                    assembly_codeList.add(assembly_code);
                }
                else if(s1.equals("EXTDEF"))
                {
                    count =2;
                    String s2 = sc.next();
                    s2 = s2.toUpperCase();
                    Assembly_Code assembly_code = new Assembly_Code(null, s1, s2);
                    External_def = s2.split(",");
                    assembly_codeList.add(assembly_code);
                    continue;
                }
                else if(s1.equals("EXTREF"))
                {
                    count =2;
                    String s2 = sc.next();
                    s2 = s2.toUpperCase();
                    Assembly_Code assembly_code = new Assembly_Code(null, s1, s2);
                    External_ref = s2.split(",");
                    assembly_codeList.add(assembly_code);
                    continue;
                }
                else{  // ALL MNEMONICS
                    for (int i = 0; i < mnemonicList.size(); i++)
                    {
                        String mnemonic_type123 = mnemonicList.get(i).mn;
                        String mnemonic_type4 = "+"+mnemonicList.get(i).mn;
                        String mnemonic_type5 = "&"+mnemonicList.get(i).mn;
                        String mnemonic_type6 = "$"+mnemonicList.get(i).mn;
                        if(s1.equals(mnemonic_type123) && mnemonicList.get(i).format.equals("1"))
                        {
                            count = 1;
                        }
                        else if (s1.equals(mnemonic_type123) || s1.equals(mnemonic_type4) || s1.equals(mnemonic_type5) || s1.equals(mnemonic_type6))
                        {
                            if(s1.equals("RSUB") || s1.equals("+RSUB") || s1.equals("&RSUB") || s1.equals("$RSUB") ){
                                count=1;
                            }
                            else {
                                count = 2;
                            }
                        }
                    }
                    //String variable;
                    if (count == 3) {
                        String s2 = sc.next();
                        s2 = s2.toUpperCase();
                        String variable = sc.next();
                        variable = variable.toUpperCase();
                        Assembly_Code assembly_code = new Assembly_Code(s1, s2, variable);
                        assembly_codeList.add(assembly_code);
                    } else if (count == 2) {
                        String s2 = sc.next();
                        s2 = s2.toUpperCase();
                        Assembly_Code assembly_code = new Assembly_Code(null, s1, s2);
                        assembly_codeList.add(assembly_code);
                    }
                    else {   //count = 1
                        Assembly_Code assembly_code = new Assembly_Code(null, s1, null);
                        assembly_codeList.add(assembly_code);
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Assembly input file not found!");
        }
        catch (Exception e)
        {
            System.out.println("Logical error in assembly code reading!!");
        }

        ////////////////////////////LITERALS/////////////////////////////
        //////////////////PUT LITERALS AFTER LTORG OR END///////////////
        for(int i=0;i<assembly_codeList.size();i++)
        {
            if(assembly_codeList.get(i).mnemonic.equals("LTORG") || assembly_codeList.get(i).mnemonic.equals("END"))
            {
                for(int j=i-1;j>0 && !(assembly_codeList.get(j).mnemonic.equals("LTORG"));j--)
                {
                    if(assembly_codeList.get(j).variable != null)
                        if(assembly_codeList.get(j).variable.charAt(0) == '='  )
                        {
                            Assembly_Code literal = new Assembly_Code("*",assembly_codeList.get(j).variable,null);
                            assembly_codeList.add(i+1,literal);
                        }
                }
            }
        }

        /*for(int i=0;i<assembly_codeList.size();i++)
        {
            if(assembly_codeList.get(i).variable != null)
            {
                if(assembly_codeList.get(i).variable.charAt(0) == '=')
                {
                    for(int j=i;j<assembly_codeList.size();j++)
                    {
                        if(assembly_codeList.get(j).mnemonic.equals("LTORG") || assembly_codeList.get(j).mnemonic.equals("END"))
                        {
                            Assembly_Code literal = new Assembly_Code("*",assembly_codeList.get(i).variable,null);
                            assembly_codeList.add(j+1,literal);
                            break;
                        }
                    }
                }
            }

        }*/


        /////////////////////////////PASS1//////////////////////////////
        //////////////////CALCULATE LOCATION COUNTER////////////////////
        /////////////////////////check format  if(word,resw,..) else foramt 1,2,3,4,5,6
        assembly_codeList.get(1).setAddress(Starting_location);
        for(int i=1; i<assembly_codeList.size()-1;i++){

            if(assembly_codeList.get(i).mnemonic.equals("BASE"))   //for pass2
            {
                assembly_codeList.get(i+1).setAddress(assembly_codeList.get(i).getAddress());
                assembly_codeList.get(i).setAddress(assembly_codeList.get(i+1).getAddress());
                baselength = assembly_codeList.get(i).variable;

            }
            else if(assembly_codeList.get(i).mnemonic.equals("EXTDEF") ||assembly_codeList.get(i).mnemonic.equals("EXTREF"))
            {
                assembly_codeList.get(i+1).setAddress(assembly_codeList.get(i).getAddress());
                assembly_codeList.get(i).setAddress(assembly_codeList.get(i+1).getAddress());
                baselength = assembly_codeList.get(i).variable;
            }
            else if(assembly_codeList.get(i).mnemonic.equals("LTORG"))
            {
                assembly_codeList.get(i+1).setAddress(assembly_codeList.get(i).getAddress());
                assembly_codeList.get(i).setAddress(assembly_codeList.get(i+1).getAddress());
            }
            else if(assembly_codeList.get(i).mnemonic.equals("END") && i != assembly_codeList.size()-1)
            {
                assembly_codeList.get(i+1).setAddress(assembly_codeList.get(i).getAddress());
                assembly_codeList.get(i).setAddress(assembly_codeList.get(i+1).getAddress());
            }
            else if(assembly_codeList.get(i).mnemonic.equals("END"))
            {
                continue;
            }
            else if( assembly_codeList.get(i).mnemonic.charAt(0) == '=')
            {
                char corx = assembly_codeList.get(i).mnemonic.charAt(1);
                String characters = assembly_codeList.get(i).mnemonic;

                if(corx == 'X')
                {
                    int size = (int)Math.ceil((characters.length() -4)/ 2.0);
                    int value = Integer.parseInt(Starting_location, 16);
                    value +=size;
                    Starting_location = Integer.toHexString(value);
                    assembly_codeList.get(i+1).setAddress(Starting_location);
                }
                else if( corx == 'C')
                {
                    int size = characters.length() - 4;
                    int value = Integer.parseInt(Starting_location, 16);
                    value +=size;
                    Starting_location = Integer.toHexString(value);
                    assembly_codeList.get(i+1).setAddress(Starting_location);
                }
            }
            else if(assembly_codeList.get(i).variable != null && assembly_codeList.get(i).variable.charAt(0) == '=')  //ENDFIL LDA =C'EOF'
            {
                int index = searchMnemonicList(assembly_codeList.get(i).mnemonic);
                String format = mnemonicList.get(index).getFormat();
                String opcode = mnemonicList.get(index).getOpcode();
                assembly_codeList.get(i).setOpcode(opcode);
                if(format.equals("3/4"))
                {
                    if(assembly_codeList.get(i).mnemonic.charAt(0) == '+')
                    {
                        format = "4";
                        int value = Integer.parseInt(Starting_location, 16);
                        value += 4;
                        Starting_location = Integer.toHexString(value);
                        assembly_codeList.get(i + 1).setAddress(Starting_location);
                    }
                    else {
                        format = "3";
                        int value = Integer.parseInt(Starting_location, 16);
                        value += 3;
                        Starting_location = Integer.toHexString(value);
                        assembly_codeList.get(i + 1).setAddress(Starting_location);
                    }
                }
                assembly_codeList.get(i).setFormat(format);


            }
            else if( assembly_codeList.get(i).mnemonic.equals("RESW"))
            {
                String v=assembly_codeList.get(i).variable;
                int var=Integer.parseInt(v);
                var=var*3;
                String varhex = Integer.toHexString(var);

                int value = Integer.parseInt(Starting_location, 16);
                value += var;
                Starting_location = Integer.toHexString(value);
                assembly_codeList.get(i+1).setAddress(Starting_location);
            }
            else if (assembly_codeList.get(i).mnemonic.equals("WORD"))
            {
                int value = Integer.parseInt(Starting_location, 16);
                value += 3;
                Starting_location = Integer.toHexString(value);
                assembly_codeList.get(i+1).setAddress(Starting_location);
            }
            else if (assembly_codeList.get(i).mnemonic.equals("RESB"))
            {
                String v=assembly_codeList.get(i).variable;
                int var=Integer.parseInt(v);
                String varhex = Integer.toHexString(var);

                int value = Integer.parseInt(Starting_location, 16);
                value += var;
                Starting_location = Integer.toHexString(value);
                assembly_codeList.get(i+1).setAddress(Starting_location);

            }

            else if (assembly_codeList.get(i).mnemonic.equals("BYTE"))
            {
                char corx = assembly_codeList.get(i).variable.charAt(0);
                String characters = assembly_codeList.get(i).variable;

                if(corx == 'X')
                {
                    int size = (int)Math.ceil((characters.length() -3)/ 2.0);
                    int value = Integer.parseInt(Starting_location, 16);
                    value +=size;
                    Starting_location = Integer.toHexString(value);
                    assembly_codeList.get(i+1).setAddress(Starting_location);
                }
                else if( corx == 'C')
                {
                    int size = characters.length() - 3;
                    int value = Integer.parseInt(Starting_location, 16);
                    value +=size;
                    Starting_location = Integer.toHexString(value);
                    assembly_codeList.get(i+1).setAddress(Starting_location);
                }
            }
            else {

                int index = searchMnemonicList(assembly_codeList.get(i).mnemonic);
                String format = mnemonicList.get(index).getFormat();
                String opcode = mnemonicList.get(index).getOpcode(); //for pass 2
                assembly_codeList.get(i).setOpcode(opcode); // for pass 2
                char firstchar = assembly_codeList.get(i).getMnemonic().charAt(0);
                if (firstchar == '+')
                {
                    assembly_codeList.get(i).setFormat("4");
                    int value = Integer.parseInt(Starting_location, 16);
                    value += 4;
                    Starting_location = Integer.toHexString(value);
                    assembly_codeList.get(i + 1).setAddress(Starting_location);
                } else if (firstchar == '&') {
                    assembly_codeList.get(i).setFormat("5");
                    int value = Integer.parseInt(Starting_location, 16);
                    value += 3;
                    Starting_location = Integer.toHexString(value);
                    assembly_codeList.get(i + 1).setAddress(Starting_location);
                } else if (firstchar == '$') {
                    assembly_codeList.get(i).setFormat("6");
                    int value = Integer.parseInt(Starting_location, 16);
                    value += 4;
                    Starting_location = Integer.toHexString(value);
                    assembly_codeList.get(i + 1).setAddress(Starting_location);
                } else //format 1,2,3
                {
                    if (format.equals("1")) {
                        assembly_codeList.get(i).setFormat("1");
                        int value = Integer.parseInt(Starting_location, 16);
                        value++;
                        Starting_location = Integer.toHexString(value);
                        assembly_codeList.get(i + 1).setAddress(Starting_location);
                    } else if (format.equals("2")) {
                        assembly_codeList.get(i).setFormat("2");
                        int value = Integer.parseInt(Starting_location, 16);
                        value += 2;
                        Starting_location = Integer.toHexString(value);
                        assembly_codeList.get(i + 1).setAddress(Starting_location);
                    } else if (format.equals("3/4")) {
                        assembly_codeList.get(i).setFormat("3");
                        int value = Integer.parseInt(Starting_location, 16);
                        value += 3;
                        Starting_location = Integer.toHexString(value);
                        assembly_codeList.get(i + 1).setAddress(Starting_location);
                    }
                }
            }
        }

        /////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////PRINT ASSEMBLY CODE AND PASS1 IN CONSOLE///////////////////////////// todo
        /*System.out.println(assembly_codeList.get(0).getAddress() + " " + assembly_codeList.get(0)); //print codee!!!
        for (int i=1;i<assembly_codeList.size();i++)  //print code
        {
            System.out.print( assembly_codeList.get(i).getAddress()+ " ");
            System.out.print(assembly_codeList.get(i).toString());
            System.out.print( "   "+assembly_codeList.get(i).opcode);
            System.out.println("    "+assembly_codeList.get(i).format);
        }*/
        ///////////////////////////////////////////////////////////////////////////////////////////



        //////////////////////////////get all symbols and their address and add them to symbol table arraylist//////////
        for (int i=1;i<assembly_codeList.size();i++)
        {
            if(assembly_codeList.get(i).getSymbol()!=null && !(assembly_codeList.get(i).getSymbol().equals("*")))
            {
                String symbol = assembly_codeList.get(i).getSymbol();
                String add = assembly_codeList.get(i).getAddress();
                com.company.SymbolTable symbolTable = new com.company.SymbolTable(symbol,add);
                symbolTableArrayList.add(symbolTable);
            }

        }
        ////////////////////////////////////////////////////////////////////
        /////////////////PRINT SYMBOL TABLE IN A FILE///////////////////////
        try {
            File symbol_Table_File = new File("symbol table.txt");
            PrintWriter printSymbolTable = new PrintWriter(symbol_Table_File);
            printSymbolTable.println("Symbol Table");
            printSymbolTable.println("------------");
            printSymbolTable.println("Symbol\tAddress");
            for (int i=0;i<symbolTableArrayList.size();i++)
            {
                printSymbolTable.println(symbolTableArrayList.get(i).toString());
            }
            printSymbolTable.close();
        }
        catch (Exception e)
        {
            System.out.println("Symbol Table File Not Found!!");
        }

        ////////////////////////Literal Table////////////////////////////
        for(int i=1;i<assembly_codeList.size();i++)
        {
            if(assembly_codeList.get(i).getSymbol()!=null && assembly_codeList.get(i).symbol.equals("*"))
            {
                String symbol = assembly_codeList.get(i).mnemonic;
                String address = assembly_codeList.get(i).getAddress();
                boolean flag = false;
                for(int j=0;j<literal_tableArrayList.size();j++)
                {
                    if(literal_tableArrayList.get(j).literal.equals(symbol))
                        flag=true;
                }
                if(!flag)
                    literal_tableArrayList.add(new Literal_Table(symbol,address));
            }
        }
        ////////////////////////////////////////////////////////////////////
        ///////////////////////////Print literal table in a file//////////////
        try {
            File literal_Table_File = new File("Literal Table.txt");
            PrintWriter printSymbolTable = new PrintWriter(literal_Table_File);
            printSymbolTable.println("Literal Table");
            printSymbolTable.println("------------");
            printSymbolTable.println("Literal\tAddress");
            for (int i = 0; i< literal_tableArrayList.size(); i++)
            {
                printSymbolTable.println(literal_tableArrayList.get(i).toString());
            }
            printSymbolTable.close();
        }
        catch (Exception e)
        {
            System.out.println("Literal table File Not Found!!");
        }

        //////////////////PRINT ASSEMBLY CODE AND PASS1 IN A FILE////////////////////
        try {
            File pass1_File = new File("PASS1.txt");
            PrintWriter printPass1 = new PrintWriter(pass1_File);
            printPass1.println("Pass 1");
            printPass1.println("------");
            for (int i=0;i<assembly_codeList.size();i++)
            {
                printPass1.print(assembly_codeList.get(i).getAddress() + '\t');
                printPass1.println(assembly_codeList.get(i).toString());
            }
            printPass1.close();
        }
        catch (Exception e)
        {
            System.out.println("Pass1 File Not Found!!");
        }


        /////////////////////////////////////////////////////
        ///////////////////////////pass 2////////////////////
        for (int i=1;i<assembly_codeList.size();i++)
        {
            String n=null,ii=null,x=null,b=null,e=null;
            String p= "1";
            String format = assembly_codeList.get(i).getFormat();
            String opcode = assembly_codeList.get(i).getOpcode();
            String objectCode;
            String TA =null;
            String displacment;
            String first6opcode = null;

            if(format.equals("1"))
            {
                assembly_codeList.get(i).setObjectCode(opcode);
                continue;
            }
            else if(format.equals("2"))
            {
                calculateFormat2(i, opcode);
            }
            else if(format.equals("3"))
            {
                CalculateFormat3(i, opcode, TA);
            }
            else if(format.equals("4"))
            {
                CalculateFormat4(i, opcode, TA);
            }
            else if(format.equals("5"))
            {
                CalculateFormat5(i,opcode,TA);
            }
            else if(format.equals("6"))
            {
                CalculateFormat6(i,  opcode, TA);
            }
            else if( assembly_codeList.get(i).mnemonic.equals("WORD"))
            {
                int  a = Integer.parseInt(assembly_codeList.get(i).variable);
                String word = Integer.toHexString(a);
                word = fixLengthTo6(word);
                assembly_codeList.get(i).objectCode = word;
            }
            else if(assembly_codeList.get(i).mnemonic.equals("BYTE"))
            {
                if(assembly_codeList.get(i).variable.charAt(0) == 'X')
                {
                    int length = assembly_codeList.get(i).variable.length();
                    String objectcode = assembly_codeList.get(i).variable.substring(2,length-1);
                    assembly_codeList.get(i).setObjectCode(objectcode);
                }
                else if(assembly_codeList.get(i).variable.charAt(0) == 'C')
                {
                    int length = assembly_codeList.get(i).variable.length();
                    String objectcode = assembly_codeList.get(i).variable.substring(2,length-1);
                    objectcode = String_to_ASCII(objectcode);
                    assembly_codeList.get(i).setObjectCode(objectcode);
                }
            }
            else if(assembly_codeList.get(i).mnemonic.charAt(0) == '=')
            {
                if(assembly_codeList.get(i).mnemonic.charAt(1) == 'X')
                {
                    int length = assembly_codeList.get(i).mnemonic.length();
                    String objectcode = assembly_codeList.get(i).mnemonic.substring(3,length-1);
                    assembly_codeList.get(i).setObjectCode(objectcode);
                }
                else if(assembly_codeList.get(i).mnemonic.charAt(1) == 'C')
                {
                    int length = assembly_codeList.get(i).mnemonic.length();
                    String objectcode = assembly_codeList.get(i).mnemonic.substring(3,length-1);
                    objectcode = String_to_ASCII(objectcode);
                    assembly_codeList.get(i).setObjectCode(objectcode);
                }
            }
            else
                assembly_codeList.get(i).setObjectCode(null);
        }


        ///////////////print pass 2 in pass2 file\\\\\\\\\\\\\\\\\\\\\\\\\\
        try {
            File pass2_File = new File("PASS2.txt");
            PrintWriter printPass2 = new PrintWriter(pass2_File);
            printPass2.println("Pass 2");
            printPass2.println("------");
            printPass2.print(assembly_codeList.get(0).getAddress() + '\t');
            printPass2.println(assembly_codeList.get(0).toString());
            for (int i=1;i<assembly_codeList.size();i++)
            {
                printPass2.print(assembly_codeList.get(i).getAddress() + '\t');
                printPass2.print(assembly_codeList.get(i).toString());
                printPass2.println("\t"+assembly_codeList.get(i).objectCode);
            }
            printPass2.close();
        }
        catch (Exception e)
        {
            System.out.println("Pass2 File Not Found!!");
        }
        ////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////
        
        
        ////////////////////////////HTE RECORD////////////////////////////////////
        try {
            File HTE_File = new File("HTE.txt");
            PrintWriter printHTE = new PrintWriter(HTE_File);


            //             H RECORD    /////////
            if(programmeName.length()<6)      //programme name
                programmeName = String.join("", Collections.nCopies(6-programmeName.length(),"X")) + programmeName;

            String codeLength = Integer.toHexString(Integer.parseInt(Starting_location,16) - Integer.parseInt(Startloc,16));
            codeLength = fixLengthTo6(codeLength);
            Startloc = fixLengthTo6(Startloc);
            printHTE.println("H" + "." + programmeName + "." + Startloc + "." +codeLength);
            ////////////////////////////////////////////
            ///////////////D record//////////////////////
            printHTE.print("D.");
            for(int j=0;j<External_def.length;j++)
            {
                String x = External_def[j];
                printHTE.print(x+ ".");
                for(int k=1;k<assembly_codeList.size();k++)
                {
                    System.out.println(assembly_codeList.get(k).symbol + "    " + x);
                    if(assembly_codeList.get(k).symbol != null) {
                        if (assembly_codeList.get(k).symbol.contains(x)) {
                            printHTE.print("." + assembly_codeList.get(k).address);
                        }
                    }
                }
            }
            printHTE.println();
            ////////////R record///////////////////
            printHTE.print("R");
            for(int j=0;j<External_ref.length;j++)
            {
                String x = External_ref[j];
                if(x.length()<6)
                x =  x + String.join("", Collections.nCopies(6-x.length(),"X"));
                printHTE.print( "."+x);
            }
            printHTE.println();



            //           T RECORD    ///////////
            int i=1;
            int sizerecord=0;
            int startingsize=0;
            String textrecord="";
            String startingaddress="";
            while (i<assembly_codeList.size())
            {
                if(startingsize == 0)
                {
                    startingaddress = assembly_codeList.get(i-1).address;
                }
                if(!(assembly_codeList.get(i).mnemonic.equals("RESW")) && !(assembly_codeList.get(i).mnemonic.equals("RESB")) && !(assembly_codeList.get(i).mnemonic.equals("END")))
                {
                    startingsize = (int) Math.ceil(textrecord.length()/2.0);
                    if(!(assembly_codeList.get(i).mnemonic.equals("BASE")))
                    {
                        sizerecord= (int) Math.ceil((textrecord+assembly_codeList.get(i).objectCode).length()/2.0);
                    }
                    if(assembly_codeList.get(i).mnemonic.equals("BASE") || assembly_codeList.get(i).mnemonic.equals("LTORG"));
                    else if(sizerecord<=30) {   //////////////
                        textrecord +=  "." + assembly_codeList.get(i).objectCode ;
                    }
                    else
                    {
                        printHTE.println("T"+" 00"+ startingaddress+" " +Integer.toHexString(startingsize).toUpperCase(Locale.ROOT)+" "+ textrecord);
                        textrecord=assembly_codeList.get(i).objectCode;
                        startingsize=0;
                        sizerecord=0;
                    }
                }
                else {
                    if(!textrecord.equals(""))
                        printHTE.println("T"+" 00"+startingaddress+" " +Integer.toHexString(sizerecord)+" "+ textrecord);
                        textrecord="";
                        sizerecord=0;
                }
                i++;
            }
            //////////////////////////////////////////////////////////////////
            /////////////////// M   RECORD ///////////////////////
            String location;
            for (int i1=0;i1<assembly_codeList.size();i1++)
            {
                if(assembly_codeList.get(i1).mnemonic.equals("WORD"))
                {
                    location = assembly_codeList.get(i1).address;
                    location = fixLengthTo6(location);
                    printHTE.println("M." + location + "." + "06");
                }
                else if(assembly_codeList.get(i1).getFormat().equals("4") && assembly_codeList.get(i1).variable.charAt(0) != '#')
                {
                    location = assembly_codeList.get(i1).address;
                    int locationINT = Integer.parseInt(location,16);
                    locationINT++;
                    location = Integer.toHexString(locationINT);
                    location = fixLengthTo6(location);
                    printHTE.println("M." + location + "." + "05");
                }
            }




            /////////////////////////////////////////////////////////
            ////////////////E Record //////////////////////
            printHTE.println("E." + Startloc);

            printHTE.close();
        }
        catch (Exception e)
        {
            System.out.println("HTE RECORD FILE ERROR!!!!!");
        }
    }



    private static void calculateFormat2(int i, String opcode) {
        String objectCode;
        //REGISTERS//
        String a= "0";
        String xx= "1";
        String s = "4";
        String t = "5";
        if(assembly_codeList.get(i).variable.length() == 1) { //CLEAR A
            if (assembly_codeList.get(i).variable.equals("A")) {
                objectCode = opcode  + a + "0";
                assembly_codeList.get(i).setObjectCode(objectCode);
            } else if (assembly_codeList.get(i).variable.equals("X")) {
                objectCode = opcode  + xx+ "0";
                assembly_codeList.get(i).setObjectCode(objectCode);
            } else if (assembly_codeList.get(i).variable.equals("S")) {
                objectCode = opcode  + s + "0";
                assembly_codeList.get(i).setObjectCode(objectCode);
            } else if (assembly_codeList.get(i).variable.equals("T")) {
                objectCode = opcode + t + "0" ;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
        }
        else if(assembly_codeList.get(i).variable.length() == 3) //COMPR A,S
        {
            if(assembly_codeList.get(i).variable.equals("A,X"))
            {
                objectCode = opcode + a +xx;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
            else if(assembly_codeList.get(i).variable.equals("A,S"))
            {
                objectCode = opcode + a +s;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
            else if(assembly_codeList.get(i).variable.equals("A,T"))
            {
                objectCode = opcode + a +t;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
            else if(assembly_codeList.get(i).variable.equals("X,A"))
            {
                objectCode = opcode + xx + a;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
            else if(assembly_codeList.get(i).variable.equals("X,S"))
            {
                objectCode = opcode + xx + s;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
            else if(assembly_codeList.get(i).variable.equals("X,T"))
            {
                objectCode = opcode + xx + t;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
            else if(assembly_codeList.get(i).variable.equals("S,A"))
            {
                objectCode = opcode + s + a;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
            else if(assembly_codeList.get(i).variable.equals("S,X"))
            {
                objectCode = opcode + s + xx;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
            else if(assembly_codeList.get(i).variable.equals("S,T"))
            {
                objectCode = opcode + s + t;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
            else if(assembly_codeList.get(i).variable.equals("T,A"))
            {
                objectCode = opcode + t + a;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
            else if(assembly_codeList.get(i).variable.equals("T,X"))
            {
                objectCode = opcode + t+ xx;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }
            else if(assembly_codeList.get(i).variable.equals("T,S"))
            {
                objectCode = opcode + t +s;
                assembly_codeList.get(i).setObjectCode(objectCode);
            }

        }
    }

    private static void CalculateFormat3(int i, String opcode, String TA) {
        String ii;
        String b;
        String p;
        String e;
        String x;
        String objectCode;
        String first6opcode;
        String n;
        String displacment;
        e="0";


        if(assembly_codeList.get(i).mnemonic.contains("RSUB"))
        {
            b="0";
            p="0";
            n="1";
            ii="1";
            x = "0";
            displacment = "000";
            first6opcode = "010011";

        }
        else {
            /////////////first 6 characters of opcode///////////////////
            String opcodeBINARY;
            int opcodeINT = Integer.parseInt(opcode, 16);
            opcodeBINARY = Integer.toBinaryString(opcodeINT);
            if(opcodeBINARY.length() < 8)
            {
                opcodeBINARY = String.join("", Collections.nCopies(8-opcodeBINARY.length(),"0")) + opcodeBINARY;
            }
            first6opcode = opcodeBINARY.substring(0, 6);  // binary string
            //////////////////////////////////////////////////////////


            ////////check indexing/////////////////////////
            int length = assembly_codeList.get(i).variable.length();
            char lastChar = assembly_codeList.get(i).variable.charAt(length - 1);
            char lastlastChar = assembly_codeList.get(i).variable.charAt(length - 2);
            if (lastChar == 'X' && lastlastChar == ',') {
                x = "1";
            } else {
                x = "0";
            }
            ////////////////////////////////////////

            ////////check immediate and indirect////
            char firstChar = assembly_codeList.get(i).variable.charAt(0);
            if (firstChar == '#')  //immediate
            {
                n = "0";
                ii = "1";
                String imm = assembly_codeList.get(i).variable.substring(1);
                if (isInt(imm))    // #3 ///////////////////////////////////
                {
                    p = "0";
                    b = "0";
                    int immINT = Integer.parseInt(imm);
                    displacment = Integer.toHexString(immINT);

                }  ///////////////////////////////////////////////////
                else  //  #variable //////////////////////////////////////////
                {
                    p = "1";
                    b = "0";
                    for (int j = 0; j < symbolTableArrayList.size(); j++) {
                        if (imm.equals(symbolTableArrayList.get(j).symbol)) {
                            TA = symbolTableArrayList.get(j).address;
                        }
                    }

                    String PC = assembly_codeList.get(i + 1).address;
                    int PCint = Integer.parseInt(PC, 16);
                    int TAint = Integer.parseInt(TA, 16);
                    int displacmentint = TAint - PCint;
                    displacment = Integer.toHexString(displacmentint);  // hex string
                    if (displacmentint <= 2047 && displacmentint >= -2048) {
                        p = "1";
                        b = "0";
                    } else {
                        p = "0";
                        b = "1";
                        for (int k = 0; k < symbolTableArrayList.size(); k++) {
                            if (baselength.equals(symbolTableArrayList.get(k).symbol)) {
                                baselength = symbolTableArrayList.get(k).address;
                            }
                        }
                        int BASEint = Integer.parseInt(PC, 16);
                        TAint = Integer.parseInt(TA, 16);
                        displacmentint = TAint - BASEint;
                        displacment = Integer.toHexString(displacmentint);

                    }

                    ///////////////////////////////////////////////////////


                }
            }
            else if (firstChar == '@') //indirect  @RETADR
            {
                n = "1";
                ii = "0";

                //////////////////////////////////////////

                p = "1";
                b = "0";
                String indirect = assembly_codeList.get(i).variable.substring(1);
                for (int j = 0; j < symbolTableArrayList.size(); j++) {
                    if (indirect.equals(symbolTableArrayList.get(j).symbol)) {
                        TA = symbolTableArrayList.get(j).address;
                    }
                }

                String PC = assembly_codeList.get(i + 1).address;
                int PCint = Integer.parseInt(PC, 16);
                int TAint = Integer.parseInt(TA, 16);
                int displacmentint = TAint - PCint;
                displacment = Integer.toHexString(displacmentint);  // hex string
                if (displacmentint <= 2047 && displacmentint >= -2048) {
                    p = "1";
                    b = "0";
                } else {
                    p = "0";
                    b = "1";
                    for (int k = 0; k < symbolTableArrayList.size(); k++) {
                        if (baselength.equals(symbolTableArrayList.get(k).symbol)) {
                            baselength = symbolTableArrayList.get(k).address;
                        }
                    }
                    int BASEint = Integer.parseInt(PC, 16);
                    TAint = Integer.parseInt(TA, 16);
                    displacmentint = TAint - BASEint;
                    displacment = Integer.toHexString(displacmentint);

                }
                /////////////////////////////////////////

            }
            else if( firstChar == '=')  //literal =
            {
                n = "1";
                ii = "1";
                x = "0";
                b = "0"; //assumption
                p = "1";
                e = "0";
                for(int j=0;j<literal_tableArrayList.size();j++) {
                    if (assembly_codeList.get(i).variable.equals(literal_tableArrayList.get(j).literal)) {
                        TA = literal_tableArrayList.get(j).address;
                        break;
                    }
                }
                    String PC = assembly_codeList.get(i+1).address;
                    int PCint = Integer.parseInt(PC, 16);
                    int TAint = Integer.parseInt(TA, 16);
                    int displacmentint = TAint - PCint;
                    displacment = Integer.toHexString(displacmentint);  // hex string
                    if (displacmentint <= 2047 && displacmentint >= -2048)
                    {
                        p = "1";
                        b = "0";
                    }
                    else
                    {
                        p = "0";
                        b = "1";
                        for (int k = 0; k < symbolTableArrayList.size(); k++)
                        {
                            if (baselength.equals(symbolTableArrayList.get(k).symbol)) {
                                baselength = symbolTableArrayList.get(k).address;
                            }
                        }
                        int BASEint = Integer.parseInt(PC, 16);
                        TAint = Integer.parseInt(TA, 16);
                        displacmentint = TAint - BASEint;
                        displacment = Integer.toHexString(displacmentint);
                    }
            }
            else  //simple
            {
                n = "1";
                ii = "1";
                for (int j = 0; j < symbolTableArrayList.size(); j++) {
                    if (assembly_codeList.get(i).variable.contains(symbolTableArrayList.get(j).symbol)) {
                        TA = symbolTableArrayList.get(j).address;
                    }
                }
                String PC = assembly_codeList.get(i +1 ).address;
                int PCint = Integer.parseInt(PC, 16);
                int TAint = Integer.parseInt(TA, 16);
                int displacmentint = TAint - PCint;
                displacment = Integer.toHexString(displacmentint);  // hex string
                if (displacmentint <= 2047 && displacmentint >= -2048)
                {
                    p = "1";
                    b = "0";
                }
                else
                    {
                    p = "0";
                    b = "1";

                    for (int k = 0; k < symbolTableArrayList.size(); k++)
                    {
                        if (baselength.equals(symbolTableArrayList.get(k).symbol)) {
                            baselength = symbolTableArrayList.get(k).address;
                        }
                    }
                    int BASEint = Integer.parseInt(baselength, 16);
                    TAint = Integer.parseInt(TA, 16);
                    displacmentint = TAint - BASEint;
                    displacment = Integer.toHexString(displacmentint);
                }
            }


        }
        if(displacment.length()>3)
        {
            displacment = displacment.substring(displacment.length()-3,displacment.length());
        }
        else if(displacment.length() < 3)
        {
            displacment = String.join("", Collections.nCopies(3-displacment.length(),"0")) + displacment;
        }
        ////////////CONCATE OBJECT CODE///////////////////
        System.out.println(assembly_codeList.get(i).toString()); //todo
        System.out.println("first6opcode= " + first6opcode + " n " + n + " i " + ii + " x " + x + " b " + b + " p "+ p + " e " + e);
        System.out.println("displacment " + displacment + " TA " + TA );
        objectCode = first6opcode + n + ii + x + b + p + e;  //  6opcode + n + i + x + b + p + e
        int decimal = Integer.parseInt(objectCode,2);   // to binary string
        String semifinalobjectcode = Integer.toString(decimal,16); // to hex string
        objectCode = semifinalobjectcode + displacment;  // 6opcode + n + i + x + b + p + e + displacment
        objectCode = fixLengthTo6(objectCode);
        assembly_codeList.get(i).setObjectCode(objectCode); // set object code
    }

    private static void CalculateFormat4(int i, String opcode, String TA) {
        String ii;
        String objectCode;
        String first6opcode;
        String n;
        String displacment;
        String b;
        String p;
        String e;
        String x;
        e="1";
        char firstChar = assembly_codeList.get(i).variable.charAt(0);
        b = "0";
        p = "0";
        ii = "0"; // assumption

        int opcodeINT = Integer.parseInt(opcode, 16);
        String opcodeBINARY = Integer.toBinaryString(opcodeINT);


        if(opcodeBINARY.length() < 8)
        {
            opcodeBINARY = String.join("", Collections.nCopies(8-opcodeBINARY.length(),"0")) + opcodeBINARY;
        }
        first6opcode = opcodeBINARY.substring(0, 6);  // binary string

        ////////////////////CHECK INDEXING////////////////////////////////
        int length = assembly_codeList.get(i).variable.length();
        char lastChar = assembly_codeList.get(i).variable.charAt(length-1);
        char lastlastChar = assembly_codeList.get(i).variable.charAt(length-2);
        if(lastChar == 'X' && lastlastChar == ',')
        {
            x = "1";
        }
        else
        {
            x="0";
        }
        /////////////////////////////////////////////////////////////////

        if(firstChar == '#'){
            n="0";
            ii="1";
            String imm = assembly_codeList.get(i).variable.substring(1);

            if(isInt(imm))    // #3 ///////////////////////////////////
            {
                int immINT = Integer.parseInt(imm);
                displacment = "0" + Integer.toHexString(immINT);

            }
            else {
                for (int j = 0; j < symbolTableArrayList.size(); j++) {
                    if (imm.equals(symbolTableArrayList.get(j).symbol)) {
                        TA = symbolTableArrayList.get(j).address;
                    }
                }
                displacment = "0" + TA;
            }
        }
        else if(firstChar=='@'){  //INDIRECT
            n="1";
            ii="0";

            for(int j=0;j<symbolTableArrayList.size();j++)
            {
                if(assembly_codeList.get(i).variable.equals(symbolTableArrayList.get(j).symbol))
                {
                    TA = symbolTableArrayList.get(j).address;
                }
            }
            displacment="0"+ TA;
        }

        else{  //SIMPLE
            n="1";
            ii="1";

            for(int j=0;j<symbolTableArrayList.size();j++)
            {
                if(assembly_codeList.get(i).variable.equals(symbolTableArrayList.get(j).symbol))
                {
                    TA = symbolTableArrayList.get(j).address;
                }
            }
            displacment="0"+ TA;
        }

        objectCode = first6opcode + n + ii + x + b + p + e;
        int decimal = Integer.parseInt(objectCode,2);
        String semifinalobjectcode = Integer.toString(decimal,16);
        objectCode = semifinalobjectcode + displacment;
        assembly_codeList.get(i).setObjectCode(objectCode);
    }

    private static void CalculateFormat5(int i, String opcode, String TA) {
        String ii;
        String b;
        String p;
        String e;
        String x;
        String objectCode;
        String first6opcode;
        String n;
        String displacment;
        /////////////first 6 characters of opcode///////////////////
        String opcodeBINARY;
        int opcodeINT = Integer.parseInt(opcode, 16);
        opcodeBINARY = Integer.toBinaryString(opcodeINT);
        if(opcodeBINARY.length() < 8)
        {
            opcodeBINARY = String.join("", Collections.nCopies(8-opcodeBINARY.length(),"0")) + opcodeBINARY;
        }
        first6opcode = opcodeBINARY.substring(0, 6);  // binary string
        //////////////////////////////////////////////////////////

        ////////check indexing/////////////////////////
        int length = assembly_codeList.get(i).variable.length();
        char lastChar = assembly_codeList.get(i).variable.charAt(length - 1);
        char lastlastChar = assembly_codeList.get(i).variable.charAt(length - 2);
        if (lastChar == 'X' && lastlastChar == ',') {
            x = "1";
        } else {
            x = "0";
        }
        ////////////////////////////////////////

        for (int j = 0; j < symbolTableArrayList.size(); j++) {
            if (assembly_codeList.get(i).variable.contains(symbolTableArrayList.get(j).symbol)) {
                TA = symbolTableArrayList.get(j).address;
            }
        }
        String PC = assembly_codeList.get(i +1 ).address;
        int PCint = Integer.parseInt(PC, 16);
        int TAint = Integer.parseInt(TA, 16);
        int displacmentint = TAint - PCint;
        displacment = Integer.toHexString(displacmentint);  // hex string
        if (displacmentint <= 2047 && displacmentint >= -2048)
        {
            p = "1";
            b = "0";
        }
        else {
            p = "0";
            b = "1";

            for (int k = 0; k < symbolTableArrayList.size(); k++) {
                if (baselength.equals(symbolTableArrayList.get(k).symbol)) {
                    baselength = symbolTableArrayList.get(k).address;
                }
            }
            int BASEint = Integer.parseInt(baselength, 16);
            TAint = Integer.parseInt(TA, 16);
            displacmentint = TAint - BASEint;
            displacment = Integer.toHexString(displacmentint);
        }
        if(displacment.length()>3)
        {
            displacment = displacment.substring(displacment.length()-3,displacment.length());
        }
        else if(displacment.length() < 3)
        {
            displacment = String.join("", Collections.nCopies(3-displacment.length(),"0")) + displacment;
        }

        int disint = Integer.parseInt(displacment, 16);
        if(disint%2==0)  // n F1
            n = "1";
        else n= "0";

        if(disint >= 0) // i F2
            ii="0";
        else ii="1";

        if(disint == 0) // e F3
            e = "1";
        else e = "0";


        ////////////CONCATE OBJECT CODE///////////////////
        objectCode = first6opcode + n + ii + x + b + p + e;  //  6opcode + n + i + x + b + p + e
        int decimal = Integer.parseInt(objectCode,2);   // to binary string
        String semifinalobjectcode = Integer.toString(decimal,16); // to hex string
        objectCode = semifinalobjectcode + displacment;  // 6opcode + n + i + x + b + p + e + displacment
        objectCode = fixLengthTo6(objectCode);
        assembly_codeList.get(i).setObjectCode(objectCode); // set object code


    }

    private static void CalculateFormat6(int i, String opcode, String TA) {
            String ii;
            String objectCode;
            String first6opcode;
            String n;
            String displacment;
            String b;
            String p;
            String e;
            String x;
            e = "1";
            char firstChar = assembly_codeList.get(i).variable.charAt(0);
            b = "0";
            p = "0";
            ii = "0"; // assumption

            int opcodeINT = Integer.parseInt(opcode, 16);
            String opcodeBINARY = Integer.toBinaryString(opcodeINT);


            if (opcodeBINARY.length() < 8) {
                opcodeBINARY = String.join("", Collections.nCopies(8 - opcodeBINARY.length(), "0")) + opcodeBINARY;
            }
            first6opcode = opcodeBINARY.substring(0, 6);  // binary string

            ////////////////////CHECK INDEXING////////////////////////////////
            int length = assembly_codeList.get(i).variable.length();
            char lastChar = assembly_codeList.get(i).variable.charAt(length - 1);
            char lastlastChar = assembly_codeList.get(i).variable.charAt(length - 2);
            if (lastChar == 'X' && lastlastChar == ',') {
                x = "1";
            } else {
                x = "0";
            }
            /////////////////////////////////////////////////////////////////

            if (firstChar == '#') {
                n = "0";
                ii = "1";
                String imm = assembly_codeList.get(i).variable.substring(1);

                if (isInt(imm))    // #3 ///////////////////////////////////
                {
                    int immINT = Integer.parseInt(imm);
                    displacment = "0" + Integer.toHexString(immINT);

                } else {
                    for (int j = 0; j < symbolTableArrayList.size(); j++) {
                        if (imm.equals(symbolTableArrayList.get(j).symbol)) {
                            TA = symbolTableArrayList.get(j).address;
                        }
                    }
                    displacment = "0" + TA;
                }
            } else if (firstChar == '@') {  //INDIRECT
                n = "1";
                ii = "0";

                for (int j = 0; j < symbolTableArrayList.size(); j++) {
                    if (assembly_codeList.get(i).variable.equals(symbolTableArrayList.get(j).symbol)) {
                        TA = symbolTableArrayList.get(j).address;
                    }
                }
                displacment = "0" + TA;
            } else {  //SIMPLE
                n = "1";
                ii = "1";

                for (int j = 0; j < symbolTableArrayList.size(); j++) {
                    if (assembly_codeList.get(i).variable.equals(symbolTableArrayList.get(j).symbol)) {
                        TA = symbolTableArrayList.get(j).address;
                    }
                }
                displacment = "0" + TA;
            }
            int disint = Integer.parseInt(displacment, 16);
            if (disint % 2 == 0)  //F4 b
                b = "0";
            else
                b = "1";

            if (disint == 0)
                p = "0";
            else p = "1";


            if (displacment.equals(baselength))
                e = "0";
            else
                e = "1";


            objectCode = first6opcode + n + ii + x + b + p + e;
            int decimal = Integer.parseInt(objectCode, 2);
            String semifinalobjectcode = Integer.toString(decimal, 16);
            objectCode = semifinalobjectcode + displacment;
            assembly_codeList.get(i).setObjectCode(objectCode);

    }

    private static String fixLengthTo6(String x) {
        if(x.length()<6)
            x = String.join("", Collections.nCopies(6-x.length(),"0")) + x;
        return x;
    }

    public static String String_to_ASCII(String ascii) {

        // Initialize final String
        String hex = "";

        // Make a loop to iterate through
        // every character of ascii string
        for (int i = 0; i < ascii.length(); i++) {

            // take a char from
            // position i of string
            char ch = ascii.charAt(i);

            // cast char to integer and
            // find its ascii value
            int in = (int)ch;

            // change this ascii value
            // integer to hexadecimal value
            String part = Integer.toHexString(in);

            // add this hexadecimal value
            // to final string.
            hex += part;
        }

        // return the final string hex
        return hex;
    }



}

//////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////
