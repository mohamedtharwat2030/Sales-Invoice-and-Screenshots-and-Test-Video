
package com.sales.model;

import java.util.ArrayList;
                                // Invoice class
public class InvAllData {
    
    private int num ;
    private String date ;
    private String customer ;
    private ArrayList<DataLine> dataLines ;

    
    
    public InvAllData() {
    }

    public InvAllData(int num, String date, String customer) {
        this.num = num;
        this.date = date;
        this.customer = customer;
    }
    
    public double getInvTotal ()   //to get the total of invoice
    {
        
        double total =0.0 ;
        for(DataLine line : getDataLines()){
            
            total += line.getDataLineTotal();
        }
        return total ;
    }

    // to get item lines
    public ArrayList<DataLine> getDataLines() {
        if (dataLines== null){
            dataLines = new ArrayList<>();
        }
        
        return dataLines;
    }
//name
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
// number
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
// date of inv
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "InvAllData{" + "num=" + num + ", date=" + date + ", customer=" + customer + '}';
    }

    Object getInvoiceTotal() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getCsvFile() {
        return num + "," + date + "," + customer;
    }
    
    
}
