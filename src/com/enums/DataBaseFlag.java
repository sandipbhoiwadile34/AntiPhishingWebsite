/*
 * This is enum to set the flags which describe data is exist into the database or not
 */
package com.enums;

/**
 *
 * @author Sandip Bhoi
 */
public enum DataBaseFlag {
 
    DATAEXIST(1),
    DATANOTEXIST(0);
    
    /**
     * 
     * @param flag  set the flag status
     */
    private DataBaseFlag(int flag){
        this.flag=flag;
    }
    private final int flag;
    
    /**
     * 
     * @return : the flag
     */
    public int getFlag() {
        return flag;
    }
}
