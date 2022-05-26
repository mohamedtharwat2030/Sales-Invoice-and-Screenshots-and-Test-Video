
package com.sales.controller;

import com.sales.model.DataLine;
import com.sales.model.InvAllData;
import com.sales.model.ItemTableModel;
import com.sales.model.TableModelInv;
import com.sales.view.InvPopup;
import com.sales.view.ItemPopup;
import com.sales.view.OrignalFrame;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.w3c.dom.xpath.XPathResult;

public class InvControl implements ActionListener , ListSelectionListener{
    
    private OrignalFrame frame ;
    private InvPopup invPopup ;
    private ItemPopup itemPopup ;
    
    public InvControl(OrignalFrame frame) {
    this.frame = frame ;
   
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       String actionCommand = e.getActionCommand();
       
        System.out.println("Action   " +actionCommand);
        
        switch(actionCommand){
            
            case "Load File":
                loadFile();
                break;
                
            case "Save File":
                saveFile();
                break;
                
            case "Create New Invoice":
                createNewInvoice();
                break;    
            
            case "Delete Invoice":
                deleteInvoice();
                break;
                
            case "Save Item":
                saveNewItem();
                break;
                
            case "Cancel Item":
                cancelItem();
                break;   
            case "cancelCreateInvoice":
                cancelCreateInvoice();
                break;
            case "okCreateInvoice":
                okCreateInvoice();
                break;
            case "oKCreateItem":
                oKCreateItem();
                break;
            case "cancelcreateItem":
                cancelcreateItem();
                break;
            
        }


    }

    
    @Override
    public void valueChanged(ListSelectionEvent e) {
       int indexSelect = frame.getInvoicesTable().getSelectedRow();
       if (indexSelect != -1 ){
       System.out.println("you select"+ indexSelect);
        InvAllData LocalInv = frame.getInvoices().get(indexSelect); 
        frame.getInvNumberLabel().setText(""+LocalInv.getNum());
        frame.getInvDataLabel().setText(LocalInv.getDate());
        frame.getCustomerNameLabel().setText(LocalInv.getCustomer());
        frame.getInvTotalLabel().setText(""+LocalInv.getInvTotal());
        ItemTableModel itemTableModel = new ItemTableModel(LocalInv.getDataLines());
        frame.getItemsTable().setModel(itemTableModel);
        itemTableModel.fireTableDataChanged();
       }
        
        
    }   
    // open CSV files header and line
    private void loadFile() {
        JFileChooser choseFile = new JFileChooser();
        try{
            JOptionPane.showMessageDialog(frame, "Please choose Invoice Header File",
                    "Notification Message", JOptionPane.INFORMATION_MESSAGE);
            int choose = choseFile.showOpenDialog(frame) ;
            if(choose == JFileChooser.APPROVE_OPTION){
                File mainFileHeader = choseFile.getSelectedFile();
                Path mainPathHeader = Paths.get(mainFileHeader.getAbsolutePath()); 
                java.util.List<String> mainLinesHeader = Files.readAllLines(mainPathHeader);
                System.out.println("read inv Done");
                
                ArrayList<InvAllData> InvAllDataArray = new ArrayList<>();
                for (String eachLineHeader : mainLinesHeader) {
                    try{
                    String[] headerElements = eachLineHeader.split(",");
                    int invoiceNumber = Integer.parseInt(headerElements[0]);
                    String invoiceDate = headerElements[1];
                    String customerName = headerElements[2];
                    
                    InvAllData invAllData = new InvAllData(invoiceNumber, invoiceDate, customerName);
                    InvAllDataArray.add(invAllData);
                }catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Attintion Error in format line ", "Attintion Error", JOptionPane.ERROR_MESSAGE);
                  
                        // Any file format rather than CSV.. Display Error message to wrong line file format 
                }
                }
                System.out.println("check here");
                JOptionPane.showMessageDialog(frame, "Please choose Invoice Line File",
                        "Notification Message", JOptionPane.INFORMATION_MESSAGE);
                choose = choseFile.showOpenDialog(frame) ;
            if(choose == JFileChooser.APPROVE_OPTION){
                File fileLine = choseFile.getSelectedFile();
                Path linePath = Paths.get(fileLine.getAbsolutePath()); 
               java.util.List<String> allLines = Files.readAllLines(linePath);
                System.out.println("Lines read Dooooone ");
                for (String allLine : allLines) {
                    try{
                        String[] lineElements = allLine.split(",");
                        int invoiceNumber = Integer.parseInt(lineElements[0]);
                        String itemName = lineElements[1];
                        double itemPrice = Double.parseDouble(lineElements[2]);
                        int count = Integer.parseInt(lineElements[3]);
                        InvAllData inv = null;
                        for (InvAllData invoiceNew : InvAllDataArray) {
                            if (invoiceNew.getNum() == invoiceNumber) {
                                inv = invoiceNew;
                                break;
                            }
                        }
                        
                        DataLine lineData = new DataLine( itemName, itemPrice,count, inv);
                        inv.getDataLines().add(lineData);
                    }catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Attintion Error in format line ", "Attintion Error", JOptionPane.ERROR_MESSAGE);
                            
                                                    //Again >>> Any file format rather than CSV.. Display Error message to wrong line file format 
                            
                        } 
                        
                    }
                    System.out.println("Check here");
                }
                frame.setInvoices(InvAllDataArray);
                TableModelInv tableModelInv = new TableModelInv(InvAllDataArray);
                frame.setTableModelInv(tableModelInv);
                frame.getInvoicesTable().setModel(tableModelInv);
                frame.getTableModelInv().fireTableDataChanged();
            }
         }  catch(IOException ex){
             ex.printStackTrace();
             JOptionPane.showMessageDialog(frame, "Attintion Cannot read this file", "Attintion Error", JOptionPane.ERROR_MESSAGE);
             } 
    }
// save data in new files
    private void saveFile() {
ArrayList<InvAllData> invoices = frame.getInvoices();
        String head = "";
        String lines = "";
        for (InvAllData invoice : invoices) {
               String CsvInvoice = invoice.getCsvFile();
               head += CsvInvoice;
               head += "\n";

            for (DataLine line : invoice.getDataLines()) {
                String CsvItems = line.getCsvFile();
                lines += CsvItems;
                lines += "\n";
            }
        }
        System.out.println("Please Test ");
        try {
            JFileChooser filechoose = new JFileChooser();
            int result = filechoose.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File firstFile = filechoose.getSelectedFile();
                FileWriter fileWrite = new FileWriter(firstFile);
                fileWrite.write(head);
                fileWrite.flush();
                fileWrite.close();
                
                result = filechoose.showSaveDialog(frame);
                 JOptionPane.showMessageDialog(filechoose, "Great Jop File saved ","Notification Message", JOptionPane.INFORMATION_MESSAGE);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File ItemFile = filechoose.getSelectedFile();
                    FileWriter fileWrite2 = new FileWriter(ItemFile);
                    fileWrite2.write(lines);
                    fileWrite2.flush();
                    fileWrite2.close();
                }
            }
        } catch (Exception ex) {

        }

    }
//create new invoices
    private void createNewInvoice() {
        invPopup = new InvPopup(frame);
        invPopup.setVisible(true);


    }

    private void deleteInvoice()       //delete invoice
    {
    int thisRow = frame.getInvoicesTable().getSelectedRow();
    if (thisRow != -1 ) {
        frame.getInvoices().remove(thisRow);
        frame.getTableModelInv().fireTableDataChanged();
    
     }
    } 

    private void saveNewItem()  // create new line of item data
    {
        itemPopup = new ItemPopup(frame);
        itemPopup.setVisible(true);
    }
    private void cancelItem()      //delete the line of item data
    
    {
      
       int selectedRow = frame.getItemsTable().getSelectedRow();
        if (selectedRow != -1) {
            ItemTableModel itemTableModel = (ItemTableModel) frame.getItemsTable().getModel();
            itemTableModel.getDataLines().remove(selectedRow);
            itemTableModel.fireTableDataChanged();
            frame.getTableModelInv().fireTableDataChanged();
       
        }
    }
    private void cancelCreateInvoice() // cancel creation invoice whin press cancel
    {
        invPopup.setVisible(false);
        invPopup.dispose();
        invPopup = null ;
    }

    private void okCreateInvoice()    //create invoice whin press ok
    {
        String date = invPopup.getAllinvoiceDateField().getText();
        String customer = invPopup.getCustomerNmField().getText();
                int invNum = frame.getNextInvoiceNum();  
                try {
                    String[] dateElemnts = date.split("-");  // "22-05-2013" -> {"22", "05", "2013"}  xy-qw-20ij
            if (dateElemnts.length < 3) {
                JOptionPane.showMessageDialog(frame, "Attintion format date Wrong ", "Attintion Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day =   Integer.parseInt(dateElemnts[0]);
                int month = Integer.parseInt(dateElemnts[1]);
                int year =  Integer.parseInt(dateElemnts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(frame, "Attintion format date Wrong", "Attintion Error", JOptionPane.ERROR_MESSAGE);
                } else {
                InvAllData invoice = new InvAllData(invNum , date , customer);
                frame.getInvoices().add(invoice);
                frame.getTableModelInv().fireTableDataChanged();
                invPopup.setVisible(false);
                invPopup.dispose();
                invPopup = null;
                }
            }
                } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Attintion format date Wrong", "Attintion Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void oKCreateItem() // create new item data line after press ok
    {
        
         String item = itemPopup.getItemNmField().getText();
        String count1 = itemPopup.getCtItemsField().getText();
        String price1 = itemPopup.getPriceItemField().getText();
        int count2 = Integer.parseInt(count1);
        double price2 = Double.parseDouble(price1);
        int selectedInvoice = frame.getInvoicesTable().getSelectedRow();
        if (selectedInvoice != -1) {
            InvAllData invoice = frame.getInvoices().get(selectedInvoice);
            DataLine line = new DataLine(item, price2, count2, invoice);
            invoice.getDataLines().add(line);
            ItemTableModel itemTableModel = (ItemTableModel) frame.getItemsTable().getModel();
            itemTableModel.fireTableDataChanged();
            frame.getTableModelInv().fireTableDataChanged(); 
           }
        itemPopup.setVisible(false);
        itemPopup.dispose();
        itemPopup=null;
        
    }    

    private void cancelcreateItem()   // cancel create new item data line after press cancel
    {
        
        itemPopup.setVisible(false);
        itemPopup.dispose();
        itemPopup = null ;
        
    }
    
}
