
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head lang="en">
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
	<meta http-equiv="x-ua-compatible" content="ie=edge">
	<title>BaiWa Template</title>

	<link href="<c:url value='/theme/img/favicon.114x114.png' />" rel="apple-touch-icon" type="image/png" sizes="114x114"> 
	<link href="<c:url value='/theme/img/favicon.72x72.png' />" rel="apple-touch-icon" type="image/png" sizes="72x72"> 
	<link href="<c:url value='/theme/img/favicon.57x57.png' />" rel="apple-touch-icon" type="image/png"> 
	<link href="<c:url value='/theme/img/favicon.png' />" rel="icon" type="image/png"> 
	<link href="<c:url value='/theme/img/favicon.ico' />" rel="shortcut icon"> 


	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->

	<link rel="stylesheet" href="<c:url value='/theme/css/lib/font-awesome/font-awesome.min.css' />">
	<link rel="stylesheet" href="<c:url value='/theme/css/main.css' />">

</head>
<body>



    <div class="page-center">
        <div class="page-center-in">
            <div class="container-fluid">
				<form class="sign-box" action="<c:url value='/login.htm' />" method="POST">               
                    <div class="sign-avatar">
                        <img src="<c:url value='/theme/img/kmitl_logo.png' />" alt="">
                    </div>
                    <header class="sign-title">Sign In</header>
                    <div class="form-group">
                        <input type="text" name="username" class="form-control" placeholder="Username"/ value="user">
                    </div>
                    <div class="form-group">
                        <input type="password"  name="password" class="form-control" placeholder="Password" value="password" />
                    </div>
                    <div class="form-group">
                        <div class="checkbox float-left">
                            <input type="checkbox" id="signed-in"/>
                            <label for="signed-in">Keep me signed in</label>
                        </div>
                        <div class="float-right reset">
                            <a href="reset-password.html">Reset Password</a>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-rounded">Sign in</button>
                    <p class="sign-note">New to our website? <a href="sign-up.html">Sign up</a></p>
                    <!--<button type="button" class="close">
                        <span aria-hidden="true">&times;</span>
                    </button>-->
                </form>
            </div>
        </div>
    </div><!--.page-center-->


<script src="<c:url value='/theme/js/lib/jquery/jquery.min.js' />"></script>
<script src="<c:url value='/theme/js/lib/tether/tether.min.js' />"></script>
<script src="<c:url value='/theme/js/lib/bootstrap/bootstrap.min.js' />"></script>
<script src="<c:url value='/theme/js/plugins.js' />"></script>
<script src="<c:url value='/theme/js/app.js' />"></script>



</body>
</html>