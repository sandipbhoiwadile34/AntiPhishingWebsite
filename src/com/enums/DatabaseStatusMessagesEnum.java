package com.enums;

/**
 *  This Enum class can specify which oeration is perform over the Database like update, added, Delete, Already Exist, Or database error
 */

/**
 *
 * @author Sandip Bhoi
 */
public enum DatabaseStatusMessagesEnum {
    
    ADDED("Details Added Successfully"),
    UPDATED("Details Updated Successfully"),
    DELETED("Details Deleted Successfully"),
    EXIST("Details Already Exist"),
    ERROR("Database Error")
    ;
    
    /**
     * 
     * @param value : set the value of Status of the Database Action
     */
    private DatabaseStatusMessagesEnum(final String value){
        this.value=value;
    }

    /**
     * 
     * @return value of Status of the Database Action
     */
    public String getValue() {
        return value;
    }
    
    
    private final String value;
}
