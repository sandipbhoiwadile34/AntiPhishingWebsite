package com.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.antiphishing.model.User;
import com.util.Constants;
import com.util.OTPGenerator;
import com.util.SendMailBySite;

/**
 * Servlet implementation class ResendOtp
 */
@WebServlet("/ResendOtp")
public class ResendOtp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResendOtp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Resend the Otp with setting the session and User authentication
		
		HttpSession session = request.getSession();
		int otp= OTPGenerator.gen();
		System.out.println("otp : "+otp);
		session.setAttribute("otp",otp+"");
		
		String msg="<h1>Otp is : "+otp+"</h1>";
		User user = (User) session.getAttribute("user");
    	
		String result=SendMailBySite.sendMail(user.getEmail(), Constants.MAILMESSAGE, msg,null);
    	System.out.println("Result : "+result);
    	
    	if(result.equals("Failed")){
    		response.sendRedirect("OTP.jsp?match=Exception");
    	}else{
    		response.sendRedirect("OTP.jsp");
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
