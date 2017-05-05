/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.util;

/**
 *
 * @author ANGEL : 
 * this call represent the Mail sending code with attachments
 */
import com.util.Constants;
import java.util.Properties;  
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;  
import javax.mail.internet.*;  
  
public class SendMailBySite {
    
    /**
     * 
     * @param toUser
     * @param subject
     * @param emailMessage
     * @param fileName
     * @return 
     */
    public static String sendMail(String toUser,String subject,String emailMessage,String fileName)
    {
        String host="smtp.gmail.com";  
        
        final String user=Constants.EMAILID;//change accordingly  
        final String password=Constants.PASSWORD ;//change accordingly  
        
        //Get the session object  
        Properties props = new Properties();  
        props.put("mail.smtp.host",host);  
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.debug", "false");
        props.put("mail.smtp.port", Constants.EMAILPORT);
        props.setProperty("mail.smtp.protocol", "smtps");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.ssl.enable", "true");  

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password);
            }
        });
        //Compose the message  
        try {  
        MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(user));  
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(toUser));        
        message.setSubject(subject);
        if(fileName==null)
        {
            message.setContent(emailMessage,"text/html" );
        }
        else
        {
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            // messageBodyPart = new MimeBodyPart();
            //String file = "path of file to be attached";
            //String fileName = "attachmentName";
            DataSource source = new FileDataSource(fileName);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
        }
        
       //send the message  
        Transport.send(message);  

        System.out.println("message sent successfully...");  

        return "Message Send Successfully";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Failed"; }  
    }
    
    public static void main(String[] args) {  
    	
    	String msg="<h1>sending html mail check</h1>";        
    	String result=SendMailBySite.sendMail("sandipbhoiwadile@gmail.com", "Message From Notice Application", msg,null);
    	System.out.println("Result : "+result);
    }  
}  