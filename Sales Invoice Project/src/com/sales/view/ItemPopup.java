/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sales.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author DELL
 */
public class ItemPopup extends JDialog{
    private JTextField itemNmField;
    private JTextField ctItemsField;
    private JTextField priceItemField;
    private JLabel itemNmLbl;
    private JLabel ctItemLabel;
    private JLabel priceItemLabel;
    private JButton pressOk;
    private JButton pressCancel;
    
    public ItemPopup(OrignalFrame frame) {
        itemNmField = new JTextField(20);    //  for items name field and label
        itemNmLbl = new JLabel("Item Name");
        
        ctItemsField = new JTextField(20);  // for item count and label
        ctItemLabel = new JLabel("Item Count");
        
        priceItemField = new JTextField(20);  //for item price and label 
        priceItemLabel = new JLabel("Item Price");
        
        pressOk = new JButton("OK");
        pressCancel = new JButton("Cancel");
        
        pressOk.setActionCommand("oKCreateItem");
        pressCancel.setActionCommand("cancelcreateItem");
        
        pressOk.addActionListener(frame.getInvControl());
        pressCancel.addActionListener(frame.getInvControl());
        setLayout(new GridLayout(4, 2));
        
        add(itemNmLbl);   // adding item, name, and price labels and fields
        add(itemNmField);
        add(ctItemLabel);
        add(ctItemsField);
        add(priceItemLabel);
        add(priceItemField);
        add(pressOk);
        add(pressCancel);
        
        pack();
    }
          // to get field of item name
    public JTextField getItemNmField() {
        return itemNmField;
    }
// to get field of item count
    public JTextField getCtItemsField() {
        return ctItemsField;
    }
// to get field of item price
    public JTextField getPriceItemField() {
        return priceItemField;
    }
}
