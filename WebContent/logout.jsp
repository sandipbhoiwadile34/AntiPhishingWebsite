
<%-- 
  Logout ,clear the session redirect to login page
--%>
<html>
<head>
	 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
	 <link rel="stylesheet" type="text/css" href="css/style.css">
	 <title>logout Page</title>
</head>
<body>
	 <%		
		
		 session.removeAttribute("document");
		 session.removeAttribute("message");
		 session.removeAttribute("user");
		 session.invalidate();
		 response.sendRedirect("login.jsp");
	 %>

</body>
</html>