/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enums;

/**
 *
 * @author Sandip Bhoi
 */
public enum ModelEnums {
    
    ID("id"),
    CATEGORYNAME("categoryName"),
    ITEMNAME("itemName"),
    UNITNAME("unitName"),
    USERNAME("userName"),
    FLAVORNAME("flavor"),
    VENDORNAME("vendorName"),
    VENDORADDRESS("address"),
    CONTACTNUMBER("contactNumber"),
    EXPENCEID("dailyExpenseId"),
    CATEGORYID("itemCategoryId");
    
    private final String columnNames;
    
    /**
     * 
     * @param columnsNames : set the columnname for getting the filter records based on column name
     */
    private ModelEnums(final String columnsNames){
        this.columnNames=columnsNames;
    }

    /**
     * 
     * @return the column name
     */
    public String getColumnNames() {
        return columnNames;
    }
    
}
