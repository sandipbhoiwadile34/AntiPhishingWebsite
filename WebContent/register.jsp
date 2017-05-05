<%-- 
   Show  Register GUI
   
--%>
<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6 lt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7 lt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8 lt8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
<meta charset="UTF-8" />
<!-- <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">  -->
<title>Registration Form</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Login and Registration Form with HTML5 and CSS3" />
<meta name="keywords"
	content="html5, css3, form, switch, animation, :target, pseudo-class" />
<meta name="author" content="Codrops" />
<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css" href="css/demo.css" />
<link rel="stylesheet" type="text/css" href="css/style2.css" />
<link rel="stylesheet" type="text/css" href="css/animate-custom.css" />

</head>
<body bgcolor="#E6E6FA">
	<div class="container">
		<!-- Codrops top bar -->
		<div class="codrops-top">
			<!-- <a href="">
                    <strong>&laquo; Previous Demo: </strong>Responsive Content Navigator
                </a> -->
			<span class="right"> <!-- a href=" http://tympanus.net/codrops/2012/03/27/login-and-registration-form-with-html5-and-css3/">
                        <strong>Back to the Codrops Article</strong>
                    </a> -->
			</span>
			<div class="clr"></div>
		</div>
		<!--/ Codrops top bar -->
		<header>
			<h1>
				Anti-Phishing Website using Visual Cryptography <span></span>
			</h1>
			<!-- <nav class="codrops-demos">
					<span>Click <strong>"Join us"</strong> to see the form switch</span>
					<a href="index.html">Demo 1</a>
					<a href="index2.html" class="current-demo">Demo 2</a>
					<a href="index3.html">Demo 3</a>
				</nav> -->
		</header>
		<section>
			<div id="container_demo">
				<!-- hidden anchor to stop jump http://www.css3create.com/Astuce-Empecher-le-scroll-avec-l-utilisation-de-target#wrap4  -->
				<a class="hiddenanchor" id="toregister"></a> <a class="hiddenanchor"
					id="tologin"></a>
				<div id="wrapper">
					<div id="login" class="animate form">
						<form action="UserRegister" method="post" autocomplete="on"
							enctype="multipart/form-data">
							<h1>Sign up</h1>
							<p>
								<label for="usernamesignup" class="uname" data-icon="u">
									User Name</label> <input id="username" name="username"
									required="required" type="text" placeholder="UserName"
									pattern="[a-zA-Z\s]+" />
							</p>
							<p>
								<label for="emailsignup" class="youmail" data-icon="e">
									Email-ID</label> <input id="email" name="email" required="required"
									type="email" placeholder="mysupermail@mail.com" />
							</p>
							<p>
								<label for="passwordsignup_confirm" class="youpasswd"
									data-icon="u">Mobile Number </label> <input id="mobile"
									name="mobile" required="required" type="tel"
									placeholder="9876765654" maxlength="10" pattern="[0-9]+" />
							</p>
							<p>
								<label for="passwordsignup_confirm" class="youpasswd"	
									data-icon="u">select Image As Password</label> <input type="file" name="image">
							</p>
							<p class="signin button">
								<input type="submit" value="Sign up" />
							</p>
							<p class="change_link">
								Already a member ? <a href="login.jsp" class="to_login">Log-In
								</a>
							</p>
						</form>
					</div>

				</div>
			</div>
		</section>
	</div>
</body>
</html>