<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8" />
<title>System Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Login and Registration Form with HTML5 and CSS3" />
<meta name="keywords"
	content="html5, css3, form, switch, animation, :target, pseudo-class" />
<meta name="author" content="Codrops" />
<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel="stylesheet" type="text/css" href="css/animate-custom.css" />
</head>
<%
    String message="";
	if(request.getParameter("message")!=null){
		if(request.getParameter("message").toString().equals("success")){
			message="User Register successfully";
		}
		else if(request.getParameter("message").toString().equals("fail")){
			message="Email Id Not Registered";
		}
	%>
<body style="color: red" onload="alert('<%=message %>')">
	<%} else { %>

<body style="color: red" >
	<%} %>
	<!--      <body background="images/1.jpg"> -->
	<div class="container">
		<!-- Codrops top bar -->
		<div class="codrops-top">
			<!--  <a href="">
                    <strong>&laquo; Previous Demo: </strong>Responsive Content Navigator
                </a> -->
			<span class="right"> <!-- <a href=" http://tympanus.net/codrops/2012/03/27/login-and-registration-form-with-html5-and-css3/">
                        <strong>Back to the Codrops Article</strong>
                    </a> -->
			</span>
			<div class="clr"></div>
		</div>
		<!--/ Codrops top bar -->
		<header>
			<h1>Anti-Phishing Website using Visual Cryptography</h1>

		</header>
		<section>
			<div id="container_demo">
				<!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
				<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
					id="tologin"></a>
				<div id="wrapper">
					<div id="login" class="animate form">
						<form action="GeneratePassword" method="post" autocomplete="on">
							<h1>Log in</h1>
							<p>
								<label for="email" class="uname" data-icon="u"> Your
									email </label> <input id="email" name="email" required="required"
									type="email" placeholder=" mymail@mail.com" />
							</p>
							<p class="login button">
								<input type="submit" value="Genereate Passowrd" />
							</p>
							<p class="change_link">
								Not a member yet ? <a href="register.jsp" class="to_register">Join
									us</a>
							</p>
						</form>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>
</html>
