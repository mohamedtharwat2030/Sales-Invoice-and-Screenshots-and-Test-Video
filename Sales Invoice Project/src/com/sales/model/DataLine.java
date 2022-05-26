
package com.sales.model;

public class DataLine   // items of line data .....
{       
    private String itemName ;
    private double price ;
    private int count ;
    private InvAllData invoice ;
    

    public DataLine() {
    }

    public DataLine( String itemName, double price, int count) {
        
        this.itemName = itemName;
        this.price = price;
        this.count = count;
    }

    public DataLine( String itemName, double price, int count, InvAllData invoice) {
        
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.invoice = invoice;
    }
    
    public double getDataLineTotal ()  // total for each line
    {
        
        return price*count ;
    }

    // same item count
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

// Item name
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

// item price
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    // item data 
    public String toString() {
        return "DataLine{" + " invoice=" + invoice.getNum() +  ",itemName=" + itemName + ", price=" + price + ", count=" + count + '}';
    }
// get invoice
    public InvAllData getInvoice() {
        return invoice;
    }
 //get invoice in CSV file
    public String getCsvFile() {
        return invoice.getNum() + "," + itemName + "," + price + "," + count;
    }
    
            
    
    
    
}
