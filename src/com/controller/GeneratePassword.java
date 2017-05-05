package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.antiphishing.model.User;
import com.data.access.AbstractDao;
import com.image.sharing.ImageSharing;

/**
 * Servlet implementation class GeneratePassword
 */
@WebServlet("/GeneratePassword")
public class GeneratePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeneratePassword() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//Generate the Password based on the image and Watermarked
		ImageSharing iSharing=new ImageSharing();
		String userName=request.getParameter("email").toString();
		
		AbstractDao abstractDao=new AbstractDao();
		HashMap<String, String> map=new HashMap<>();
		map.put("email",userName);
		User user=(User)abstractDao.authenticate(map, User.class);
		
		//get user authentication if the User is present in database or not
		
		if(user==null){
			response.sendRedirect("login.jsp?message=fail");
		}
		else{
			
			//store the authentication users profile in session
			HttpSession session=request.getSession();
			session.setAttribute("user", user);
			String logos = File.separator+"tempshare"+File.separator;		
	        String LOGO_UPLOAD_DIRECTORY = getServletContext().getRealPath(logos);
	        
	        int userid=(int)user.getId();
	        
	        
	        //create the share that user want to on mail.
	    	iSharing.GenerateShare(LOGO_UPLOAD_DIRECTORY, user.getEmail(), userid); 
	    	response.sendRedirect("Match.jsp?message=success");
		}
		
	}
}
