package com.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.antiphishing.model.User;
import com.data.access.AbstractDao;
import com.image.sharing.ImageSharing;

/**
 * Servlet implementation class UploadFile1
 */
@WebServlet("/UserRegister")
public class UserRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserRegister() {
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

		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		String logos = File.separator + "upload" + File.separator;
		String LOGO_UPLOAD_DIRECTORY = getServletContext().getRealPath(logos);

		String filePath = "none";
		User user = new User();
		try {
			List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem file : multiparts) {
				String fieldName = file.getFieldName();

				if (fieldName.equals("username")) {
					user.setUsername(file.getString());
				}
				if (fieldName.equals("email")) {
					user.setEmail(file.getString());
				}
				if (fieldName.equals("mobile")) {
					user.setMobile(file.getString());
				}
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

		AbstractDao abstractDao = new AbstractDao();

		HashMap<String, String> map = new HashMap<>();
		map.put("email", user.getEmail());
		;

		// get user authentication if the User is present in database or not

		if (abstractDao.authenticate(map, User.class) == null) {
			response.sendRedirect("login.jsp?message=EMAIL_EXIST");
		} else {

			User user2 = (User) abstractDao.addAndReturn(user, User.class);
			PrintWriter out = response.getWriter();
			ImageSharing iSharing = new ImageSharing();
			out.println(getServletContext().getRealPath(""));
			iSharing.CreateShares(getServletContext().getRealPath(""), filePath, (int) user2.getId());
			response.sendRedirect("login.jsp?message=success");
		}
	}
}
