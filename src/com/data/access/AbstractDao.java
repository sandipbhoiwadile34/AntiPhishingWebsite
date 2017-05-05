/**
 * Is used to perform the Database Releted Actions
 * Responsible to connect the applicaition to the Database
 * to get the SessionFactory object which contains the connection for database 
 * responsible to perform all database releted activity like insert, delete update, select and other join queries
 */


package com.data.access;

import com.enums.DataBaseFlag;
import com.enums.DatabaseStatusMessagesEnum;
import com.enums.ModelEnums;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


/**
 * it implemants the DataAccess interface which contains the Method Declareation
 * @author Sandip Bhoi
 */
public class AbstractDao implements DataAccess {

    /**
     * static object of sessionFactoty;
     */
    private static SessionFactory factory;

    /**
     * Create AbstractDao object which initialize the sessionFactory
     */
    public AbstractDao() {
        // TODO Auto-generated constructor stub
        setFactory(SessionFactorys.getSessionFactory());
    }

    /**
     * 
     * @param dataInterface : dataInterface is the Reference of the Database Entity which is inserted into the database 
     * @return : The DatabaseStatusMessagesEnum if any error in database connection then it returns the DatabaseStatusMessagesEnum.ERROR
     *           if Entity is inserted into the Database the it returns the DatabaseStatusMessagesEnum.ADDED.
     *           if any Exeption throws it will catches and the whole transaction is rollbacked and Print the Exception Stactstrace message
     */
    @Override
    public DatabaseStatusMessagesEnum add(DataInterface dataInterface) {
        // TODO Auto-generated method stub
        Session session = factory.openSession();
        Transaction tx = null;
        DatabaseStatusMessagesEnum dsme = DatabaseStatusMessagesEnum.ERROR;

        try {
            tx = session.beginTransaction();
            session.save(dataInterface);
            tx.commit();
            dsme = DatabaseStatusMessagesEnum.ADDED;
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return dsme;
    }

    /**
     * 
     * @param dataInterface : dataInterface is the Reference of the Database Entity which is inserted into the database 
     * @param da : is the Entity Class reference into the form of .class
     * @return : The if any error in database connection then it returns the null
     *           if Entity is inserted into the Database it returns the max id record from the database otherwise null will return 
     */
    public Object addAndReturn(DataInterface dataInterface, Class<?> da) {
        // TODO Auto-generated method stub
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(dataInterface);
            tx.commit();
            Criteria c = session.createCriteria(da);
            c.addOrder(Order.desc(ModelEnums.ID.getColumnNames()));
            c.setMaxResults(1);
            return c.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * 
     * @param da : Entity reference in the form of .class 
     * @param column : column for getting the max value records
     * @return : max value record based on the column
     */
    public Object getMax(Class<?> da,String column) {
        // TODO Auto-generated method stub
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria c = session.createCriteria(da);
            c.addOrder(Order.desc(column));
            c.setMaxResults(1);
            return c.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return null;
    }
    
    /**
     * 
     * @param map : Contains the Key : value pair and it contains the column name and its value 
     * @param dataInterface : Entity which is inserted into the database
     * @param da : Entity reference in the form of .class
     * @return : DatabaseStatusMessagesEnum.ERROR
     *           if any error in database connection then it returns the DatabaseStatusMessagesEnum.ERROR
     *           if Entity is inserted into the Database the it returns the DatabaseStatusMessagesEnum.ADDED.
     *           if Entity Data based on @param map is Already inserted into the Database the it returns the DatabaseStatusMessagesEnum.EXIST.
     *           if any Exeption throws it will catches and the whole transaction is rollbacked and Print the Exception Stactstrace message
     */
    public DatabaseStatusMessagesEnum addByCriteria(HashMap<String, String> map, DataInterface dataInterface, Class<?> da) {
        // TODO Auto-generated method stub
        Session session = factory.openSession();
        Transaction tx = null;
        DatabaseStatusMessagesEnum dsme = DatabaseStatusMessagesEnum.ERROR;
        DataBaseFlag dataBaseFlag = DataBaseFlag.DATANOTEXIST;
        try {
            tx = session.beginTransaction();

            if (map.size() > 0) {
                Criteria criteriaclasses = createCriteriaQuery(session, map, da);
                if (criteriaclasses.uniqueResult() != null) {
                    dataBaseFlag = DataBaseFlag.DATAEXIST;
                }
            }
            if (dataBaseFlag == DataBaseFlag.DATANOTEXIST) {
                session.save(dataInterface);
                tx.commit();
                dsme = DatabaseStatusMessagesEnum.ADDED;
            } else {
                dsme = DatabaseStatusMessagesEnum.EXIST;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return dsme;
    }

    /**
     * 
     * @param map : Contains the Key : value pair and it contains the column name and its value 
     * @param da : Entity reference in the form of .class
     * @return : The if any error in database connection then it returns the null
     *           if Entity is Available into the Database it returns Object containing the All data Based on @param map from the database otherwise null will return 
     */
    public Object authenticate(HashMap<String, String> map, Class<?> da) {
        // TODO Auto-generated method stub
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            if (map.size() > 0) {
                Criteria criteriaclasses = createCriteriaQuery(session, map, da);
                return criteriaclasses.uniqueResult();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * 
     * @param session : current session for accessing the Database connection
     * @param mp : ontains the Key : value pair and it contains the column name and its value 
     * @param da : Entity reference in the form of .class
     * @return : Criteria based on the @param map key value pair which is the Filter fro getting the information from database. 
     */
    public Criteria createCriteriaQuery(Session session, HashMap<String, String> mp, Class<?> da) {
        Iterator<Entry<String, String>> it = mp.entrySet().iterator();
        Criteria criteriaclasses = session.createCriteria(da);
        while (it.hasNext()) {
            Entry<String, String> pair = it.next();
            //System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); 
            // avoids a ConcurrentModificationException
            criteriaclasses.add(Restrictions.eq(pair.getKey().toString(), pair.getValue()));
        }
        return criteriaclasses;
    }

    /**
     * 
     * @param dataInterface : dataInterface is the Reference of the Database Entity which is Deleted From the database 
     * @return : The DatabaseStatusMessagesEnum if any error in database connection then it returns the DatabaseStatusMessagesEnum.ERROR
     *           if Entity is Deleted from the Database the it returns the DatabaseStatusMessagesEnum.DELETED.
     *           if any Exeption throws it will catches and the whole transaction is rollbacked and Print the Exception Stactstrace message
     */
    @Override
    public DatabaseStatusMessagesEnum delete(DataInterface dataInterface) {
        // TODO Auto-generated method stub
        Session session = factory.openSession();
        Transaction tx = null;
        DatabaseStatusMessagesEnum dsme = DatabaseStatusMessagesEnum.ERROR;
        try {
            tx = session.beginTransaction();
            session.delete(dataInterface);
            tx.commit();
            dsme = DatabaseStatusMessagesEnum.DELETED;
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return dsme;
    }

     /**
     * 
     * @param dataInterface : dataInterface is the Reference of the Database Entity which is Updated into the database 
     * @return : The DatabaseStatusMessagesEnum if any error in database connection then it returns the DatabaseStatusMessagesEnum.ERROR
     *           if Entity is Deleted from the Database the it returns the DatabaseStatusMessagesEnum.UPDATED.
     *           if any Exeption throws it will catches and the whole transaction is rollbacked and Print the Exception Stactstrace message
     */
    @Override
    public DatabaseStatusMessagesEnum update(DataInterface dataInterface) {
        // TODO Auto-generated method stub
        Session session = factory.openSession();
        Transaction tx = null;
        DatabaseStatusMessagesEnum dsme = DatabaseStatusMessagesEnum.ERROR;
        try {
            tx = session.beginTransaction();
            session.update(dataInterface);
            tx.commit();
            dsme = DatabaseStatusMessagesEnum.UPDATED;
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return dsme;
    }

    /**
     * 
     * @param colName : column name of the Entity
     * @param value : String value of the column of Entity
     * @param da :  Entity reference in the form of .class
     * @return  : The if any error in database connection then it returns the null
     *            if Entity is Available into the Database it returns Object containing the All data Based on @param colname and @param value from the database otherwise null will return 
     */
    @Override
    public Object getById(String colName, String value, Class<?> da) {
        // TODO Auto-generated method stub
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteriaclasses = session.createCriteria(da)
                    .add(Restrictions.eq(colName, value));
            return criteriaclasses.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return null;
    }

    /**
     * 
     * @param colName : column name of the Entity
     * @param value : int value of the column of Entity
     * @param da :  Entity reference in the form of .class
     * @return  : The if any error in database connection then it returns the null
     *            if Entity is Available into the Database it returns Object containing the All data Based on @param colname and @param value from the database otherwise null will return 
     */
    @Override
    public Object getById(String colName, long value, Class<?> da) {
        // TODO Auto-generated method stub
        
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteriaclasses = session.createCriteria(da)
                    .add(Restrictions.eq(colName, value));
            return criteriaclasses.uniqueResult();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return null;
    }

    
    /**
     * 
     * @param hSql : Hibernate Query language for getting the records from the Database 
     * @return : list of records based on the HSql;
     */
    @SuppressWarnings({"unchecked"})
    @Override
    public List<DataInterface> listByQuery(String hSql) {
        // TODO Auto-generated method stub
        Session session = factory.openSession();
        Transaction tx = null;
        List<?> dataInterfaceList = null;
        try {
            tx = session.beginTransaction();
            //System.out.println(hSql);
            dataInterfaceList = session.createQuery(hSql).list();
            tx.commit();

        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return (List<DataInterface>) dataInterfaceList;
    }

    /**
     * 
     * @return  the session factory object
     */
    public static SessionFactory getFactory() {
        return factory;
    }

    /**
     * 
     * @param factory  sets the session factory object
     */
    public static void setFactory(SessionFactory factory) {
        AbstractDao.factory = factory;
    }

    /**
     * 
     * @param query : Hibernate Query language for getting the records from the Database 
     * @return : list of records based on the HSql;
     */
    public List<?> getResultBySqlQuery(String query){
        Session session = factory.openSession();
        Transaction tx = null;
        List<?> list = null;
        try {
            
            tx = session.beginTransaction();
            SQLQuery q = session.createSQLQuery(query);
            list = q.list();
            tx.commit();
            return list;
        } catch (Exception e) {
            return list;
            
        } finally {
            session.close();
        }
    }

    /**
     * executes the testing procedures
     * @param args command line arguments
     */
    public static void main(String args[]) {
        
    }
}
