package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.antiphishing.model.User;
import com.util.Constants;
import com.util.ImgDiffPercent;
import com.util.OTPGenerator;
import com.util.SendMailBySite;

/**
 * Servlet implementation class MatchImage
 */
@WebServlet("/MatchImage")
public class MatchImage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MatchImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		//get the image from multipart request
		
		String logos = File.separator + "tempshare" + File.separator;
		String LOGO_UPLOAD_DIRECTORY = getServletContext().getRealPath(logos);
		// int userid=1;
		String filePath = "none";
		try {
			List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem file : multiparts) {

				if (!file.isFormField()) {
					String path = new File(file.getName()).getName();
					try {
						filePath = LOGO_UPLOAD_DIRECTORY + File.separator + path;
						file.write(new File(filePath));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(filePath);
		
		// Check the image with system image and with user image
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		double dif = ImgDiffPercent.getDifference(filePath,
				LOGO_UPLOAD_DIRECTORY + File.separator + "share2_on_mail" + user.getId() + ".jpg");

		if (dif == 0.0) {
			int otp= OTPGenerator.gen();
			System.out.println("otp : "+otp);
			session.setAttribute("otp",otp+"");
			String msg="<h1>Otp is : "+otp+"</h1>";        
	    	String result=SendMailBySite.sendMail(user.getEmail(), Constants.MAILMESSAGE, msg,null);
	    	System.out.println("Result : "+result);
	    	if(result.equals("Failed")){
	    		response.sendRedirect("OTP.jsp?message=error");
	    	}else{
	    		response.sendRedirect("OTP.jsp");
	    	}
		} else {
			response.sendRedirect("Match.jsp?match=no");
		}
	}
}
