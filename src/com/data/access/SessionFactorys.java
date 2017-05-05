package com.data.access;

import javax.swing.JOptionPane;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 * This is the class to make a connection with database using the Hibernate.cfg xml file
 * this file contains the Dialects : responsible to convert the HQL into the Database specific language
 * Driver : database specific driver
 * User name & password: user name credential for database connection authentication
 * Mapping classes : all Entity Classes are mapped into database Table
 * 
 * 
 * @author Sandip Bhoi
 */
public class SessionFactorys {

    private static SessionFactory session = null;

    /**
     *
     * @return the session factory which contains the database configuration and
     * to hold the connection
     */
    
    public static SessionFactory getSessionFactory() {
        try {
            session = new AnnotationConfiguration().
                    configure().
                    buildSessionFactory();
        } catch (Throwable ex) {
            //System.err.println("Failed to create sessionFactory object." + ex);
            JOptionPane.showMessageDialog(null,"Database Error");
            //throw new ExceptionInInitializerError(ex);
        }
        return session;
    }
}
