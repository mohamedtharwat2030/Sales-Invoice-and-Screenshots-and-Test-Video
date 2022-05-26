
package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TableModelInv extends AbstractTableModel{
    private ArrayList<InvAllData> invoices ;
    private String [] tableColumns = {"No." , "Date", "Customer", "Total"};

    public TableModelInv(ArrayList<InvAllData> invoices) {
        this.invoices = invoices;
    }
    //get size of rows
    @Override
    public int getRowCount() {
        return invoices.size();
    }
// get length of column
    @Override
    public int getColumnCount() {
        return tableColumns.length ;

    }
    // for column names
    public String getColumnName (int column){
        return  tableColumns [column];


    }
    
    @Override
     //get the value
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvAllData invoice = invoices.get(rowIndex);
        // index of the column
        switch (columnIndex) {
            case 0: return invoice.getNum();
            case 1: return invoice.getDate();
            case 2: return invoice.getCustomer();
            case 3: return invoice.getInvTotal();
            default : return "";
        }
        


    }
}
