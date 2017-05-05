package com.data.access;

import com.enums.DatabaseStatusMessagesEnum;
import java.util.List;

/**
 * This class contains the declaration of Database specific funtions
 * @author Sandip Bhoi
 */
public interface DataAccess {
    /**
     * 
     * @param dataInterface : dataInterface is the Reference of the Database Entity which is inserted into the database 
     * @return : The DatabaseStatusMessagesEnum if any error in database connection then it returns the DatabaseStatusMessagesEnum.ERROR
     *           if Entity is inserted into the Database the it returns the DatabaseStatusMessagesEnum.ADDED.
     *           if any Exeption throws it will catches and the whole transaction is rollbacked and Print the Exception Stactstrace message
     */
    public DatabaseStatusMessagesEnum add(DataInterface dataInterface);
    
   /**
     * 
     * @param dataInterface : dataInterface is the Reference of the Database Entity which is Deleted From the database 
     * @return : The DatabaseStatusMessagesEnum if any error in database connection then it returns the DatabaseStatusMessagesEnum.ERROR
     *           if Entity is Deleted from the Database the it returns the DatabaseStatusMessagesEnum.DELETED.
     *           if any Exeption throws it will catches and the whole transaction is rollbacked and Print the Exception Stactstrace message
     */
    public DatabaseStatusMessagesEnum delete(DataInterface dataInterface);
    
    
     /**
     * 
     * @param dataInterface : dataInterface is the Reference of the Database Entity which is Updated into the database 
     * @return : The DatabaseStatusMessagesEnum if any error in database connection then it returns the DatabaseStatusMessagesEnum.ERROR
     *           if Entity is Deleted from the Database the it returns the DatabaseStatusMessagesEnum.UPDATED.
     *           if any Exeption throws it will catches and the whole transaction is rollbacked and Print the Exception Stactstrace message
     */
    public DatabaseStatusMessagesEnum update(DataInterface dataInterface);
    
    /**
     * 
     * @param colName : column name of the Entity
     * @param value : String value of the column of Entity
     * @param da :  Entity reference in the form of .class
     * @return  : The if any error in database connection then it returns the null
     *            if Entity is Available into the Database it returns Object containing the All data Based on @param colname and @param value from the database otherwise null will return 
     */
    public Object getById(String colName,String value,Class<?> da);
    
    /**
     * 
     * @param colName : column name of the Entity
     * @param value : int value of the column of Entity
     * @param da :  Entity reference in the form of .class
     * @return  : The if any error in database connection then it returns the null
     *            if Entity is Available into the Database it returns Object containing the All data Based on @param colname and @param value from the database otherwise null will return 
     */
    public Object getById(String colName,long value,Class<?> da);
    
    /**
     * 
     * @param hSql : Hibernate Query language for getting the records from the Database 
     * @return : list of records based on the HSql;
     */
    public List<DataInterface> listByQuery(String hSql);
}
