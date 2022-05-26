
package com.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ItemTableModel extends AbstractTableModel{
    
    private ArrayList<DataLine> dataLines ;
    private String[] columns = {"No.", "Item Name", "Item Price", "Count", "Item Total"};

    public ItemTableModel(ArrayList<DataLine> dataLines) {
        this.dataLines = dataLines;
    }
    
    public ArrayList<DataLine>getDataLines(){
            return dataLines ;
    }
    
    @Override
    // get size (count) of rows
    public int getRowCount() {
        
        return dataLines.size();
    }
    @Override
    // get lenth (count) of columns
    public int getColumnCount() {
        return  columns.length ;
    }    
    public String getColumnName(int x) {
        return columns[x];
    }
    @Override
    //get values
    public Object getValueAt(int rowIndex, int columnIndex) {
        DataLine line = dataLines.get(rowIndex);
        
        switch(columnIndex) {
            case 0: return line.getInvoice().getNum();
            case 1: return line.getItemName();
            case 2: return line.getPrice();
            case 3: return line.getCount();
            case 4: return line.getDataLineTotal();
            default : return "";
        }


    }
    
}
